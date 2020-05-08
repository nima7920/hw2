import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BoardScales extends JFrame {
    private JLabel description1, description2, setM, setN;
    private JTextField getM, getN;
    private JButton setButton;

    public BoardScales() {
        setM = new JLabel("enter m<61 and >11(recommended size is 20)");
        setN = new JLabel("enter n<31 and >5(recommended siz is 10)");
        description1 = new JLabel("Enter the Dimensions of the Game Board");
        description2 = new JLabel("(It is recommended to set m=2n)");
        getM = new JTextField(10);
        getN = new JTextField(10);
        setButton = new JButton("Set");
        prepareBoard();
    }

    private void prepareBoard() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 300);
        description1.setBounds(30, 10, 400, 20);
        description2.setBounds(30, 30, 400, 20);

        setN.setBounds(30, 80, 250, 20);
        setM.setBounds(30, 120, 300, 20);
        getN.setBounds(350, 80, 100, 20);
        getM.setBounds(350, 120, 100, 20);
        setButton.setBounds(200, 150, 100, 50);
        setLayout(null);
        add(description1);
        add(description2);
        add(setN);
        add(getN);
        add(setM);
        add(getM);
        add(setButton);
        setVisible(true);
        setButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String n = getN.getText().trim(), m = getM.getText().trim();
                if (isValidN(n) && isValidM(m)) {
                    Scales.setBoard_Dimension_n(Integer.valueOf(n));
                    Scales.setBoard_Dimension_m(Integer.valueOf(m));
                    setVisible(false);
                    dispose();
                    new GameBoard();
                } else {
                    JOptionPane.showMessageDialog(null, "InValid entries.can't set dimensions");
                }
            }
        });
    }

    private boolean isValidN(String s) {
        try {
            int a = Integer.valueOf(s);
            if (a < 31 && a > 5) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isValidM(String s) {
        try {
            int a = Integer.valueOf(s);
            if (a < 61 && a > 11) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
}
