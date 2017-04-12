import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import pr.smash.Dependencies.Methods;
import pr.smash.Dependencies.Player;
import pr.smash.Dependencies.Match;
import pr.smash.Dependencies.Tournament;
import pr.smash.Dependencies.TournamentPlacings;
import pr.smash.Dependencies.PlayerRanking;
import pr.smash.Dependencies.smashGGPlayer;
import pr.smash.Dependencies.SortablePlayerList;


/**
 * Servlet implementation class ImportFromUrl
 */
public class ImportFromUrl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor. 
	 */
	public ImportFromUrl() {
	}

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Methods method = new Methods();
		//PROGRAM FLOW:
		//0 we redirect the user if the form isn't filled out
		//1 initialize all objects
		//2 if any of them are null, we will create a new one
		//3 import the standings and matches
		//4 update the session objects
		//return the user back to the original page


		//0 we redirect the user if the form isn't filled out

		if(request.getParameter("importUrl") == null || request.getParameter("importUrl").equals("")) {
			method.alertAndRedirectError("Please enter the url you would like to import from", request, response);
			return;	
		}
		else {

			//1 initialize all objects we need, these are the players, matches, tournaments and standings
			HttpSession session = request.getSession();
			ArrayList<Player> players = method.getSessionPlayers(session);
			ArrayList<Match> includedMatches = method.getSessionIncludedMatches(session);
			ArrayList<Tournament> tournaments = method.getSessionTournaments(session);
			ArrayList<TournamentPlacings> includedPlacings = method.getSessionIncludedPlacings(session);

			//3 import the standings and matches
			String url = request.getParameter("importUrl");
			if(url.contains("challonge")){
				url = url.replace("https", "http");
				url = url.replace(".svg", "");
				if(url.contains("/standings")){
					url = url.substring(0, url.indexOf("/standings"));
				}
				if(url.contains("www.")){
					url = url.replaceAll("www.", "");
				}
				String tourneyName;
				if(!url.contains("http://challonge.com")){
					url = url.substring(url.indexOf("//")+2);
					tourneyName = url.substring(0, url.indexOf("."))+"-"+url.substring(url.lastIndexOf("/")+1);
				}else{
					tourneyName = url.substring(url.lastIndexOf("/")+1);
				}
				for(int i = 0; i < tournaments.size(); i++){
					if(tournaments.get(i).getName().equals(tourneyName)){
						method.alertAndRedirectError("Tournament already entered", request, response);
						return;
					}
				}

				Tournament newTourney = new Tournament(tourneyName);
				JSONObject json = null;
				try {
					json = method.processChallonge(tourneyName);
				}catch(Exception e) {
					method.alertAndRedirectError("Problem entering tournament, please report this error!", request, response);
					return;
				}	
				json = (JSONObject) json.get("tournament");
				if(json==null) {
					method.alertAndRedirectError("Hmmm Challonge couldn't find this tournament, please report this error!", request, response);
					return;
				}

				JSONArray participants = (JSONArray) json.get("participants");
				ArrayList<smashGGPlayer> ggPlayers = new  ArrayList<smashGGPlayer>();
				Player standings[] = new Player[participants.size()];
				boolean fixedUrl = false;
				String pageText = "";
				String standArr[] = null;
				for(int i = 0; i < participants.size(); i++){
					JSONObject player =(JSONObject) ((JSONObject)participants.get(i)).get("participant");
					JSONArray group_player_ids = (JSONArray) player.get("group_player_ids");
					for(int j = 0; j < group_player_ids.size(); j++){
						method.addSmashGGPlayer(players, ggPlayers,  (Long)(( (JSONArray) player.get("group_player_ids")).get(j)), method.trimSponsor((String)player.get("display_name"))).getPlayer();
					}
					method.addSmashGGPlayer(players, ggPlayers,  (Long) player.get("id"), method.trimSponsor((String)player.get("display_name")) ).getPlayer();
					int index;
					if(player.get("final_rank")==null){
						if(group_player_ids.size()!=0){
							index = participants.size()-1;
							while(standings[index] != null){
								index--;
							}
							try {
								standings[index] = method.getSmashGGPlayerFromId((Long) player.get("id"), ggPlayers).getPlayer();
							}catch(Exception e) {
								method.alertAndRedirectError("There must have been a weird character", request, response);
								return;
							}

						}
						else{
							//here we get the actual standings from the standings url. 
							if(!fixedUrl){
								url = "http://"+ url +"/standings";
								fixedUrl = true;
								pageText = method.getPageTextFromURLString(url);
								standArr = pageText.split("<span>");
							}
						}
					}
					else{
						index = ((Long) player.get("final_rank")).intValue() - 1;
						while(standings[index] != null){
							index++;
						}
						try {
							standings[index] = method.getSmashGGPlayerFromId((Long) player.get("id"), ggPlayers).getPlayer();
						}catch(Exception e) {
							method.alertAndRedirectError("There must have been a weird character", request, response);
							return;
						}
					}
				}
				if(fixedUrl){
					for(int j =1; j <standArr.length; j++){
						standArr[j] = standArr[j].substring(0, standArr[j].indexOf("</span>"));
						int index = j-1;
						while(standings[index] != null){
							index++;
						}
						try {
							standings[index] = method.getPlayerFromName(method.trimSponsor(standArr[j]), players);
						}catch(Exception e) {
							method.alertAndRedirectError("There must have been a weird character", request, response);
							return;
						}
					}
				}
				for(int i = 0; i<standings.length; i++){
					newTourney.addResults(standings[i]);
				}
				JSONArray matches = (JSONArray) json.get("matches");
				for(int i = 0; i < matches.size(); i++){
					try{
						JSONObject currMatch = (JSONObject)((JSONObject) matches.get(i)).get("match");
						if(((String)currMatch.get("state")).equals("complete")){
							int aScore = 0;
							int bScore = 0;
							String scores_csv = (String)currMatch.get("scores_csv");
							if(scores_csv.contains(",")){
								String scores[] = scores_csv.split(",");
								for(int j = 0; j < scores.length; j++){
									aScore += Integer.parseInt(scores[j].substring(0, scores[j].indexOf("-")))  ;
									bScore += Integer.parseInt(scores[j].substring(scores[j].indexOf("-")+1) )  ;
								}
							}
							else{
								aScore = Integer.parseInt(scores_csv.substring(0, scores_csv.indexOf("-")))  ;
								bScore = Integer.parseInt(scores_csv.substring(scores_csv.indexOf("-")+1))  ;
							}
							if(aScore > -1 && bScore >-1){
								Player playerA = method.getSmashGGPlayerFromId((Long)currMatch.get("player1_id"), ggPlayers).getPlayer();
								Player playerB = method.getSmashGGPlayerFromId((Long)currMatch.get("player2_id"), ggPlayers).getPlayer();
								Match newMatch;
								if(aScore > bScore)
									newMatch = new Match(playerA, aScore, bScore, playerB, newTourney.getName());
								else
									newMatch = new Match(playerB, bScore,  aScore,  playerA, newTourney.getName());
								method.enterMatch(newMatch, includedMatches);
							}
						}
					}catch(Exception e){
						//Here a dq is ignored
					}
				}
				for(int i = 0; i<newTourney.getResults().size(); i++){
					method.enterPlacing(newTourney.getResults().get(i), newTourney.getName(), i,  newTourney.getResults(), includedPlacings);
				}
				tournaments.add(newTourney);
				method.updatePlacingRankings(newTourney);
			}
			else if(url.contains("smash.gg")){
				String game = request.getParameter("game");
				game = request.getParameter("game");
				if(game.equals("Select a Game")) {
					method.alertAndRedirectError("Please select a game when using SmashGG", request, response);
					//session.setAttribute("saveURL", url);
					return;
				}
				boolean includePools = request.getParameter("radio").equals("include");
				ArrayList<smashGGPlayer> smashGGPlayers = new ArrayList<smashGGPlayer>();
				String tournament = url.substring(url.indexOf("tournament")+"tournament".length()+1);
				if(tournament.contains("/"))
					tournament= tournament.substring(0, tournament.indexOf("/"));
				for(int i = 0; i < tournaments.size(); i++){
					if(tournaments.get(i).getName().equals(tournament+"/0")){
						method.alertAndRedirectError("Tournament already entered", request, response);
						return;
					}
				}
				/**
				if(total_count % 64 != 0) pages++;
				Long finalPlacement = (long) 0;
				Tournament mainBracket = new Tournament(tournament);
				Player[] mainBracketPlayers = new Player[total_count.intValue()];
				for(int i = 1; i < pages+1; i++){
					apiURL = "https://api.smash.gg/tournament/"+tournament+"/event/"+game+"/standings?entityType=event&expand[]=entrants&page="+i+"&per_page=64";
					pageText = method.getPageTextFromURLString(apiURL);

					JSONParser parser = new JSONParser();
					JSONObject wrapper = null;
					try {
						wrapper = (JSONObject) parser.parse(pageText);
					} catch (ParseException e) {
						method.alertAndRedirectError("Hm there was an error", request, response);
						//e.printStackTrace();
						return;
					}
					JSONObject items = (JSONObject) wrapper.get("items");
					JSONObject entities = (JSONObject) items.get("entities");
					JSONArray entrants = (JSONArray) entities.get("entrants");
					for(int j = 0; j < entrants.size(); j++){
						JSONObject curr_entrant = (JSONObject) entrants.get(j);
						finalPlacement = (Long) curr_entrant.get("finalPlacement");
						try {
							int index = finalPlacement.intValue()-1;
							if(index > 0){
									while(mainBracketPlayers[index] != null){
										index++;
										if(index >= mainBracketPlayers.length){
											index--;
												//so if smash gg is doing the weird glitch where they dont actually fill the entire bracket. 
												while(mainBracketPlayers[index] != null){
													index--;
												}//at the end of this loop, we know the previous player is there, so we can simply place the player at the current index
											break;
										}
									}
								//}
							}
							mainBracketPlayers[index] = method.addSmashGGPlayer( players, smashGGPlayers, (Long) curr_entrant.get("id"),  method.trimSponsor((String) curr_entrant.get("name")) ).getPlayer();
						}catch(Exception e) {
							System.out.println(pageText);
						}
					}
				}
				for(int j = 0; j < mainBracketPlayers.length; j++){
					mainBracket.addResults(mainBracketPlayers[j]);
				}
				tournaments.add(mainBracket);
				for(int i = 0; i<mainBracket.getResults().size(); i++){
					method.enterPlacing(mainBracket.getResults().get(i), mainBracket.getName(), i,  mainBracket.getResults(), includedPlacings);
				}
				method.updatePlacingRankings(mainBracket);
				//*/
				String apiURL = "https://api.smash.gg/tournament/"+tournament+"/event/"+game+"?expand[]=groups";
				String pageText = method.getPageTextFromURLString(apiURL);
				JSONParser	parser = new JSONParser();
				JSONObject wrapper = null;
				try {
					wrapper = (JSONObject) parser.parse(pageText);
				} catch (ParseException e) {
					method.alertAndRedirectError("There must have been a weird character", request, response);
					e.printStackTrace();
					return;
				}
				boolean started = false;
				JSONObject entities = (JSONObject) wrapper.get("entities");
				if(entities == null) {
					method.alertAndRedirectError("This game was not at this event", request, response);
					return;
				}
				JSONArray groups = (JSONArray) entities.get("groups");
				for(int i = groups.size() - 1; i >= 0 ; i--){
					
					JSONObject curr = (JSONObject) groups.get(i);
					Long phase_group = (Long) curr.get("id");
					String new_request = "https://api.smash.gg/phase_group/"+phase_group+"?expand[]=sets&expand[]=entrants&expand[]=standings&expand[]=seeds";

					pageText = method.getPageTextFromURLString(new_request);

					JSONObject group_wrapper = null;
					try {
						group_wrapper = (JSONObject) parser.parse(pageText);
					} catch (ParseException e1) {
						method.alertAndRedirectError("There was an error", request, response);
						//e1.printStackTrace();
						return;
					}
					
					JSONObject group_entities = (JSONObject) group_wrapper.get("entities");
					JSONObject curr_group = (JSONObject) group_entities.get("groups");

					String stringIdentifier = (String) curr_group.get("displayIdentifier");
					
					//if(i == 144) System.out.println(pageText);
					if(includePools || stringIdentifier.equals("1")) {
						if(!includePools) started = true;
						//if(stringIdentifier.equals("1")) System.out.println(123);
						JSONArray curr_entrants = (JSONArray) group_entities.get("entrants");
					//	boolean mustUsePlacings = false;
						//this loop initializes player list
						for(int j = 0; j < curr_entrants.size(); j++){
							JSONObject entrant = (JSONObject) curr_entrants.get(j);
							/**
							Long curr_placement = (Long) entrant.get("finalPlacement");
							if(curr_placement == null || curr_placement > finalPlacement){
								mustUsePlacings = true;
							}
							//*/
							method.addSmashGGPlayer(players, smashGGPlayers, (Long) entrant.get("id"), method.trimSponsor((String)entrant.get("name")));
						}
				//		if(mustUsePlacings){
							Tournament newTourney = new Tournament(tournament+"/"+i);
							
							Player[] tempPlayers = new Player[curr_entrants.size()];
							for(int j = 0; j < curr_entrants.size(); j++){
								JSONObject entrant = (JSONObject) curr_entrants.get(j);
								String entrantName = method.trimSponsor((String) entrant.get("name"));
								Long entrantID = (Long) entrant.get("id");
								Long curr_placement =  (long) 0;
								//**
								JSONArray standings = (JSONArray) group_entities.get("standings");
								for(int k = 0; k < standings.size(); k++){
									JSONObject standing = (JSONObject) standings.get(k);
									if((Long) standing.get("entrantId") - entrantID == 0){
										curr_placement = (Long) standing.get("pendingPlacement");
										break;
									}
								}
								//*/
								int index = curr_placement.intValue()-1;
								/**
								while(tempPlayers[index] != null){
									index++;
									if(index >= tempPlayers.length){
										index--;
										//so if smash gg is doing the weird glitch where they dont actually fill the entire bracket. 
										while(tempPlayers[index] != null){
											index--;
										}//at the end of this loop, we know the previous player is there, so we can simply place the player at the current index
										break;
									}

									//}
								}			
								//*/			
								tempPlayers[index] = method.addSmashGGPlayer(players, smashGGPlayers, entrantID,  entrantName).getPlayer();
							}
							for(int j = 0; j < curr_entrants.size(); j++){
								newTourney.addResults(tempPlayers[j]);
							}
							for(int j = 0; j < newTourney.getResults().size(); j++){
								method.enterPlacing(newTourney.getResults().get(j), newTourney.getName(), j,  newTourney.getResults(), includedPlacings);
							}
							tournaments.add(newTourney);
							method.updatePlacingRankings(newTourney);
					//	}
						JSONArray group_sets = (JSONArray) group_entities.get("sets");
						for(int j = 0; j < group_sets.size(); j++){
							JSONObject set = (JSONObject) group_sets.get(j);
							Long wID = (Long) set.get("winnerId");
							Long lID = (Long) set.get("loserId");
							if((Long) set.get("entrant1Score") != null && wID != null && lID != null && (Long) set.get("entrant2Score") != null){
								int aScore = ((Long) set.get("entrant1Score")).intValue();
								int bScore = ((Long) set.get("entrant2Score")).intValue();
								Match match;
								try{
									if(aScore >=0 &&  bScore >= 0) {
										if(aScore > bScore)
											match = new Match(method.getSmashGGPlayerFromId(wID, smashGGPlayers).getPlayer(), aScore, bScore, method.getSmashGGPlayerFromId(lID, smashGGPlayers).getPlayer(), tournament+"/"+i);
										else
											match = new Match(method.getSmashGGPlayerFromId(wID, smashGGPlayers).getPlayer(), bScore, aScore, method.getSmashGGPlayerFromId(lID, smashGGPlayers).getPlayer(), tournament+"/"+i);
										method.enterMatch(match, includedMatches);
									}
								}catch(Exception e){
									method.alertAndRedirectError("There was an error", request, response);
									return;
								}
							}
						}
					}
					else {
						if(started) {
							break;
						}
					}
				}
			}else {
				method.alertAndRedirectError("Please provide a valid url", request, response);
				return;
			}
			//4 update the session objects
			session.setAttribute("players", players);
			session.setAttribute("includedMatches", includedMatches);
			session.setAttribute("tournaments", tournaments);
			session.setAttribute("includedPlacings", includedPlacings);
			session.setAttribute("pr", new SortablePlayerList(players, 2));
			method.alertAndRedirectError("Everything imported successfully", request, response);
			return;
		}
	}
}
