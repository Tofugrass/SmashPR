

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
import pr.smash.Dependencies.Tournament;
import pr.smash.Dependencies.TournamentPlacings;
import pr.smash.Dependencies.PlayerRanking;

/**
 * Servlet implementation class ExcludeMatch
 */
public class ExcludeMatch extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ExcludeMatch() {
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
		if(request.getParameter("winner")==null || request.getParameter("loser")==null || request.getParameter("wScore")==null || request.getParameter("lScore")==null || request.getParameter("event")==null ){
			method.alertAndRedirectError("Please enter all fields", request, response);
			return;
		}
		else {
			ArrayList<Match> includedMatches = method.getSessionIncludedMatches(session);
			ArrayList<Match> excludedMatches = method.getSessionExcludedMatches(session);
			ArrayList<Tournament> tournaments = method.getSessionTournaments(session);
			Player w = null;
			Player l = null;
			try{
				w =  method.getPlayerFromName(method.trimSponsor(request.getParameter("winner")), players);
				l =  method.getPlayerFromName(method.trimSponsor(request.getParameter("loser")), players);
			}catch(Exception e) {
				method.alertAndRedirectError("Players not found", request, response);
				return;
			}
			int ws = 0;
			int ls = 0;
			try {
				ws = Integer.parseInt(request.getParameter("wScore").trim());
				ls = Integer.parseInt(request.getParameter("lScore").trim());
			}
			catch(Exception e) {
				method.alertAndRedirectError("Please enter a valid score", request, response);
				return;
			}
			if(request.getParameter("radio").equals("exclude")) {				
				boolean found = false;
				for(int i = 0; i < includedMatches.size();i++){
					Match curr = includedMatches.get(i);
					if(curr.getWinner().equals(w) && curr.getLoser().equals(l) && curr.getWinScore()==ws && curr.getLoseScore()==ls&&curr.getTourney().equalsIgnoreCase(request.getParameter("event").trim())){
						//if we find the correct match we must exclude it and update player rankings				
						for(int j = 0; j<w.getMatchRankings().size();j++){
							PlayerRanking playerRanking = w.getMatchRankings().get(j);
							if(playerRanking.getPlayer().equals(l)){
								found = true;
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
										if(newMatch.getWinner().equals(w) && newMatch.getLoser().equals(l)&&newMatch.getWinScore() == ws && newMatch.getLoseScore() == ls && newMatch.getTourney().equalsIgnoreCase(request.getParameter("event").trim())){
											playerRanking.getMatches().remove(k);
											break;
										}
									}
								}
								break;
							}
						}
						excludedMatches.add(curr);
						includedMatches.remove(i);
						//textArea_1.append("Match excluded successfully");
						matchList = l.getLosses();
						matchList.remove(matchList.indexOf(curr));

						method.alertAndRedirectError("Match excluded successfully", request, response);
						return;
					}
				}

				if(!found) {
					method.alertAndRedirectError("Match not found", request, response);
					return;
				}

			}
			else {
				try{
					boolean found = false;
					for(int i = 0; i < excludedMatches.size();i++){
						Match curr = excludedMatches.get(i);
						if(curr.getWinner().equals(w) && curr.getLoser().equals(l) && curr.getWinScore()==ws && curr.getLoseScore()==ls&&curr.getTourney().equalsIgnoreCase(request.getParameter("event").trim())){
							//if we find the correct match we must exclude it and update player rankings
							found = true;
							excludedMatches.remove(i);
							method.enterMatch(curr, includedMatches);
							method.alertAndRedirectError("Match included successfully", request, response);
							return;
						}
					}
					if(!found) {
						method.alertAndRedirectError("Match not found", request, response);
						return;
					}
				}catch(Exception e){ 
					method.alertAndRedirectError("Match not found", request, response);
					return;
				}
			}

		}

		//if(!found) JOptionPane.showMessageDialog(null, "Match not found"); //*/

	}






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
