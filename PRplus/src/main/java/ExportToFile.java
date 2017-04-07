

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

/**
 * Servlet implementation class ExportToFile
 */
public class ExportToFile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ExportToFile() {
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
		if(players == null || request.getParameter("fileName") == null || request.getParameter("fileName").equals("")){
			method.alertAndRedirectError("Please enter data before saving", request, response);
			return;
		}
		method.alertAndRedirectError("Data saved to default download location as: "+ request.getParameter("fileName")+".pr", request, response);
		return;
		/**
		 * 
				FileWriter write = null;
				try {
					write = new FileWriter( txtname_1.getText()+".txt" , false);

					PrintWriter print_line = new PrintWriter( write );
					print_line.println("__PlayerList__");
					for(int i = 0;i < players.size(); i++){
						Player player = players.get(i);
						print_line.println(player.getName());
					}
					print_line.println("__Tournaments__");
					for(int i = 0;i < tournaments.size(); i++){
						Tournament tournament = tournaments.get(i);
						print_line.println(tournament.getName());
						for(int j = 0;j<tournament.getResults().size();j++){
							print_line.println(tournament.getResults().get(j).getName());
						}
						print_line.println("__");
					}

					print_line.println("__Players__");
					for(int i = 0;i < players.size(); i++){
						Player player = players.get(i);
						print_line.println(player.getName());
						print_line.println("Placings:");
						for(int j = 0;j<player.getPlacings().size();j++){
							print_line.println(player.getPlacings().get(j).getTournament()+" "+player.getPlacings().get(j).getPlacing());
						}
						print_line.println("Match Rankings:");
						for(int j = 0;j<player.getMatchRankings().size();j++){
							print_line.println(player.getMatchRankings().get(j).getPlayer().getName()+" "+player.getMatchRankings().get(j).getUpsets()+" "+player.getMatchRankings().get(j).getAttempts());
						}
						print_line.println("Placement Rankings:");
						for(int j = 0;j<player.getPlacingRankings().size();j++){
							print_line.println(player.getPlacingRankings().get(j).getPlayer().getName()+" "+player.getPlacingRankings().get(j).getUpsets()+" "+player.getPlacingRankings().get(j).getAttempts());
						}
						print_line.println("Wins:");
						for(int j = 0;j<player.getWins().size();j++){
							print_line.println(player.getWins().get(j).getTourney()+" "+player.getWins().get(j).getWinScore()+" "+player.getWins().get(j).getLoseScore()+" "+player.getWins().get(j).getLoser().getName());
						}
						print_line.println("Losses:");
						for(int j = 0;j<player.getLosses().size();j++){
							print_line.println(player.getLosses().get(j).getTourney()+" "+player.getLosses().get(j).getWinScore()+" "+player.getLosses().get(j).getLoseScore()+" "+player.getLosses().get(j).getWinner().getName());
						}
						print_line.println("__");
					}

					print_line.println("__Excluded Matches__");
					for(int i = 0;i < excludedMatches.size(); i++){
						Match match = excludedMatches.get(i);
						print_line.println(match.getWinner().getName()+" "+match.getWinScore()+" "+match.getLoseScore()+" "+match.getLoser().getName()+" "+match.getTourney());
					}
					print_line.println("__Excluded Placings__");
					for(int i = 0;i < excludedPlacings.size(); i++){
						TournamentPlacings placing= excludedPlacings.get(i);
						print_line.print(placing.getPlayer().getName()+" "+placing.getTournament());
					}
					print_line.close();
					JOptionPane.showMessageDialog(null, "Data exported successfully");
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "Error occured while exporting data");
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
