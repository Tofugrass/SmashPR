

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import pr.smash.Dependencies.Match;
import pr.smash.Dependencies.Methods;
import pr.smash.Dependencies.Player;
import pr.smash.Dependencies.PlayerRanking;
import pr.smash.Dependencies.Tournament;
import pr.smash.Dependencies.TournamentPlacings;

/**
 * Servlet implementation class UndoImport
 */
public class UndoImport extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UndoImport() {
		super();
		// TODO Auto-generated constructor stub
	}
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		//PROGRAM FLOW:
		//0 we redirect the user if the form isn't filled out
		//1 initialize all objects
		//2 if any of them are null, we will create a new one
		//3 import the standings and matches
		//4 update the session objects
		//return the user back to the original page


		//0 we redirect the user if the form isn't filled out	


		Methods method = new Methods();
		// */	
		//1 initialize all objects we need, these are the players, matches, tournaments and standings
		HttpSession session = request.getSession();
		ArrayList<Player> players = method.getSessionPlayers(session);
		ArrayList<Match> includedMatches = method.getSessionIncludedMatches(session);
		ArrayList<Tournament> tournaments = method.getSessionTournaments(session);
		ArrayList<TournamentPlacings> includedPlacings = method.getSessionIncludedPlacings(session);

		//we must find all of the new placings and matches, and exclude them.
		//then we need to delete the new players and new tournament.

		Tournament tournament = tournaments.get(tournaments.size() - 1);
		String matchString;
		if(tournament.getName().contains("/")) {
			matchString = tournament.getName().substring(0, tournament.getName().indexOf("/"));
		}
		else {
			matchString = tournament.getName();
		}
		for(int t = tournaments.size() - 1; t >= 0; t--) {
			tournament = tournaments.get(t);
			//exclude matches
			for(int i = includedMatches.size() - 1; i >= 0 ;i--){
				Match curr = includedMatches.get(i);
				if(curr.getTourney().getName().contains(matchString)){
					//if we find the correct match we must exclude it and update player rankings				
					Player w = curr.getWinner();
					Player l = curr.getLoser();
					int ws = curr.getWinScore();
					int ls = curr.getLoseScore();
					for(int j = 0; j<w.getMatchRankings().size();j++){
						PlayerRanking playerRanking = w.getMatchRankings().get(j);
						if(playerRanking.getPlayer().equals(l)){
							if(playerRanking.getAttempts()==1){
								w.getMatchRankings().remove(j);
							}
							else{
								playerRanking.addAttempt(-1);
								playerRanking.addUpset(1);
								playerRanking.addGameWin(-1*ws);
								playerRanking.addGameLoss(-1*ls);
								for(int k = 0; k < playerRanking.getMatches().size(); k++){
									if(playerRanking.getMatches().get(k).equals(curr)){
										playerRanking.getMatches().remove(k);
										break;
									}
								}
							}
							break;
						}
					}
					ArrayList<Match> matchList = w.getWins();
					matchList.remove(matchList.indexOf(curr));
					for(int j = 0; j<l.getMatchRankings().size();j++){
						PlayerRanking playerRanking = l.getMatchRankings().get(j);
						if(playerRanking.getPlayer().equals(w)){
							if(playerRanking.getAttempts()==1){
								l.getMatchRankings().remove(j);
							}
							else{
								playerRanking.addAttempt(-1);
								playerRanking.addUpset(-1);
								playerRanking.addGameWin(-1*ls);
								playerRanking.addGameLoss(-1*ws);
								//loser and winner dont have the same match
								for(int k = 0; k < playerRanking.getMatches().size(); k++){
									Match newMatch = playerRanking.getMatches().get(k);
									if(newMatch.getWinner().equals(w) && newMatch.getLoser().equals(l)&&newMatch.getWinScore() == ws && newMatch.getLoseScore() == ls && newMatch.getTourney().equals( tournament.getName())){
										playerRanking.getMatches().remove(k);
										break;
									}
								}
							}
							break;
						}
					}
					includedMatches.remove(i);
					matchList = l.getLosses();
					matchList.remove(matchList.indexOf(curr));
				}
				/*else {
					break;
				}	*/
			}
			for(int z = includedPlacings.size() - 1; z >= 0; z--) {
				TournamentPlacings currPlacing = includedPlacings.get(z);
				if(currPlacing.getTournament().contains(matchString)) {
					Player player = currPlacing.getPlayer();
					for(int i = 0; i < player.getPlacings().size(); i++){
						if(currPlacing.getTournament().contains(matchString)){
							includedPlacings.remove(currPlacing);

							for(int j = 0; j < currPlacing.getPlayers().size(); j++){
								Player other = currPlacing.getPlayers().get(j);

								//below for loop updates player A's placing ranking
								for(int k = 0; k < player.getPlacingRankings().size(); k++){
									PlayerRanking otherRanking = player.getPlacingRankings().get(k);

									//need to update player and other players placing ranking
									if(otherRanking.getPlayer().equals(other)){
										if(otherRanking.getAttempts() == 1){
											player.getPlacingRankings().remove(otherRanking);
										}
										else{
											otherRanking.addAttempt(-1);
											int outplaced = currPlacing.didPlayerOutplace(player, other);
											otherRanking.addUpset(outplaced);
										}
										break;
									}
								}

								//below for loop updates player B's placing ranking
								for(int k = 0; k < other.getPlacingRankings().size(); k++){
									PlayerRanking playerRanking = other.getPlacingRankings().get(k);

									if(playerRanking.getPlayer().equals(player)){
										if(playerRanking.getAttempts() == 1){
											other.getPlacingRankings().remove(playerRanking);
										}
										else{
											playerRanking.addAttempt(-1);
											int outplaced = currPlacing.didPlayerOutplace(player, other);
											playerRanking.addUpset(-1*outplaced);
										}
										break;
									}
								}
							}
							player.getPlacings().remove(currPlacing);
							break;
						}
					}
				}
				/*else {
					break;
				}*/
			}
			tournaments.remove(t);
		}
		for(int i = players.size() - 1; i >= 0; i--) {
			Player player = players.get(i);
			if(player.getPlacings().size() == 0 && player.getLosses().size() == 0 && player.getPlacings().size() == 0 && player.getMatchRankings().size() == 0) {
				players.remove(i);
			}
		}
		session.setAttribute("canUndo", "false");
		method.alertAndRedirectError("Undo was successful", request, response);
		return;



		/*		ArrayList<Player> oldPlayers = method.getSessionOldPlayers(session);
		ArrayList<Match> oldIncludedMatches = method.getSessionOldIncludedMatches(session);
		ArrayList<Tournament> oldTournaments = method.getSessionOldTournaments(session);
		ArrayList<TournamentPlacings> oldIncludedPlacings = method.getSessionOldIncludedPlacings(session);

		for(int i = oldPlayers.size() - 1; i >= 0 ; i--) {
			if(oldPlayers.get(i) != null) {
				players.set(i, oldPlayers.get(i));
			}
		}
		while(oldPlayers.size() != players.size()) {
			players.remove(players.size()-1);
		}
		for(int i = oldIncludedMatches.size() - 1; i >= 0 ; i--) {
			if(oldIncludedMatches.get(i) != null) {
				includedMatches.set(i, oldIncludedMatches.get(i));
			}
		}
		while(oldIncludedMatches.size() != includedMatches.size()) {
			includedMatches.remove(includedMatches.size()-1);
		}
		for(int i = oldTournaments.size() - 1; i >= 0 ; i--) {
			if(oldTournaments.get(i) != null) {
				tournaments.set(i, oldTournaments.get(i));
			}
		}
		while(oldTournaments.size() != tournaments.size()) {
			tournaments.remove(tournaments.size()-1);
		}
		for(int i = oldIncludedPlacings.size() - 1; i >= 0 ; i--) {
			if(oldIncludedPlacings.get(i) != null) {
				includedPlacings.set(i, oldIncludedPlacings.get(i));
			}
		}
		while(oldIncludedPlacings.size() != includedPlacings.size()) {
			includedPlacings.remove(includedPlacings.size()-1);
		}
		System.out.println(tournaments.size());
		System.out.println(oldTournaments.size());
		for(int i = 0; i < players.size(); i++) {
			if (players.get(i).getName().equals("ORLY")) {
				Player player = players.get(i);
				for(int j = 0; j < player.getPlacings().size(); j++) {
					System.out.println(player.getPlacings().get(j).getTournament());
				}
				player = oldPlayers.get(i);
				for(int j = 0; j < player.getPlacings().size(); j++) {
					System.out.println(player.getPlacings().get(j).getTournament());
				}
				break;
			}
		}
		method.disableUndo(session);

		session.removeAttribute("players");
		session.removeAttribute("includedMatches");
		session.removeAttribute("tournaments");
		session.removeAttribute("includedPlacings");

		session.setAttribute("players", players);
		session.setAttribute("includedMatches", includedMatches);
		session.setAttribute("tournaments", tournaments);
		session.setAttribute("includedPlacings", includedPlacings);
		method.alertAndRedirectError("Undo was successful", request, response);
		return;
		 */}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
