import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;

public class ValidationCheck {
    private HashSet<Character> letters = new HashSet<>();
    private HashSet<String> wordsInputted = new HashSet<>();

    /* Insert Characters into Validation Set*/
    public void insert(String word){
        for(int i = 0; i < word.length(); i++) {
            letters.add(word.charAt(i));
        }
    }

    /* Determines if User has inputted letters only from validation set*/
    private boolean inputValid(String word) {
        // Check to see if the word is at least 3 characters 
        if(word == null || word.length() < 3 || word.equals("")) return false;
        for(int i = 0; i < word.length(); i++) {
            if(!letters.contains(word.charAt(i))) return false;
        }
        return true && isWordNotInputted(word);
    }

    /* Ensure User is NOT inputting the same word */
    public boolean isWordNotInputted(String word){
        if(!wordsInputted.contains(word)) {
            wordsInputted.add(word);
            return true;
        } 
        return false;
    }

    public boolean isWordInputted(String word){
        System.out.println(word);
        if(wordsInputted.contains(word)) return true;
        return false;
    }

    /* Check if user word input is an english word in the dictionary */
    private boolean wordExistsInDictionary(String word) {
        try {
            BufferedReader in = new BufferedReader(new FileReader(
                    "words_alpha.txt"));
            String str;
            while ((str = in.readLine()) != null) {
                // str match the user input = word exists in dictionary
                if (str.indexOf(word) != -1 && str.equals(word)) {
                    return true;
                }
            }
            in.close();
        } catch (IOException e) {
        }
        return false;
    }

    /* Check if Valid Word --> all conditions met */
    public boolean isValid(String word) {
        return inputValid(word) && wordExistsInDictionary(word);
    }

    /* Reset Validation Check to empty Set */
    public void removeCharacters() {
        letters.clear();
    }

    /* Return number of points based on the length of word */
    public int assignPoints(String word) {
        return word.length();
    }
}