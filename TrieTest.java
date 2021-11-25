import java.util.HashSet;
import java.util.Scanner;

public class TrieTest {
    public final static int ALPHABET_SIZE = 26;
	private TrieNode root;
	
    /* Class Constrcutor */
	public TrieTest() {
		root = new TrieNode('\0');
	}
	
    /* Nested TrieNode Class */
	public class TrieNode {
		public char c;
		public boolean isWord;
		public TrieNode[] children;

		// TrieNode Constructor 
		public TrieNode(char c) {
			this.c = c;
			isWord = false;
			children = new TrieNode[ALPHABET_SIZE];
		}
	};
	
    /* Insert word into Trie */
	public void insert(String word) {
		TrieNode curr = root;
		
		// iterate through all characters
		for(int i = 0; i < word.length(); i++) {
			// extract character
			char c = word.charAt(i);
			//check if curr already has a node created at char c
			int index = c - 'a';
			if(curr.children[index] == null) {
				curr.children[index] = new TrieNode(c);
			}
			//moving down the chain inside trie
			curr = curr.children[index];
		}
		
		// very last character needs to be marked as true 
		curr.isWord = true;
	}

    /* Helper Function: Determines if word is in the Trie*/
    private TrieNode getNode(String word) {
        // set current node to root
        TrieNode curr = root;
        // traversing to end of Trie
        for(int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            // if trie does not have the character -> word does not exist
            if(curr.children[c-'a'] == null) return null;
            curr = curr.children[c-'a'];
        }
        // last character in the word
        return curr;
    }

    // returns if word is in the Trie
    public boolean wordExists(String word) {
        TrieNode temp = getNode(word);
        return temp != null;
    }

    // return if any word that starts with given prefix
    public boolean startsWith(String prefix) {
        return getNode(prefix) != null;
    }

    public static void main(String[] args) {
        TrieTest vc = new TrieTest();
        // list of words users will see in the game
        String[] keys = {"hello"};
        String word = "hello";
        HashSet<Character> set = new HashSet<>();
        for(int i = 0; i < word.length(); i++) {
            set.add(word.charAt(i));
        }
        
        // adding words to trie
        for(int i = 0; i < keys.length; i++) {
            vc.insert(keys[i]);
        }

        Scanner sc = new Scanner(System.in);
		System.out.println("Enter word: ");
	    String temp = sc.nextLine();

        for(int j = 0; j < temp.length(); j++) {
            char c = temp.charAt(j);
            if(!set.contains(c)) System.out.println("false");
        }
        System.out.println("true");
        // System.out.println(vc.wordExists(temp));

    }
}