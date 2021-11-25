import java.io.*;

public class User implements java.io.Serializable {
    private String name;
    private int currScore;
    private String userLevel;

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public int getCurrScore(){
        return currScore;
    }

    public void setCurrScore(int newScore){
        currScore = newScore;
    }

    public String getUserLevel(){
        return userLevel;
    }

    public void setUserLevel(String newLevel){
        userLevel = newLevel;
    }

    public User(String name, int currScore, String userLevel){
        this.name = name;
        this.currScore = currScore;
        this.userLevel = userLevel;
    }

    public User() {
        
    }

}
