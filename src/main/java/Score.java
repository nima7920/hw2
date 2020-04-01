import javax.swing.*;

public class Score extends JLabel implements ReStartable {
    private static Score score;
    private long currentScore = 0;

    private Score() {
        setBounds(Scales.ScoreLabel_X, Scales.ScoreLabel_Y, Scales.ScoreLabel_Width, Scales.ScoreLabel_Height);
        setForeground(Scales.ScoreLabel_Color);
        setText("Score:" + currentScore);
        setVisible(true);
    }

    public static Score getInstance() {
        if (score == null) score = new Score();
        return score;
    }

    public void updateScore(long newScore) {
        currentScore = newScore;
        setText("Score:" + currentScore);
    }

    @Override
    public void reStart() {
        updateScore(0);
    }
}
