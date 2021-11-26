public class ScoreAdmin {
    int newScore;
    String newLevel;

    public void updateScore(User obj) {
        obj.setCurrScore(newScore);
    }

    public void updateUserLevel(User obj) {
        obj.setUserLevel(newLevel);
    }
}
