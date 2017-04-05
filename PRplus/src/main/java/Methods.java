import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class Methods {
	public Methods() {}
	
	
	public void alertAndRedirect(String message, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		//TODO: ALERT THE USER	
		request.getRequestDispatcher("/home.jsp").forward(request, response);
	}
	public smashGGPlayer getSmashGGPlayerFromId(Long id, ArrayList<smashGGPlayer> smashGGPlayers) throws Exception{
		for(int i = 0; i < smashGGPlayers.size(); i++){
			if(smashGGPlayers.get(i).getId() - id == 0){
				return smashGGPlayers.get(i);
			}
		}
		throw new Exception("There must have been a weird character");
	}
	public String trimSponsor(String name){
		name = name.substring(name.lastIndexOf("|")+1).trim().toUpperCase();
		name = name.replaceAll("\\s+","");
		name = name.replaceAll("-","");
		name = name.replaceAll("'","");
		//name = name.replaceAll("!","");
		//name = name.replaceAll("?","");
		name = name.replaceAll("&#39;","");
		name = name.replaceAll("&QUOT;", "\"");
		name = name.replaceAll("&AMP;", "&");
		name = name.replaceAll("INVITATIONPENDING", "");
		if(name.contains("()")){
			name = name.substring(0, name.indexOf("()"));
		}
		/**
		if(name.startsWith("VWS")){
			name = name.substring(name.indexOf("VWS")+3);
		}
		else if(name.startsWith("GHQ")){
			name = name.substring(name.indexOf("GHQ")+3);
		}
		else if(name.startsWith("IX")){
			name = name.substring(name.indexOf("IX")+2);
		}
		else if(name.startsWith("INC")){
			name = name.substring(name.indexOf("INC")+3);
		}
		else if(name.startsWith("PNDA")){
			name = name.substring(name.indexOf("PNDA")+4);
		}
		else if(name.startsWith("WIT")){
			name = name.substring(name.indexOf("WIT")+3);
		}
		else if(name.startsWith("UNH")){
			name = name.substring(name.indexOf("UNH")+3);
		}
		else if(name.startsWith("GARBAGE")){
			name = name.substring(name.indexOf("GARBAGE")+7);
		}
		else if(name.startsWith("MUK.LYFE")){
			name = name.substring(name.indexOf("MUK.LYFE")+8);
		}
		else if(name.startsWith("GLS")){
			name = name.substring(name.indexOf("GLS")+3);
		}
		else if(name.startsWith("TSI")){
			name = name.substring(name.indexOf("TSI")+3);
		}
		else if(name.startsWith("PRISM")){
			name = name.substring(name.indexOf("PRISM")+5);
		}
		else if(name.startsWith("M150")){
			name = name.substring(name.indexOf("M150")+4);
		}
		else if(name.startsWith("EHG")){
			name = name.substring(name.indexOf("EHG")+3);
		}
		else if(name.startsWith("OES")){
			name = name.substring(name.indexOf("OES")+3);
		}
		else if(name.startsWith("MUK LYFE")){
			name = name.substring(name.indexOf("MUK LYFE")+9);
		}
		else if(name.startsWith("URT")){
			name = name.substring(name.indexOf("URT")+3);
		}
		else if(name.startsWith("ME")){
			name = name.substring(name.indexOf("ME")+2);
		}
		 */
		return name;
	}
	public smashGGPlayer addSmashGGPlayer(ArrayList<Player> players, ArrayList<smashGGPlayer> ggPlayers, Long id, String name ){
		boolean playerExists = false;
		for(int i = 0; i < ggPlayers.size(); i++){
			if(ggPlayers.get(i).getId()==id){
				return ggPlayers.get(i);
			}
		}
		if(!playerExists){
			smashGGPlayer newPlayer = new smashGGPlayer(id, addPlayer(players, name));
			ggPlayers.add(newPlayer);
			return newPlayer;
		}
		return null;
	}
	public Player addPlayer(ArrayList<Player> players, String name){
		Player newPlayer = null;
		boolean playerExists = false;
		for(int i = 0; i < players.size(); i++){
			if(players.get(i).getName().equals(name)){
				newPlayer = players.get(i); 
				playerExists = true;
			}
		}
		if(!playerExists){
			newPlayer = new Player(name);
			players.add(newPlayer);
		}
		return newPlayer;
	}
	public String getPageTextFromURLString(String request) throws IOException {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet getRequest = new HttpGet(request);
		getRequest.setHeader("Content-Type", "application/x-www-form-urlencoded");
		getRequest.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.98 Safari/537.36");
		CloseableHttpResponse response;
		response = client.execute(getRequest);
		String responseString = EntityUtils.toString(response.getEntity());
		//TODO: use this for debugging
		//	System.out.println(responseString);
		client.close();
		response.close();
		return responseString;
	}
	public JSONObject processChallonge(String tourneyString) throws ClientProtocolException, IOException, ParseException{
		String api_key = "eHZ8ALetEIB8d67RuqoK5whEgcZENEy1KCKO1X4y";
		String url = "https://api.challonge.com/v1/tournaments/"+tourneyString+".json?api_key="+api_key+"&include_participants=1&include_matches=1";
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet getRequest = new HttpGet(url);
		getRequest.setHeader("Content-Type", "application/x-www-form-urlencoded");
		getRequest.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.98 Safari/537.36");
		CloseableHttpResponse response;
		response = client.execute(getRequest);
		String responseString = EntityUtils.toString(response.getEntity());
		//TODO: use this for debugging
		//System.out.println(responseString);
		client.close();
		response.close();
		return((JSONObject) new JSONParser().parse(responseString));
	}


	public Player getPlayerFromName(String name, ArrayList<Player> players) throws Exception{
		for (int i = 0; i < players.size(); i++)
			if(players.get(i).getName().equals(name)) return players.get(i);
		//System.out.println(name);
		throw new Exception("There must have been a weird character");
	}


	public void enterMatch(Match match, ArrayList<Match> matchList){
		Player player = match.getWinner();
		boolean rankingExisted = false;
		for(int i = 0; i < player.getMatchRankings().size(); i++){
			PlayerRanking playerRanking=player.getMatchRankings().get(i);
			if(playerRanking.getPlayer().equals(match.getLoser())){//so if the other player is the loser
				rankingExisted = true;
				playerRanking.addAttempt(1);
				playerRanking.addUpset(-1);
				playerRanking.addGameWin(match.getWinScore());
				playerRanking.addGameLoss(match.getLoseScore());
				playerRanking.addMatch(match);
			}
		}
		if(!rankingExisted){
			//if the ranking didnt exist previously, we must make a new one
			PlayerRanking playerRanking = new PlayerRanking(match.getLoser());
			playerRanking.addAttempt(1);
			playerRanking.addUpset(-1);
			playerRanking.addGameWin(match.getWinScore());
			playerRanking.addGameLoss(match.getLoseScore());
			player.addMatchRanking(playerRanking);
			playerRanking.addMatch(match);
		}
		player.addWin(match);
		rankingExisted = false;
		player = match.getLoser();
		for(int i = 0; i < player.getMatchRankings().size(); i++){

			PlayerRanking playerRanking=player.getMatchRankings().get(i);
			if(playerRanking.getPlayer().equals(match.getWinner())){//so if this player is the Winner
				rankingExisted = true;
				playerRanking.addAttempt(1);
				playerRanking.addUpset(1);
				playerRanking.addGameLoss(match.getWinScore());
				playerRanking.addGameWin(match.getLoseScore());
				playerRanking.addMatch(match);
			}
		}
		if(!rankingExisted){
			//if the ranking didnt exist previously, we must make a new one
			PlayerRanking playerRanking = new PlayerRanking(match.getWinner());
			playerRanking.addAttempt(1);
			playerRanking.addUpset(1);
			playerRanking.addGameLoss(match.getWinScore());
			playerRanking.addGameWin(match.getLoseScore());
			player.addMatchRanking(playerRanking);
			playerRanking.addMatch(match);
		}
		player.addLoss(match);
		matchList.add(match);
	}


	public void enterPlacing(Player player, String name, int placing, ArrayList<Player> players, ArrayList<TournamentPlacings> placingsList){
		int place = placing;
		if (placing >= 1088)
			place = 1089;
		else if (placing >= 832)
			place = 833;
		else if (placing >= 576)
			place = 577;
		else if (placing >= 448)
			place = 449;
		else if (placing >= 320)
			place = 321;
		else if (placing >= 256)
			place = 257;
		else if (placing >= 192)
			place = 193;
		else if (placing >= 128)
			place = 129;
		else if (placing >= 96)
			place = 97;
		else if (placing >= 64)
			place = 65;
		else if(placing >= 48)
			place = 49;
		else if(placing >= 32)
			place = 33;
		else if(placing >= 24)
			place = 25;
		else if(placing >= 16)
			place = 17;
		else if(placing >= 12)
			place = 13;
		else if(placing >= 8)
			place = 9;
		else if(placing >= 6)
			place = 7;
		else if(placing >= 4)
			place = 5;
		else if(placing == 3)
			place = 4;
		else if(placing == 2)
			place = 3;
		else if(placing == 1)
			place = 2;
		else if(placing == 0)
			place = 1;
		TournamentPlacings result = new TournamentPlacings(player,name, place, players);
		placingsList.add(result);
		player.addPlacing(result);
	}
	public void updatePlacingRankings(Tournament newTourney) {
		for(int i = 0; i<newTourney.getResults().size(); i++){
			for(int j = 0; j < newTourney.getResults().size(); j++){
				Player playerA = newTourney.getResults().get(i);
				int aPlacing = 0;
				Player playerB = newTourney.getResults().get(j);
				int bPlacing = 0;
				if(!playerA.equals(playerB)){
					for(int k = 0; k < playerA.getPlacings().size(); k++){
						if(playerA.getPlacings().get(k).getTournament().equals(newTourney.getName())){
							aPlacing = playerA.getPlacings().get(k).getPlacing();
						}
					}
					for(int k = 0; k < playerB.getPlacings().size(); k++){
						if(playerB.getPlacings().get(k).getTournament().equals(newTourney.getName())){
							bPlacing = playerB.getPlacings().get(k).getPlacing();
						}
					}
					boolean exists = false;
					for(int k = 0; k < playerA.getPlacingRankings().size(); k++){
						if(playerA.getPlacingRankings().get(k).getPlayer().equals(playerB)){
							exists = true;
							playerA.getPlacingRankings().get(k).addAttempt(1);
							if(aPlacing > bPlacing){
								playerA.getPlacingRankings().get(k).addUpset(1);
							}
							else if(aPlacing < bPlacing){
								playerA.getPlacingRankings().get(k).addUpset(-1);
							}
						}
					}
					if(!exists){
						PlayerRanking ranking = new PlayerRanking(playerB);
						ranking.addAttempt(1);
						if(aPlacing > bPlacing){
							ranking.addUpset(1);
						}
						else if(aPlacing < bPlacing){
							ranking.addUpset(-1);
						}
						playerA.addPlacingRanking(ranking);
					}
				}
			}
		}
	}
}
