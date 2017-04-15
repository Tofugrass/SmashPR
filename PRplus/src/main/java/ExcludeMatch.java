

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
		Methods method = new Methods();
		HttpSession session = request.getSession();
		ArrayList<Player> players = method.getSessionPlayers(session);
		if(request.getParameter("winner")==null || request.getParameter("loser")==null || request.getParameter("wScore")==null || request.getParameter("lScore")==null || request.getParameter("event")==null ){
			method.alertAndRedirectError("Please enter all fields", request, response);
			return;
		}
		else {
			ArrayList<Match> includedMatches = method.getSessionIncludedMatches(session);
			ArrayList<Match> excludedMatches = method.getSessionExcludedMatches(session);
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
				for(int i = 0; i < includedMatches.size();i++){
					Match curr = includedMatches.get(i);
					if(curr.getWinner().equals(w) && curr.getLoser().equals(l) && curr.getWinScore()==ws && curr.getLoseScore()==ls&&curr.getTourney().getName().equalsIgnoreCase(request.getParameter("event").trim())){						
						//excludedMatches.add(curr);
					curr.exclude(includedMatches, excludedMatches, session);
					method.alertAndRedirectError("Match excluded successfully", request, response);
					return;
					}
				}
				method.alertAndRedirectError("Match not found", request, response);
				return;
			}
			else {
				for(int i = 0; i < excludedMatches.size();i++){
					Match curr = excludedMatches.get(i);
					if(curr.getWinner().equals(w) && curr.getLoser().equals(l) && curr.getWinScore()==ws && curr.getLoseScore()==ls&&curr.getTourney().getName().equalsIgnoreCase(request.getParameter("event").trim())){
						//if we find the correct match we must exclude it and update player rankings
						curr.include(includedMatches, excludedMatches, method);
						method.alertAndRedirectError("Match included successfully", request, response);
						return;
					}
				}
				method.alertAndRedirectError("Match not found", request, response);
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
