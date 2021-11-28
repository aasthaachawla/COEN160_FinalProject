import java.io.*;

public class FileManager {
    public void serialize(User playerToSerialize) {
        // Serialize instance via ObjectOutputStream
		try {
			FileOutputStream fileOut = new FileOutputStream("players.dat");
			ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
			objectOut.writeObject(playerToSerialize);
			objectOut.close();
			fileOut.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

    }

    private void checkIFUserExists(User out, User currentPlayer) {
        if(out.getName().equals(currentPlayer.getName()) && out.getCurrScore() == currentPlayer.getCurrScore()) {
            currentPlayer.setName(out.getName());
            currentPlayer.setCurrScore(out.getCurrScore());
            System.out.println("This player exists already");
        }
        else {
            //serialize(currentPlayer);
            System.out.println("This player does not exist yet.");
        }
    }

    public void deserialize(User currentPlayer) {
        // Deserialize and print output
		try {
			FileInputStream fileIn = new FileInputStream("players.dat");
			ObjectInputStream objectIn = new ObjectInputStream(fileIn);
			User out = (User) objectIn.readObject();
			System.out.println("Deserialized output: " + out.getName() + " " + out.getCurrScore() + " " + out.getUserLevel());
			
            // check if user already exists in text file
            checkIFUserExists(out, currentPlayer);

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
        User dummy = new User("dummy object", 1, "no");
        User dummy2 = new User("new dummy", 2, "no");
        fm.serialize(dummy2);
        fm.deserialize(dummy);
        fm.deserialize(dummy2);
    }
}

// TODO: check if we can append multiple players to this text file via instances of FileManager