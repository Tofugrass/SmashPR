

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import pr.smash.Dependencies.Methods;
import pr.smash.Dependencies.Player;
import pr.smash.Dependencies.SortablePlayerList;
import pr.smash.Dependencies.Tournament;

/**
 * Servlet implementation class LoadTournamentData
 */
public class LoadTournamentData extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoadTournamentData() {
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

		if(request.getParameter("tournamentName") == null || request.getParameter("tournamentName").equals("") ) {
			method.alertAndRedirect("Please enter a tournament you would like to look up", request, response);
			return;	
		}
		else {
			HttpSession session = request.getSession();
			ArrayList<Tournament> tournaments = method.getSessionTournaments(session);
			String returnString = "";
			String tournamentName = request.getParameter("tournamentName");
			for(int i = tournaments.size()-1; i >= 0; i--) {
				if(tournaments.get(i).getName().equalsIgnoreCase(tournamentName)) {
					Tournament tournament = tournaments.get(i);
					returnString += tournament.getName() + "\n";
					for (int j = 0; j < tournament.getResults().size(); j++) {
						returnString += tournament.getResults().get(j).getName()+"\n";
					}
					request.setAttribute("displayData", true);
					request.setAttribute("playerData", returnString);
					method.alertAndRedirect("Tournament added successfully", request, response);
					return;
				}
			}
			request.setAttribute("displayData", true);
			request.setAttribute("playerData", "Tournament not found\n");
			method.alertAndRedirect("Tournament not found", request, response);
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
