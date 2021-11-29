import java.io.*;
import java.util.*;

public class FileManager {    
    private ArrayList<User> userObjects = new ArrayList<>();

    /* serialize the ArrayList of userObjects in a preexisting file */
    public void serialize() {
        // Serialize instance via ObjectOutputStream
		try {
			FileOutputStream fileOut = new FileOutputStream("players.dat");
			ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
			objectOut.writeObject(userObjects);
			objectOut.close();
			fileOut.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

    }

    /* update the user object with their most up-to-date score and user level. */
    public void updateUserObject(User currPlayer){
        for(int i = 0; i<userObjects.size(); i++){
            if(userObjects.get(i).getName().equals(currPlayer.getName())) {
                // find the specific userObject
                userObjects.get(i).setCurrScore(currPlayer.getCurrScore());
                userObjects.get(i).setHighScore(currPlayer.getHighScore());
                userObjects.get(i).setUserLevel(currPlayer.getUserLevel());
                serialize();
                return;
            }
        }
    }
    /* first method called -> check if a user already exists in the List. If not, add a new User object. */
    public User checkIFUserExists(String name) {
        deserialize();
        for(int i = 0; i<userObjects.size(); i++){
            if(userObjects.get(i).getName().equals(name)) {
                // player already exists
                System.out.println("User exists");
                System.out.println("score + " + userObjects.get(i).getHighScore());
                return userObjects.get(i);
            }
        }
        // player does not exist. create a new one.
        System.out.println("User does not exist");
        User newPlayer = new User(name, 0, 0, "beginner");
        userObjects.add(newPlayer);
        return newPlayer; 
    }

    /* deserialize the ArrayList of userObjects in a file */
    public void deserialize() {
        // Deserialize and print output
		try {
			FileInputStream fileIn = new FileInputStream("players.dat");
			ObjectInputStream objectIn = new ObjectInputStream(fileIn);

            userObjects = (ArrayList) objectIn.readObject();

            for(User u: userObjects)
                System.out.println(u);

            objectIn.close();
			fileIn.close();
            	
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        FileManager fm = new FileManager();
        User dummy = new User("dummy object", 1, 1, "na");
        fm.userObjects.add(dummy);
        fm.serialize();
        fm.checkIFUserExists("dummy object");
    }
}
