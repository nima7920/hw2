import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Action implements KeyListener, ActionListener {
    private static Action action;
    private Timer timer;
    private boolean paused = true;

    private Action() {
        timer = new Timer(1000, new Ticker());
    }

    public static Action getInstance() {
        if (action == null) action = new Action();
        return action;
    }

    public void resetTimer() {
        timer.stop();
        Sound.getInstance().playReachedEnd();

        timer.setDelay(1000);
        timer.start();
    }

    public void gameOver() {

        timer.stop();
        Sound.getInstance().playGameOver();
        paused = true;
        Sound.getInstance().playGameOver();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == GameButtons.getInstance().getStartButton()) {
            paused = false;
            timer.start();
        } else if (e.getSource() == GameButtons.getInstance().getDropButton()) {
            if (paused == false) {
                timer.setDelay(1);
            }
        } else {  // undo is pressed
            if (paused == false) {
                GameController.getInstance().undo();
            }

        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case Scales.Rotate_Key: {
                GameController.getInstance().rotate();
                break;
            }
            case Scales.Right_Key: {
                GameController.getInstance().moveRight();
                break;
            }
            case Scales.Left_Key: {
                GameController.getInstance().moveLeft();
                break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    private class Ticker implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            GameController.getInstance().moveDown();
        }
    }
}
