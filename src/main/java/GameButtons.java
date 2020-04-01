import javax.swing.*;
import java.awt.*;

public class GameButtons extends JPanel {
    private static GameButtons gameButtons;
    private JButton startButton;
    private JButton undoButton;
    private JButton dropButton;
    private GameButtons(){

        prepareBoard();
        prepareButtons();
    }

    public static GameButtons getInstance(){
        if(gameButtons==null) gameButtons=new GameButtons();
        return gameButtons;
    }

    private void prepareButtons(){

        startButton=new JButton("Start");
        undoButton=new JButton("Undo");
        dropButton=new JButton("Drop");
        add(startButton);
        add(undoButton);
        add(dropButton);
        startButton.addActionListener(Action.getInstance());
        undoButton.addActionListener(Action.getInstance());
        dropButton.addActionListener(Action.getInstance());
    }
    private void prepareBoard(){
        setBounds(Scales.GameButtons_X,Scales.GameButtons_Y,Scales.GameButtons_Width,Scales.GameButtons_Height);
        setBackground(Scales.GameButtons_Color);
        setLayout(new FlowLayout());
        setVisible(true);
    }

    public JButton getStartButton() {
        return startButton;
    }

    public JButton getUndoButton() {
        return undoButton;
    }

    public JButton getDropButton() {
        return dropButton;
    }

}
