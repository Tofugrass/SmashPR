import java.util.*;
public class Tournament {
	private String name;
	private ArrayList<Player> results = new ArrayList<Player>(); 
	private int watchListPlacing;
	public Tournament(String name){
		this.name = name;
	}
	public ArrayList<Player> getResults(){
		return results;
	}
	public void addResults(Player player){
		results.add(player);
	}
	public void setWatchListPlacing(int i){
		watchListPlacing = i;
	}
	public int getWatchListPlacing(){
		return watchListPlacing;
	}
	public String getName(){
		return name;
	}
	//updateAll should be called on every tournament after all scores are entered
	
}
