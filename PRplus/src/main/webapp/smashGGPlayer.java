import org.json.simple.JSONArray;

public class smashGGPlayer {
private long id;
private Player player;
public smashGGPlayer(long id, Player player){
	this.id = id;
	this.player = player;
}

public Player getPlayer() {
	return player;
}
public void setPlayer(Player player) {
	this.player = player;
}
public long getId() {
	return id;
}
public void setId(long id) {
	this.id = id;
}


}
