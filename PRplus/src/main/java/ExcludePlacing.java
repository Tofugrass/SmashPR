

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import pr.smash.Dependencies.Methods;
import pr.smash.Dependencies.Player;
import pr.smash.Dependencies.Tournament;
import pr.smash.Dependencies.TournamentPlacings;
import pr.smash.Dependencies.PlayerRanking;
/**
 * Servlet implementation class ExcludePlacing
 */
public class ExcludePlacing extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ExcludePlacing() {
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
		
		if(request.getParameter("player") == null || request.getParameter("event") == null) {
			method.alertAndRedirectError("Please enter a valid player name and event", request, response);
			return;
		}
		ArrayList<Player> players = method.getSessionPlayers(session);
		ArrayList<Tournament> tournaments = method.getSessionTournaments(session);
		ArrayList<TournamentPlacings> includedPlacings = method.getSessionIncludedPlacings(session);
		ArrayList<TournamentPlacings> excludedPlacings = method.getSessionExcludedPlacings(session);
		
		Player player = null;
		try{
			player = method.getPlayerFromName(method.trimSponsor(request.getParameter("player")), players);
		}catch(Exception e){
			method.alertAndRedirectError("Tag not recognized", request, response);
			return;
		}
		Tournament tournament = method.getTournamentFromName(((String) request.getParameter("event").trim()), tournaments);
		if(tournament == null){
			method.alertAndRedirectError("Tournament not recognized", request, response);
			return;
		}
		else{
		
		if(request.getParameter("radio").equals("exclude")) {

			boolean found = false;
			for(int i = 0; i < player.getPlacings().size(); i++){
				TournamentPlacings currPlacing = player.getPlacings().get(i);
				if(currPlacing.getTournament().equalsIgnoreCase(tournament.getName())){
					found = true;
					excludedPlacings.add(currPlacing);
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
			if(!found) {
				method.alertAndRedirectError("Player did not attend that event", request, response);
				return;
			}
			else {
				method.alertAndRedirectError("Placing excluded succesfully", request, response);
				return;
			}
		
		}
		else {

			boolean found = false;
			for( int i = 0; i < excludedPlacings.size(); i++){
				if(excludedPlacings.get(i).getTournament().equalsIgnoreCase(tournament.getName())&&excludedPlacings.get(i).getPlayer().equals(player)){
					found = true;
					TournamentPlacings placing = excludedPlacings.get(i);
					player.addPlacing(placing);
					excludedPlacings.remove(placing);
					includedPlacings.add(placing);
					int aPlacing = placing.getPlacing();
					for(int j = 0; j < placing.getPlayers().size(); j++){
						Player other = placing.getPlayers().get(j);
						if(!other.equals(player)){
							int bPlacing = 0;
							for(int k = 0; k < other.getPlacings().size(); k++){
								if(other.getPlacings().get(k).getTournament().equals(tournament.getName())){
									bPlacing = other.getPlacings().get(k).getPlacing();
									break;
								}
							}
							boolean exists = false;
							for(int k = 0; k < player.getPlacingRankings().size(); k++){
								if(player.getPlacingRankings().get(k).getPlayer().equals(other)){
									exists = true;
									player.getPlacingRankings().get(k).addAttempt(1);
									if(aPlacing > bPlacing){
										player.getPlacingRankings().get(k).addUpset(1);
									}
									else if(aPlacing < bPlacing){
										player.getPlacingRankings().get(k).addUpset(-1);
									}
									break;
								}
							}
							if(!exists){
								PlayerRanking ranking = new PlayerRanking(other);
								ranking.addAttempt(1);
								if(aPlacing > bPlacing){
									ranking.addUpset(1);
								}
								else if(aPlacing < bPlacing){
									ranking.addUpset(-1);
								}
								player.addPlacingRanking(ranking);
							}
							exists = false;
							for(int k = 0; k < other.getPlacingRankings().size(); k++){
								if(other.getPlacingRankings().get(k).getPlayer().equals(player)){
									exists = true;
									other.getPlacingRankings().get(k).addAttempt(1);
									if(aPlacing > bPlacing){
										other.getPlacingRankings().get(k).addUpset(-1);
									}
									else if(aPlacing < bPlacing){
										other.getPlacingRankings().get(k).addUpset(1);
									}
									break;
								}
							}
							if(!exists){
								PlayerRanking ranking = new PlayerRanking(player);
								ranking.addAttempt(1);
								if(aPlacing > bPlacing){
									ranking.addUpset(-1);
								}
								else if(aPlacing < bPlacing){
									ranking.addUpset(1);
								}
								other.addPlacingRanking(ranking);
							}
						}
					}
					break;
				}
			}
			if(!found) {

				method.alertAndRedirectError("Placing not found", request, response);
				return;
			
			}
			else {
				method.alertAndRedirectError("Placing included successfully", request, response);
				return;
			}
		
		}
		}
		
		/**
		 * 
							
						if(rdbtnExcludePlacing.isSelected()){}

						else{}
					}
				}
			
		 */
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
