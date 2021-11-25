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

    private JFrame window;
	private JPanel topPanel;

    public GameManager(User player) {
        this.player = player;
        name = player.getName();
        score = player.getCurrScore();
        userLevel = player.getUserLevel();
        boardCounter = 0;

        // TODO: make a timer
        totalTime = new Timer();
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

    public void resetBoard() {
        boardCounter++;
        if(boardCounter == 3) score -= 5;
    }

    public boolean validateWord(String input) {
        // call ValidationCheck to verify the inputted word matches all the criteria:
            // word does not utilize letters outside the options
            // word is a valid dictionary word
            // word is at least three letters
       
        // Initialize Validation with input word
        ValidationCheck vc = new ValidationCheck("altred");
        // Insert letters into validation set
        vc.insert(vc.wordInput);
        return vc.isValid(input);
    }

}
