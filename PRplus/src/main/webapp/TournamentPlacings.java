import java.util.*;
public class TournamentPlacings {
	private Player player;
	private String tournament;
	private ArrayList<Player> players = new ArrayList<Player>();
	private int placing;
public TournamentPlacings(Player player, String tournament, int placing, ArrayList<Player> players){
	this.setPlayer(player);
	this.players = players;
	this.tournament = tournament;
	this.placing = placing;
}
public String getTournament(){
	return tournament;
}
public int getPlacing(){
	return placing;
}
//get players returns the players in order of placing. 
public ArrayList<Player> getPlayers(){
	return players;
}
/**
 * 
 * @param a First player
 * @param b Second Player
 * @return 1 if a outplaced b, -1 if the opposite, and 0 if they tied
 */
public int didPlayerOutplace(Player a, Player b){
	int aPlacing = 0;
	for(int i = 0; i < a.getPlacings().size(); i++){
		if(a.getPlacings().get(i).getTournament().equalsIgnoreCase(tournament)){
			aPlacing = a.getPlacings().get(i).getPlacing();
			break;
		}
	}
	int bPlacing = 0;
	for(int i = 0; i < b.getPlacings().size(); i++){
		if(b.getPlacings().get(i).getTournament().equals(tournament)){
			bPlacing = b.getPlacings().get(i).getPlacing();
			break;
		}
	}
	if(aPlacing > bPlacing) return -1;
	else if(bPlacing > aPlacing) return 1;
	else return 0;
}
public Player getPlayer() {
	return player;
}
public void setPlayer(Player player) {
	this.player = player;
}
}
