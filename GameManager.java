import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import javax.swing.*;
import java.awt.*;

public class GameManager {
    private User player;
    private String name;
    private String userLevel;
    private int score;
    private int boardCounter;
    private Timer totalTime;
    private String currWord;

    private ValidationCheck vc;
    private ArrayList<String> keys;

    private JFrame window;
	private JPanel topPanel;

    public GameManager(User player) {
        this.player = player;
        name = player.getName();
        score = player.getCurrScore();
        userLevel = player.getUserLevel();
        boardCounter = 0;
        keys = new ArrayList<String>();
        vc = new ValidationCheck();

        // TODO: make a timer
        totalTime = new Timer();
        
        // Randomly select a word from List of sample words
        int random = generateRandomNumber();
        currWord = keys.get(random);
        vc.insert(currWord);
        // remove this key from the arraylist when its used 
    }

    public int generateRandomNumber(){
        Random rand = new Random(); //instance of random class
        
        //generate random values from 0 to size of list
        int randomInt = rand.nextInt(keys.size()); 
        return randomInt;
    }
    public void initializeKeys(){
        keys.add("altred");
        keys.add("hello");
        keys.add("good");
        keys.add("morning");
        keys.add("toyou");
    }

    public void startGame(){
        // new User, send a JOptionPane to create this user.
        if(name.equals("")) {

        }
    }

    public void endGame(){

    }

    public void setTimer(){

    }

    public void checkTimer(){

    }

    public void buildGUI() {
        window = new JFrame();
		topPanel = new JPanel();

        // set the window's content pane to the top panel and display the window
        window.setContentPane(topPanel);
        window.pack();
        window.setTitle("Final Project - Aastha & Shivangi");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
    }

    public void updateWord(){
        // remove current word on board
        keys.remove(currWord);
        // remove letters from validation set
        vc.removeCharacters();
        // set current word to newly generated word 
        currWord = keys.get(generateRandomNumber());
        // insert into validation set to allow for checks
        vc.insert(currWord);
    }

    public void resetBoard() {
        boardCounter++;
        if(boardCounter == 3) score -= 5;
        // set board to new word 
        updateWord();
    }

    // For a specific word, user scores increases by x amt of points
    public void updateUserScore(String input){
        int currentSum = player.getCurrScore();
        currentSum  += vc.assignPoints(input);
        player.setCurrScore(currentSum);
    }

    // Returns player's score at the end of the game
    public int finalGameScore(){
        return player.getCurrScore();
    }

    public boolean validateWord(String input) {
        // call ValidationCheck to verify the inputted word matches all the criteria:
            // word does not utilize letters outside the options
            // word is a valid dictionary word
            // word is at least three letters
       
        return vc.isValid(input);
    }

}
