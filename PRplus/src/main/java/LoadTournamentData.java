

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
			method.alertAndRedirectError("Please enter a tournament you would like to look up", request, response);
			return;	
		}
		else {
			HttpSession session = request.getSession();
			ArrayList<Tournament> tournaments = method.getSessionTournaments(session);
			String returnString = "Tournament to Display: ";
			String tournamentName = request.getParameter("tournamentName");
			for(int j = tournaments.size()-1; j >= 0; j--) {
				if(tournaments.get(j).getName().equalsIgnoreCase(tournamentName)) {
					Tournament tournament = tournaments.get(j);
					returnString += tournament.getName() + "\n";
					for(int i = 0; i< tournament.getResults().size(); i++){
						int place = i;
						if (i >= 1088)
							place = 1089;
						else if (i >= 832)
							place = 833;
						else if (i >= 576)
							place = 577;
						else if (i >= 448)
							place = 449;
						else if (i >= 320)
							place = 321;
						else if (i >= 256)
							place = 257;
						else if (i >= 192)
							place = 193;
						else if (i >= 128)
							place = 129;
						else if (i >= 96)
							place = 97;
						else if (i >= 64)
							place = 65;
						else if(i >= 48)
							place = 49;
						else if(i >= 32)
							place = 33;
						else if(i >= 24)
							place = 25;
						else if(i >= 16)
							place = 17;
						else if(i >= 12)
							place = 13;
						else if(i >= 8)
							place = 9;
						else if(i >= 6)
							place = 7;
						else if(i >= 4)
							place = 5;
						else if(i == 3)
							place = 4;
						else if(i == 2)
							place = 3;
						else if(i == 1)
							place = 2;
						else if(i == 0)
							place = 1;
						returnString += tournament.getResults().get(i).getName()+" placed "+place+"\n";
					}
					request.setAttribute("displayData", true);
					request.setAttribute("playerData", returnString);
					method.alertAndRedirect("Tournament added successfully", request, response);
					return;
				}
			}
			request.setAttribute("displayData", true);
			request.setAttribute("playerData", "Tournament not found\n");
			method.alertAndRedirectError("Tournament not found", request, response);
			return;
		}
		/**
		 
		 
				Tournament tourney = getTournamentFromName(txtname_2.getText().trim(), tournaments);
				if(tourney==null){
					JOptionPane.showMessageDialog(null, "Tournament does not exist");
				}
				else{
					textArea_1.setText("");
					textArea_1.append(tourney.getName()+"\n");
					
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
