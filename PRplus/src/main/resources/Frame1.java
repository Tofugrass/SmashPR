/**
 * @author MicahD
 */

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.SwingConstants;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;

import java.awt.TextArea;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
public class Frame1 {

	private JFrame frmPowerRankingGenerator;
	private JTextField txtname;
	private JLabel lblHowToImport;
	boolean dataEntered = false;
	String user = "";
	private JTextField txttag;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	SortablePlayerList pr = null;
	/**
	 * @wbp.nonvisual location=271,19
	 */
	private JTextField txtsorts;
	private JTextField txtoptionalSecondPlayer;
	private JTextField txtname_1;
	private JTextField txtname_2;
	private JTextField txttag_1;
	private JTextField txtresponse;
	private final ButtonGroup buttonGroup_2 = new ButtonGroup();
	private JTextField txtlosersTag;
	private JTextField txtwinnersScore;
	private JTextField txtlosersScore;
	private JTextField txtevent;
	private JTextField txthttpmadsmashchallongecommtsdms;
	private JTextField txttag_2;
	private JTextField txtevent_1;
	private final ButtonGroup buttonGroup_3 = new ButtonGroup();
	private JTextField txtoldName;
	private JTextField txtnewName;
	private JTextField txtPlayerA;
	private JTextField txtPlayerB;
	private JTextField txttag_3;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_7;
	private JTextField textField_8;
	private final ButtonGroup buttonGroup_1 = new ButtonGroup();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Frame1 window = new Frame1();
					window.frmPowerRankingGenerator.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Frame1() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		ArrayList<Tournament> tournaments = new ArrayList<Tournament>();
		ArrayList<Player> players = new ArrayList<Player>();
		ArrayList<Player> manualTournamentResults = new ArrayList<Player>();
		ArrayList<Match> includedMatches = new ArrayList<Match>();
		ArrayList<Match> excludedMatches = new ArrayList<Match>();
		ArrayList<TournamentPlacings> includedPlacings = new ArrayList<TournamentPlacings>();
		ArrayList<TournamentPlacings> excludedPlacings = new ArrayList<TournamentPlacings>();

		JButton btnEnter_4 = new JButton("Enter"); 	

		frmPowerRankingGenerator = new JFrame();
		frmPowerRankingGenerator.getContentPane().setBackground(Color.LIGHT_GRAY);
		frmPowerRankingGenerator.setTitle("PR+ v1.00 beta");
		frmPowerRankingGenerator.setBounds(100, 100, 1475, 700);
		frmPowerRankingGenerator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmPowerRankingGenerator.getContentPane().setLayout(null);
		JButton btnAddevent = new JButton("AddEvent");
		btnAddevent.setEnabled(false);

		ImageIcon img = new ImageIcon("prplus.ico");
		frmPowerRankingGenerator.setIconImage(img.getImage());

		//Image image = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/prplus.ico"));
		//	ImageIcon icon = new ImageIcon( );
		//	frmPowerRankingGenerator.setIconImage(icon.getImage());

		JLabel lblExampleFileDatatxt = new JLabel("Accepted files are txt's (dont include .txt)");
		lblExampleFileDatatxt.setBounds(26, 108, 274, 14);
		frmPowerRankingGenerator.getContentPane().add(lblExampleFileDatatxt);
		JButton btnExportToFile = new JButton("Export to File");
		btnExportToFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
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
			}
		});
		JLabel lblToExcludeOr = new JLabel("To exclude or include matches enter the ");
		lblToExcludeOr.setEnabled(false);
		JRadioButton rdbtnExcludeMatch = new JRadioButton("Exclude Match");
		rdbtnExcludeMatch.setEnabled(false);
		rdbtnExcludeMatch.setSelected(true);
		buttonGroup_2.add(rdbtnExcludeMatch);
		rdbtnExcludeMatch.setBounds(932, 180, 109, 23);
		frmPowerRankingGenerator.getContentPane().add(rdbtnExcludeMatch);

		JLabel lblTheRequiredInformation = new JLabel("the required information and press enter");
		lblTheRequiredInformation.setEnabled(false);
		lblTheRequiredInformation.setBounds(940, 450, 252, 14);

		JRadioButton rdbtnIncludeMatch = new JRadioButton("Include Match");
		rdbtnIncludeMatch.setEnabled(false);
		buttonGroup_2.add(rdbtnIncludeMatch);
		rdbtnIncludeMatch.setBounds(1046, 179, 109, 23);
		frmPowerRankingGenerator.getContentPane().add(rdbtnIncludeMatch);

		JLabel lblToExcludeOr_1 = new JLabel("To exclude or include placings enter");
		lblToExcludeOr_1.setEnabled(false);
		lblToExcludeOr_1.setBounds(942, 428, 223, 14);
		JLabel label = new JLabel("Optional Player Tag");
		btnExportToFile.setEnabled(false);
		btnExportToFile.setBounds(40, 300, 122, 23);
		frmPowerRankingGenerator.getContentPane().add(btnExportToFile);
		TextArea textArea_1 = new TextArea();
		textArea_1.setEditable(false);
		textArea_1.setBounds(234, 146, 678, 385);
		frmPowerRankingGenerator.getContentPane().add(textArea_1);
		JButton btnFromTextFile = new JButton("From file");
		JButton btnLookForTournament = new JButton("Look for Tournament");
		JRadioButton rdbtnMatches = new JRadioButton("Matches");
		rdbtnMatches.setEnabled(false);
		buttonGroup.add(rdbtnMatches);
		JLabel lblMergePlayerA = new JLabel("Merge player A into player B");

		JRadioButton rdbtnMelee = new JRadioButton("Melee");
		rdbtnMelee.setSelected(true);

		rdbtnMatches.setBounds(778, 27, 86, 23);
		frmPowerRankingGenerator.getContentPane().add(rdbtnMatches);
		JLabel lbloptionalSecondPlayer = new JLabel("(optional second player for set history)");
		lbloptionalSecondPlayer.setEnabled(false);
		lbloptionalSecondPlayer.setHorizontalAlignment(SwingConstants.LEFT);
		lbloptionalSecondPlayer.setBounds(554, 60, 234, 14);
		frmPowerRankingGenerator.getContentPane().add(lbloptionalSecondPlayer);
		JLabel lblBelowAndFollow = new JLabel("required information below and press enter");
		lblBelowAndFollow.setEnabled(false);
		lblBelowAndFollow.setBounds(931, 159, 261, 14);
		JButton btnShowExcludedPlacings = new JButton("Show Excluded Placings");
		JRadioButton rdbtnExcludePlacing = new JRadioButton("Exclude Placing");
		rdbtnExcludePlacing.setEnabled(false);
		buttonGroup_3.add(rdbtnExcludePlacing);
		rdbtnExcludePlacing.setSelected(true);
		rdbtnExcludePlacing.setBounds(942, 471, 122, 23);

		JLabel lblTheEventAnd = new JLabel("the event and press enter");
		lblTheEventAnd.setEnabled(false);
		lblTheEventAnd.setBounds(1202, 430, 214, 14);

		JLabel lblOnceListIs = new JLabel("Once list is complete, name ");
		lblOnceListIs.setEnabled(false);
		lblOnceListIs.setBounds(1202, 414, 252, 14);

		txttag_3 = new JTextField();
		txttag_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				txttag_3.setText("");
			}
		});

		JButton btnEnter_3 = new JButton("Enter");

		JRadioButton rdbtnIncludePlacing = new JRadioButton("Include Placing");
		rdbtnIncludePlacing.setEnabled(false);
		buttonGroup_3.add(rdbtnIncludePlacing);
		rdbtnIncludePlacing.setBounds(1068, 471, 124, 23);

		JButton btnEnter_1 = new JButton("Enter");
		btnEnter_1.setEnabled(false);

		JLabel lblChangePlayerName = new JLabel("Change Player Name");

		JButton btnEnter_2 = new JButton("Enter");
		btnEnter_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(txttag_2.getText().equals("(Tag)")){
					JOptionPane.showMessageDialog(null, "Please enter a valid player tag");
				}
				else if(txtevent_1.getText().equals("(Event)")){
					JOptionPane.showMessageDialog(null, "Please enter a valid event name");
				}
				else{
					Player player = null;
					try{
						player = getPlayerFromName(trimSponsor(txttag_2.getText()), players);
					}catch(Exception e){
						JOptionPane.showMessageDialog(null, "Tag not recognized");
					}
					Tournament tournament = getTournamentFromName((txtevent_1.getText().trim()), tournaments);
					if(player == null){
						JOptionPane.showMessageDialog(null, "Tag not recognized");
					}
					else if(tournament == null){
						JOptionPane.showMessageDialog(null, "Event not recognized");
					}
					else{
						if(rdbtnExcludePlacing.isSelected()){
							boolean found = false;
							for(int i = 0; i < player.getPlacings().size(); i++){
								TournamentPlacings currPlacing = player.getPlacings().get(i);
								if(currPlacing.getTournament().equalsIgnoreCase(tournament.getName())){
									found = true;
									excludedPlacings.add(currPlacing);
									includedPlacings.remove(currPlacing);

									for(int j = 0; j < currPlacing.getPlayers().size(); j++){
										Player other = currPlacing.getPlayers().get(j);

										//below for loop updates player A's placing ranking
										for(int k = 0; k < player.getPlacingRankings().size(); k++){
											PlayerRanking otherRanking = player.getPlacingRankings().get(k);

											//need to update player and other players placing ranking
											if(otherRanking.getPlayer().equals(other)){
												if(otherRanking.getAttempts() == 1){
													player.getPlacingRankings().remove(otherRanking);
												}
												else{
													otherRanking.addAttempt(-1);
													int outplaced = currPlacing.didPlayerOutplace(player, other);
													otherRanking.addUpset(outplaced);
												}
												break;
											}
										}

										//below for loop updates player B's placing ranking
										for(int k = 0; k < other.getPlacingRankings().size(); k++){
											PlayerRanking playerRanking = other.getPlacingRankings().get(k);

											if(playerRanking.getPlayer().equals(player)){
												if(playerRanking.getAttempts() == 1){
													other.getPlacingRankings().remove(playerRanking);
												}
												else{
													playerRanking.addAttempt(-1);
													int outplaced = currPlacing.didPlayerOutplace(player, other);
													playerRanking.addUpset(-1*outplaced);
												}
												break;
											}
										}
									}


									player.getPlacings().remove(currPlacing);
									break;
								}
							}
							if(!found) JOptionPane.showMessageDialog(null, "Player did not attend that event");
							else textArea_1.setText("Placing excluded successfully");
						}

						else{
							boolean found = false;
							for( int i = 0; i < excludedPlacings.size(); i++){
								if(excludedPlacings.get(i).getTournament().equalsIgnoreCase(tournament.getName())&&excludedPlacings.get(i).getPlayer().equals(player)){
									found = true;
									TournamentPlacings placing = excludedPlacings.get(i);
									player.addPlacing(placing);
									excludedPlacings.remove(placing);
									includedPlacings.add(placing);
									int aPlacing = placing.getPlacing();
									for(int j = 0; j < placing.getPlayers().size(); j++){
										Player other = placing.getPlayers().get(j);
										if(!other.equals(player)){
											int bPlacing = 0;
											for(int k = 0; k < other.getPlacings().size(); k++){
												if(other.getPlacings().get(k).getTournament().equals(tournament.getName())){
													bPlacing = other.getPlacings().get(k).getPlacing();
													break;
												}
											}
											boolean exists = false;
											for(int k = 0; k < player.getPlacingRankings().size(); k++){
												if(player.getPlacingRankings().get(k).getPlayer().equals(other)){
													exists = true;
													player.getPlacingRankings().get(k).addAttempt(1);
													if(aPlacing > bPlacing){
														player.getPlacingRankings().get(k).addUpset(1);
													}
													else if(aPlacing < bPlacing){
														player.getPlacingRankings().get(k).addUpset(-1);
													}
													break;
												}
											}
											if(!exists){
												PlayerRanking ranking = new PlayerRanking(other);
												ranking.addAttempt(1);
												if(aPlacing > bPlacing){
													ranking.addUpset(1);
												}
												else if(aPlacing < bPlacing){
													ranking.addUpset(-1);
												}
												player.addPlacingRanking(ranking);
											}
											exists = false;
											for(int k = 0; k < other.getPlacingRankings().size(); k++){
												if(other.getPlacingRankings().get(k).getPlayer().equals(player)){
													exists = true;
													other.getPlacingRankings().get(k).addAttempt(1);
													if(aPlacing > bPlacing){
														other.getPlacingRankings().get(k).addUpset(-1);
													}
													else if(aPlacing < bPlacing){
														other.getPlacingRankings().get(k).addUpset(1);
													}
													break;
												}
											}
											if(!exists){
												PlayerRanking ranking = new PlayerRanking(player);
												ranking.addAttempt(1);
												if(aPlacing > bPlacing){
													ranking.addUpset(-1);
												}
												else if(aPlacing < bPlacing){
													ranking.addUpset(1);
												}
												other.addPlacingRanking(ranking);
											}
										}
									}
									break;
								}
							}
							if(!found)	JOptionPane.showMessageDialog(null, "Placing not found");
							else	textArea_1.setText("Placing included successfully");
						}
					}
				}
			}
		});
		btnEnter_2.setEnabled(false);
		btnEnter_2.setBounds(944, 536, 89, 23);

		JRadioButton rdbtnPlacings = new JRadioButton("Placings");
		rdbtnPlacings.setEnabled(false);
		rdbtnPlacings.setSelected(true);
		buttonGroup.add(rdbtnPlacings);
		rdbtnPlacings.setBounds(692, 27, 79, 23);
		frmPowerRankingGenerator.getContentPane().add(rdbtnPlacings);

		JButton btnLookForPlayer = new JButton("Look for Player");
		btnLookForPlayer.setEnabled(false);
		btnLookForPlayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				user = txttag.getText().trim().toUpperCase();
				Player player = null;
				try{
					player = getPlayerFromName(user, players);
				}catch(Exception e){
					JOptionPane.showMessageDialog(null, "Tag not recognized");
				}
				if(txtoptionalSecondPlayer.getText().equals("")||txtoptionalSecondPlayer.getText().equals("(Tag)")){
					if(player!=null){
						//matches
						textArea_1.setText(user+"\n");
						if(rdbtnMatches.isSelected()){
							textArea_1.append("___WINS___\n");
							for(int i =0; i<player.getWins().size(); i++){
								textArea_1.append(player.getName()+" won ("+player.getWins().get(i).getWinScore()+"-"+player.getWins().get(i).getLoseScore()+") over "+player.getWins().get(i).getLoser().getName()+" at "+player.getWins().get(i).getTourney()+"\n");
							}
							textArea_1.append("___LOSSES___\n");
							for(int i =0; i<player.getLosses().size(); i++){
								textArea_1.append(player.getName()+" lost ("+player.getLosses().get(i).getWinScore()+"-"+player.getLosses().get(i).getLoseScore()+") to "+player.getLosses().get(i).getWinner().getName()+" at "+player.getLosses().get(i).getTourney()+"\n");
							}
							Collections.sort(player.getMatchRankings());
							for(int i = 0; i < player.getMatchRankings().size(); i++){
								if(player.getMatchRankings().get(i).getRatio()<0){
									textArea_1.append("Has a WINNING RECORD against "+ player.getMatchRankings().get(i).getPlayer().getName()+" "+ player.getMatchRankings().get(i).getRatio()+". They have "+player.getMatchRankings().get(i).getAttempts()+" sets.\n");
								}
								else if(player.getMatchRankings().get(i).getRatio()==0){
									textArea_1.append("Has a TIEING RECORD against "+ player.getMatchRankings().get(i).getPlayer().getName()+" "+ player.getMatchRankings().get(i).getRatio()+". They have "+player.getMatchRankings().get(i).getAttempts()+" sets.\n");
								}
								else {
									textArea_1.append("Has a LOSING RECORD against "+ player.getMatchRankings().get(i).getPlayer().getName()+" "+ player.getMatchRankings().get(i).getRatio()+". They have "+player.getMatchRankings().get(i).getAttempts()+" sets.\n");
								}
							}
						}
						//standings
						if(rdbtnPlacings.isSelected()){
							textArea_1.setText(user+"\n");
							for(int i = 0; i < player.getPlacings().size(); i++){
								textArea_1.append("Placed "+player.getPlacings().get(i).getPlacing()+" at "+ player.getPlacings().get(i).getTournament()+"\n");
							}
							Collections.sort(player.getPlacingRankings());
							for(int i = 0; i < player.getPlacingRankings().size(); i++){
								if(player.getPlacingRankings().get(i).getRatio()<0){
									textArea_1.append("Places HIGHER than "+ player.getPlacingRankings().get(i).getPlayer().getName()+" ("+ player.getPlacingRankings().get(i).getRatio()+"). With "+player.getPlacingRankings().get(i).getAttempts()+" attempts.\n");
								}
								else if(player.getPlacingRankings().get(i).getRatio()==0){
									textArea_1.append("TIES with "+ player.getPlacingRankings().get(i).getPlayer().getName()+" ("+ player.getPlacingRankings().get(i).getRatio()+"). With "+player.getPlacingRankings().get(i).getAttempts()+" attempts.\n");
								}
								else if(player.getPlacingRankings().get(i).getRatio()>0){
									textArea_1.append("Places LOWER than "+ player.getPlacingRankings().get(i).getPlayer().getName()+" ("+ player.getPlacingRankings().get(i).getRatio()+"). With "+player.getPlacingRankings().get(i).getAttempts()+" attempts.\n");
								}
							}

						}

					}
					else{
						JOptionPane.showMessageDialog(null,"Tag not recognized");
					}
				}
				else{
					if(player!=null){
						Player player2 = null;
						try{
							player2 = getPlayerFromName(txtoptionalSecondPlayer.getText().trim().toUpperCase(), players);
						}catch(Exception e){
							JOptionPane.showMessageDialog(null, "Tag not recognized");
						}
						if(player2!=null){
							boolean played = false;
							//matches
							textArea_1.setText(user+" vs. "+player2.getName()+"\n");
							textArea_1.append("__Matches__\n");
							int match = pr.matchAnalysis(player, player2).getInt();
							if(match < 0){
								textArea_1.append("Indirect match analysis reveals a "+match*-1+" score in "+player.getName()+"'s favor\n");
							}
							else{
								textArea_1.append("Indirect match analysis reveals a "+match+" score in "+player2.getName()+"'s favor\n");
							}
							for(int i = 0; i < player.getMatchRankings().size(); i++){
								if(player.getMatchRankings().get(i).getPlayer().getName().equals(player2.getName())){
									played = true;
									textArea_1.append(user+" has "+player.getMatchRankings().get(i).getGameWins()+"(W)-"+player.getMatchRankings().get(i).getGameLosses()+"(L) game score against "+player2.getName()+"\n");
									for(int j = 0; j < player.getMatchRankings().get(i).getMatches().size(); j++){
										if(player.getMatchRankings().get(i).getMatches().get(j).getWinner().equals(player)){
											textArea_1.append(player.getName()+" beat "+player2.getName()+" "+player.getMatchRankings().get(i).getMatches().get(j).getWinScore()+"-"+player.getMatchRankings().get(i).getMatches().get(j).getLoseScore()+" at "+player.getMatchRankings().get(i).getMatches().get(j).getTourney()+"\n");
										}
										else{
											textArea_1.append(player.getName()+" lost to "+player2.getName()+" "+player.getMatchRankings().get(i).getMatches().get(j).getLoseScore()+"-"+player.getMatchRankings().get(i).getMatches().get(j).getWinScore()+" at "+player.getMatchRankings().get(i).getMatches().get(j).getTourney()+"\n");

										}

									}
									break;
								}
							}
							if(!played){
								textArea_1.append("These players have never played\n");
							}
							played = false;
							match = pr.placingAnalysis(player, player2).getInt();
							textArea_1.append("__Placings__\n");
							if(match<0){
								textArea_1.append("Indirect placing analysis reveals a "+match*-1+" score in "+player.getName()+"'s favor\n");
							}else{
								textArea_1.append("Indirect placing analysis reveals a "+match+" score in "+player2.getName()+"'s favor\n");
							}
							for(int i = 0; i < player.getPlacings().size();i++){
								if(player.getPlacings().get(i).getPlayers().contains(player2)){
									played = true;
									for(int j = 0; j < player2.getPlacings().size(); j++){
										if(player2.getPlacings().get(j).getTournament().equals(player.getPlacings().get(i).getTournament())){
											if(player.getPlacings().get(i).getPlacing() < player2.getPlacings().get(j).getPlacing()){
												textArea_1.append(user+" placed HIGHER than "+player2.getName()+" at "+player.getPlacings().get(i).getTournament()+" ("+player.getPlacings().get(i).getPlacing()+"-"+player2.getPlacings().get(j).getPlacing()+")\n");
											}
											else if (player.getPlacings().get(i).getPlacing() == player2.getPlacings().get(j).getPlacing()){
												textArea_1.append(user+" TIED "+player2.getName()+" at "+player.getPlacings().get(i).getTournament()+" ("+player.getPlacings().get(i).getPlacing()+"-"+player2.getPlacings().get(j).getPlacing()+")\n");
											}
											else{
												textArea_1.append(user+" placed LOWER than "+player2.getName()+" at "+player.getPlacings().get(i).getTournament()+" ("+player.getPlacings().get(i).getPlacing()+"-"+player2.getPlacings().get(j).getPlacing()+")\n");
											}
											break;
										}
									}
									break;
								}
							}
							if(!played){
								textArea_1.append("These players have never attended the same tournament\n");
							}
						}
						else{
							JOptionPane.showMessageDialog(null,"Player 2 tag not recognized");
						}
					}
					else{
						JOptionPane.showMessageDialog(null,"Player 1 tag not recognized");
					}
				}
			}
		});


		btnLookForPlayer.setBounds(554, 27, 132, 23);
		btnLookForPlayer.setEnabled(false);
		frmPowerRankingGenerator.getContentPane().add(btnLookForPlayer);

		txtsorts = new JTextField();
		txtsorts.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				txtsorts.setText("");
			}
		});
		JButton btnShowMatches = new JButton("Show Excluded Matches");
		btnShowMatches.setEnabled(false);
		txtsorts.setEnabled(false);
		txtsorts.setHorizontalAlignment(SwingConstants.CENTER);
		txtsorts.setText("(Sorts)");
		txtsorts.setBounds(62, 195, 86, 20);
		frmPowerRankingGenerator.getContentPane().add(txtsorts);
		txtsorts.setColumns(10);
		txttag = new JTextField();
		txttag.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				txttag.setText("");
			}
		});
		JLabel lblOptionalPlayerTag = new JLabel("Optional Player Tag");
		lblOptionalPlayerTag.setEnabled(false);
		txttag.setEnabled(false);
		txttag.setHorizontalAlignment(SwingConstants.CENTER);
		txttag.setText("(Tag)");
		txttag.setBounds(455, 28, 86, 20);
		frmPowerRankingGenerator.getContentPane().add(txttag);
		txttag.setColumns(10);
		JButton btnPrintPowerRankings = new JButton("Print Power Rankings");
		btnPrintPowerRankings.setEnabled(false);
		btnPrintPowerRankings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textArea_1.setText("");
				int sorts = 5;
				try{
					sorts = Integer.parseInt(txtsorts.getText());
				}catch(Exception e){
					JOptionPane.showMessageDialog(null, "Default number "+sorts+" used");
				}
				if(sorts<0){
					JOptionPane.showMessageDialog(null, "Default number "+sorts+" used");				}
				for(int i = 0; i < sorts; i++){
					pr.sort();
				}
				for(int i = 0; i < pr.getList().size() ; i++){
					textArea_1.append(pr.getList().get(i).getName()+"\n");
				}
			}
		});
		btnPrintPowerRankings.setBounds(20, 226, 183, 23);
		frmPowerRankingGenerator.getContentPane().add(btnPrintPowerRankings);
		JButton btnEnter = new JButton("From URL");
		btnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String url = txthttpmadsmashchallongecommtsdms.getText();
				if(url.equals("")||url.equals("URL")){
					JOptionPane.showMessageDialog(null, "Please provide a valid url:\n\"http://madsmash.challonge.com/mtsd10ms\"\n\"http://challonge.com/u7vaxfqp\"\n\"https://smash.gg/tournament/kings-of-the-north-v/details\"\n\"https://smash.gg/tournament/tales-of-jank-8-big-rigs-and-snapple-sprints\"");
				}
				else{
					if(url.contains("challonge")){
						url = url.replace("https", "http");
						if(url.contains("/standings")){
							url = url.substring(0, url.indexOf("/standings"));
						}
						if(url.contains("www.")){
							url = url.replaceAll("www.", "");
						}

						try {

							boolean alreadyEntered = false;
							String tourneyName;
							if(!url.contains("http://challonge.com")){
			
								url = url.substring(url.indexOf("//")+2);
								tourneyName = url.substring(0, url.indexOf("."))+"-"+url.substring(url.lastIndexOf("/")+1);
							}else{
								tourneyName = url.substring(url.lastIndexOf("/")+1);
							}
							for(int i = 0; i < tournaments.size(); i++){
								if(tournaments.get(i).getName().equals(tourneyName)){
									JOptionPane.showMessageDialog(null, "Tournament already entered");
									alreadyEntered = true;
									break;
								}
							}
							if(!alreadyEntered){
								Tournament newTourney = new Tournament(tourneyName);
								JSONObject json = processChallonge(tourneyName);
								json = (JSONObject) json.get("tournament");
								if(json==null) JOptionPane.showMessageDialog(null, "Hmmm Challonge couldn't find this tournament");
								else{
									try{
									JSONArray participants = (JSONArray) json.get("participants");
									ArrayList<smashGGPlayer> ggPlayers = new  ArrayList<smashGGPlayer>();
									Player standings[] = new Player[participants.size()];
									boolean fixedUrl = false;
									String pageText = "";
									String standArr[] = null;
									for(int i = 0; i < participants.size(); i++){
										JSONObject player =(JSONObject) ((JSONObject)participants.get(i)).get("participant");
										JSONArray group_player_ids = (JSONArray) player.get("group_player_ids");
										for(int j = 0; j < group_player_ids.size(); j++){
											addSmashGGPlayer(players, ggPlayers,  (Long)(( (JSONArray) player.get("group_player_ids")).get(j)), trimSponsor((String)player.get("display_name"))  ).getPlayer();
										}
										addSmashGGPlayer(players, ggPlayers,  (Long) player.get("id"), trimSponsor((String)player.get("display_name")) ).getPlayer();
										int index;
										if(player.get("final_rank")==null){
											if(group_player_ids.size()!=0){
												index = participants.size()-1;
												while(standings[index] != null){
													index--;
												}
												standings[index] = getSmashGGPlayerFromId((Long) player.get("id"), ggPlayers).getPlayer();
											}
											else{
												//TODO: here, we need to get the actual standings from the standings url. 
												if(!fixedUrl){
													url = "http://"+ url +"/standings";
													fixedUrl = true;
													pageText = getPageTextFromURLString(url);
													standArr = pageText.split("<span>");
												}
											}
										}
										else{
											index = ((Long) player.get("final_rank")).intValue() - 1;
											while(standings[index] != null){
												index++;
											}
											standings[index] = getSmashGGPlayerFromId((Long) player.get("id"), ggPlayers).getPlayer();
										}
									}
									if(fixedUrl){
										for(int j =1; j <standArr.length; j++){
											standArr[j] = standArr[j].substring(0, standArr[j].indexOf("</span>"));
											int index = j-1;
											while(standings[index] != null){
												index++;
											}
											standings[index] = getPlayerFromName(trimSponsor(standArr[j]), players);
										}
									}
									for(int i = 0; i<standings.length; i++){
										newTourney.addResults(standings[i]);
									}
									JSONArray matches = (JSONArray) json.get("matches");
									for(int i = 0; i < matches.size(); i++){
										try{
											JSONObject currMatch = (JSONObject)((JSONObject) matches.get(i)).get("match");
											if(((String)currMatch.get("state")).equals("complete")){
												int aScore = 0;
												int bScore = 0;
												String scores_csv = (String)currMatch.get("scores_csv");
												if(scores_csv.contains(",")){
													String scores[] = scores_csv.split(",");
													for(int j = 0; j < scores.length; j++){
														aScore += Integer.parseInt(scores[j].substring(0, scores[j].indexOf("-")))  ;
														bScore += Integer.parseInt(scores[j].substring(scores[j].indexOf("-")+1) )  ;
													}
												}
												else{
													aScore = Integer.parseInt(scores_csv.substring(0, scores_csv.indexOf("-")))  ;
													bScore = Integer.parseInt(scores_csv.substring(scores_csv.indexOf("-")+1))  ;
												}
												if(aScore > -1 && bScore >-1){
													Player playerA = getSmashGGPlayerFromId((Long)currMatch.get("player1_id"), ggPlayers).getPlayer();
													Player playerB = getSmashGGPlayerFromId((Long)currMatch.get("player2_id"), ggPlayers).getPlayer();
													Match newMatch;
													if(aScore > bScore)
														newMatch = new Match(playerA, aScore, bScore, playerB, newTourney.getName());
													else
														newMatch = new Match(playerB, bScore,  aScore,  playerA, newTourney.getName());
													enterMatch(newMatch, includedMatches);
												}
											}
										}catch(Exception e){
											//Here a dq is ignored
										}
									}



									for(int i = 0; i<newTourney.getResults().size(); i++){
										enterPlacing(newTourney.getResults().get(i), newTourney.getName(), i,  newTourney.getResults(), includedPlacings);
									}
									tournaments.add(newTourney);
									for(int i = 0; i<newTourney.getResults().size(); i++){
										for(int j = 0; j < newTourney.getResults().size(); j++){
											Player playerA = newTourney.getResults().get(i);
											int aPlacing = 0;
											Player playerB = newTourney.getResults().get(j);
											int bPlacing = 0;
											if(!playerA.equals(playerB)){
												for(int k = 0; k < playerA.getPlacings().size(); k++){
													if(playerA.getPlacings().get(k).getTournament().equals(newTourney.getName())){
														aPlacing = playerA.getPlacings().get(k).getPlacing();
														break;
													}
												}
												for(int k = 0; k < playerB.getPlacings().size(); k++){
													if(playerB.getPlacings().get(k).getTournament().equals(newTourney.getName())){
														bPlacing = playerB.getPlacings().get(k).getPlacing();
														break;
													}
												}
												boolean exists = false;
												for(int k = 0; k < playerA.getPlacingRankings().size(); k++){
													if(playerA.getPlacingRankings().get(k).getPlayer().equals(playerB)){
														exists = true;
														playerA.getPlacingRankings().get(k).addAttempt(1);
														if(aPlacing > bPlacing){
															playerA.getPlacingRankings().get(k).addUpset(1);
														}
														else if(aPlacing < bPlacing){
															playerA.getPlacingRankings().get(k).addUpset(-1);
														}
														break;
													}
												}
												if(!exists){
													PlayerRanking ranking = new PlayerRanking(playerB);
													ranking.addAttempt(1);
													if(aPlacing > bPlacing){
														ranking.addUpset(1);
													}
													else if(aPlacing < bPlacing){
														ranking.addUpset(-1);
													}
													playerA.addPlacingRanking(ranking);
												}
											}
										}

									}

									//*/
									pr = new SortablePlayerList(players, 2);
									JOptionPane.showMessageDialog(null, "Data imported succesfully");

									//TODO: Initialize elements

									if(!dataEntered){
										label.setEnabled(true);
										txttag_3.setEnabled(true);
										btnShowExcludedPlacings.setEnabled(true);
										btnEnter_4.setEnabled(true);
										txtPlayerB.setEnabled(true);
										txtPlayerA.setEnabled(true);
										lblMergePlayerA.setEnabled(true);
										lblChangePlayerName.setEnabled(true);
										btnEnter_3.setEnabled(true);
										txtoldName.setEnabled(true);
										txtnewName.setEnabled(true);
										btnEnter_2.setEnabled(true);
										txtevent_1.setEnabled(true);
										txttag_2.setEnabled(true);
										rdbtnIncludePlacing.setEnabled(true);
										rdbtnExcludePlacing.setEnabled(true);
										lblTheRequiredInformation.setEnabled(true);
										lblToExcludeOr_1.setEnabled(true);
										btnEnter_1.setEnabled(true);
										txtevent.setEnabled(true);
										txtlosersScore.setEnabled(true);
										txtlosersTag.setEnabled(true);
										txtwinnersScore.setEnabled(true);
										txtresponse.setEnabled(true);
										rdbtnIncludeMatch.setEnabled(true);
										rdbtnExcludeMatch.setEnabled(true);
										lblBelowAndFollow.setEnabled(true);
										lblToExcludeOr.setEnabled(true);
										lblOptionalPlayerTag.setEnabled(true);
										txttag_1.setEnabled(true);
										btnShowMatches.setEnabled(true);
										//	rdbtnExcluded.setEnabled(true);
										//	rdbtnIncluded.setEnabled(true);
										btnLookForPlayer.setEnabled(true);
										btnPrintPowerRankings.setEnabled(true);
										rdbtnPlacings.setEnabled(true);
										rdbtnMatches.setEnabled(true);
										txttag.setEnabled(true);
										txtsorts.setEnabled(true);
										lbloptionalSecondPlayer.setEnabled(true);
										txtoptionalSecondPlayer.setEnabled(true);
										txtname_1.setEnabled(true);
										btnExportToFile.setEnabled(true);
										//btnEnter.setEnabled(false);
										btnFromTextFile.setEnabled(false);
										txtname.setEnabled(false);
										txtname_2.setEnabled(true);
										btnLookForTournament.setEnabled(true);
										lblExampleFileDatatxt.setEnabled(false);
									}
									dataEntered = true;
									}catch(Exception e){ JOptionPane.showMessageDialog(null, "There was an error importing this bracket");}
								}
							}
						} catch (MalformedURLException e) {
							JOptionPane.showMessageDialog(null, "Please provide a valid url:\n\"http://madsmash.challonge.com/mtsd10ms\"\n\"http://challonge.com/u7vaxfqp\"\n\"https://smash.gg/tournament/kings-of-the-north-v/details\"\n\"https://smash.gg/tournament/tales-of-jank-8-big-rigs-and-snapple-sprints\"");

						} catch (IOException e) {
							JOptionPane.showMessageDialog(null, "Please provide a valid url:\n\"http://madsmash.challonge.com/mtsd10ms\"\n\"http://challonge.com/u7vaxfqp\"\n\"https://smash.gg/tournament/kings-of-the-north-v/details\"\n\"https://smash.gg/tournament/tales-of-jank-8-big-rigs-and-snapple-sprints\"");

						} catch (ParseException e) {
							JOptionPane.showMessageDialog(null, "Sometimes challonge sends weird things. You don't need to restart client, but you should export your data and try again.");
						}
					}
					else if(url.contains("smash.gg")){
						String game = "";
						if(rdbtnMelee.isSelected()){
							game = "melee-singles";
						}
						else{
							game = "wii-u-singles";
						}

						ArrayList<smashGGPlayer> smashGGPlayers = new ArrayList<smashGGPlayer>();
						String tournament = url.substring(url.indexOf("tournament")+"tournament".length()+1);
						if(tournament.contains("/"))
							tournament= tournament.substring(0, tournament.indexOf("/"));
						//System.out.println(tournament);

						//String request = "https://api.smash.gg/tournament/"+tournament_slug+"?expand[]=phase";

						String request = "https://api.smash.gg/tournament/"+tournament+"/event/"+game+"/standings?entityType=event&expand[]=entrants&page=1&per_page=25";
						//**
						try  {
							URL webpage = new URL(request);
							Scanner scan = new Scanner(webpage.openStream());
							String pageText = scan.next();
							scan.close();

							Long total_count = Long.parseLong(pageText.substring(pageText.indexOf("total_count")+13, pageText.indexOf(",")));
							Long pages = total_count / 64; 
							if(total_count % 64 != 0) pages++;
							Long finalPlacement = (long) 0;
							Tournament mainBracket = new Tournament(tournament);
							Player[] mainBracketPlayers = new Player[total_count.intValue()];
							for(int i = 1; i < pages+1; i++){
								request = "https://api.smash.gg/tournament/"+tournament+"/event/"+game+"/standings?entityType=event&expand[]=entrants&page="+i+"&per_page=64";
								pageText = getPageTextFromURLString(request);

								JSONParser parser = new JSONParser();
								JSONObject wrapper = (JSONObject) parser.parse(pageText);
								JSONObject items = (JSONObject) wrapper.get("items");
								JSONObject entities = (JSONObject) items.get("entities");
								JSONArray entrants = (JSONArray) entities.get("entrants");
								for(int j = 0; j < entrants.size(); j++){
									JSONObject curr_entrant = (JSONObject) entrants.get(j);
									finalPlacement = (Long) curr_entrant.get("finalPlacement");
									int index = finalPlacement.intValue()-1;
									while(mainBracketPlayers[index] != null){
										index++;
									}
									mainBracketPlayers[index] = addSmashGGPlayer( players, smashGGPlayers, (Long) curr_entrant.get("id"),  trimSponsor((String) curr_entrant.get("name")) ).getPlayer();
								}
							}
							for(int j = 0; j < mainBracketPlayers.length; j++){
								mainBracket.addResults(mainBracketPlayers[j]);
							}
							tournaments.add(mainBracket);
							for(int i = 0; i<mainBracket.getResults().size(); i++){
								enterPlacing(mainBracket.getResults().get(i), mainBracket.getName(), i,  mainBracket.getResults(), includedPlacings);
							}

							for(int x = 0; x<mainBracket.getResults().size(); x++){
								for(int j = 0; j < mainBracket.getResults().size(); j++){
									Player playerA = mainBracket.getResults().get(x);
									int aPlacing = 0;
									Player playerB = mainBracket.getResults().get(j);
									int bPlacing = 0;
									if(!playerA.equals(playerB)){
										for(int k = 0; k < playerA.getPlacings().size(); k++){
											if(playerA.getPlacings().get(k).getTournament().equals(mainBracket.getName())){
												aPlacing = playerA.getPlacings().get(k).getPlacing();
											}
										}
										for(int k = 0; k < playerB.getPlacings().size(); k++){
											if(playerB.getPlacings().get(k).getTournament().equals(mainBracket.getName())){
												bPlacing = playerB.getPlacings().get(k).getPlacing();
											}
										}
										boolean exists = false;
										for(int k = 0; k < playerA.getPlacingRankings().size(); k++){
											if(playerA.getPlacingRankings().get(k).getPlayer().equals(playerB)){
												exists = true;
												playerA.getPlacingRankings().get(k).addAttempt(1);
												if(aPlacing > bPlacing){
													playerA.getPlacingRankings().get(k).addUpset(1);
												}
												else if(aPlacing < bPlacing){
													playerA.getPlacingRankings().get(k).addUpset(-1);
												}
											}
										}
										if(!exists){
											PlayerRanking ranking = new PlayerRanking(playerB);
											ranking.addAttempt(1);
											if(aPlacing > bPlacing){
												ranking.addUpset(1);
											}
											else if(aPlacing < bPlacing){
												ranking.addUpset(-1);
											}
											playerA.addPlacingRanking(ranking);
										}
									}
								}
							}



							request = "https://api.smash.gg/tournament/"+tournament+"/event/"+game+"?expand[]=groups";
							//request = "https://api.smash.gg/phase_group/241630?expand[]=sets";		

							//System.out.println(total_count);
							//System.out.println("_____________");
							//System.out.println(finalPlacement);
							//System.out.println("_____________");
							//System.out.println(request);
							pageText = getPageTextFromURLString(request);

							JSONParser	parser = new JSONParser();
							JSONObject	wrapper = (JSONObject) parser.parse(pageText);
							JSONObject entities = (JSONObject) wrapper.get("entities");
							JSONArray groups = (JSONArray) entities.get("groups");
							//int ambracknum = 0;
							for(int i = 0; i < groups.size(); i++){
								JSONObject curr = (JSONObject) groups.get(i);
								Long phase_group = (Long) curr.get("id");
								String new_request = "https://api.smash.gg/phase_group/"+phase_group+"?expand[]=sets&expand[]=entrants&expand[]=standings&expand[]=seeds";

								//System.out.println("_____________");
								//System.out.println(new_request);

								pageText = getPageTextFromURLString(new_request);

								JSONObject group_wrapper = (JSONObject) parser.parse(pageText);
								JSONObject group_entities = (JSONObject) group_wrapper.get("entities");

								//	JSONObject curr_group = (JSONObject) group_entities.get("groups");
								JSONArray curr_entrants = (JSONArray) group_entities.get("entrants");
								boolean mustUsePlacings = false;
								//this loop initializes player list
								for(int j = 0; j < curr_entrants.size(); j++){
									JSONObject entrant = (JSONObject) curr_entrants.get(j);
									Long curr_placement = (Long) entrant.get("finalPlacement");
									if(curr_placement == null || curr_placement > finalPlacement){
										mustUsePlacings = true;
										break;
									}
								}
								//smashGGPlayer addSmashGGPlayer(ArrayList<Player> players, ArrayList<smashGGPlayer> ggPlayers, Long id, Long finalPlacement, String name ){
								if(mustUsePlacings){
									Tournament newTourney = new Tournament(tournament+"/"+i);
									Player[] tempPlayers = new Player[curr_entrants.size()];
									for(int j = 0; j < curr_entrants.size(); j++){
										JSONObject entrant = (JSONObject) curr_entrants.get(j);
										String entrantName = trimSponsor((String) entrant.get("name"));
										Long entrantID = (Long) entrant.get("id");
										Long curr_placement =  (long) 0;
										JSONArray standings = (JSONArray) group_entities.get("standings");
										for(int k = 0; k < standings.size(); k++){
											JSONObject standing = (JSONObject) standings.get(k);
											if((Long) standing.get("entrantId") - entrantID == 0){
												curr_placement = (Long) standing.get("placement");
												break;
											}
										}
										int index = curr_placement.intValue()-1;
										while(tempPlayers[index] != null){
											index++;
										}
										tempPlayers[index] = addSmashGGPlayer(players, smashGGPlayers, entrantID,  entrantName).getPlayer();
									}

									for(int j = 0; j < curr_entrants.size(); j++){
										//System.out.println(tempPlayers[j].getName());
										newTourney.addResults(tempPlayers[j]);
									}
									for(int j = 0; j < newTourney.getResults().size(); j++){
										enterPlacing(newTourney.getResults().get(j), newTourney.getName(), j,  newTourney.getResults(), includedPlacings);
									}
									tournaments.add(newTourney);

									for(int x = 0; x<newTourney.getResults().size(); x++){
										for(int j = 0; j < newTourney.getResults().size(); j++){
											Player playerA = newTourney.getResults().get(x);
											int aPlacing = 0;
											Player playerB = newTourney.getResults().get(j);
											int bPlacing = 0;
											if(!playerA.equals(playerB)){
												for(int k = 0; k < playerA.getPlacings().size(); k++){
													if(playerA.getPlacings().get(k).getTournament().equals(newTourney.getName())){
														aPlacing = playerA.getPlacings().get(k).getPlacing();
													}
												}
												for(int k = 0; k < playerB.getPlacings().size(); k++){
													if(playerB.getPlacings().get(k).getTournament().equals(newTourney.getName())){
														bPlacing = playerB.getPlacings().get(k).getPlacing();
													}
												}
												boolean exists = false;
												for(int k = 0; k < playerA.getPlacingRankings().size(); k++){
													if(playerA.getPlacingRankings().get(k).getPlayer().equals(playerB)){
														exists = true;
														playerA.getPlacingRankings().get(k).addAttempt(1);
														if(aPlacing > bPlacing){
															playerA.getPlacingRankings().get(k).addUpset(1);
														}
														else if(aPlacing < bPlacing){
															playerA.getPlacingRankings().get(k).addUpset(-1);
														}
													}
												}
												if(!exists){
													PlayerRanking ranking = new PlayerRanking(playerB);
													ranking.addAttempt(1);
													if(aPlacing > bPlacing){
														ranking.addUpset(1);
													}
													else if(aPlacing < bPlacing){
														ranking.addUpset(-1);
													}
													playerA.addPlacingRanking(ranking);
												}
											}
										}
									}
								}
								/**
								else{
									for(int j = 0; j < curr_entrants.size(); j++){
										JSONObject entrant = (JSONObject) curr_entrants.get(j);
										String entrantName = (String) entrant.get("display_name");
										Long entrantID = (Long) entrant.get("id");
										Long curr_placement = (Long) entrant.get("finalPlacement");
										System.out.println(entrantName+" finished "+ curr_placement +" at "+newTourney.getName());
										//smashGGPlayer addSmashGGPlayer(ArrayList<Player> players, ArrayList<smashGGPlayer> ggPlayers, Long id, Long finalPlacement, String name ){
									}
								}
								//*/
								//System.out.println(ambracknum);

								JSONArray group_sets = (JSONArray) group_entities.get("sets");
								for(int j = 0; j < group_sets.size(); j++){
									//	System.out.println(j);
									JSONObject set = (JSONObject) group_sets.get(j);
									Long wID = (Long) set.get("winnerId");
									Long lID = (Long) set.get("loserId");
									if((Long) set.get("entrant1Score") != null && wID != null && lID != null && (Long) set.get("entrant2Score") != null){
										//	System.out.println(wID);

										int aScore = ((Long) set.get("entrant1Score")).intValue();
										int bScore = ((Long) set.get("entrant2Score")).intValue();
										Match match;
										try{
										if(aScore > bScore)
											match = new Match(getSmashGGPlayerFromId(wID, smashGGPlayers).getPlayer(), aScore, bScore, getSmashGGPlayerFromId(lID, smashGGPlayers).getPlayer(), tournament);
										else
											match = new Match(getSmashGGPlayerFromId(wID, smashGGPlayers).getPlayer(), bScore, aScore, getSmashGGPlayerFromId(lID, smashGGPlayers).getPlayer(), tournament);
										enterMatch(match, includedMatches);
										}catch(Exception e){
												JOptionPane.showMessageDialog(null, "Hmm one of the players must have an odd character. Please report this on the GitHub wiki");
										}
										//System.out.println(j);
										//System.out.println(w.getPlayer().getName()+" "+l.getPlayer().getName());
										
									}
								}

							}
							JOptionPane.showMessageDialog(null, "Data imported succesfully");
							//TODO: Initialize elements 

							if(!dataEntered){
								label.setEnabled(true);
								txttag_3.setEnabled(true);
								btnShowExcludedPlacings.setEnabled(true);
								btnEnter_4.setEnabled(true);
								txtPlayerB.setEnabled(true);
								txtPlayerA.setEnabled(true);
								lblMergePlayerA.setEnabled(true);
								lblChangePlayerName.setEnabled(true);
								btnEnter_3.setEnabled(true);
								txtoldName.setEnabled(true);
								txtnewName.setEnabled(true);
								btnEnter_2.setEnabled(true);
								txtevent_1.setEnabled(true);
								txttag_2.setEnabled(true);
								rdbtnIncludePlacing.setEnabled(true);
								rdbtnExcludePlacing.setEnabled(true);
								lblTheRequiredInformation.setEnabled(true);
								lblToExcludeOr_1.setEnabled(true);
								btnEnter_1.setEnabled(true);
								txtevent.setEnabled(true);
								txtlosersScore.setEnabled(true);
								txtlosersTag.setEnabled(true);
								txtwinnersScore.setEnabled(true);
								txtresponse.setEnabled(true);
								rdbtnIncludeMatch.setEnabled(true);
								rdbtnExcludeMatch.setEnabled(true);
								lblBelowAndFollow.setEnabled(true);
								lblToExcludeOr.setEnabled(true);
								lblOptionalPlayerTag.setEnabled(true);
								txttag_1.setEnabled(true);
								btnShowMatches.setEnabled(true);
								//	rdbtnExcluded.setEnabled(true);
								//	rdbtnIncluded.setEnabled(true);
								btnLookForPlayer.setEnabled(true);
								btnPrintPowerRankings.setEnabled(true);
								rdbtnPlacings.setEnabled(true);
								rdbtnMatches.setEnabled(true);
								txttag.setEnabled(true);
								txtsorts.setEnabled(true);
								lbloptionalSecondPlayer.setEnabled(true);
								txtoptionalSecondPlayer.setEnabled(true);
								txtname_1.setEnabled(true);
								btnExportToFile.setEnabled(true);
								//btnEnter.setEnabled(false);
								btnFromTextFile.setEnabled(false);
								txtname.setEnabled(false);
								txtname_2.setEnabled(true);
								btnLookForTournament.setEnabled(true);
								lblExampleFileDatatxt.setEnabled(false);

							}
							pr = new SortablePlayerList(players, 2);
							dataEntered = true;
						}

						catch (IOException e) {
							JOptionPane.showMessageDialog(null, "Please provide a valid url:\n\"http://madsmash.challonge.com/mtsd10ms\"\n\"http://challonge.com/u7vaxfqp\"\n\"https://smash.gg/tournament/kings-of-the-north-v/details\"\n\"https://smash.gg/tournament/tales-of-jank-8-big-rigs-and-snapple-sprints\"");

						} catch (ParseException e) {
							JOptionPane.showMessageDialog(null, "Please provide a valid url:\n\"http://madsmash.challonge.com/mtsd10ms\"\n\"http://challonge.com/u7vaxfqp\"\n\"https://smash.gg/tournament/kings-of-the-north-v/details\"\n\"https://smash.gg/tournament/tales-of-jank-8-big-rigs-and-snapple-sprints\"");
						}
					/**	catch (Exception e) {
							JOptionPane.showMessageDialog(null, "Sometimes Smash.gg sends invalid data.\nThere is nothing I can do about it, I apologize.\nTry restarting this client and entering this tournament first.\nThen, I would suggest exporting your progress regularly, in case this happens again.");
						}*/
					}
					else{
						JOptionPane.showMessageDialog(null, "Please provide a valid url:\n\"http://madsmash.challonge.com/mtsd10ms\"\n\"http://challonge.com/u7vaxfqp\"\n\"https://smash.gg/tournament/kings-of-the-north-v/details\"\n\"https://smash.gg/tournament/tales-of-jank-8-big-rigs-and-snapple-sprints\"");
					}
				}

			}
		});
		btnEnter.setBounds(251, 80, 98, 23);
		frmPowerRankingGenerator.getContentPane().add(btnEnter);

		txtname = new JTextField();
		txtname.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				txtname.setText("");
			}
		});
		txtname.setHorizontalAlignment(SwingConstants.CENTER);
		txtname.setText("(Name)");
		txtname.setBounds(20, 133, 86, 20);
		frmPowerRankingGenerator.getContentPane().add(txtname);
		txtname.setColumns(10);

		lblHowToImport = new JLabel("How would you like to import data?");
		lblHowToImport.setBounds(20, 11, 208, 14);
		frmPowerRankingGenerator.getContentPane().add(lblHowToImport);


		btnFromTextFile.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				Scanner scanner = null;
				try{
					scanner = new Scanner (new File(txtname.getText()+".txt"));
					String line = "";
					while(scanner.hasNextLine()){
						line = scanner.nextLine();
						if(line.equals("__PlayerList__")){
							line = scanner.nextLine();
							while(!line.equals("__Tournaments__")){
								players.add(new Player(line));
								line = scanner.nextLine();
							}
						}
						if(line.equals("__Tournaments__")){
							line = scanner.nextLine();
							while(!line.equals("__Players__")){
								Tournament newTourney = new Tournament(line);
								line = scanner.nextLine();
								while(!line.equals("__")){
									newTourney.addResults(getPlayerFromName(line, players));
									line = scanner.nextLine();
								}
								tournaments.add(newTourney);
								line = scanner.nextLine();
								for(int i = 0; i<newTourney.getResults().size(); i++){
									enterPlacing(newTourney.getResults().get(i), newTourney.getName(), i,  newTourney.getResults(), includedPlacings);
								}
							}
						}
						if(line.equals("__Players__")){
							line = scanner.nextLine();

							while(!line.equals("__Excluded Matches__")){
								Player newPlayer = getPlayerFromName(line, players);
								line = scanner.nextLine();

								if(line.equals("Placings:")){
									line = scanner.nextLine();
									//**		
									while(!line.equals("Match Rankings:")){
										//newPlayer.addPlacingRanking(line.substring(0,line.indexOf(" ")), Integer.parseInt(line.substring(line.indexOf(" ")+1,line.length())), players);
										line = scanner.nextLine();
									}// */
								}
								if(line.equals("Match Rankings:")){
									line = scanner.nextLine();
									while(!line.equals("Placement Rankings:")){
										PlayerRanking newRanking = new PlayerRanking(getPlayerFromName(line.substring(0, line.indexOf(" ")), players));
										newRanking.setUpsets(Integer.parseInt(line.substring(line.indexOf(" ")+1,line.lastIndexOf(" "))));
										newRanking.setAttempts(Integer.parseInt(line.substring(line.lastIndexOf(" ")+1)));
										newPlayer.addMatchRanking(newRanking);
										line = scanner.nextLine();
									}
								}
								if(line.equals("Placement Rankings:")){
									line = scanner.nextLine();
									while(!line.equals("Wins:")){
										PlayerRanking newRanking = new PlayerRanking(getPlayerFromName(line.substring(0, line.indexOf(" ")), players));
										newRanking.setUpsets(Integer.parseInt(line.substring(line.indexOf(" ")+1,line.lastIndexOf(" "))));
										newRanking.setAttempts(Integer.parseInt(line.substring(line.lastIndexOf(" ")+1)));
										line = scanner.nextLine();
										newPlayer.addPlacingRanking(newRanking);
									}
								}
								if(line.equals("Wins:")){
									line = scanner.nextLine();
									while(!line.equals("Losses:")){
										String[] tokens = line.split(" ");
										Player playerB = getPlayerFromName(tokens[3], players);
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
										line = scanner.nextLine();
									}
								}
								if(line.equals("Losses:")){
									line = scanner.nextLine();
									while(!line.equals("__")){
										String[] tokens = line.split(" ");
										Player playerB = getPlayerFromName(tokens[3], players);
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

										line = scanner.nextLine();
									}
								}
							}
						}
						if(line.equals("__Excluded Matches__")){
							line = scanner.nextLine();
							while(!line.equals("__Excluded Placings__")){
								String[] tokens = line.split(" ");
								Match newMatch= new Match(getPlayerFromName(tokens[0],players), Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]), getPlayerFromName(tokens[3],players), tokens[4]);
								excludedMatches.add(newMatch);
								line = scanner.nextLine();
							}
						}
						if(line.equals("__Excluded Placings__")){
							while(scanner.hasNextLine()){
								line = scanner.nextLine();

								String[] tokens = line.split(" ");
								Player player = getPlayerFromName(tokens[0], players);
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
							}
						}
					}
					//TODO: Initialize elements
					label.setEnabled(true);
					txttag_3.setEnabled(true);
					btnShowExcludedPlacings.setEnabled(true);
					btnEnter_4.setEnabled(true);
					txtPlayerB.setEnabled(true);
					txtPlayerA.setEnabled(true);
					lblMergePlayerA.setEnabled(true);
					lblChangePlayerName.setEnabled(true);
					btnEnter_3.setEnabled(true);
					txtoldName.setEnabled(true);
					txtnewName.setEnabled(true);
					btnEnter_2.setEnabled(true);
					txtevent_1.setEnabled(true);
					txttag_2.setEnabled(true);
					rdbtnIncludePlacing.setEnabled(true);
					rdbtnExcludePlacing.setEnabled(true);
					lblTheRequiredInformation.setEnabled(true);
					lblToExcludeOr_1.setEnabled(true);
					btnEnter_1.setEnabled(true);
					txtevent.setEnabled(true);
					txtlosersScore.setEnabled(true);
					txtlosersTag.setEnabled(true);
					txtwinnersScore.setEnabled(true);
					txtresponse.setEnabled(true);
					rdbtnIncludeMatch.setEnabled(true);
					rdbtnExcludeMatch.setEnabled(true);
					lblBelowAndFollow.setEnabled(true);
					lblToExcludeOr.setEnabled(true);
					lblOptionalPlayerTag.setEnabled(true);
					txttag_1.setEnabled(true);
					btnShowMatches.setEnabled(true);
					//	rdbtnExcluded.setEnabled(true);
					//	rdbtnIncluded.setEnabled(true);
					btnLookForPlayer.setEnabled(true);
					btnPrintPowerRankings.setEnabled(true);
					rdbtnPlacings.setEnabled(true);
					rdbtnMatches.setEnabled(true);
					txttag.setEnabled(true);
					txtsorts.setEnabled(true);
					lbloptionalSecondPlayer.setEnabled(true);
					txtoptionalSecondPlayer.setEnabled(true);
					txtname_1.setEnabled(true);
					btnExportToFile.setEnabled(true);
					//btnEnter.setEnabled(false);
					btnFromTextFile.setEnabled(false);
					txtname.setEnabled(false);
					txtname_2.setEnabled(true);
					btnLookForTournament.setEnabled(true);
					lblExampleFileDatatxt.setEnabled(false);
					pr = new SortablePlayerList(players, 2);
					JOptionPane.showMessageDialog(null, "Data imported succesfully");
				}
				catch(Exception e){
					JOptionPane.showMessageDialog(null, "Could not find file");
				}

			}

		});
		btnFromTextFile.setForeground(Color.BLACK);
		btnFromTextFile.setBounds(117, 133, 86, 23);
		frmPowerRankingGenerator.getContentPane().add(btnFromTextFile);

		txtoptionalSecondPlayer = new JTextField();
		txtoptionalSecondPlayer.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				txtoptionalSecondPlayer.setText("");
			}
		});
		txtoptionalSecondPlayer.setText("(Tag)");
		txtoptionalSecondPlayer.setEnabled(false);
		txtoptionalSecondPlayer.setHorizontalAlignment(SwingConstants.CENTER);
		txtoptionalSecondPlayer.setBounds(455, 59, 86, 20);
		frmPowerRankingGenerator.getContentPane().add(txtoptionalSecondPlayer);
		txtoptionalSecondPlayer.setColumns(10);



		txtname_1 = new JTextField();
		txtname_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				txtname_1.setText("");
			}
		});
		txtname_1.setEnabled(false);
		txtname_1.setHorizontalAlignment(SwingConstants.CENTER);
		txtname_1.setText("(Name)");
		txtname_1.setBounds(62, 269, 86, 20);
		frmPowerRankingGenerator.getContentPane().add(txtname_1);
		txtname_1.setColumns(10);

		txtname_2 = new JTextField();
		txtname_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				txtname_2.setText("");
			}
		});
		txtname_2.setEnabled(false);
		txtname_2.setHorizontalAlignment(SwingConstants.CENTER);
		txtname_2.setText("(Name)");
		txtname_2.setBounds(455, 89, 86, 20);
		frmPowerRankingGenerator.getContentPane().add(txtname_2);
		txtname_2.setColumns(10);


		btnLookForTournament.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Tournament tourney = getTournamentFromName(txtname_2.getText().trim(), tournaments);
				if(tourney==null){
					JOptionPane.showMessageDialog(null, "Tournament does not exist");
				}
				else{
					textArea_1.setText("");
					textArea_1.append(tourney.getName()+"\n");
					for(int i = 0; i< tourney.getResults().size(); i++){
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
						textArea_1.append(tourney.getResults().get(i).getName()+" placed "+place+"\n");
					}
				}
			}
		});
		btnLookForTournament.setEnabled(false);
		btnLookForTournament.setBounds(554, 88, 161, 23);
		frmPowerRankingGenerator.getContentPane().add(btnLookForTournament);


		btnShowMatches.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textArea_1.setText("");
				if(excludedMatches.size() == 0)textArea_1.append("No matches excluded\n");
				else if(txttag_1.getText().equals("(Tag)") || txttag_1.getText().equals("")){
					for(int i = 0; i< excludedMatches.size(); i++){
						Match mat = excludedMatches.get(i);
						textArea_1.append(mat.getWinner().getName()+" "+mat.getWinScore()+"-"+mat.getLoseScore()+" "+mat.getLoser().getName()+" at "+mat.getTourney()      +"\n");
					}
				}
				else{
					try{
						Player player = getPlayerFromName(trimSponsor(txttag_1.getText()),players);
						textArea_1.append(player.getName()+"\n");
						if(excludedMatches.size() == 0)textArea_1.append("No matches excluded\n");
						for(int i = 0; i< excludedMatches.size(); i++){
							Match mat = excludedMatches.get(i);
							if(mat.getWinner().equals(player)||mat.getLoser().equals(player))
								textArea_1.append(mat.getWinner().getName()+" "+mat.getWinScore()+"-"+mat.getLoseScore()+" "+mat.getLoser().getName()+" at "+mat.getTourney()+"\n");
						}
					}catch(Exception e){
						JOptionPane.showMessageDialog(null, "Player not found, check your spelling");
					}
				}
			}
		});
		btnShowMatches.setBounds(942, 62, 189, 23);
		frmPowerRankingGenerator.getContentPane().add(btnShowMatches);

		btnEnter_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					Player playerA = getPlayerFromName(trimSponsor(txtPlayerA.getText().trim()), players);
					Player playerB = getPlayerFromName(trimSponsor(txtPlayerB.getText().trim()), players);

					for(int i = 0; i < playerA.getPlacings().size(); i++){
						TournamentPlacings currPlayerAplacing = playerA.getPlacings().get(i);
						int index = currPlayerAplacing.getPlayers().indexOf(playerA);
						currPlayerAplacing.getPlayers().set(index, playerB);
						currPlayerAplacing.setPlayer(playerB);
						playerB.addPlacing(currPlayerAplacing);
					}
					for(int i = 0; i < playerA.getPlacingRankings().size(); i++){
						PlayerRanking currPlayerAplacingRanking = playerA.getPlacingRankings().get(i);
						Player playerAother = currPlayerAplacingRanking.getPlayer();
						boolean found = false;
						for(int j = 0; j < playerB.getPlacingRankings().size(); j++){
							PlayerRanking currPlayerBplacingRanking = playerB.getPlacingRankings().get(j);
							Player playerBother = currPlayerBplacingRanking.getPlayer();
							if(playerAother == playerBother){
								found = true;
								currPlayerBplacingRanking.addAttempt(currPlayerAplacingRanking.getAttempts());
								currPlayerBplacingRanking.addUpset(currPlayerAplacingRanking.getUpsets());
								for(int k =0; k < playerBother.getPlacingRankings().size(); k++){
									if(playerBother.getPlacingRankings().get(k).getPlayer().equals(playerB)){
										playerBother.getPlacingRankings().get(k).addAttempt(currPlayerAplacingRanking.getAttempts());
										playerBother.getPlacingRankings().get(k).addUpset(-1*currPlayerAplacingRanking.getUpsets());
									}
									else if(playerBother.getPlacingRankings().get(k).getPlayer().equals(playerA)){
										playerBother.getPlacingRankings().remove(k);
									}

								}

								break;
							}
						}
						if(!found){
							playerB.addPlacingRanking(currPlayerAplacingRanking);
							for(int k =0; k < playerAother.getPlacingRankings().size(); k++){
								if(playerAother.getPlacingRankings().get(k).getPlayer().equals(playerA)){
									playerAother.getPlacingRankings().get(k).setPlayer(playerB);
									break;
								}
							}
						}
					}
					for(int i = 0; i < playerA.getMatchRankings().size(); i++){
						PlayerRanking currPlayerAmatchRanking = playerA.getMatchRankings().get(i);
						Player playerAother = currPlayerAmatchRanking.getPlayer();
						boolean found = false;
						for(int j = 0; j < playerB.getMatchRankings().size(); j++){
							PlayerRanking currPlayerBmatchRanking = playerB.getMatchRankings().get(j);
							Player playerBother = currPlayerBmatchRanking.getPlayer();
							if(playerAother == playerBother){
								found = true;
								currPlayerBmatchRanking.addAttempt(currPlayerAmatchRanking.getAttempts());
								currPlayerBmatchRanking.addUpset(currPlayerAmatchRanking.getUpsets());
								currPlayerBmatchRanking.addGameWin(currPlayerAmatchRanking.getGameWins());
								currPlayerBmatchRanking.addGameLoss(currPlayerAmatchRanking.getGameLosses());
								for(int l =0; l < currPlayerAmatchRanking.getMatches().size(); l++){
									currPlayerBmatchRanking.addMatch(currPlayerAmatchRanking.getMatches().get(l));
								}
								for(int k =0; k < playerBother.getMatchRankings().size(); k++){
									if(playerBother.getMatchRankings().get(k).getPlayer().equals(playerB)){
										PlayerRanking playerBotherRanking = playerBother.getMatchRankings().get(k);
										playerBotherRanking.addAttempt(currPlayerAmatchRanking.getAttempts());
										playerBotherRanking.addUpset(-1*currPlayerAmatchRanking.getUpsets());
										playerBotherRanking.addGameWin(currPlayerAmatchRanking.getGameLosses());
										playerBotherRanking.addGameLoss(currPlayerAmatchRanking.getGameWins());
										for(int l =0; l < currPlayerAmatchRanking.getMatches().size(); l++){
											playerBotherRanking.addMatch(currPlayerAmatchRanking.getMatches().get(l));
										}
									}
									else if(playerBother.getMatchRankings().get(k).getPlayer().equals(playerA))
										playerBother.getMatchRankings().remove(k);
								}
								break;
							}
						}
						if(!found){
							playerB.addMatchRanking(currPlayerAmatchRanking);
							for(int k =0; k < playerAother.getMatchRankings().size(); k++){
								if(playerAother.getMatchRankings().get(k).getPlayer().equals(playerA)){
									playerAother.getMatchRankings().get(k).setPlayer(playerB);
									break;
								}
							}
						}

					}
					for(int i = 0; i < playerA.getWins().size(); i++){
						//TODO:  we must also update the match object to include the correct players. 
						Match match = playerA.getWins().get(i);
						match.setWinner(playerB);
						playerB.addWin(match);

					}
					for(int i = 0; i < playerA.getLosses().size(); i++){
						Match match =playerA.getLosses().get(i);
						match.setLoser(playerB);
						playerB.addLoss(match);
					}
					players.remove(playerA);
					textArea_1.setText("Players combined successfully");
				
				}catch(Exception y){
					JOptionPane.showMessageDialog(null, "Players not found, check your spelling");
				}
			}
		});


		txttag_1 = new JTextField();
		txttag_1.setEnabled(false);
		txttag_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				txttag_1.setText("");
			}
		});
		txttag_1.setHorizontalAlignment(SwingConstants.CENTER);
		txttag_1.setText("(Tag)");
		txttag_1.setBounds(942, 95, 86, 20);
		frmPowerRankingGenerator.getContentPane().add(txttag_1);
		txttag_1.setColumns(10);


		lblOptionalPlayerTag.setBounds(1043, 98, 122, 14);
		frmPowerRankingGenerator.getContentPane().add(lblOptionalPlayerTag);

		txtresponse = new JTextField();
		txtresponse.setEnabled(false);
		txtresponse.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				txtresponse.setText("");
			}
		});
		txtresponse.setHorizontalAlignment(SwingConstants.CENTER);
		txtresponse.setText("(Winner's Tag)");
		txtresponse.setBounds(932, 210, 98, 20);
		frmPowerRankingGenerator.getContentPane().add(txtresponse);
		txtresponse.setColumns(10);


		btnEnter_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//int step = 0;
				textArea_1.setText("");
				if(rdbtnExcludeMatch.isSelected()){
					if(txtresponse.getText().equals("(Winner's Tag)") || txtwinnersScore.getText().equals("(Winner's Score)")||txtlosersScore.getText().equals("(Loser's Score)") || txtlosersTag.getText().equals("(Loser's Tag)") || txtevent.getText().equals("(Event)")){
						textArea_1.append("Please type the name of the winning player in the first box, the winner's score in the second box,\n the loser in the third box, the loser's score in the fourth box, and the event name in the fifth\n");
					}
					else{
						try{
						Player w = getPlayerFromName(trimSponsor(txtresponse.getText()), players);
						Player l = getPlayerFromName(trimSponsor(txtlosersTag.getText()), players);
						
							int ws = Integer.parseInt(txtwinnersScore.getText().trim());
							int ls = Integer.parseInt(txtlosersScore.getText().trim());
							if(w==null || l == null){
								JOptionPane.showMessageDialog(null, "Couldn't find players, check your spelling");
							}
							else{
								boolean found = false;
								for(int i = 0; i < includedMatches.size();i++){
									Match curr = includedMatches.get(i);
									if(curr.getWinner().equals(w) && curr.getLoser().equals(l) && curr.getWinScore()==ws && curr.getLoseScore()==ls&&curr.getTourney().equalsIgnoreCase(txtevent.getText().trim())){
										//if we find the correct match we must exclude it and update player rankings				
										for(int j = 0; j<w.getMatchRankings().size();j++){
											PlayerRanking playerRanking = w.getMatchRankings().get(j);
											if(playerRanking.getPlayer().equals(l)){
												found = true;
												if(playerRanking.getAttempts()==1){
													w.getMatchRankings().remove(j);
												}
												else{
													playerRanking.addAttempt(-1);
													playerRanking.addUpset(1);
													playerRanking.addGameWin(-1*ws);
													playerRanking.addGameLoss(-1*ls);
													for(int k = 0; k < playerRanking.getMatches().size(); k++){
														if(playerRanking.getMatches().get(k).equals(curr)){
															playerRanking.getMatches().remove(k);
															break;
														}
													}
												}
												break;
											}
										}
										ArrayList<Match> matchList = w.getWins();
										matchList.remove(matchList.indexOf(curr));
										for(int j = 0; j<l.getMatchRankings().size();j++){
											PlayerRanking playerRanking = l.getMatchRankings().get(j);
											if(playerRanking.getPlayer().equals(w)){
												if(playerRanking.getAttempts()==1){
													l.getMatchRankings().remove(j);
												}
												else{
													playerRanking.addAttempt(-1);
													playerRanking.addUpset(-1);
													playerRanking.addGameWin(-1*ls);
													playerRanking.addGameLoss(-1*ws);
													//loser and winner dont have the same match
													for(int k = 0; k < playerRanking.getMatches().size(); k++){
														Match newMatch = playerRanking.getMatches().get(k);
														if(newMatch.getWinner().equals(w) && newMatch.getLoser().equals(l)&&newMatch.getWinScore() == ws && newMatch.getLoseScore() == ls && newMatch.getTourney().equalsIgnoreCase(txtevent.getText().trim())){
															playerRanking.getMatches().remove(k);
															break;
														}
													}
												}
												break;
											}
										}
										excludedMatches.add(curr);
										includedMatches.remove(i);
										textArea_1.append("Match excluded successfully");
										matchList = l.getLosses();
										matchList.remove(matchList.indexOf(curr));
										break;
									}
								}
								if(!found) JOptionPane.showMessageDialog(null, "Match not found");
							}
						}catch(Exception e){
							JOptionPane.showMessageDialog(null, "Match not found");
						}
					}
				}
				else{
					if(txtresponse.getText().equals("(Winner's Tag)") || txtwinnersScore.getText().equals("(Winner's Score)")||txtlosersScore.getText().equals("(Loser's Score)") || txtlosersTag.getText().equals("(Loser's Tag)") || txtevent.getText().equals("(Event)")){
						textArea_1.append("Please type the name of the winning player in the first box, the winner's score in the second box,\n the loser in the third box, the loser's score in the fourth box, and the event name in the fifth\n");
					}
					else{
						try{
						Player w = getPlayerFromName(trimSponsor(txtresponse.getText()), players);
						Player l = getPlayerFromName(trimSponsor(txtlosersTag.getText()), players);
						int ws = Integer.parseInt(txtwinnersScore.getText().trim());
						int ls = Integer.parseInt(txtlosersScore.getText().trim());

						boolean found = false;
						for(int i = 0; i < excludedMatches.size();i++){
							Match curr = excludedMatches.get(i);
							if(curr.getWinner().equals(w) && curr.getLoser().equals(l) && curr.getWinScore()==ws && curr.getLoseScore()==ls&&curr.getTourney().equalsIgnoreCase(txtevent.getText().trim())){
								//if we find the correct match we must exclude it and update player rankings
								found = true;
								excludedMatches.remove(i);
								enterMatch(curr, includedMatches);
								textArea_1.append("Match included successfully");
								break;
							}
						}
						if(!found) JOptionPane.showMessageDialog(null, "Match not found");
						}catch(Exception e){
							JOptionPane.showMessageDialog(null, "Match not found");
						}
					}

				}
			}
		});
		btnEnter_1.setBounds(1046, 269, 89, 23);
		frmPowerRankingGenerator.getContentPane().add(btnEnter_1);


		lblToExcludeOr.setLabelFor(btnEnter_1);
		lblToExcludeOr.setBounds(931, 133, 234, 23);
		frmPowerRankingGenerator.getContentPane().add(lblToExcludeOr);


		frmPowerRankingGenerator.getContentPane().add(lblBelowAndFollow);

		txtlosersTag = new JTextField();
		txtlosersTag.setEnabled(false);
		txtlosersTag.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				txtlosersTag.setText("");
			}
		});
		txtlosersTag.setText("(Loser's Tag)");
		txtlosersTag.setHorizontalAlignment(SwingConstants.CENTER);
		txtlosersTag.setBounds(932, 238, 98, 20);
		frmPowerRankingGenerator.getContentPane().add(txtlosersTag);
		txtlosersTag.setColumns(10);

		txtwinnersScore = new JTextField();
		txtwinnersScore.setEnabled(false);
		txtwinnersScore.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				txtwinnersScore.setText("");
			}
		});
		txtwinnersScore.setHorizontalAlignment(SwingConstants.CENTER);
		txtwinnersScore.setText("(Winner's Score)");
		txtwinnersScore.setBounds(1046, 210, 109, 20);
		frmPowerRankingGenerator.getContentPane().add(txtwinnersScore);
		txtwinnersScore.setColumns(10);

		txtlosersScore = new JTextField();
		txtlosersScore.setEnabled(false);
		txtlosersScore.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				txtlosersScore.setText("");
			}
		});
		txtlosersScore.setHorizontalAlignment(SwingConstants.CENTER);
		txtlosersScore.setText("(Loser's Score)");
		txtlosersScore.setBounds(1046, 238, 109, 20);
		frmPowerRankingGenerator.getContentPane().add(txtlosersScore);
		txtlosersScore.setColumns(10);

		txtevent = new JTextField();
		txtevent.setEnabled(false);
		txtevent.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				txtevent.setText("");
			}
		});
		txtevent.setHorizontalAlignment(SwingConstants.CENTER);
		txtevent.setText("(Event)");
		txtevent.setBounds(932, 269, 98, 20);
		frmPowerRankingGenerator.getContentPane().add(txtevent);
		txtevent.setColumns(10);

		txthttpmadsmashchallongecommtsdms = new JTextField();
		txthttpmadsmashchallongecommtsdms.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				txthttpmadsmashchallongecommtsdms.setText("");
			}
		});
		txthttpmadsmashchallongecommtsdms.setText("URL");
		txthttpmadsmashchallongecommtsdms.setBounds(20, 81, 221, 20);
		frmPowerRankingGenerator.getContentPane().add(txthttpmadsmashchallongecommtsdms);
		txthttpmadsmashchallongecommtsdms.setColumns(10);

		JLabel lblExampleUrlHttpmadsmashchallongecommtsdms = new JLabel("Accepted Url's are smash.gg and challonge.com");
		lblExampleUrlHttpmadsmashchallongecommtsdms.setBounds(20, 28, 346, 23);
		frmPowerRankingGenerator.getContentPane().add(lblExampleUrlHttpmadsmashchallongecommtsdms);

		txttag_2 = new JTextField();
		txttag_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				txttag_2.setText("");
			}
		});
		txttag_2.setHorizontalAlignment(SwingConstants.CENTER);
		txttag_2.setEnabled(false);
		txttag_2.setText("(Tag)");
		txttag_2.setBounds(944, 505, 86, 20);
		frmPowerRankingGenerator.getContentPane().add(txttag_2);
		txttag_2.setColumns(10);


		frmPowerRankingGenerator.getContentPane().add(lblToExcludeOr_1);


		frmPowerRankingGenerator.getContentPane().add(lblTheRequiredInformation);

		txtevent_1 = new JTextField();
		txtevent_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				txtevent_1.setText("");
			}
		});
		txtevent_1.setHorizontalAlignment(SwingConstants.CENTER);
		txtevent_1.setEnabled(false);
		txtevent_1.setText("(Event)");
		txtevent_1.setBounds(1045, 505, 86, 20);
		frmPowerRankingGenerator.getContentPane().add(txtevent_1);
		txtevent_1.setColumns(10);


		frmPowerRankingGenerator.getContentPane().add(rdbtnExcludePlacing);


		frmPowerRankingGenerator.getContentPane().add(rdbtnIncludePlacing);


		frmPowerRankingGenerator.getContentPane().add(btnEnter_2);

		txtoldName = new JTextField();
		txtoldName.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				txtoldName.setText("");
			}
		});
		txtoldName.setEnabled(false);
		txtoldName.setHorizontalAlignment(SwingConstants.CENTER);
		txtoldName.setText("(Old Name)");
		txtoldName.setBounds(20, 366, 86, 20);
		frmPowerRankingGenerator.getContentPane().add(txtoldName);
		txtoldName.setColumns(10);

		txtnewName = new JTextField();
		txtnewName.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				txtnewName.setText("");
			}
		});
		txtnewName.setEnabled(false);
		txtnewName.setHorizontalAlignment(SwingConstants.CENTER);
		txtnewName.setText("(New Name)");
		txtnewName.setBounds(117, 366, 86, 20);
		frmPowerRankingGenerator.getContentPane().add(txtnewName);
		txtnewName.setColumns(10);


		lblChangePlayerName.setEnabled(false);
		lblChangePlayerName.setBounds(20, 341, 153, 14);
		frmPowerRankingGenerator.getContentPane().add(lblChangePlayerName);


		btnEnter_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String oldName = trimSponsor(txtoldName.getText().trim());
				String newName = trimSponsor(txtnewName.getText().trim());
				Player player = null;
				Player newPlayer = null;
				try{
					player = getPlayerFromName(oldName, players);
					try{
						newPlayer = getPlayerFromName(newName, players);
						JOptionPane.showMessageDialog(null, "That tag is already being used!");
					}catch(Exception e){
						player.setName(newName);
						textArea_1.setText("Name changed successfully");
					}
				}
				catch(Exception e){
					JOptionPane.showMessageDialog(null, "Player not found, check spelling");
				}
			}
		});
		btnEnter_3.setEnabled(false);
		btnEnter_3.setBounds(117, 392, 89, 23);
		frmPowerRankingGenerator.getContentPane().add(btnEnter_3);


		lblMergePlayerA.setEnabled(false);
		lblMergePlayerA.setBounds(26, 449, 202, 14);
		frmPowerRankingGenerator.getContentPane().add(lblMergePlayerA);

		txtPlayerA = new JTextField();
		txtPlayerA.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				txtPlayerA.setText("");
			}
		});
		txtPlayerA.setEnabled(false);
		txtPlayerA.setHorizontalAlignment(SwingConstants.CENTER);
		txtPlayerA.setText("Player A");
		txtPlayerA.setBounds(20, 477, 86, 20);
		frmPowerRankingGenerator.getContentPane().add(txtPlayerA);
		txtPlayerA.setColumns(10);

		txtPlayerB = new JTextField();
		txtPlayerB.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				txtPlayerB.setText("");
			}
		});
		txtPlayerB.setEnabled(false);
		txtPlayerB.setHorizontalAlignment(SwingConstants.CENTER);
		txtPlayerB.setText("Player B");
		txtPlayerB.setBounds(117, 477, 86, 20);
		frmPowerRankingGenerator.getContentPane().add(txtPlayerB);
		txtPlayerB.setColumns(10);


		btnEnter_4.setEnabled(false);
		btnEnter_4.setBounds(114, 508, 89, 23);
		frmPowerRankingGenerator.getContentPane().add(btnEnter_4);


		btnShowExcludedPlacings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textArea_1.setText("");
				if(excludedPlacings.size() == 0)textArea_1.append("No placings excluded\n");
				else if(txttag_3.getText().equals("(Tag)") || txttag_3.getText().equals("")){
					for(int i = 0; i< excludedPlacings.size(); i++){
						TournamentPlacings placing = excludedPlacings.get(i);
						textArea_1.append(placing.getPlayer().getName()+" placed " +placing.getPlacing()+" at "+placing.getTournament()      +"\n");
					}
				}
				else{
					Player player = null;
					try{
						player = getPlayerFromName(trimSponsor(txttag_3.getText()),players);

						textArea_1.append(player.getName()+"\n");
						for(int i = 0; i< excludedPlacings.size(); i++){
							TournamentPlacings placing = excludedPlacings.get(i);
							if(placing.getPlayer().equals(player))
								textArea_1.append(placing.getPlayer().getName()+" placed " +placing.getPlacing()+" at "+placing.getTournament()      +"\n");
						}
					
					}catch(Exception e){
						JOptionPane.showMessageDialog(null, "Player not found, check your spelling");
					}
					
				}
			}
		});
		btnShowExcludedPlacings.setEnabled(false);
		btnShowExcludedPlacings.setBounds(932, 360, 199, 23);
		frmPowerRankingGenerator.getContentPane().add(btnShowExcludedPlacings);


		txttag_3.setEnabled(false);
		txttag_3.setHorizontalAlignment(SwingConstants.CENTER);
		txttag_3.setText("(Tag)");
		txttag_3.setBounds(932, 397, 86, 20);
		frmPowerRankingGenerator.getContentPane().add(txttag_3);
		txttag_3.setColumns(10);


		label.setEnabled(false);
		label.setBounds(1033, 400, 122, 14);
		frmPowerRankingGenerator.getContentPane().add(label);

		textField = new JTextField();
		textField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				textField.setText("");
			}
		});
		textField.setText("(Winner's Tag)");
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setColumns(10);
		textField.setBounds(1225, 121, 98, 20);
		frmPowerRankingGenerator.getContentPane().add(textField);

		textField_1 = new JTextField();
		textField_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				textField_1.setText("");
			}
		});
		textField_1.setText("(Loser's Tag)");
		textField_1.setHorizontalAlignment(SwingConstants.CENTER);
		textField_1.setColumns(10);
		textField_1.setBounds(1225, 149, 98, 20);
		frmPowerRankingGenerator.getContentPane().add(textField_1);

		textField_2 = new JTextField();
		textField_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				textField_2.setText("");
			}
		});
		textField_2.setText("(Winner's Score)");
		textField_2.setHorizontalAlignment(SwingConstants.CENTER);
		textField_2.setColumns(10);
		textField_2.setBounds(1339, 121, 109, 20);
		frmPowerRankingGenerator.getContentPane().add(textField_2);

		textField_3 = new JTextField();
		textField_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				textField_3.setText("");
			}
		});
		textField_3.setText("(Loser's Score)");
		textField_3.setHorizontalAlignment(SwingConstants.CENTER);
		textField_3.setColumns(10);
		textField_3.setBounds(1339, 149, 109, 20);
		frmPowerRankingGenerator.getContentPane().add(textField_3);

		textField_4 = new JTextField();
		textField_4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				textField_4.setText("");
			}
		});
		textField_4.setText("(Event)");
		textField_4.setHorizontalAlignment(SwingConstants.CENTER);
		textField_4.setColumns(10);
		textField_4.setBounds(1225, 180, 98, 20);
		frmPowerRankingGenerator.getContentPane().add(textField_4);

		JButton button = new JButton("Enter");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(textField_2.getText().equals("")|| textField.getText().equals("") || textField_1.getText().equals("") || textField_3.getText().equals("") || textField_4.getText().equals("")){
					JOptionPane.showMessageDialog(null, "Please enter all fields");
				}else{
					try{
					Player w = getPlayerFromName(trimSponsor(textField.getText()), players);
					Player l = getPlayerFromName(trimSponsor(textField_1.getText()), players);
					
						int ws = Integer.parseInt(textField_2.getText().trim());
						int ls = Integer.parseInt(textField_3.getText().trim());
						if(w == null){
							w = new Player(trimSponsor(textField.getText()));
						}
						if(l == null){
							l = new Player(trimSponsor(textField_1.getText()));
						}
						Match match = new Match(w, ws, ls, l, textField_4.getText().trim());
						enterMatch(match, includedMatches);
						textArea_1.setText("Match added");



						//TODO: Initialize elements

						label.setEnabled(true);
						txttag_3.setEnabled(true);
						btnShowExcludedPlacings.setEnabled(true);
						btnEnter_4.setEnabled(true);
						txtPlayerB.setEnabled(true);
						txtPlayerA.setEnabled(true);
						lblMergePlayerA.setEnabled(true);
						lblChangePlayerName.setEnabled(true);
						btnEnter_3.setEnabled(true);
						txtoldName.setEnabled(true);
						txtnewName.setEnabled(true);
						btnEnter_2.setEnabled(true);
						txtevent_1.setEnabled(true);
						txttag_2.setEnabled(true);
						rdbtnIncludePlacing.setEnabled(true);
						rdbtnExcludePlacing.setEnabled(true);
						lblTheRequiredInformation.setEnabled(true);
						lblToExcludeOr_1.setEnabled(true);
						btnEnter_1.setEnabled(true);
						txtevent.setEnabled(true);
						txtlosersScore.setEnabled(true);
						txtlosersTag.setEnabled(true);
						txtwinnersScore.setEnabled(true);
						txtresponse.setEnabled(true);
						rdbtnIncludeMatch.setEnabled(true);
						rdbtnExcludeMatch.setEnabled(true);
						lblBelowAndFollow.setEnabled(true);
						lblToExcludeOr.setEnabled(true);
						lblOptionalPlayerTag.setEnabled(true);
						txttag_1.setEnabled(true);
						btnShowMatches.setEnabled(true);
						//	rdbtnExcluded.setEnabled(true);
						//	rdbtnIncluded.setEnabled(true);
						btnLookForPlayer.setEnabled(true);
						btnPrintPowerRankings.setEnabled(true);
						rdbtnPlacings.setEnabled(true);
						rdbtnMatches.setEnabled(true);
						txttag.setEnabled(true);
						txtsorts.setEnabled(true);
						lbloptionalSecondPlayer.setEnabled(true);
						txtoptionalSecondPlayer.setEnabled(true);
						txtname_1.setEnabled(true);
						btnExportToFile.setEnabled(true);
						//btnEnter.setEnabled(false);
						btnFromTextFile.setEnabled(false);
						txtname.setEnabled(false);
						txtname_2.setEnabled(true);
						btnLookForTournament.setEnabled(true);
						lblExampleFileDatatxt.setEnabled(false);
						pr = new SortablePlayerList(players, 2);
					}
					catch(Exception e){
						JOptionPane.showMessageDialog(null, "Please enter a valid score");
					}

				}



			}
		});
		button.setBounds(1339, 180, 89, 23);
		frmPowerRankingGenerator.getContentPane().add(button);

		textField_7 = new JTextField();
		textField_7.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				textField_7.setText("");
			}
		});
		textField_7.setText("(Tag)");
		textField_7.setHorizontalAlignment(SwingConstants.CENTER);
		textField_7.setColumns(10);
		textField_7.setBounds(1225, 341, 86, 20);
		frmPowerRankingGenerator.getContentPane().add(textField_7);

		textField_8 = new JTextField();
		textField_8.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				textField_8.setText("");
			}
		});
		textField_8.setEnabled(false);
		textField_8.setText("(Event)");
		textField_8.setHorizontalAlignment(SwingConstants.CENTER);
		textField_8.setColumns(10);
		textField_8.setBounds(1220, 454, 86, 20);
		frmPowerRankingGenerator.getContentPane().add(textField_8);

		JButton btnAddplayer = new JButton("AddPlayer");
		btnAddplayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(textField_7.getText().equals("")||textField_7.getText().equals("(Tag)")){
					JOptionPane.showMessageDialog(null, "Please enter the name of a player");
				}
				else{
					Player player = null;
					try{
						player =  getPlayerFromName(trimSponsor(textField_7.getText()), players);
						
					}catch(Exception e){
						player = new Player(trimSponsor(textField_7.getText()));
					}
					textArea_1.setText("New Event Results:\n");
					manualTournamentResults.add(player);

					btnAddevent.setEnabled(true);
					textField_8.setEnabled(true);
					lblOnceListIs.setEnabled(true);
					lblTheEventAnd.setEnabled(true);
					for(int i = 0; i < manualTournamentResults.size(); i++){
						textArea_1.append(manualTournamentResults.get(i).getName()+"\n");
					}
				}
			}
		});
		btnAddplayer.setBounds(1321, 341, 107, 23);
		frmPowerRankingGenerator.getContentPane().add(btnAddplayer);

		JLabel lblAddNewMatch = new JLabel("Add new match by entering the required info");
		lblAddNewMatch.setBounds(1196, 84, 252, 14);
		frmPowerRankingGenerator.getContentPane().add(lblAddNewMatch);

		JLabel lblAddNewEvent = new JLabel("Add new event by entering standings in order");
		lblAddNewEvent.setBounds(1174, 314, 274, 14);
		frmPowerRankingGenerator.getContentPane().add(lblAddNewEvent);


		frmPowerRankingGenerator.getContentPane().add(lblOnceListIs);


		frmPowerRankingGenerator.getContentPane().add(lblTheEventAnd);


		btnAddevent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(textField_8.getText().equals("")||textField_8.getText().equals("(Event)")){
					JOptionPane.showMessageDialog(null, "Please enter the name of the event");
				}
				else{
					textArea_1.setText("New tournament created\n");
					ArrayList<Player> reultsCopy = new ArrayList<Player>();
					for(int i = 0; i < manualTournamentResults.size(); i++){
						reultsCopy.add(manualTournamentResults.get(i));
					}
					Tournament newTourney = new Tournament(textField_8.getText().trim());
					for(int i = 0; i < reultsCopy.size(); i++){
						Player player = reultsCopy.get(i);
						if(!players.contains(player)){
							players.add(player);
							textArea_1.append("Added "+player.getName()+"\n");
						}
						enterPlacing(player, newTourney.getName(), i, reultsCopy, includedPlacings);
					}
					tournaments.add(newTourney);
					for(int i = 0; i<newTourney.getResults().size(); i++){
						for(int j = 0; j < newTourney.getResults().size(); j++){
							Player playerA = newTourney.getResults().get(i);
							int aPlacing = 0;
							Player playerB = newTourney.getResults().get(j);
							int bPlacing = 0;
							if(!playerA.equals(playerB)){
								for(int k = 0; k < playerA.getPlacings().size(); k++){
									if(playerA.getPlacings().get(k).getTournament().equals(newTourney.getName())){
										aPlacing = playerA.getPlacings().get(k).getPlacing();
									}
								}
								for(int k = 0; k < playerB.getPlacings().size(); k++){
									if(playerB.getPlacings().get(k).getTournament().equals(newTourney.getName())){
										bPlacing = playerB.getPlacings().get(k).getPlacing();
									}
								}
								boolean exists = false;
								for(int k = 0; k < playerA.getPlacingRankings().size(); k++){
									if(playerA.getPlacingRankings().get(k).getPlayer().equals(playerB)){
										exists = true;
										playerA.getPlacingRankings().get(k).addAttempt(1);
										if(aPlacing > bPlacing){
											playerA.getPlacingRankings().get(k).addUpset(1);
										}
										else if(aPlacing < bPlacing){
											playerA.getPlacingRankings().get(k).addUpset(-1);
										}
									}
								}
								if(!exists){
									PlayerRanking ranking = new PlayerRanking(playerB);
									ranking.addAttempt(1);
									if(aPlacing > bPlacing){
										ranking.addUpset(1);
									}
									else if(aPlacing < bPlacing){
										ranking.addUpset(-1);
									}
									playerA.addPlacingRanking(ranking);
								}
							}
						}
					}
					while(manualTournamentResults.size()!=0){
						manualTournamentResults.remove(0);
					}
					textArea_1.setText("Event added");

					//TODO: Initialize elemetns 

					label.setEnabled(true);
					txttag_3.setEnabled(true);
					btnShowExcludedPlacings.setEnabled(true);
					btnEnter_4.setEnabled(true);
					txtPlayerB.setEnabled(true);
					txtPlayerA.setEnabled(true);
					lblMergePlayerA.setEnabled(true);
					lblChangePlayerName.setEnabled(true);
					btnEnter_3.setEnabled(true);
					txtoldName.setEnabled(true);
					txtnewName.setEnabled(true);
					btnEnter_2.setEnabled(true);
					txtevent_1.setEnabled(true);
					txttag_2.setEnabled(true);
					rdbtnIncludePlacing.setEnabled(true);
					rdbtnExcludePlacing.setEnabled(true);
					lblTheRequiredInformation.setEnabled(true);
					lblToExcludeOr_1.setEnabled(true);
					btnEnter_1.setEnabled(true);
					txtevent.setEnabled(true);
					txtlosersScore.setEnabled(true);
					txtlosersTag.setEnabled(true);
					txtwinnersScore.setEnabled(true);
					txtresponse.setEnabled(true);
					rdbtnIncludeMatch.setEnabled(true);
					rdbtnExcludeMatch.setEnabled(true);
					lblBelowAndFollow.setEnabled(true);
					lblToExcludeOr.setEnabled(true);
					lblOptionalPlayerTag.setEnabled(true);
					txttag_1.setEnabled(true);
					btnShowMatches.setEnabled(true);
					//	rdbtnExcluded.setEnabled(true);
					//	rdbtnIncluded.setEnabled(true);
					btnLookForPlayer.setEnabled(true);
					btnPrintPowerRankings.setEnabled(true);
					rdbtnPlacings.setEnabled(true);
					rdbtnMatches.setEnabled(true);
					txttag.setEnabled(true);
					txtsorts.setEnabled(true);
					lbloptionalSecondPlayer.setEnabled(true);
					txtoptionalSecondPlayer.setEnabled(true);
					txtname_1.setEnabled(true);
					btnExportToFile.setEnabled(true);
					//btnEnter.setEnabled(false);
					btnFromTextFile.setEnabled(false);
					txtname.setEnabled(false);
					txtname_2.setEnabled(true);
					btnLookForTournament.setEnabled(true);
					lblExampleFileDatatxt.setEnabled(false);
					pr = new SortablePlayerList(players, 2);

				}
			}
		});
		btnAddevent.setBounds(1316, 453, 89, 23);
		frmPowerRankingGenerator.getContentPane().add(btnAddevent);

		JButton btnPressThisTo = new JButton("Press this to delete current list");
		btnPressThisTo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				while(manualTournamentResults.size()!=0){
					manualTournamentResults.remove(0);
				}
				textArea_1.setText("New tournament cleared successfully");
			}
		});
		btnPressThisTo.setBounds(1214, 372, 214, 23);
		frmPowerRankingGenerator.getContentPane().add(btnPressThisTo);


		buttonGroup_1.add(rdbtnMelee);
		rdbtnMelee.setBounds(20, 56, 79, 23);
		frmPowerRankingGenerator.getContentPane().add(rdbtnMelee);

		JRadioButton rdbtnWiiU = new JRadioButton("Wii U");
		buttonGroup_1.add(rdbtnWiiU);
		rdbtnWiiU.setBounds(131, 56, 79, 23);
		frmPowerRankingGenerator.getContentPane().add(rdbtnWiiU);
	}

	public static void enterPlacing(Player player, String name, int placing, ArrayList<Player> players, ArrayList<TournamentPlacings> placingsList){
		int place = placing;
		if (placing >= 1088)
			place = 1089;
		else if (placing >= 832)
			place = 833;
		else if (placing >= 576)
			place = 577;
		else if (placing >= 448)
			place = 449;
		else if (placing >= 320)
			place = 321;
		else if (placing >= 256)
			place = 257;
		else if (placing >= 192)
			place = 193;
		else if (placing >= 128)
			place = 129;
		else if (placing >= 96)
			place = 97;
		else if (placing >= 64)
			place = 65;
		else if(placing >= 48)
			place = 49;
		else if(placing >= 32)
			place = 33;
		else if(placing >= 24)
			place = 25;
		else if(placing >= 16)
			place = 17;
		else if(placing >= 12)
			place = 13;
		else if(placing >= 8)
			place = 9;
		else if(placing >= 6)
			place = 7;
		else if(placing >= 4)
			place = 5;
		else if(placing == 3)
			place = 4;
		else if(placing == 2)
			place = 3;
		else if(placing == 1)
			place = 2;
		else if(placing == 0)
			place = 1;
		TournamentPlacings result = new TournamentPlacings(player,name, place, players);
		placingsList.add(result);
		player.addPlacing(result);
	}
	public static void enterMatch(Match match, ArrayList<Match> matchList){
		Player player = match.getWinner();
		boolean rankingExisted = false;
		for(int i = 0; i < player.getMatchRankings().size(); i++){
			PlayerRanking playerRanking=player.getMatchRankings().get(i);
			if(playerRanking.getPlayer().equals(match.getLoser())){//so if the other player is the loser
				rankingExisted = true;
				playerRanking.addAttempt(1);
				playerRanking.addUpset(-1);
				playerRanking.addGameWin(match.getWinScore());
				playerRanking.addGameLoss(match.getLoseScore());
				playerRanking.addMatch(match);
			}
		}
		if(!rankingExisted){
			//if the ranking didnt exist previously, we must make a new one
			PlayerRanking playerRanking = new PlayerRanking(match.getLoser());
			playerRanking.addAttempt(1);
			playerRanking.addUpset(-1);
			playerRanking.addGameWin(match.getWinScore());
			playerRanking.addGameLoss(match.getLoseScore());
			player.addMatchRanking(playerRanking);
			playerRanking.addMatch(match);
		}
		player.addWin(match);
		rankingExisted = false;
		player = match.getLoser();
		for(int i = 0; i < player.getMatchRankings().size(); i++){

			PlayerRanking playerRanking=player.getMatchRankings().get(i);
			if(playerRanking.getPlayer().equals(match.getWinner())){//so if this player is the Winner
				rankingExisted = true;
				playerRanking.addAttempt(1);
				playerRanking.addUpset(1);
				playerRanking.addGameLoss(match.getWinScore());
				playerRanking.addGameWin(match.getLoseScore());
				playerRanking.addMatch(match);
			}
		}
		if(!rankingExisted){
			//if the ranking didnt exist previously, we must make a new one
			PlayerRanking playerRanking = new PlayerRanking(match.getWinner());
			playerRanking.addAttempt(1);
			playerRanking.addUpset(1);
			playerRanking.addGameLoss(match.getWinScore());
			playerRanking.addGameWin(match.getLoseScore());
			player.addMatchRanking(playerRanking);
			playerRanking.addMatch(match);
		}
		player.addLoss(match);
		matchList.add(match);
	}
	public static Player addPlayer(ArrayList<Player> players, String name){
		Player newPlayer = null;
		boolean playerExists = false;
		for(int i = 0; i < players.size(); i++){
			if(players.get(i).getName().equals(name)){
				newPlayer = players.get(i); 
				playerExists = true;
			}
		}
		if(!playerExists){
			newPlayer = new Player(name);
			players.add(newPlayer);
		}
		return newPlayer;
	}
	public String trimSponsor(String name){
		name = name.substring(name.lastIndexOf("|")+1).trim().toUpperCase();
		name = name.replaceAll("\\s+","");
		name = name.replaceAll("-","");
		name = name.replaceAll("'","");
		//name = name.replaceAll("!","");
		//name = name.replaceAll("?","");
		name = name.replaceAll("&#39;","");
		name = name.replaceAll("&QUOT;", "\"");
		name = name.replaceAll("INVITATIONPENDING", "");
		if(name.contains("()")){
			name = name.substring(0, name.indexOf("()"));
		}
		/**
		if(name.startsWith("VWS")){
			name = name.substring(name.indexOf("VWS")+3);
		}
		else if(name.startsWith("GHQ")){
			name = name.substring(name.indexOf("GHQ")+3);
		}
		else if(name.startsWith("IX")){
			name = name.substring(name.indexOf("IX")+2);
		}
		else if(name.startsWith("INC")){
			name = name.substring(name.indexOf("INC")+3);
		}
		else if(name.startsWith("PNDA")){
			name = name.substring(name.indexOf("PNDA")+4);
		}
		else if(name.startsWith("WIT")){
			name = name.substring(name.indexOf("WIT")+3);
		}
		else if(name.startsWith("UNH")){
			name = name.substring(name.indexOf("UNH")+3);
		}
		else if(name.startsWith("GARBAGE")){
			name = name.substring(name.indexOf("GARBAGE")+7);
		}
		else if(name.startsWith("MUK.LYFE")){
			name = name.substring(name.indexOf("MUK.LYFE")+8);
		}
		else if(name.startsWith("GLS")){
			name = name.substring(name.indexOf("GLS")+3);
		}
		else if(name.startsWith("TSI")){
			name = name.substring(name.indexOf("TSI")+3);
		}
		else if(name.startsWith("PRISM")){
			name = name.substring(name.indexOf("PRISM")+5);
		}
		else if(name.startsWith("M150")){
			name = name.substring(name.indexOf("M150")+4);
		}
		else if(name.startsWith("EHG")){
			name = name.substring(name.indexOf("EHG")+3);
		}
		else if(name.startsWith("OES")){
			name = name.substring(name.indexOf("OES")+3);
		}
		else if(name.startsWith("MUK LYFE")){
			name = name.substring(name.indexOf("MUK LYFE")+9);
		}
		else if(name.startsWith("URT")){
			name = name.substring(name.indexOf("URT")+3);
		}
		else if(name.startsWith("ME")){
			name = name.substring(name.indexOf("ME")+2);
		}
		 */
		return name;
	}
	public static Player getPlayerFromName(String name, ArrayList<Player> players) throws Exception{
		for (int i = 0; i < players.size(); i++)
			if(players.get(i).getName().equals(name)) return players.get(i);
		//System.out.println(name);
		throw new Exception("There must have been a weird character");
	}
	public static Tournament getTournamentFromName(String name, ArrayList<Tournament> tournaments){
		for (int i = 0; i < tournaments.size(); i++)
			if(tournaments.get(i).getName().equalsIgnoreCase(name)) return tournaments.get(i);
		return null;
	}
	/**
	public static String[] formatInputStandings(String input) throws IOException{
		String newString = input.substring(input.indexOf("display_name'><span>")+20);
		String[] arr =  newString.split("display_name'><span>");
		for(int i = 0; i < arr.length; i++){
			arr[i] = arr[i].substring(0,arr[i].indexOf("</span>"));
			//System.out.println(arr[i]);
		}
		return arr;
	}
	//*/
	/**
	public static String formatInputMatches(String input) throws IOException{
		int index = 0;
		if(input.contains("\"player1\":")){
			index = input.indexOf("\"player1\":");
			input = input.substring(index);
		}
		if(input.contains("</script><div data-react-class=\"TournamentController\"")){
			index = input.indexOf("</script><div data-react-class=\"TournamentController\"");
			input = input.substring(0,index);			
		}
		input = input.replaceAll("underway_at\":", "\n");
		return input;
	}
	/*/
	public static String getPageTextFromURLString(String request) throws IOException {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet getRequest = new HttpGet(request);
		getRequest.setHeader("Content-Type", "application/x-www-form-urlencoded");
		getRequest.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.98 Safari/537.36");
		CloseableHttpResponse response;
		response = client.execute(getRequest);
		String responseString = EntityUtils.toString(response.getEntity());
		//TODO: use this for debugging
		//	System.out.println(responseString);
		client.close();
		response.close();
		return responseString;
	}
	//*/
	/**
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 * @throws ParseException */

	public static JSONObject processChallonge(String tourneyString) throws ClientProtocolException, IOException, ParseException{
		String api_key = "eHZ8ALetEIB8d67RuqoK5whEgcZENEy1KCKO1X4y";
		String url = "https://api.challonge.com/v1/tournaments/"+tourneyString+".json?api_key="+api_key+"&include_participants=1&include_matches=1";
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet getRequest = new HttpGet(url);
		getRequest.setHeader("Content-Type", "application/x-www-form-urlencoded");
		getRequest.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.98 Safari/537.36");
		CloseableHttpResponse response;
		response = client.execute(getRequest);
		String responseString = EntityUtils.toString(response.getEntity());
		//TODO: use this for debugging
		//System.out.println(responseString);
		client.close();
		response.close();
		return((JSONObject) new JSONParser().parse(responseString));
	}

	public static smashGGPlayer addSmashGGPlayer(ArrayList<Player> players, ArrayList<smashGGPlayer> ggPlayers, Long id, String name ){
		boolean playerExists = false;
		for(int i = 0; i < ggPlayers.size(); i++){
			if(ggPlayers.get(i).getId()==id){
				return ggPlayers.get(i);
			}
		}
		if(!playerExists){
			smashGGPlayer newPlayer = new smashGGPlayer(id, addPlayer(players, name));
			ggPlayers.add(newPlayer);
			return newPlayer;
		}
		return null;
	}
	public static smashGGPlayer getSmashGGPlayerFromId(Long id, ArrayList<smashGGPlayer> smashGGPlayers) throws Exception{
		for(int i = 0; i < smashGGPlayers.size(); i++){
			if(smashGGPlayers.get(i).getId() - id == 0){
				return smashGGPlayers.get(i);
			}
		}
		throw new Exception("There must have been a weird character");
	}
}
