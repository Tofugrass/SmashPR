
public class Match {
private Player winner;
private Player loser;
private int winScore;
private int loseScore;
private String tourney;
public Match(Player w, int win, int lose, Player l, String tournament){
	setWinner(w);
	setLoser(l);
	setWinScore(win);
	setLoseScore(lose);
	setTourney(tournament);
}
public Player getLoser() {
	return loser;
}
public void setLoser(Player loser) {
	this.loser = loser;
}
public int getWinScore() {
	return winScore;
}
public void setWinScore(int winScore) {
	this.winScore = winScore;
}
public int getLoseScore() {
	return loseScore;
}
public void setLoseScore(int loseScore) {
	this.loseScore = loseScore;
}
public Player getWinner() {
	return winner;
}
public void setWinner(Player winner) {
	this.winner = winner;
}
public String getTourney() {
	return tourney;
}
public void setTourney(String tourney) {
	this.tourney = tourney;
}
}
