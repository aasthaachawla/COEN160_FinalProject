public class ScoreAdmin {
    int newScore;
    int newLevel;

    public void updateScore(User obj) {
        obj.currScore = newScore;
    }

    public void updateUserLevel(User obj) {
        obj.userLevel = newLevel;
    }
}
