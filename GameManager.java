import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameManager {
    private User player;
    private int boardCounter;
    private Timer timer;
    private String currWord;
    private FileManager fm;
    private boolean boardIntermediateLevel, boardAdvancedLevel;

    private ValidationCheck vc;
    private ArrayList<String> keys;

    /* GUI private attributes */
    private JFrame window;
	private JPanel topPanel;
    private JPanel playerInfoPanel;
    private JPanel wordPanel;
    private JPanel interactiveGamePanel;
    private JPanel timerAndWordPanel;
    private JPanel descriptionPanel;

    private JTextField nameField;
    private JTextField currentScoreField;
    private JTextField highScoreField;
    private JButton gameDescription;
    
    private JTextField timerLabel;
    private JTextField givenScrambleTextField;

    private JTextArea wordsInputtedTextArea;
    private JTextField resultTextField;

    private JButton refreshGame;
    private JTextField userInputField;
    private JButton submitWord;

    public GameManager() {
        fm = new FileManager();
        boardCounter = 0;
        keys = new ArrayList<String>();
        vc = new ValidationCheck();
        boardIntermediateLevel = false; 
        boardAdvancedLevel = false;

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
        keys.add("ipantca");
        keys.add("dsismis");
        keys.add("alpidpe");
        keys.add("nahmopt");
        keys.add("ibgorty");
    }

    /* Starts the GUI and builds a User/Player object. Sets all necessary attributes.*/
    public void startGame(){
        buildGUI();
        // new User, send a JOptionPane to create this user.
        String getUserInput = JOptionPane.showInputDialog(topPanel, "Enter your name to login as an existing user \nor create a new user. Select cancel to login as a guest.");
        if(getUserInput == null){
            nameField.setText("Name: Guest");
            currentScoreField.setText("Current Score: 0");
            player = new User("Guest", 0, 0, "Beginner");
        }
        else {
            player = fm.checkIFUserExists(getUserInput);
            nameField.setText("Name: " + player.getName());
            highScoreField.setText("High Score: " + player.getHighScore() + " "); 
            player.setCurrScore(0);
            currentScoreField.setText("Current Score: 0");
        }
        
        givenScrambleTextField.setText(currWord);
        howToPlay();
        givenScrambleTextField.setForeground(Color.black);
        setTimer();
        userInputField.setText("");
        userInputField.setForeground(Color.black);
    }

    /* Offers the user an option to continue playing. Otherwise, ends the game and updates the stored information about User */
    public void endGame(){
        // update the stored object information on this user provided they are not a guest, and serialize it.
        player.setHighScore(maxScore());
        if(!player.getName().equals("Guest"))
            fm.updateUserObject(player);

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
            currentScoreField.setText("Current Score: " + player.getCurrScore());
            highScoreField.setText("High Score: " + player.getHighScore());
            // update high score and high score field 
            initializeKeys();
            setTimer();
        }
    }

    public void setTimer(){
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            int i = 60;

            public void run() {
                timerLabel.setText("Time left: " + i);
                i--;
                if (i < 0) {
                    timer.cancel();
                    timerLabel.setText("Time Over");
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
        playerInfoPanel = new JPanel(new GridLayout(0, 3));
        wordPanel = new JPanel(new BorderLayout());
        interactiveGamePanel = new JPanel(new BorderLayout());
        timerAndWordPanel = new JPanel(new GridLayout(2, 0));
        descriptionPanel = new JPanel(new BorderLayout());

        // fonts
        Font regularTextFont = new Font("SansSerif", Font.PLAIN, 16);
        Font wordFont = new Font("SansSerif", Font.BOLD, 20);
        Font resultsFont = new Font("SansSerif", Font.ITALIC, 14);

        // create GUI components for the playerInfoPanel - name, score, and description
        nameField = new JTextField("Name: " + "Guest");
        nameField.setEditable(false);
        nameField.setHorizontalAlignment(JTextField.CENTER);
        nameField.setBackground(Color.decode("#CCDCFD"));
        nameField.setFont(regularTextFont);
        currentScoreField = new JTextField("Current Score: " + "0");
        currentScoreField.setEditable(false);
        currentScoreField.setHorizontalAlignment(JTextField.CENTER);
        currentScoreField.setBackground(Color.decode("#CCDCFD"));
        currentScoreField.setFont(regularTextFont);
        highScoreField = new JTextField("High Score: " + "0");
        highScoreField.setEditable(false);
        highScoreField.setHorizontalAlignment(JTextField.CENTER);
        highScoreField.setBackground(Color.decode("#CCDCFD"));
        highScoreField.setFont(regularTextFont);
        playerInfoPanel.add(nameField);
        playerInfoPanel.add(currentScoreField);
        playerInfoPanel.add(highScoreField);
        playerInfoPanel.setBackground(Color.decode("#9EA3F5"));
        
        timerLabel = new JTextField("");
        timerLabel.setEditable(false);
        timerLabel.setHorizontalAlignment(JTextField.CENTER);
        timerLabel.setBackground(Color.decode("#CCDCFD"));
        timerLabel.setForeground(Color.black);
        timerLabel.setFont(regularTextFont);
        timerAndWordPanel.add(timerLabel);
        timerAndWordPanel.add(playerInfoPanel);
        timerAndWordPanel.setBackground(Color.decode("#9EA3F5"));

        // create GUI components for wordPanel - timer, valid user inputted words and the result status
        givenScrambleTextField = new JTextField(currWord); // TODO: autogenerate these words
        givenScrambleTextField.setEditable(false);
        givenScrambleTextField.setHorizontalAlignment(JTextField.CENTER);
        givenScrambleTextField.setForeground(Color.decode("#BBBEFE"));
        givenScrambleTextField.setBackground(Color.decode("#BBBEFE"));
        givenScrambleTextField.setFont(wordFont);
        wordsInputtedTextArea = new JTextArea(200, 400);
        wordsInputtedTextArea.setEditable(false);
        wordsInputtedTextArea.setBackground(Color.decode("#CCDCFD"));
        wordsInputtedTextArea.setFont(regularTextFont);
        resultTextField = new JTextField("");
        resultTextField.setEditable(false);
        resultTextField.setBackground(Color.decode("#BBBEFE"));
        resultTextField.setFont(resultsFont);
        wordPanel.add(givenScrambleTextField, BorderLayout.NORTH);
        wordPanel.add(wordsInputtedTextArea, BorderLayout.CENTER);
        wordPanel.add(resultTextField, BorderLayout.SOUTH);
        wordPanel.setBackground(Color.decode("#9EA3F5"));

        // create GUI components for interactiveGamePanel - buttons and text field for user to interact with
        gameDescription = new JButton("ⓘ");
        refreshGame = new JButton("Refresh Game");
        descriptionPanel.add(gameDescription, BorderLayout.WEST);
        descriptionPanel.add(refreshGame, BorderLayout.EAST);
        descriptionPanel.setBackground(Color.decode("#9EA3F5"));
        userInputField = new JTextField("", 100);
        userInputField.setText("Enter words here");
        userInputField.setForeground(Color.lightGray);
        userInputField.setFont(regularTextFont);
        submitWord = new JButton("Submit");
        interactiveGamePanel.add(descriptionPanel, BorderLayout.WEST);
        interactiveGamePanel.add(userInputField, BorderLayout.CENTER);
        interactiveGamePanel.add(submitWord, BorderLayout.EAST);
        interactiveGamePanel.setBackground(Color.decode("#9EA3F5"));

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

        gameDescription.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // give a pop up description
                howToPlay();
            }
        });

        // add the main 3 panels to the top panel with a Border Layout 
        topPanel.setForeground(Color.white);
        topPanel.add(timerAndWordPanel, BorderLayout.NORTH);
        topPanel.add(wordPanel, BorderLayout.CENTER);
        topPanel.add(interactiveGamePanel, BorderLayout.SOUTH);
        topPanel.setBackground(Color.lightGray);
        // set the window's content pane to the top panel and display the window
        window.setContentPane(topPanel);
        window.pack();
        window.setTitle("Final Project - Aastha & Shivangi");
        window.setSize(700, 400);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
    }

    /* show how to play before game */
    public void howToPlay() {
        JOptionPane.showMessageDialog(window, "You have 60 seconds to list as many words that can be created \nwith the letters you are given (and repeated letters are allowed). \nIn this time, you can press the “Refresh Board” button to change the set of letters. If the \n“Refresh Board” button is pressed 3 times, you will lose 5 points. \nIf you choose to login, you must play through a full game in order for your score to be stored. \nRules: \n- You must create words that are at least 3 letters in length, and you must provide unique words \n- any reused words will not increment points. \n- Points are assigned based on the number of letters in the word.", "Rules", JOptionPane.INFORMATION_MESSAGE);
    }

    /* update the score and word-input area when the user inputs a word */
    public void updateBoard(String input) {
        // validate if the word matches the rules
        if(validateWord(input)) {
            wordsInputtedTextArea.append(input + "\n");
            updateUserScore(input);
            resultTextField.setText("");
        }
        // if the word is invalid, tell the user, don't update the score
        else {
            if((!vc.wordExistsInDictionary(input))){
                resultTextField.setForeground(Color.blue);
                resultTextField.setText("That's not a real english word! Try again.");
            } else if (!vc.isWordNotInputted(input)) {
                resultTextField.setForeground(Color.blue);
                resultTextField.setText("This word has already been inputted! Try again.");
            }
            else {
                resultTextField.setForeground(Color.red);
                resultTextField.setText("That is not a valid word! Try again.");
            }
        }
        // reset the user input field
        userInputField.setText("");
        checkUserLevel();
    }

    /* update the word on the board */
    public void updateWord(){
        if(keys.isEmpty())
            initializeKeys();
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

    /* reset the GUI components of the board and the word */
    public void resetBoardEndGame() {
        userInputField.setText("");
        wordsInputtedTextArea.setText("");

        // set board to new word 
        // remove this key from the arraylist when its used 
        updateWord();
    }
    
    /* resets the board when the resetBoard button is pressed */
    public void resetBoard() {
        // update the board counter
        boardCounter++;
        // if the reset button has been hit 3 times, decrement the score by 5 and display the status
        if(boardCounter >= 3){
            // score -= 5; // TODO: do we need this?
            // TODO: call updateUserScore in place of modifying here?
            player.setCurrScore(player.getCurrScore()-5);
            currentScoreField.setText("Current Score: " + player.getCurrScore());
            resultTextField.setForeground(Color.blue);
            if(boardCounter == 3)
                resultTextField.setText("Board has been reset 3 times! You lose 5 points.");
            else 
                resultTextField.setText("Board has been reset more than 3 times! You lose 5 points.");
        }

        resetBoardEndGame();   
    }

    /* Set user level based on their current score */
    public void checkUserLevel() {
        if(!boardIntermediateLevel && player.getCurrScore() >= 20 && player.getCurrScore() < 35){
            player.setUserLevel("Intermediate");
            JOptionPane.showMessageDialog(window, "Congratulations! You have advanced to intermediate level.");
            boardIntermediateLevel = true;
        }
        else if(!boardAdvancedLevel && player.getCurrScore() >= 35 && player.getCurrScore() < 40) {
            player.setUserLevel("Master");
            JOptionPane.showMessageDialog(window, "Congratulations! You have advanced to master level.");
            boardAdvancedLevel = true;
        }
    }

    // For a specific word, user scores increases by x amt of points
    public void updateUserScore(String input){
        int currentSum = player.getCurrScore();
        currentSum += vc.assignPoints(input);
        player.setCurrScore(currentSum);
        currentScoreField.setText("Current Score: " + player.getCurrScore());
    }

    // Returns player's score at the end of the game
    public int finalGameScore(){
        return player.getCurrScore();
    }

    // Updates user's highest score compared 
    public int maxScore(){
        return Math.max(player.getCurrScore(), player.getHighScore());
    }

    /* Call ValidationCheck to verify the inputted word matches all the criteria:
    * word does not utilize letters outside the options
    * word is a valid dictionary word
    * word is at least three letters 
    */
    public boolean validateWord(String input) {        
        return vc.isValid(input);
    }

}
