import java.util.*;
public class PlayerRanking implements Comparable<PlayerRanking>{
	private Player player;
	private int upsets = 0;
	private int attempts = 0;
	private int gameWins = 0;
	private int gameLosses = 0;
	private ArrayList<Match> matches = new ArrayList<Match>();
	public PlayerRanking(Player player){
		this.player = player;
	}
	public ArrayList<Match> getMatches(){
		return matches;
	}
	public void addUpset(int a){
		upsets = upsets + a;
	}
	public int getUpsets(){
		return upsets;
	}
	public void setUpsets(int a ){
		 upsets = a;
	}
	public void addAttempt(int a){
		attempts = attempts +a; 
	}
	public Player getPlayer(){
		return player;
	}
	public void setPlayer(Player a){
		player = a;
	}
	public Double getRatio(){
		return ((double)upsets/attempts);
	}
	public void setAttempts(int attempts){
		this.attempts = attempts;
	}
	public int getAttempts(){
		return attempts;
	}
	public void addMatch(Match match){
		matches.add(match);
	}
	public int compareTo(PlayerRanking other) {
		// TODO Auto-generated method stub
		if(this.getRatio() > other.getRatio()){
			return 1;
		}
		else if (this.getRatio() < other.getRatio()){
			return -1;
		}
		else{
			return 0;
		}
	}
	public int getGameWins() {
		return gameWins;
	}
	public void setGameWins(int gameWins) {
		this.gameWins = gameWins;
	}
	public int getGameLosses() {
		return gameLosses;
	}
	public void setGameLosses(int gameLosses) {
		this.gameLosses = gameLosses;
	}
	public void addGameLoss(int numLostGames){
		gameLosses = gameLosses + numLostGames;
	}
	public void addGameWin(int numWonGames){
		gameWins = gameWins + numWonGames;
	}
}
