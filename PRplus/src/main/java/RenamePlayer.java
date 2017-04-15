

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import pr.smash.Dependencies.Methods;
import pr.smash.Dependencies.Player;

/**
 * Servlet implementation class RenamePlayer
 */
public class RenamePlayer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RenamePlayer() {
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
		
		String oldName = method.trimSponsor(request.getParameter("playerA").trim());
		String newName =  method.trimSponsor(request.getParameter("playerB").trim());
		Player player = null;
		try{
			player = method.getPlayerFromName(oldName, players);
			try{
				method.getPlayerFromName(newName, players);
				method.alertAndRedirectError("That tag is already being used!", request, response);
				return;
			}catch(Exception e){
				player.setName(newName);
				//session.setAttribute("canUndo", "false");
				method.alertAndRedirectError("Name changed successfully", request, response);
				return;
			}
		}
		catch(Exception e){
			method.alertAndRedirectError("Player not found, check spelling", request, response);
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
