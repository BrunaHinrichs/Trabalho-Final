import java.io.Serializable;
import java.util.Comparator;

public class Player implements Serializable{

	private long time;
	private String nickname;
	private String difficulty;
	private int minesRight;
	private boolean win;

	public Player(long time, String nickname, String difficulty, int minesRight, boolean win){
		this.time = time;
		this.nickname = nickname;
		this.difficulty = difficulty;
		this.minesRight = minesRight;
		this.win = win;
	}

	public long getTime(){
		return this.time;
	}

	public String getNickName(){
		return this.nickname;
	}

	public String getDifficulty(){
		return this.difficulty;
	}

	public String getMinesRight(){
		return this.difficulty;
	}

	public boolean getWin(){
		return this.win;
	}
	
	public String toString(){
		//if(this.win)
			return "Nome: "+nickname+"\nDificuldade: "+difficulty+"\nTempo: "+time/1000.0+"s\n";
	//	else
		//	return "Nome: "+nickname+"\nDificuldade: "+difficulty+"\nMinas Desarmadas: "+minesRight+"\n";
	}
}

class PlayerComparatorByTime implements Comparator<Player>{
	@Override
	public int compare(Player o1, Player o2){
		return (int)(o1.getTime() - o2.getTime());
	}
}
