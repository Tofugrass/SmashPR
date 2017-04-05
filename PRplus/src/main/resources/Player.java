import java.util.*;
public class Player implements Comparable<Player>{
	private String name;
	private ArrayList<TournamentPlacings> placings = new ArrayList<TournamentPlacings>();
	private ArrayList<PlayerRanking> matchRankings = new ArrayList<PlayerRanking>();
	private ArrayList<PlayerRanking> placingRankings = new ArrayList<PlayerRanking>();
	private ArrayList<Match> wins = new ArrayList<Match>();
	private ArrayList<Match> losses = new ArrayList<Match>();
	private ArrayList<String> dqs = new ArrayList<String>();
	private Long id;
	public Player (String name){
		this.name = name;
	}
	//addPlacing rounds placing to the standard 5, 7, 9, 13, 17, 25, 33
	//param placing should be players index in result arraylist
	public void addPlacing(TournamentPlacings placing){
		placings.add(placing);
	}
	public String getName(){
		return name;
	}
	public void setName(String newName){
		name = newName;
	}
	public ArrayList<PlayerRanking> getMatchRankings(){
		return matchRankings;
	}
	public void addMatchRanking(PlayerRanking a){
		matchRankings.add(a);
	}
	public ArrayList<TournamentPlacings> getPlacings(){
		return placings;
	}
	public int compareTo(Player playerB) {
	/**	
		for(int i = 0; i<playerRankings.size(); i++){
			if(playerRankings.get(i).getPlayer().equals(playerB)){//if players attended same event
				if(playerRankings.get(i).getRatio() < 0){//if player a usually outplaced player b
					return 1;
				}
				else if(playerRankings.get(i).getRatio() == 0){//if player a and player b tied
					if(placings.size()>playerB.getPlacings().size()){//if player a went to more tournaments
						return 1;
					}
					else if(placings.size() == playerB.getPlacings().size()){//if player a and player b went to the same number of tournaments
						if(avgPR.size() > playerB.getAvgPR().size()){//if player a has played with more people
							return 1;
						}
						else if(avgPR.size() == playerB.getAvgPR().size()){//if player a and player b played with the same number of people
							return 0;
						}
						else{//if player b has played with more people
							return -1;
						}
						
					}
				}
				return -1;//if players attended same event at least once and player b outplaced player a
			}
		}
		return 0;
		**/
		
		return 0;
		//a -1.0 ratio means that player a ouitplaced player c all the time
		//a negative number means player a is better than player c
		
		/**
		int count = 0;//a positive count means player is better than player b
		boolean comparable = false;
		for(int i =0; i < playerRankings.size(); i++){//loop through all of player a's player rankings
			PlayerRanking thisPlayerCRanking = playerRankings.get(i);//this is the current third party player we are analyzing from player a's perspective
			for(int j = 0; j < playerB.getPlayerRankings().size(); j++){//loop through all player b's player rankings
				PlayerRanking otherPlayerCRanking = playerB.getPlayerRankings().get(j);//this is the current third party player we are analyzing from player b's perspective
				if(thisPlayerCRanking.getPlayer().equals(otherPlayerCRanking.getPlayer())){//if there is a mutual player c to compare
					comparable = true;
					if(thisPlayerCRanking.getRatio() < otherPlayerCRanking.getRatio()){//if player a outplaces player c more than player does
						count++;
					}
					else if(thisPlayerCRanking.getRatio() < otherPlayerCRanking.getRatio()){//if player a outplaces player c less than player does
						count--;
					}
				}
			}
		}
		if(!comparable){//if they dont have a mutual player to compare against each other we return a tie
			return 0;
		}
		if(count > 0){//if player a outplaces all player C's more often than player b does we return 1
			return 1;
		}
		else if(count < 0){//if player b outplaces all player C's more often than player a does we return 1
			return -1;
		}
		else{//if there is a tie
			
			
			if(placings.size()>playerB.getPlacings().size()){//if player a went to more tournaments
				return 1;
			}
			else if(placings.size() == playerB.getPlacings().size()){//if player a and player b went to the same number of tournaments
				if(avgPR.size() > playerB.getAvgPR().size()){//if player a has played with more people
					return 1;
				}
				else if(avgPR.size() == playerB.getAvgPR().size()){//if player a and player b played with the same number of people
					return 0;
				}
				else{//if player b has played with more people
					return -1;
				}
				
			}
		}
		return 0; //**/
	}
	public ArrayList<Match> getWins() {
		return wins;
	}
	public void addWin(Match win) {
		wins.add(win);
	}
	public ArrayList<Match> getLosses() {
		return losses;
	}
	public void addLoss(Match loss) {
		losses.add(loss);
	}
	
	public void addPlacingRanking(PlayerRanking a) {
		placingRankings.add(a);
	}
	public ArrayList<PlayerRanking> getPlacingRankings() {
		return placingRankings;
	}
	public void setPlacingRankings(ArrayList<PlayerRanking> placingRankings) {
		this.placingRankings = placingRankings;
	}
	public ArrayList<String> getDqs() {
		return dqs;
	}
	public void setDs(ArrayList<String> dqs) {
		this.dqs = dqs;
	}
	public void addDq(String dqs) {
		this.dqs.add(dqs);
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	

}
