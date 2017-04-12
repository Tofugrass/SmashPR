

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
import pr.smash.Dependencies.TournamentPlacings;
import pr.smash.Dependencies.SortablePlayerList;
import pr.smash.Dependencies.PlayerRanking;

/**
 * Servlet implementation class MergePlayers
 */
public class MergePlayers extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MergePlayers() {
		super();
		// TODO Auto-generated constructor stub
	}
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Methods method = new Methods();
		if(request.getParameter("playerA") == null || request.getParameter("playerA").equals("") || request.getParameter("playerB") == null || request.getParameter("playerB").equals("") ) {
			method.alertAndRedirectError("Please enter two valid player tags", request, response);
			return;	
		}

		HttpSession session = request.getSession();
		ArrayList<Player> players = method.getSessionPlayers(session);
		ArrayList<Match> excludedMatches = method.getSessionExcludedMatches(session);
		ArrayList<TournamentPlacings> excludedPlacings = method.getSessionExcludedPlacings(session);
		try{
			Player playerA = null; 
			Player playerB = null;
			try{
				playerA = method.getPlayerFromName(method.trimSponsor(request.getParameter("playerA").trim()), players);
				playerB = method.getPlayerFromName(method.trimSponsor(request.getParameter("playerB").trim()), players);
			}catch(Exception e){
				method.alertAndRedirectError("Tags not recognized", request, response);
				return;	
			}
			for(int i = 0; i < playerA.getPlacings().size(); i++){
				TournamentPlacings currPlayerAplacing = playerA.getPlacings().get(i);
				int index = currPlayerAplacing.getPlayers().indexOf(playerA);
				currPlayerAplacing.getPlayers().set(index, playerB);
				currPlayerAplacing.setPlayer(playerB);
				playerB.addPlacing(currPlayerAplacing);
			}
			for(int i = 0; i < playerA.getPlacingRankings().size(); i++){
				PlayerRanking currPlayerAplacingRanking = playerA.getPlacingRankings().get(i);
				Player playerAother = currPlayerAplacingRanking.getPlayer();
				boolean found = false;
				for(int j = 0; j < playerB.getPlacingRankings().size(); j++){
					PlayerRanking currPlayerBplacingRanking = playerB.getPlacingRankings().get(j);
					Player playerBother = currPlayerBplacingRanking.getPlayer();
					if(playerAother == playerBother){
						found = true;
						currPlayerBplacingRanking.addAttempt(currPlayerAplacingRanking.getAttempts());
						currPlayerBplacingRanking.addUpset(currPlayerAplacingRanking.getUpsets());
						for(int k =0; k < playerBother.getPlacingRankings().size(); k++){
							if(playerBother.getPlacingRankings().get(k).getPlayer().equals(playerB)){
								playerBother.getPlacingRankings().get(k).addAttempt(currPlayerAplacingRanking.getAttempts());
								playerBother.getPlacingRankings().get(k).addUpset(-1*currPlayerAplacingRanking.getUpsets());
							}
							else if(playerBother.getPlacingRankings().get(k).getPlayer().equals(playerA)){
								playerBother.getPlacingRankings().remove(k);
							}

						}

						break;
					}
				}



				if(!found){
					playerB.addPlacingRanking(currPlayerAplacingRanking);
					for(int k =0; k < playerAother.getPlacingRankings().size(); k++){
						if(playerAother.getPlacingRankings().get(k).getPlayer().equals(playerA)){
							playerAother.getPlacingRankings().get(k).setPlayer(playerB);
							break;
						}
					}
				}
			}
			for(int i = 0; i < playerA.getMatchRankings().size(); i++){
				PlayerRanking currPlayerAmatchRanking = playerA.getMatchRankings().get(i);
				Player playerAother = currPlayerAmatchRanking.getPlayer();
				boolean found = false;
				for(int j = 0; j < playerB.getMatchRankings().size(); j++){
					PlayerRanking currPlayerBmatchRanking = playerB.getMatchRankings().get(j);
					Player playerBother = currPlayerBmatchRanking.getPlayer();
					if(playerAother == playerBother){
						found = true;
						currPlayerBmatchRanking.addAttempt(currPlayerAmatchRanking.getAttempts());
						currPlayerBmatchRanking.addUpset(currPlayerAmatchRanking.getUpsets());
						currPlayerBmatchRanking.addGameWin(currPlayerAmatchRanking.getGameWins());
						currPlayerBmatchRanking.addGameLoss(currPlayerAmatchRanking.getGameLosses());
						for(int l =0; l < currPlayerAmatchRanking.getMatches().size(); l++){
							currPlayerBmatchRanking.addMatch(currPlayerAmatchRanking.getMatches().get(l));
						}
						for(int k =0; k < playerBother.getMatchRankings().size(); k++){
							if(playerBother.getMatchRankings().get(k).getPlayer().equals(playerB)){
								PlayerRanking playerBotherRanking = playerBother.getMatchRankings().get(k);
								playerBotherRanking.addAttempt(currPlayerAmatchRanking.getAttempts());
								playerBotherRanking.addUpset(-1*currPlayerAmatchRanking.getUpsets());
								playerBotherRanking.addGameWin(currPlayerAmatchRanking.getGameLosses());
								playerBotherRanking.addGameLoss(currPlayerAmatchRanking.getGameWins());
								for(int l =0; l < currPlayerAmatchRanking.getMatches().size(); l++){
									playerBotherRanking.addMatch(currPlayerAmatchRanking.getMatches().get(l));
								}
							}
							else if(playerBother.getMatchRankings().get(k).getPlayer().equals(playerA))
								playerBother.getMatchRankings().remove(k);
						}
						break;
					}
				}
				if(!found){
					playerB.addMatchRanking(currPlayerAmatchRanking);
					for(int k =0; k < playerAother.getMatchRankings().size(); k++){
						if(playerAother.getMatchRankings().get(k).getPlayer().equals(playerA)){
							playerAother.getMatchRankings().get(k).setPlayer(playerB);
							break;
						}
					}
				}

			}
			for(int i = 0; i < playerA.getWins().size(); i++){
				//TODO:  we must also update the match object to include the correct players. 
				Match match = playerA.getWins().get(i);
				match.setWinner(playerB);
				playerB.addWin(match);

			}
			for(int i = 0; i < playerA.getLosses().size(); i++){
				Match match = playerA.getLosses().get(i);
				match.setLoser(playerB);
				playerB.addLoss(match);
			}
			for(int i = 0; i < excludedMatches.size(); i++) {
				Match curr = excludedMatches.get(i);
				if(curr.getLoser().equals(playerA)) {
					curr.setLoser(playerB);
				}
				if(curr.getWinner().equals(playerA)) {
					curr.setWinner(playerB);
				}
			}
			for(int i = 0; i < excludedPlacings.size(); i++) {
				TournamentPlacings curr = excludedPlacings.get(i);
				if(curr.getPlayer().equals(playerA)) {
					curr.setPlayer(playerB);
				}
			}
			
			
			players.remove(playerA);
			session.setAttribute("players", players);
			session.setAttribute("pr", new SortablePlayerList(players, 2));
			method.alertAndRedirectError("Players merged successfully", request, response);
			return;
		}catch(Exception y){
			method.alertAndRedirectError("There was an error", request, response);
			return;
		}
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
