import javax.swing.*;
import java.util.ArrayList;

public class ScoreBoard extends JPanel {
    private static ScoreBoard scoreBoard;
    private JTable scoreTable;
    private String[][] table;
    private String[] row;

    private ScoreBoard() {

//        prepareScores();
        prepareBoard();
    }

    public static ScoreBoard getInstance() {
        if (scoreBoard == null) scoreBoard = new ScoreBoard();
        return scoreBoard;
    }

    public void setScores(ArrayList<Long> scores) {
        this.removeAll();
        row = new String[]{"Rank", "Score"};
        table = new String[][]{{"1", scores.get(0)+""}, {"2", scores.get(1)+""}, {3 +"", scores.get(2)+""}, {4 + "", scores.get(3)+""},
                {5 + "", scores.get(4)+""}, {"6", scores.get(5)+""}, {"7", scores.get(6)+""}, {"8", scores.get(7)+""}, {"9", scores.get(8)+""},
                {"10", scores.get(9)+""}};
        scoreTable = new JTable(table, row);
        scoreTable.setBounds(0, 50, Scales.ScoreBoard_Width, Scales.ScoreBoard_height);
        scoreTable.setEnabled(false);
        JScrollPane sp = new JScrollPane(scoreTable);
        add(sp,BoxLayout.X_AXIS);

    }

    private void prepareBoard() {
        setBackground(Scales.ScoreBoard_Color);
        setBounds(Scales.ScoreBoard_X, Scales.ScoreBoard_Y, Scales.ScoreBoard_Width, Scales.ScoreBoard_height);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        scoreTable=new JTable();
        scoreTable.setEnabled(false);
        JScrollPane sp = new JScrollPane(scoreTable);
        add(sp);
        setVisible(true);

    }


}
