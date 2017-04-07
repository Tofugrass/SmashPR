

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import pr.smash.Dependencies.Methods;
import pr.smash.Dependencies.Player;
import pr.smash.Dependencies.SortablePlayerList;
/**
 * Servlet implementation class LoadPlayerData
 */
public class LoadPlayerData extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoadPlayerData() {
		super();
		// TODO Auto-generated constructor stub
	}

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

		if(request.getParameter("playerA") == null || request.getParameter("playerA").equals("") ) {
			method.alertAndRedirect("Please enter a player you would like to look up", request, response);
			return;	
		}
		else {
			
			HttpSession session = request.getSession();
			ArrayList<Player> players = method.getSessionPlayers(session);
			SortablePlayerList pr = new SortablePlayerList(players, 2);
			String returnString = "Player to Display: ";
			try{
				Player player = null; 
				try{
					player = method.getPlayerFromName(method.trimSponsor(request.getParameter("playerA").trim()), players);
				}catch(Exception e){
					method.alertAndRedirect("Tag not recognized", request, response);
					return;	
				}
				if(player!=null) {
					if(request.getParameter("playerB") == null || request.getParameter("playerB").equals("") ) {
						//matches
						returnString += (player.getName()+"\n");
						returnString += ("__Matches__\n");

						returnString += ("___WINS___\n");
						for(int i =0; i<player.getWins().size(); i++){
							returnString += (player.getName()+" won ("+player.getWins().get(i).getWinScore()+"-"+player.getWins().get(i).getLoseScore()+") over "+player.getWins().get(i).getLoser().getName()+" at "+player.getWins().get(i).getTourney()+"\n");
						}
						returnString += ("___LOSSES___\n");
						for(int i =0; i<player.getLosses().size(); i++){
							returnString += (player.getName()+" lost ("+player.getLosses().get(i).getWinScore()+"-"+player.getLosses().get(i).getLoseScore()+") to "+player.getLosses().get(i).getWinner().getName()+" at "+player.getLosses().get(i).getTourney()+"\n");
						}
						Collections.sort(player.getMatchRankings());
						for(int i = 0; i < player.getMatchRankings().size(); i++){
							if(player.getMatchRankings().get(i).getRatio()<0){
								returnString += ("Has a WINNING RECORD against "+ player.getMatchRankings().get(i).getPlayer().getName()+" "+ player.getMatchRankings().get(i).getRatio()+". They have "+player.getMatchRankings().get(i).getAttempts()+" sets.\n");
							}
							else if(player.getMatchRankings().get(i).getRatio()==0){
								returnString += ("Has a TIEING RECORD against "+ player.getMatchRankings().get(i).getPlayer().getName()+" "+ player.getMatchRankings().get(i).getRatio()+". They have "+player.getMatchRankings().get(i).getAttempts()+" sets.\n");
							}
							else {
								returnString += ("Has a LOSING RECORD against "+ player.getMatchRankings().get(i).getPlayer().getName()+" "+ player.getMatchRankings().get(i).getRatio()+". They have "+player.getMatchRankings().get(i).getAttempts()+" sets.\n");
							}
						}

						//standings

						returnString += ("__Placings__\n");
						for(int i = 0; i < player.getPlacings().size(); i++){
							returnString += ("Placed "+player.getPlacings().get(i).getPlacing()+" at "+ player.getPlacings().get(i).getTournament()+"\n");
						}
						Collections.sort(player.getPlacingRankings());
						for(int i = 0; i < player.getPlacingRankings().size(); i++){
							if(player.getPlacingRankings().get(i).getRatio()<0){
								returnString += ("Places HIGHER than "+ player.getPlacingRankings().get(i).getPlayer().getName()+" ("+ player.getPlacingRankings().get(i).getRatio()+"). With "+player.getPlacingRankings().get(i).getAttempts()+" attempts.\n");
							}
							else if(player.getPlacingRankings().get(i).getRatio()==0){
								returnString += ("TIES with "+ player.getPlacingRankings().get(i).getPlayer().getName()+" ("+ player.getPlacingRankings().get(i).getRatio()+"). With "+player.getPlacingRankings().get(i).getAttempts()+" attempts.\n");
							}
							else if(player.getPlacingRankings().get(i).getRatio()>0){
								returnString += ("Places LOWER than "+ player.getPlacingRankings().get(i).getPlayer().getName()+" ("+ player.getPlacingRankings().get(i).getRatio()+"). With "+player.getPlacingRankings().get(i).getAttempts()+" attempts.\n");
							}
						}
					}
					else{
						Player player2 = null;
						try{
							player2 = method.getPlayerFromName(method.trimSponsor(request.getParameter("playerB").trim()), players);
						}catch(Exception e){
							method.alertAndRedirect("Player 2 tag not recognized", request, response);
							return;	
						}
						boolean played = false;
						//matches

						returnString += (player.getName()+" vs. "+player2.getName()+"\n");
						returnString += ("__Matches__\n");
						int match = pr.matchAnalysis(player, player2).getInt();
						if(match < 0){
							returnString += ("Indirect match analysis reveals a "+match*-1+" score in "+player.getName()+"'s favor\n");
						}
						else{
							returnString += ("Indirect match analysis reveals a "+match+" score in "+player2.getName()+"'s favor\n");
						}
						for(int i = 0; i < player.getMatchRankings().size(); i++){
							if(player.getMatchRankings().get(i).getPlayer().getName().equals(player2.getName())){
								played = true;
								returnString += (player.getName()+" has "+player.getMatchRankings().get(i).getGameWins()+"(W)-"+player.getMatchRankings().get(i).getGameLosses()+"(L) game score against "+player2.getName()+"\n");
								for(int j = 0; j < player.getMatchRankings().get(i).getMatches().size(); j++){
									if(player.getMatchRankings().get(i).getMatches().get(j).getWinner().equals(player)){
										returnString += (player.getName()+" beat "+player2.getName()+" "+player.getMatchRankings().get(i).getMatches().get(j).getWinScore()+"-"+player.getMatchRankings().get(i).getMatches().get(j).getLoseScore()+" at "+player.getMatchRankings().get(i).getMatches().get(j).getTourney()+"\n");
									}
									else{
										returnString += (player.getName()+" lost to "+player2.getName()+" "+player.getMatchRankings().get(i).getMatches().get(j).getLoseScore()+"-"+player.getMatchRankings().get(i).getMatches().get(j).getWinScore()+" at "+player.getMatchRankings().get(i).getMatches().get(j).getTourney()+"\n");

									}

								}
								break;
							}
						}
						if(!played){
							returnString += ("These players have never played\n");
						}
						played = false;
						match = pr.placingAnalysis(player, player2).getInt();
						returnString += ("__Placings__\n");
						if(match<0){
							returnString += ("Indirect placing analysis reveals a "+match*-1+" score in "+player.getName()+"'s favor\n");
						}else{
							returnString += ("Indirect placing analysis reveals a "+match+" score in "+player2.getName()+"'s favor\n");
						}
						for(int i = 0; i < player.getPlacings().size();i++){
							if(player.getPlacings().get(i).getPlayers().contains(player2)){
								played = true;
								for(int j = 0; j < player2.getPlacings().size(); j++){
									if(player2.getPlacings().get(j).getTournament().equals(player.getPlacings().get(i).getTournament())){
										if(player.getPlacings().get(i).getPlacing() < player2.getPlacings().get(j).getPlacing()){
											returnString += (player.getName()+" placed HIGHER than "+player2.getName()+" at "+player.getPlacings().get(i).getTournament()+" ("+player.getPlacings().get(i).getPlacing()+"-"+player2.getPlacings().get(j).getPlacing()+")\n");
										}
										else if (player.getPlacings().get(i).getPlacing() == player2.getPlacings().get(j).getPlacing()){
											returnString += (player.getName()+" TIED "+player2.getName()+" at "+player.getPlacings().get(i).getTournament()+" ("+player.getPlacings().get(i).getPlacing()+"-"+player2.getPlacings().get(j).getPlacing()+")\n");
										}
										else{
											returnString += (player.getName()+" placed LOWER than "+player2.getName()+" at "+player.getPlacings().get(i).getTournament()+" ("+player.getPlacings().get(i).getPlacing()+"-"+player2.getPlacings().get(j).getPlacing()+")\n");
										}
										break;
									}
								}
								break;
							}
						}
						if(!played){
							returnString += ("These players have never attended the same tournament\n");
						}
					}
				}
				else {
					method.alertAndRedirect("Tag not recognized", request, response);
					return;	
				}
				request.setAttribute("displayData", true);
				request.setAttribute("playerData", returnString);
				//System.out.println(returnString);
				method.alertAndRedirect("Everything went well", request, response);
				return;	
			}catch(Exception e) {
				method.alertAndRedirect("Tag not recognized", request, response);
				return;	
			}
			
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
