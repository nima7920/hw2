import javax.swing.*;
import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

public class Board extends JPanel {
    private static Board board;
    private Rectangle2D[][] cells;
    private Area usedArea;
    private Piece currentPiece;

    private Board() {
        prepareBoard();


    }

    public static Board getInstance() {
        if (board == null) board = new Board();
        return board;
    }

    private void prepareBoard() {
        addKeyListener(Action.getInstance());

        setBounds(Scales.Board_X, Scales.Board_Y, Scales.Board_Width, Scales.Board_Height);
        setBackground(Scales.Board_Color);
        setVisible(true);
        setLayout(null);
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
        requestFocus();
        g2d.setPaint(Scales.Board_Cell_Color);
        for (int i = 0; i < Scales.getBoard_Dimension_n(); i++) {
            for (int j = 0; j < Scales.getBoard_Dimension_m(); j++) {
                g2d.fill(cells[i][j]);
            }
        }
        g2d.setPaint(Scales.Board_usedArea_Color);
        g2d.fill(usedArea);
        g2d.setPaint(Scales.getPieceColor(currentPiece.getType()));
        g2d.fill(currentPiece);
    }

    public Rectangle2D[][] getCells() {
        return cells;
    }

    public void setCells(Rectangle2D[][] cells) {
        this.cells = cells;
    }

    public void setCurrentPiece(Piece currentPiece) {
        this.currentPiece = currentPiece;
    }

    public void setUsedArea(Area usedArea) {
        this.usedArea = usedArea;
    }


}
