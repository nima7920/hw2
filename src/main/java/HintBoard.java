import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

// singelton class:
public class HintBoard extends JPanel implements ReStartable {
    private static HintBoard hintBoard;
    private Piece nextPiece;
    private JLabel lblRemovedRows;
    private Rectangle2D[][] cells;

    private HintBoard() {
        prepareBoard();
    }

    public static HintBoard getInstance() {
        if (hintBoard == null) hintBoard = new HintBoard();
        return hintBoard;
    }

    private void prepareBoard() {

        setBackground(Scales.HintBoard_Color);
        setBounds(Scales.HintBoard_X, Scales.HintBoard_Y, Scales.HintBoard_Width, Scales.HintBoard_Height);
        // removed rows label
        lblRemovedRows = new JLabel("Removed rows:" + 0);
        lblRemovedRows.setBounds(Scales.HintBoard_Label_X, Scales.HintBoard_Label_Y,
                Scales.HintBoard_Label_Width, Scales.HintBoard_Label_Height);
        lblRemovedRows.setForeground(Scales.HintBoard_Label_Color);
        add(lblRemovedRows);
        // next piece:
        cells = new Rectangle2D[4][2];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 2; j++) {
                cells[i][j] = new Rectangle2D.Double(Scales.HintBoard_Cell_X + i * (Scales.HintBoard_Cell_Size + 1),
                        Scales.HintBoard_Cell_Y + j * (Scales.HintBoard_Cell_Size + 1),
                        Scales.HintBoard_Cell_Size, Scales.HintBoard_Cell_Size);
            }
        }
        setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        RenderingHints rh = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        rh.put(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHints(rh);
        g2d.setPaint(Scales.getPieceColor(nextPiece.getType()));
        g2d.fill(nextPiece);
    }

    public void setRemovedRows(long removedRows) {
        lblRemovedRows.setText("Removed rows:" + removedRows);
    }

    public void setNextPiece(Piece nextPiece) {
        this.nextPiece = nextPiece;
        int n = Scales.getBoard_Dimension_n(), m = Scales.getBoard_Dimension_m();
        int[] x = new int[]{nextPiece.getX()[0] - n / 2 + 2, nextPiece.getX()[1] - n / 2 + 2, nextPiece.getX()[2] - n / 2 + 2, nextPiece.getX()[3] - n / 2 + 2};
        int[] y = nextPiece.getY();
        nextPiece.setSquares(cells[x[0]][y[0]], cells[x[1]][y[1]], cells[x[2]][y[2]], cells[x[3]][y[3]]);
        nextPiece.merge();
        repaint();
    }

    @Override
    public void reStart() {
        setRemovedRows(0);
    }
}
