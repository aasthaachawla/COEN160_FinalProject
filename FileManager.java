import java.io.*;

public class FileManager {
    User player;

    public void serialize() {
        // Serialize instance via ObjectOutputStream
		try {
			FileOutputStream fileOut = new FileOutputStream("players.txt");
			ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
			objectOut.writeObject(player);
			objectOut.close();
			fileOut.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

    }

    public void deserialize() {
        // Deserialize and print output
		try {
			FileInputStream fileIn = new FileInputStream("players.txt");
			ObjectInputStream objectIn = new ObjectInputStream(fileIn);
			User out = (User) objectIn.readObject();
			System.out.println("Deserialized output: " + out.getName() + " " + out.getCurrScore() + " " + out.getUserLevel());
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

    public FileManager(User player) {
        this.player = player;
    }
}

// TODO: check if we can append multiple players to this text file via instances of FileManager