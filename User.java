import java.io.*;

public class User implements java.io.Serializable {
    private String name;
    private int currScore;
    private String userLevel;

    /* Constructor to create new User object*/
    public User(String name, int currScore, String userLevel){
        this.name = name;
        this.currScore = currScore;
        this.userLevel = userLevel;
    }

    /* Default Constructor */
    public User() {
        
    }
    /* Accessor and Mutator methods for private fields */
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
}
