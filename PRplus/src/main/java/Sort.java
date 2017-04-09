

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
/**
 * Servlet implementation class Sort
 */
public class Sort extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Sort() {
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
		SortablePlayerList pr = new SortablePlayerList(players, 2);
		if(request.getParameter("sorts") == null){
			int sorts = 5;
			for(int i = 0; i < sorts; i++){
				pr.sort();
			}
			method.alertAndRedirectError("Players sorted successfully", request, response);
			return;
		}else {
			int sorts = 0;
			try {
				sorts = Integer.parseInt((String) request.getParameter("sorts"));
			}catch(Exception e) {
				sorts = 5;
			}
			if(sorts<0){
				sorts = 5;
			}
			for(int i = 0; i < sorts; i++){
				pr.sort();
			}
			method.alertAndRedirectError("Players sorted successfully", request, response);
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
