import java.util.ArrayList;

public class SortablePlayerList {
	private ArrayList<Player> playerList = new ArrayList<Player>();
	private ArrayList<Match> matches = new ArrayList<Match>();
	private static int NUM;
	public SortablePlayerList(ArrayList<Player> players, int num){
		playerList = players;
		NUM = num;
	}
	public void sort(){
		//loop through all players, re establishing player a and player b each time
		for(int i = 0; i < playerList.size(); i++){
			for(int j = i+1; j <playerList.size(); j++){
				Player playerA = playerList.get(i);	
				Player playerB = playerList.get(j);
				IntBool match = matchAnalysis(playerA, playerB);
				IntBool placing = placingAnalysis(playerA, playerB);
				int count = match.getInt()*4 + placing.getInt();
				if(count < 0){
					//if the count is less than zero, we know player A is better
					if(i > j){
						playerList.set(i, playerB);
						playerList.set(j, playerA);
					}
				}
				else if (count > 0){
					if(i < j){
						//System.out.println(playerA.getName()+" "+playerB.getName());
						playerList.set(i, playerB);
						playerList.set(j, playerA);
					}
				}

				//*/		



				/**

				if(match.getBool()){
					count = match.getInt();
					if(count < 0){
						//if the count is less than zero, we know player A is better
						if(i > j){
							playerList.set(i, playerB);
							playerList.set(j, playerA);
						}
					}
					else if (count > 0){

						if(i < j){
							//System.out.println(playerA.getName()+" "+playerB.getName());
							playerList.set(i, playerB);
							playerList.set(j, playerA);
						}
					}
					else if(count == 0){
						//if they tied, we will compare their placings.
						for(int k = 0; k < playerA.getPlacingRankings().size(); k++){
							if(playerA.getPlacingRankings().get(k).getPlayer().equals(playerB)){
								//if they both attended same tournament
								if(playerA.getPlacingRankings().get(k).getRatio()<0){
									//so if player a places better than player B. 
									if(i > j){
										playerList.set(i, playerB);
										playerList.set(j, playerA);
									}
								}
								else if(playerA.getPlacingRankings().get(k).getRatio()>0){
									//if player b outplaces player a
									if(i < j){
										//System.out.println(playerA.getName()+" "+playerB.getName());
										playerList.set(i, playerB);
										playerList.set(j, playerA);
									}
								}
							}
						}
					}
				}
				else{//if they never played a mutual player C
					for(int k = 0; k < playerA.getPlacingRankings().size(); k++){
						if(playerA.getPlacingRankings().get(k).getPlayer().equals(playerB)){
							//if they both attended same tournament
							if(playerA.getPlacingRankings().get(k).getRatio()<0){
								//so if player a places better than player B. 
								if(i > j){
									playerList.set(i, playerB);
									playerList.set(j, playerA);
								}
							}
							else if(playerA.getPlacingRankings().get(k).getRatio()>0){
								//if player b outplaces player a
								if(i < j){
									//System.out.println(playerA.getName()+" "+playerB.getName());
									playerList.set(i, playerB);
									playerList.set(j, playerA);
								}
							}
							break;
						}
					}
				}
			//	*/
			}
		}
	}





	public ArrayList<Player> getList(){
		return playerList;
	}//TODO
	/**
	 * 
	 * @param playerA
	 * @param playerB
	 * @return a negative int if playerA is better, and true if they've both played a mutual playerC
	 */
	public IntBool matchAnalysis(Player playerA, Player playerB){
		int count = 0;
		boolean comparable = false;
		for(int i = 0; i < playerA.getMatchRankings().size(); i++){
			PlayerRanking currRankingA = playerA.getMatchRankings().get(i);
			for(int j = 0; j < playerB.getMatchRankings().size(); j++){
				PlayerRanking currRankingB = playerB.getMatchRankings().get(j);
				if(currRankingA.getPlayer().equals(currRankingB.getPlayer())){//if they are both looking at same player c
					comparable = true;
					if(currRankingA.getRatio() < currRankingB.getRatio()){//if player A outplaces this player more often than player b does
						count--;
					}
					else if(currRankingA.getRatio() > currRankingB.getRatio()){//if player b outplaces player c more often than player a does
						count++;
					}

				}
				else if(currRankingA.getPlayer().equals(playerB)){
					//if curr ranking A is  actually just player b
					comparable = true;
					if(currRankingA.getRatio() < 0){//if player A outplaces this player more often than player b does
						count--;

					}
					else if(currRankingA.getRatio() > 0){//if player b outplaces player c more often than player a does
						count++;
					}
				}
			}
		}
		return new IntBool(count, comparable);
	}
	public IntBool placingAnalysis(Player playerA, Player playerB){
		int count = 0;
		boolean comparable = false;
		for(int i = 0; i < playerA.getPlacingRankings().size(); i++){
			PlayerRanking currRankingA = playerA.getPlacingRankings().get(i);
			for(int j = 0; j < playerB.getPlacingRankings().size(); j++){
				PlayerRanking currRankingB = playerB.getPlacingRankings().get(j);
				if(currRankingA.getPlayer().equals(currRankingB.getPlayer())){//if they are both looking at same player c
					comparable = true;
					if(currRankingA.getRatio() < currRankingB.getRatio()){//if player A outplaces this player more often than player b does
						count--;
					}
					else if(currRankingA.getRatio() > currRankingB.getRatio()){//if player b outplaces player c more often than player a does
						count++;
					}

				}
				else if(currRankingA.getPlayer().equals(playerB)){
					//if curr ranking A is  actually just player b
					comparable = true;
					if(currRankingA.getRatio() < 0){//if player A outplaces this player more often than player b does
						count--;
					}
					else if(currRankingA.getRatio() > 0){//if player b outplaces player c more often than player a does
						count++;
					}
				}
			}
		}
		return new IntBool(count, comparable);
	}
}
