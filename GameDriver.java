public class GameDriver {
    public static void main(String[] args){
        User player1 = new User("name", 2, "beg");
        GameManager game = new GameManager(player1);
        game.startGame();
    }
}