import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;

public class ValidationCheck {
    public HashSet<Character> set = new HashSet<>();

    /* Insert Characters into Validation Set*/
    public void insert(String word){
        for(int i = 0; i < word.length(); i++) {
            set.add(word.charAt(i));
        }
    }

    /* Determines if User has inputted letters only from validation set*/
    public boolean inputValid(String word) {
        // Check to see if the word is at least 3 characters 
        if(word == null || word.length() < 3 || word.equals("")) return false;
        for(int i = 0; i < word.length(); i++) {
            if(!set.contains(word.charAt(i))) return false;
        }
        return true;
    }

    /* Check if user word input is an english word in the dictionary */
    public boolean wordExistsInDictionary(String word) {
        try {
            BufferedReader in = new BufferedReader(new FileReader(
                    "words_alpha.txt"));
            String str;
            while ((str = in.readLine()) != null) {
                if (str.indexOf(word) != -1 && str.equals(word)) {
                    // System.out.println("String: " + str + " at Index: " + str.indexOf(word));
                    return true;
                }
            }
            in.close();
        } catch (IOException e) {
        }
        return false;
    }

    /* Check if Valid Word */
    public boolean isValid(String word) {
        return inputValid(word) && wordExistsInDictionary(word);
    }

    /* Return points based on the length of word */
    public int assignPoints(String word) {
        return word.length();
    }

    public static void main(String[] args) {
        // Initialize Validation with input word
        ValidationCheck vc = new ValidationCheck();
        // Insert letters into validation set
        vc.insert("altred");

        Scanner sc = new Scanner(System.in);
        int flag = 1;
        while(flag != 0) {
            System.out.println("Enter word: ");
            String temp = sc.nextLine();
            System.out.println(vc.isValid(temp));
        }
    }
}