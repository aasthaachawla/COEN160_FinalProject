public class GameDriver {
    public static void main(String[] args){
        User player1 = new User();
        GameManager game = new GameManager(player1);
        game.buildGUI();
    }
}