import javax.swing.*;
import java.awt.*;

public class GameBoard extends JFrame {
    GameController gameController;
    public GameBoard() {
        Profile profile = Profile.getInstance();
        prepareBoard();
        addComponents();
        setVisible(true);
    }

    private void prepareBoard() {
        setSize(Scales.GameBoard_Width, Scales.GameBoard_Height);
        setTitle("Tetris");
        getContentPane().setBackground(Scales.GameBoard_Color);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);

    }

    private void addComponents() {
        Scales.initialize();
        gameController=GameController.getInstance();
        add(GameButtons.getInstance());
        add(ScoreBoard.getInstance());
        add(HintBoard.getInstance());
        add(Score.getInstance());
        add(Board.getInstance());

    }
}
