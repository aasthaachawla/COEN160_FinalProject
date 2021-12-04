/**
 * Program Description: The following class defines the User object: 
 * (1) Holds values unique to each user
 * (2) Attributes include: name, current score, high score, and level
 */
import java.io.*;
import java.util.*;

public class User implements java.io.Serializable {
    private String name;
    private int currScore;
    private int highScore;
    private String userLevel;

    /* Constructor to create new User object*/
    public User(String name, int currScore, int highScore, String userLevel){
        this.name = name;
        this.currScore = currScore;
        this.highScore = highScore;
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

    public int getHighScore(){
        return highScore;
    }

    public void setHighScore(int newScore){
        highScore = newScore;
    }
}
