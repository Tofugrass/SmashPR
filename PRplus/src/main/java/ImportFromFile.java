

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * Servlet implementation class ImportFromFile
 */

public class ImportFromFile extends HttpServlet {
	private static final long serialVersionUID = 1L;

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
		Methods method = new Methods();
		//PROGRAM FLOW:
		//0 we redirect the user if the form isn't filled out
		//1 initialize all objects
		//2 if any of them are null, we will create a new one
		//3 import the standings and matches
		//4 update the session objects
		//return the user back to the original page


		//0 we redirect the user if the form isn't filled out	
	/*	if(request.getParameter("importFile") == null) {
			System.out.println(132);
			request.getRequestDispatcher("/practiceUpload.jsp").forward(request, response);
			//method.alertAndRedirect("Please enter the url you would like to import from", request, response);
		}

		else {
			*/	
			//1 initialize all objects we need, these are the players, matches, tournaments and standings
			HttpSession session = request.getSession();
			ArrayList<Player> players = method.getSessionPlayers(session);
			ArrayList<Match> includedMatches = method.getSessionIncludedMatches(session);
			ArrayList<Match> excludedMatches = method.getSessionExcludedMatches(session);
			ArrayList<Tournament> tournaments = method.getSessionTournaments(session);
			ArrayList<TournamentPlacings> includedPlacings = method.getSessionIncludedPlacings(session);
			ArrayList<TournamentPlacings> excludedPlacings = method.getSessionExcludedPlacings(session);

			// Check that we have a file upload request

			PrintWriter out = response.getWriter();

			// Create a factory for disk-based file items
			DiskFileItemFactory factory = new DiskFileItemFactory();

			// Configure a repository (to ensure a secure temp location is used)
			ServletContext servletContext = this.getServletConfig().getServletContext();
			File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
			factory.setRepository(repository);

			// Create a new file upload handler
			ServletFileUpload upload = new ServletFileUpload(factory);

			// Parse the request
			try {
				List<FileItem> items = upload.parseRequest(request);
				if(items.size() == 0) {
					System.out.println(123);
					request.getRequestDispatcher("/practiceUpload.jsp").forward(request, response);
					//method.alertAndRedirect("Please enter the url you would like to import from", request, response);
				}
				// Process the uploaded items
				Iterator<FileItem> iter = items.iterator();
				while (iter.hasNext()) {
					FileItem item = iter.next();
					String line = item.getString();

					line = iter.next().getString();
					if(line.equals("__PlayerList__")){
						line = iter.next().getString();
						while(!line.equals("__Tournaments__")){
							players.add(new Player(line));
							line = iter.next().getString();
						}
					}
					if(line.equals("__Tournaments__")){
						line = iter.next().getString();
						while(!line.equals("__Players__")){
							Tournament newTourney = new Tournament(line);
							line = iter.next().getString();
							while(!line.equals("__")){
								try {
									newTourney.addResults(method.getPlayerFromName(line, players));
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								line = iter.next().getString();
							}
							tournaments.add(newTourney);
							line = iter.next().getString();
							for(int i = 0; i<newTourney.getResults().size(); i++){
								method.enterPlacing(newTourney.getResults().get(i), newTourney.getName(), i,  newTourney.getResults(), includedPlacings);
							}
						}
					}
					if(line.equals("__Players__")){
						line = iter.next().getString();

						while(!line.equals("__Excluded Matches__")){
							try {						
								Player newPlayer = method.getPlayerFromName(line, players); 

								line = iter.next().getString();

								if(line.equals("Placings:")){
									line = iter.next().getString();
									//**		
									while(!line.equals("Match Rankings:")){
										//newPlayer.addPlacingRanking(line.substring(0,line.indexOf(" ")), Integer.parseInt(line.substring(line.indexOf(" ")+1,line.length())), players);
										line = iter.next().getString();
									}// 
								}
								if(line.equals("Match Rankings:")){
									line = iter.next().getString();
									while(!line.equals("Placement Rankings:")){
										try {
											PlayerRanking newRanking = new PlayerRanking(method.getPlayerFromName(line.substring(0, line.indexOf(" ")), players));
											newRanking.setUpsets(Integer.parseInt(line.substring(line.indexOf(" ")+1,line.lastIndexOf(" "))));
											newRanking.setAttempts(Integer.parseInt(line.substring(line.lastIndexOf(" ")+1)));
											newPlayer.addMatchRanking(newRanking);
										} catch (Exception e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}

										line = iter.next().getString();
									}
								}
								if(line.equals("Placement Rankings:")){
									line = iter.next().getString();
									while(!line.equals("Wins:")){
										try {
											PlayerRanking newRanking = new PlayerRanking(method.getPlayerFromName(line.substring(0, line.indexOf(" ")), players));
											newRanking.setUpsets(Integer.parseInt(line.substring(line.indexOf(" ")+1,line.lastIndexOf(" "))));
											newRanking.setAttempts(Integer.parseInt(line.substring(line.lastIndexOf(" ")+1)));
											line = iter.next().getString();
											newPlayer.addPlacingRanking(newRanking);
										} catch (Exception e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}

									}
								}
								if(line.equals("Wins:")){
									line = iter.next().getString();
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

										line = iter.next().getString();
									}
								}
								if(line.equals("Losses:")){
									line = iter.next().getString();
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

										line = iter.next().getString();
									}
								}
							}catch(Exception e) {
								method.alertAndRedirect("Oof", request, response);
							}
						}
					}

					if(line.equals("__Excluded Matches__")){
						line = iter.next().getString();
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

							line = iter.next().getString();
						}
					}
					if(line.equals("__Excluded Placings__")){
						while(iter.hasNext()){
							line = iter.next().getString();

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
					}




					/**


				Scanner scanner = null;
				try{
					scanner = new Scanner (new File(txtname.getText()+".txt"));
					String line = "";
					while(scanner.hasNextLine()){}

				}
				catch(Exception e){
					JOptionPane.showMessageDialog(null, "Could not find file");
				}



					 */

				}
			} catch (FileUploadException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for(int i = 0; i < players.size(); i++)
				System.out.println(players.get(i).getName());
		}

	//}

	private void processUploadedFile(FileItem item) {
		// TODO Auto-generated method stub

	}
	private void processFormField(FileItem item) {
		// TODO Auto-generated method stub

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
