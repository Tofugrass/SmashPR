

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import pr.smash.Dependencies.Methods;
import pr.smash.Dependencies.Player;
import pr.smash.Dependencies.Match;
import pr.smash.Dependencies.SortablePlayerList;
import pr.smash.Dependencies.Tournament;
import pr.smash.Dependencies.TournamentPlacings;
import pr.smash.Dependencies.PlayerRanking;

/**
 * Servlet implementation class ImportFromFile
 */

public class ImportFromFile extends HttpServlet {
	private static final long serialVersionUID = 42432L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ImportFromFile() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
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



		ArrayList<Match> includedMatches = method.getSessionIncludedMatches(session);
		//ArrayList<Match> excludedMatches = method.getSessionExcludedMatches(session);
		ArrayList<Tournament> tournaments = method.getSessionTournaments(session);
		ArrayList<TournamentPlacings> includedPlacings = method.getSessionIncludedPlacings(session);
		//ArrayList<TournamentPlacings> excludedPlacings = method.getSessionExcludedPlacings(session);

		// Check that we have a file upload request

		// Create a factory for disk-based file items
		DiskFileItemFactory factory = new DiskFileItemFactory();

		// Configure a repository (to ensure a secure temp location is used)
		ServletContext servletContext = this.getServletConfig().getServletContext();
		File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
		factory.setRepository(repository);

		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);

		// Parse the request

		List<FileItem> items = null;
		try {
			items =  upload.parseRequest(request);
		}catch(Exception e) {
			method.alertAndRedirectError("oof", request, response);
			return;
		}

		String total = items.get(0).getString();
		// Process the uploaded items
		Scanner scan = new Scanner(total);
		String line = scan.nextLine();
		if(!line.equals("__Tournaments__")){
			scan.close();
			method.alertAndRedirectError("Please import a valid file", request, response);
			return;

		}
		while(scan.hasNextLine()) {
			/*
			if(line.equals("__PlayerList__")){
				line = scan.nextLine();
				while(!line.equals("__Tournaments__")){
					players.add(new Player(line));
					line = scan.nextLine();
				}
			}*/
			boolean alreadyEntered = false;
			line = scan.nextLine();
			Tournament newTourney = new Tournament(line);
			for(int i = 0; i < tournaments.size(); i++){
				if(tournaments.get(i).getName().equals(newTourney.getName())){
					alreadyEntered = true;
				}
			}
			if(alreadyEntered) {
				while(!line.equals("__")){
					line = scan.nextLine();
				}
			}
			else {
				line = scan.nextLine();
				while(!line.equals("__")){
					line = scan.nextLine();
					while(!line.equals("Matches-a57h5")){
						try {
							method.addPlayer(players, line);
							newTourney.addResults(method.getPlayerFromName(line, players));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							scan.close();
							method.alertAndRedirectError("File corrupted", request, response);
							return;
						}
						line = scan.nextLine();
					}
					line = scan.nextLine();
					for(int i = 0; i<newTourney.getResults().size(); i++){
						method.enterPlacing(newTourney.getResults().get(i), newTourney.getName(), i,  newTourney.getResults(), includedPlacings);
					}
					tournaments.add(newTourney);
					while(!line.equals("__")){
						try {
							String[] tokens = line.split(" ");
							Match newMatch = new Match(method.getPlayerFromName(tokens[0], players), Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]),method.getPlayerFromName(tokens[3], players) , method.getTournamentFromName(tokens[4], tournaments));
							method.enterMatch(newMatch, includedMatches);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							scan.close();
							method.alertAndRedirectError("File corrupted", request, response);
							return;
						}
						line = scan.nextLine();
					}
				}



				/*
			if(line.equals("__Players__")){
				line = scan.nextLine();
				while(!line.equals("__Excluded Matches__")){
					try {			
						Player newPlayer = method.getPlayerFromName(line, players); 
						line = scan.nextLine();
						if(line.equals("Placings:")){
							line = scan.nextLine();
							//**		
							while(!line.equals("Match Rankings:")){
								//newPlayer.addPlacingRanking(line.substring(0,line.indexOf(" ")), Integer.parseInt(line.substring(line.indexOf(" ")+1,line.length())), players);
								line = scan.nextLine();
							}// 
						}
						if(line.equals("Match Rankings:")){
							line = scan.nextLine();
							while(!line.equals("Placement Rankings:")){
								try {
									//System.out.println(line);
									//System.out.println(method.getPlayerFromName(line.substring(0, line.indexOf(" ")), players).getName());
									PlayerRanking newRanking = new PlayerRanking(method.getPlayerFromName(line.substring(0, line.indexOf(" ")), players));
									newRanking.setUpsets(Integer.parseInt(line.substring(line.indexOf(" ")+1,line.lastIndexOf(" "))));
									newRanking.setAttempts(Integer.parseInt(line.substring(line.lastIndexOf(" ")+1)));
									newPlayer.addMatchRanking(newRanking);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

								line = scan.nextLine();
							}
						}
						if(line.equals("Placement Rankings:")){

							line = scan.nextLine();
							while(!line.equals("Wins:")){
								try {
									PlayerRanking newRanking = new PlayerRanking(method.getPlayerFromName(line.substring(0, line.indexOf(" ")), players));
									newRanking.setUpsets(Integer.parseInt(line.substring(line.indexOf(" ")+1,line.lastIndexOf(" "))));
									newRanking.setAttempts(Integer.parseInt(line.substring(line.lastIndexOf(" ")+1)));
									line = scan.nextLine();
									newPlayer.addPlacingRanking(newRanking);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							}
						}
						if(line.equals("Wins:")){
							line = scan.nextLine();
							while(!line.equals("Losses:")){
								String[] tokens = line.split(" ");
								try {
									Player playerB = method.getPlayerFromName(tokens[3], players);
									Match newMatch = new Match(newPlayer, Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]),playerB , tokens[0]);
									newPlayer.addWin(newMatch);
									playerB.addLoss(newMatch);
									for(int i = 0; i < newPlayer.getMatchRankings().size(); i++){
										if(newPlayer.getMatchRankings().get(i).getPlayer().equals(playerB)){
											newPlayer.getMatchRankings().get(i).addGameWin(newMatch.getWinScore());
											newPlayer.getMatchRankings().get(i).addGameLoss(newMatch.getLoseScore());
											newPlayer.getMatchRankings().get(i).addMatch(newMatch);
											includedMatches.add(newMatch);
											break;
										}
									}
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								line = scan.nextLine();
							}
						}
						if(line.equals("Losses:")){
							line = scan.nextLine();
							while(!line.equals("__")){
								String[] tokens = line.split(" ");
								try {
									Player playerB = method.getPlayerFromName(tokens[3], players);

									Match newMatch = new Match(playerB, Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]), newPlayer, tokens[0]);
									//enterMatch(newMatch);
									//newPlayer.addLoss(newMatch);
									for(int i = 0; i < newPlayer.getMatchRankings().size(); i++){
										if(newPlayer.getMatchRankings().get(i).getPlayer().equals(playerB)){
											newPlayer.getMatchRankings().get(i).addGameWin(newMatch.getLoseScore());
											newPlayer.getMatchRankings().get(i).addGameLoss(newMatch.getWinScore());
											newPlayer.getMatchRankings().get(i).addMatch(newMatch);
											break;
										}
									}
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

								line = scan.nextLine();
							}
							line = scan.nextLine();
						}
					}catch(Exception e) {
						e.printStackTrace();
						method.alertAndRedirectError("Oof", request, response);
						scan.close();
						return;
					}
				}
			}*/

				/*if(line.equals("__Excluded Matches__")){
				line = scan.nextLine();
				while(!line.equals("__Excluded Placings__")){
					String[] tokens = line.split(" ");
					try {
						Match newMatch = new Match(method.getPlayerFromName(tokens[0],players), Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]), method.getPlayerFromName(tokens[3],players), tokens[4]);
						excludedMatches.add(newMatch);							
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					line = scan.nextLine();
				}
			}
			if(line.equals("__Excluded Placings__")){
				while(scan.hasNextLine()){
					line = scan.nextLine();

					String[] tokens = line.split(" ");
					try {
						Player player = method.getPlayerFromName(tokens[0], players);
						//int placing = Integer.parseInt(tokens[1]);
						String tournament = tokens[1];
						for(int i = 0; i < includedPlacings.size(); i++){
							TournamentPlacings currPlacing = includedPlacings.get(i);
							if(currPlacing.getPlayer().equals(player) && currPlacing.getTournament().equals(tournament)){
								player.getPlacings().remove(currPlacing);
								includedPlacings.remove(currPlacing);
								excludedPlacings.add(currPlacing);
								break;
							}
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}*/
				//System.out.println("done");
			}
		}
		scan.close();
		session.setAttribute("players", players);
		session.setAttribute("includedMatches", includedMatches);
		//session.setAttribute("excludedMatches", excludedMatches);
		session.setAttribute("tournaments", tournaments);
		session.setAttribute("includedPlacings", includedPlacings);
		session.setAttribute("canUndo", "false");
		method.alertAndRedirectError("Everything imported successfully", request, response);
		return;
	}



}
