

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;

import pr.smash.Dependencies.Match;
import pr.smash.Dependencies.Methods;
import pr.smash.Dependencies.Player;

/**
 * Servlet implementation class LoadExcludedMatches
 */
public class LoadExcludedMatches extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoadExcludedMatches() {
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
		ArrayList<Match> excludedMatches = method.getSessionExcludedMatches(session);
		ArrayList<Player> players = method.getSessionPlayers(session);
		if(excludedMatches.size() == 0) {
			method.alertAndRedirectError("No matches excluded\n", request, response);
			return;
		}
		String player = request.getParameter("player");
		if(player == null || player.equals("")) {
			String returnString = "Excluded Matches\n";
			for(int i = 0; i < excludedMatches.size(); i++) {
				Match match = excludedMatches.get(i);
				returnString+= match.getWinner().getName()+" beat "+match.getLoser().getName()+" ("+match.getWinScore()+"-"+match.getLoseScore()+") at "+match.getTourney().getName()+"\n";
			}
			request.setAttribute("displayData", true);
			request.setAttribute("playerData", returnString);
			method.alertAndRedirect("", request, response);
		}
		else {
			try{
				Player playerA = method.getPlayerFromName(method.trimSponsor(player),players);
				String returnString = "Excluded Matches for "+playerA.getName()+"\n";
				for(int i = 0; i< excludedMatches.size(); i++){
					Match mat = excludedMatches.get(i);
					if(mat.getWinner().equals(playerA)||mat.getLoser().equals(playerA)) {
						returnString+=mat.getWinner().getName()+" won ("+mat.getWinScore()+"-"+mat.getLoseScore()+") over "+mat.getLoser().getName()+" at "+mat.getTourney()+"\n";
					}
				}
				request.setAttribute("displayData", true);
				request.setAttribute("playerData", returnString);
				method.alertAndRedirect("", request, response);
			}catch(Exception e){
				method.alertAndRedirectError("Player not found, check your spelling", request, response);
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
