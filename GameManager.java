import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameManager {
    private User player;
    private String userLevel;
    private int boardCounter;
    private Timer timer;
    private String currWord;

    private ValidationCheck vc;
    private ArrayList<String> keys;

    private JFrame window;
	private JPanel topPanel;
    private JPanel playerInfoPanel;
    private JPanel wordPanel;
    private JPanel interactiveGamePanel;

    private JTextField nameField;
    private JTextField scoreField;
    private JTextField timerLabel;

    private JTextField givenScrambleTextField;
    private JTextArea wordsInputtedTextArea;
    private JTextField resultTextField;

    private JButton refreshGame;
    private JTextField userInputField;
    private JButton submitWord;

    public GameManager(User player) {
        this.player = player;
        userLevel = player.getUserLevel();
        boardCounter = 0;
        keys = new ArrayList<String>();
        vc = new ValidationCheck();

        // Randomly select a word from List of sample words
        initializeKeys();
        int random = generateRandomNumber();
        currWord = keys.get(random);
        vc.insert(currWord);
    }

    public int generateRandomNumber(){
        Random rand = new Random(); //instance of random class
        
        int randomInt = 0;
        //generate random values from 0 to size of list
        if(keys.size() != 0)
            randomInt = rand.nextInt(keys.size()); 
        return randomInt;
    }
    public void initializeKeys(){
        keys.add("altred");
        keys.add("trasthig"); // straight
        keys.add("copiduce"); // occupied
        keys.add("bacgikn");
        keys.add("daginot");
        // keys.add("DAGINOT");
        // keys.add("DAGINOT");
    }

    public void buildUser() {
        buildGUI();
        // new User, send a JOptionPane to create this user.
        String getUserInput = JOptionPane.showInputDialog(topPanel, "Enter your name.");
        if(getUserInput == null){
            nameField.setText("Name: Guest");
            scoreField.setText("Score: " + 0);
        }
        else {
            // TODO: retrieve User player and see if they exist
            player.setName(getUserInput);
            FileManager fm = new FileManager();
            fm.deserialize(player);
            // 
            nameField.setText("Name: " + player.getName());
            scoreField.setText("Score: " + player.getCurrScore());
        }
    }

    public void startGame(){
        givenScrambleTextField.setText(currWord);
        setTimer();
    }

    public void endGame(){
        Object[] options = {"New Game", "Done"};
		int n = JOptionPane.showOptionDialog(window, "Game over, thanks for playing! Do you want to play again?", "Game over!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		System.out.println(n);
        if(n == 1) {
            window.dispose();
        }
        else {
            resetBoardEndGame(); 
            boardCounter = 0; 
            player.setCurrScore(0);
            scoreField.setText("Score: " + player.getCurrScore());
            initializeKeys();
            setTimer();
        }
    }

    public void setTimer(){
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            int i = 60;

            public void run() {
                timerLabel.setText("\tTime left: " + i);
                i--;
                if (i < 0) {
                    timer.cancel();
                    timerLabel.setText("\tTime Over");
                    resultTextField.setText("Game over! Thanks for playing.");
                    endGame();
                }
            }
        }, 0, 1000);
    }

    public void buildGUI() {
        // instantiate JPanels and JFrame components 
        window = new JFrame();
		topPanel = new JPanel(new BorderLayout());
        playerInfoPanel = new JPanel(new BorderLayout());
        wordPanel = new JPanel(new BorderLayout());
        interactiveGamePanel = new JPanel(new BorderLayout());

        // create GUI components for the playerInfoPanel - name, score, and timer
        nameField = new JTextField("Name: " + player.getName());
        nameField.setEditable(false);
        scoreField = new JTextField("Score: " + player.getCurrScore());
        scoreField.setEditable(false);
        timerLabel = new JTextField("\t");
        timerLabel.setEditable(false);
        playerInfoPanel.add(nameField, BorderLayout.WEST);
        playerInfoPanel.add(timerLabel, BorderLayout.CENTER);
        playerInfoPanel.add(scoreField, BorderLayout.EAST);
        playerInfoPanel.setForeground(Color.white);

        // create GUI components for wordPanel - valid user inputted words and the result status
        givenScrambleTextField = new JTextField(currWord); // TODO: autogenerate these words
        givenScrambleTextField.setEditable(false);
        wordsInputtedTextArea = new JTextArea(200, 400);
        wordsInputtedTextArea.setEditable(false);
        resultTextField = new JTextField();
        resultTextField.setEditable(false);
        wordPanel.add(givenScrambleTextField, BorderLayout.NORTH);
        wordPanel.add(wordsInputtedTextArea, BorderLayout.CENTER);
        wordPanel.add(resultTextField, BorderLayout.SOUTH);
        wordPanel.setForeground(Color.white);

        // create GUI components for interactiveGamePanel - buttons and text field for user to interact with
        refreshGame = new JButton("Refresh Game");
        userInputField = new JTextField("", 100);
        submitWord = new JButton("Submit");
        interactiveGamePanel.add(refreshGame, BorderLayout.WEST);
        interactiveGamePanel.add(userInputField, BorderLayout.CENTER);
        interactiveGamePanel.add(submitWord, BorderLayout.EAST);
        interactiveGamePanel.setForeground(Color.white);

        // add action listener to user input text field
        userInputField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // process user input and update score if needed
                updateBoard(userInputField.getText());
            }
        });

        submitWord.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // process user input and update score if needed
                updateBoard(userInputField.getText());
            }
        });

        refreshGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // clear board
                resetBoard();
            }
        });

        // add the main 3 panels to the top panel with a Border Layout 
        topPanel.setForeground(Color.white);
        topPanel.add(playerInfoPanel, BorderLayout.NORTH);
        topPanel.add(wordPanel, BorderLayout.CENTER);
        topPanel.add(interactiveGamePanel, BorderLayout.SOUTH);

        // set the window's content pane to the top panel and display the window
        window.setContentPane(topPanel);
        window.pack();
        window.setTitle("Final Project - Aastha & Shivangi");
        window.setSize(700, 400);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
    }

    public void updateBoard(String input) {
        // validate if the word matches the rules
        if(validateWord(input)) {
            wordsInputtedTextArea.append(input + "\n");
            updateUserScore(input);
            resultTextField.setText("");
        }
        // if the word is invalid, tell the user, don't update the score
        else {
            resultTextField.setForeground(Color.red);
            resultTextField.setText("This is not a valid word! Try again.");
        }
        // reset the user input field
        userInputField.setText("");
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

        givenScrambleTextField.setText(currWord);
    }

    public void resetBoardEndGame() {
        // reset the GUI components of the board
        userInputField.setText("");
        wordsInputtedTextArea.setText("");
        resultTextField.setText("");

        // set board to new word 
        // remove this key from the arraylist when its used 
        updateWord();
    }
    

    public void resetBoard() {
        // update the board counter
        boardCounter++;
        // if the reset button has been hit 3 times, decrement the score by 5 and display the status
        if(boardCounter == 3){
            // score -= 5; // TODO: do we need this?
            // TODO: call updateUserScore in place of modifying here?
            player.setCurrScore(player.getCurrScore()-5);
            scoreField.setText("Score: " + player.getCurrScore());
            
            boardCounter = 0;
            resultTextField.setText("Board has been reset 3 times! You lose 5 points.");
        }

        resetBoardEndGame();
        
    }

    // For a specific word, user scores increases by x amt of points
    public void updateUserScore(String input){
        int currentSum = player.getCurrScore();
        currentSum += vc.assignPoints(input);
        player.setCurrScore(currentSum);
        scoreField.setText("Score: " + player.getCurrScore());
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
