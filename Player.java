import java.io.Serializable;

public class Player implements Serializable  {
    
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
}