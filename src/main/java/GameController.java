import javax.swing.*;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.util.concurrent.TimeUnit;

public class GameController {

    private Board board;
    private static GameController gameController;
    private Profile profile;
    private Rectangle2D[][] cells;
    private Area[] rows;
    private Area usedArea;
    private Piece currentPiece;
    private PieceGenerator pieceGenerator;


    private GameController() {

        initialize();

    }

    public static GameController getInstance() {
        if (gameController == null) gameController = new GameController();
        return gameController;
    }

    // Constructs objects:
    private void initialize() {
        board = Board.getInstance();
        profile = Profile.getInstance();
        int n = Scales.getBoard_Dimension_n(), m = Scales.getBoard_Dimension_m();
        cells = new Rectangle2D[Scales.getBoard_Dimension_n()][Scales.getBoard_Dimension_m()];
        rows = new Area[m];
        int x = Scales.Board_Width / Scales.getBoard_Dimension_n();
        int y = Scales.Board_Height / Scales.getBoard_Dimension_m();
        for (int j = 0; j < Scales.getBoard_Dimension_m(); j++) {
            rows[j] = new Area();
            for (int i = 0; i < Scales.getBoard_Dimension_n(); i++) {
                cells[i][j] = new Rectangle2D.Double(x * i, y * j, x - 1, y - 1);
                rows[j].add(new Area(cells[i][j]));
            }
        }
        pieceGenerator = new PieceGenerator();
        currentPiece = pieceGenerator.generatePiece();
        setPieceCells();
        usedArea = new Area();
        board.setCurrentPiece(currentPiece);
        board.setUsedArea(usedArea);
        board.setCells(cells);
        board.repaint();
    }

    public void undo() {
        currentPiece = pieceGenerator.generateSpecificPiece(currentPiece.getType());
        setPieceCells();
        board.setCurrentPiece(currentPiece);
        board.repaint();
    }

    public void reStart() {
        profile.updateScoresTable();
        Score.getInstance().reStart();
        profile.reStart();
        initialize();
    }

    private void setPieceCells() {
        int[] x = currentPiece.getX(), y = currentPiece.getY();
        currentPiece.setSquares(cells[x[0]][y[0]], cells[x[1]][y[1]], cells[x[2]][y[2]], cells[x[3]][y[3]]);
        currentPiece.merge();
        board.setCurrentPiece(currentPiece);
    }

    // checks whether a specific cell is available for piece
    private boolean isCellAvailable(int x, int y) {
        if (x >= 0 && x < Scales.getBoard_Dimension_n() && y >= 0 && y < Scales.getBoard_Dimension_m())
            if (!usedArea.contains(cells[x][y]))
                return true;

        return false;
    }

    public void moveDown() {
        if (canMoveDown()) {
            currentPiece.moveDown();
            setPieceCells();
            board.repaint();
        } else {  // when a piece reaches to the end
            reachedEnd();
        }
    }

    private void reachedEnd() {
        usedArea.add((Area) currentPiece.clone());
        Action.getInstance().resetTimer();
        cleanBoard();
        profile.addScore(1);
        currentPiece = pieceGenerator.generatePiece();
        setPieceCells();
        board.setCurrentPiece(currentPiece);
        board.repaint();
        checkGameOver();
    }

    private void cleanBoard() {
        Area tempArea = (Area) usedArea.clone();
        for (int i = 0; i < Scales.getBoard_Dimension_m(); i++) {
            tempArea.add(rows[i]);
            if (tempArea.equals(usedArea)) {
                removeRow(i);
                tempArea = (Area) usedArea.clone();
                profile.addRemovedLine();
                profile.addScore(10);
            } else {
                tempArea = (Area) usedArea.clone();
            }

        }

    }

    private void removeRow(int rowNumber) {
        usedArea.subtract(rows[rowNumber]);
        dropRow(rowNumber);
    }

    private void dropRow(int rowNumber) {
        for (int i = rowNumber - 1; i >= 0; i--) {
            for (int j = 0; j < Scales.getBoard_Dimension_n(); j++) {
                if (usedArea.contains(cells[j][i])) {
                    usedArea.subtract(new Area(cells[j][i]));
                    usedArea.add(new Area((cells[j][i + 1])));
                }
            }
        }
    }

    private void checkGameOver() {
        if (currentPiece.checkIntersection(usedArea)) { // game is over
            gameOver();
        }
    }

    private void gameOver() {
        Action.getInstance().gameOver();
        JOptionPane.showMessageDialog(board, "Game Over!! your Score is:" + profile.getScore());

        profile.saveScores();
        reStart();

    }

    public void moveLeft() {
        if (canMoveLeft()) {
            currentPiece.moveLeft();
            setPieceCells();

            board.repaint();
        }
    }

    public void moveRight() {
        if (canMoveRight()) {
            currentPiece.moveRight();
            setPieceCells();
            board.repaint();
        }
    }


    private boolean canMoveDown() {
        int[] x = currentPiece.getX(), y = currentPiece.getY();
        if (isCellAvailable(x[0], y[0] + 1) && isCellAvailable(x[1], y[1] + 1) &&
                isCellAvailable(x[2], y[2] + 1) && isCellAvailable(x[3], y[3] + 1))
            return true;
        return false;
    }

    private boolean canMoveLeft() {
        int[] x = currentPiece.getX(), y = currentPiece.getY();
        if (isCellAvailable(x[0] - 1, y[0]) && isCellAvailable(x[1] - 1, y[1]) &&
                isCellAvailable(x[2] - 1, y[2]) && isCellAvailable(x[3] - 1, y[3]))
            return true;
        return false;
    }

    private boolean canMoveRight() {
        int[] x = currentPiece.getX(), y = currentPiece.getY();
        if (isCellAvailable(x[0] + 1, y[0]) && isCellAvailable(x[1] + 1, y[1]) &&
                isCellAvailable(x[2] + 1, y[2]) && isCellAvailable(x[3] + 1, y[3]))
            return true;
        return false;
    }

    // checking whether rotate is possible,and rotates if is
    public void rotate() {
        int[] x = currentPiece.getX(), y = currentPiece.getY();
        switch (currentPiece.getType()) {
            case Wood: {
                if (currentPiece.getRotationState() == 0) {

                    if (isCellAvailable(x[1], y[1] + 2) && isCellAvailable(x[1], y[1] + 1) && isCellAvailable(x[1], y[1] - 1)) {
                        currentPiece.setX(new int[]{x[1], x[1], x[1], x[1]});
                        currentPiece.setY(new int[]{y[1] - 1, y[1], y[1] + 1, y[1] + 2});
                        currentPiece.setRotationState(1);
                        setPieceCells();
                        board.repaint();
                        break;
                    }
                    if (isCellAvailable(x[2], y[2] + 1) && isCellAvailable(x[2], y[2] - 2) && isCellAvailable(x[2], y[2] - 1)) {
                        currentPiece.setX(new int[]{x[2], x[2], x[2], x[2]});
                        currentPiece.setY(new int[]{y[2] - 2, y[2] - 1, y[2], y[1] + 1});
                        currentPiece.setRotationState(1);
                        setPieceCells();
                        board.repaint();
                        break;
                    }
                } else if (currentPiece.getRotationState() == 1) {
                    if (isCellAvailable(x[1] - 2, y[1]) && isCellAvailable(x[1] - 1, y[1]) && isCellAvailable(x[1] + 1, y[1])) {
                        currentPiece.setX(new int[]{x[1] - 2, x[1] - 1, x[1], x[1] + 1});
                        currentPiece.setY(new int[]{y[1], y[1], y[1], y[1]});
                        currentPiece.setRotationState(2);
                        setPieceCells();
                        board.repaint();
                        break;
                    }
                    if (isCellAvailable(x[2] - 1, y[2]) && isCellAvailable(x[2] + 1, y[2]) && isCellAvailable(x[2] + 2, y[2])) {
                        currentPiece.setX(new int[]{x[2] - 1, x[2], x[2] + 1, x[2] + 2});
                        currentPiece.setY(new int[]{y[2], y[2], y[2], y[2]});
                        currentPiece.setRotationState(2);
                        setPieceCells();
                        board.repaint();
                        break;
                    }
                } else if (currentPiece.getRotationState() == 2) {
                    if (isCellAvailable(x[1], y[1] - 2) && isCellAvailable(x[1], y[1] - 1) && isCellAvailable(x[1], y[1] + 1)) {
                        currentPiece.setX(new int[]{x[1], x[1], x[1], x[1]});
                        currentPiece.setY(new int[]{y[1] + 1, y[1], y[1] - 1, y[1] - 2});
                        currentPiece.setRotationState(3);
                        setPieceCells();
                        board.repaint();
                        break;
                    }
                    if (isCellAvailable(x[2], y[2] + 1) && isCellAvailable(x[2], y[2] + 2) && isCellAvailable(x[2], y[2] - 1)) {
                        currentPiece.setX(new int[]{x[2], x[2], x[2], x[2]});
                        currentPiece.setY(new int[]{y[2] + 2, y[2] + 1, y[2], y[2] - 1});
                        currentPiece.setRotationState(3);
                        setPieceCells();
                        board.repaint();
                        break;
                    }
                } else {
                    if (isCellAvailable(x[1] + 2, y[1]) && isCellAvailable(x[1] - 1, y[1]) && isCellAvailable(x[1] + 1, y[1])) {
                        currentPiece.setX(new int[]{x[1] - 1, x[1], x[1] + 1, x[1] + 2});
                        currentPiece.setY(new int[]{y[1], y[1], y[1], y[1]});
                        currentPiece.setRotationState(0);
                        setPieceCells();
                        board.repaint();
                        break;
                    }
                    if (isCellAvailable(x[2] - 1, y[2]) && isCellAvailable(x[2] + 1, y[2]) && isCellAvailable(x[2] - 2, y[2])) {
                        currentPiece.setX(new int[]{x[2] - 2, x[2] - 1, x[2], x[2] + 1});
                        currentPiece.setY(new int[]{y[2], y[2], y[2], y[2]});
                        currentPiece.setRotationState(0);
                        setPieceCells();
                        board.repaint();
                        break;
                    }
                }
                break;
            }
            case Mountain: {
                if (currentPiece.getRotationState() == 0) {
                    if (isCellAvailable(x[1], y[1] - 1)) {
                        currentPiece.setX(new int[]{x[1], x[1], x[3], x[0]});
                        currentPiece.setY(new int[]{y[1] - 1, y[1], y[3], y[0]});
                        currentPiece.setRotationState(1);
                        setPieceCells();
                        board.repaint();
                        break;
                    }
                } else if (currentPiece.getRotationState() == 1) {
                    if (isCellAvailable(x[1] + 1, y[1])) {
                        currentPiece.setX(new int[]{x[1] + 1, x[1], x[3], x[0]});
                        currentPiece.setY(new int[]{y[1], y[1], y[3], y[0]});
                        currentPiece.setRotationState(2);
                        setPieceCells();
                        board.repaint();
                        break;
                    }
                } else if (currentPiece.getRotationState() == 2) {
                    if (isCellAvailable(x[1], y[1] + 1)) {
                        currentPiece.setX(new int[]{x[1], x[1], x[3], x[0]});
                        currentPiece.setY(new int[]{y[1] + 1, y[1], y[3], y[0]});
                        currentPiece.setRotationState(3);
                        setPieceCells();
                        board.repaint();
                        break;
                    }
                } else {
                    if (isCellAvailable(x[1] - 1, y[1])) {
                        currentPiece.setX(new int[]{x[1] - 1, x[1], x[3], x[0]});
                        currentPiece.setY(new int[]{y[1], y[1], y[3], y[0]});
                        currentPiece.setRotationState(0);
                        setPieceCells();
                        board.repaint();
                        break;
                    }
                }
                break;
            }
            case Right_Leg: {
                if (currentPiece.getRotationState() == 0) {
                    if (isCellAvailable(x[2], y[2] - 1) && isCellAvailable(x[1], y[1] + 1) && isCellAvailable(x[1], y[1] - 1)) {
                        currentPiece.setX(new int[]{x[1], x[1], x[1], x[1] - 1});
                        currentPiece.setY(new int[]{y[1] + 1, y[1], y[1] - 1, y[1] - 1});
                        currentPiece.setRotationState(1);
                        setPieceCells();
                        board.repaint();
                        break;
                    }
                } else if (currentPiece.getRotationState() == 1) {
                    if (isCellAvailable(x[2] + 1, y[2]) && isCellAvailable(x[1] - 1, y[1]) && isCellAvailable(x[1] + 1, y[1])) {
                        currentPiece.setX(new int[]{x[1] - 1, x[1], x[1] + 1, x[1] + 1});
                        currentPiece.setY(new int[]{y[1], y[1], y[1], y[1] - 1});
                        currentPiece.setRotationState(2);
                        setPieceCells();
                        board.repaint();
                        break;
                    }
                } else if (currentPiece.getRotationState() == 2) {
                    if (isCellAvailable(x[2], y[2] + 1) && isCellAvailable(x[1], y[1] + 1) && isCellAvailable(x[1], y[1] - 1)) {
                        currentPiece.setX(new int[]{x[1], x[1], x[1], x[1] + 1});
                        currentPiece.setY(new int[]{y[1] - 1, y[1], y[1] + 1, y[1] + 1});
                        currentPiece.setRotationState(3);
                        setPieceCells();
                        board.repaint();
                        break;
                    }
                } else {
                    if (isCellAvailable(x[2] - 1, y[2]) && isCellAvailable(x[1] - 1, y[1]) && isCellAvailable(x[1] + 1, y[1])) {
                        currentPiece.setX(new int[]{x[1] + 1, x[1], x[1] - 1, x[1] - 1});
                        currentPiece.setY(new int[]{y[1], y[1], y[1], y[1] + 1});
                        currentPiece.setRotationState(0);
                        setPieceCells();
                        board.repaint();
                        break;
                    }
                    break;
                }
            }
            case Left_Leg: {
                if (currentPiece.getRotationState() == 0) {
                    if (isCellAvailable(x[0], y[0] + 1) && isCellAvailable(x[1], y[1] + 1) && isCellAvailable(x[1], y[1] - 1)) {
                        currentPiece.setX(new int[]{x[1], x[1], x[1], x[1] - 1});
                        currentPiece.setY(new int[]{y[1] - 1, y[1], y[1] + 1, y[1] + 1});
                        currentPiece.setRotationState(1);
                        setPieceCells();
                        board.repaint();
                        break;
                    }
                } else if (currentPiece.getRotationState() == 1) {
                    if (isCellAvailable(x[0] - 1, y[0]) && isCellAvailable(x[1] - 1, y[1]) && isCellAvailable(x[1] + 1, y[1])) {
                        currentPiece.setX(new int[]{x[1] + 1, x[1], x[1] - 1, x[1] - 1});
                        currentPiece.setY(new int[]{y[1], y[1], y[1], y[1] - 1});
                        currentPiece.setRotationState(2);
                        setPieceCells();
                        board.repaint();
                        break;
                    }
                } else if (currentPiece.getRotationState() == 2) {
                    if (isCellAvailable(x[0], y[0] - 1) && isCellAvailable(x[1], y[1] + 1) && isCellAvailable(x[1], y[1] - 1)) {
                        currentPiece.setX(new int[]{x[1], x[1], x[1], x[1] + 1});
                        currentPiece.setY(new int[]{y[1] + 1, y[1], y[1] - 1, y[1] - 1});
                        currentPiece.setRotationState(3);
                        setPieceCells();
                        board.repaint();
                        break;
                    }
                } else {
                    if (isCellAvailable(x[0] + 1, y[2]) && isCellAvailable(x[1] - 1, y[1]) && isCellAvailable(x[1] + 1, y[1])) {
                        currentPiece.setX(new int[]{x[1] - 1, x[1], x[1] + 1, x[1] + 1});
                        currentPiece.setY(new int[]{y[1], y[1], y[1], y[1] + 1});
                        currentPiece.setRotationState(0);
                        setPieceCells();
                        board.repaint();
                        break;
                    }
                }
                break;
            }

            case Right_Duck: {
                if (currentPiece.getRotationState() == 0) {
                    if (isCellAvailable(x[1] - 1, y[1]) && isCellAvailable(x[1] - 1, y[1] - 1)) {
                        currentPiece.setX(new int[]{x[2], x[1], x[1] - 1, x[1] - 1});
                        currentPiece.setY(new int[]{y[2], y[1], y[1], y[1] - 1});
                        currentPiece.setRotationState(1);
                        setPieceCells();
                        board.repaint();
                        break;
                    }
                    if (isCellAvailable(x[2] + 1, y[2]) && isCellAvailable(x[2] + 1, y[2] + 1)) {
                        currentPiece.setX(new int[]{x[2] + 1, x[2] + 1, x[2], x[1]});
                        currentPiece.setY(new int[]{y[2] + 1, y[2], y[2], y[1]});
                        currentPiece.setRotationState(1);
                        setPieceCells();
                        board.repaint();
                        break;
                    }
                } else if (currentPiece.getRotationState() == 1) {
                    if (isCellAvailable(x[1], y[1] - 1) && isCellAvailable(x[1] + 1, y[1] - 1)) {
                        currentPiece.setX(new int[]{x[2], x[1], x[1], x[1] + 1});
                        currentPiece.setY(new int[]{y[2], y[1], y[1] - 1, y[1] - 1});
                        currentPiece.setRotationState(2);
                        setPieceCells();
                        board.repaint();
                        break;
                    }
                    if (isCellAvailable(x[2], y[2] + 1) && isCellAvailable(x[2] - 1, y[2] + 1)) {
                        currentPiece.setX(new int[]{x[2] - 1, x[2], x[2], x[1]});
                        currentPiece.setY(new int[]{y[2] + 1, y[2] + 1, y[2], y[1]});
                        currentPiece.setRotationState(2);
                        setPieceCells();
                        board.repaint();
                        break;
                    }
                } else if (currentPiece.getRotationState() == 2) {
                    if (isCellAvailable(x[1] + 1, y[1]) && isCellAvailable(x[1] + 1, y[1] + 1)) {
                        currentPiece.setX(new int[]{x[2], x[1], x[1] + 1, x[1] + 1});
                        currentPiece.setY(new int[]{y[2], y[1], y[1], y[1] + 1});
                        currentPiece.setRotationState(3);
                        setPieceCells();
                        board.repaint();
                        break;
                    }
                    if (isCellAvailable(x[2] - 1, y[2]) && isCellAvailable(x[2] - 1, y[2] - 1)) {
                        currentPiece.setX(new int[]{x[2] - 1, x[2] - 1, x[2], x[1]});
                        currentPiece.setY(new int[]{y[2] - 1, y[2], y[2], y[1]});
                        currentPiece.setRotationState(3);
                        setPieceCells();
                        board.repaint();
                        break;
                    }
                } else {
                    if (isCellAvailable(x[1], y[1] + 1) && isCellAvailable(x[1] - 1, y[1] + 1)) {
                        currentPiece.setX(new int[]{x[2], x[1], x[1], x[1] - 1});
                        currentPiece.setY(new int[]{y[2], y[1], y[1] + 1, y[1] + 1});
                        currentPiece.setRotationState(0);
                        setPieceCells();
                        board.repaint();
                        break;
                    }
                    if (isCellAvailable(x[2], y[2] - 1) && isCellAvailable(x[2] + 1, y[2] - 1)) {
                        currentPiece.setX(new int[]{x[2] + 1, x[2], x[2], x[1]});
                        currentPiece.setY(new int[]{y[2] - 1, y[2] - 1, y[2], y[1]});
                        currentPiece.setRotationState(0);
                        setPieceCells();
                        board.repaint();
                        break;
                    }
                }
                break;
            }
            case Left_Duck: {
                if (currentPiece.getRotationState() == 0) {
                    if (isCellAvailable(x[1] - 1, y[1] + 1) && isCellAvailable(x[1], y[1] - 1)) {
                        currentPiece.setX(new int[]{x[1], x[1], x[0], x[1] - 1});
                        currentPiece.setY(new int[]{y[1] - 1, y[1], y[0], y[1] + 1});
                        currentPiece.setRotationState(1);
                        setPieceCells();
                        board.repaint();
                        break;
                    }
                    if (isCellAvailable(x[2] + 1, y[2] - 1) && isCellAvailable(x[2], y[2] + 1)) {
                        currentPiece.setX(new int[]{x[2] + 1, x[3], x[2], x[2]});
                        currentPiece.setY(new int[]{y[2] - 1, y[3], y[2], y[2] + 1});
                        currentPiece.setRotationState(1);
                        setPieceCells();
                        board.repaint();
                        break;
                    }
                } else if (currentPiece.getRotationState() == 1) {
                    if (isCellAvailable(x[1] - 1, y[1] - 1) && isCellAvailable(x[1] + 1, y[1])) {
                        currentPiece.setX(new int[]{x[1] + 1, x[1], x[0], x[1] - 1});
                        currentPiece.setY(new int[]{y[1], y[1], y[0], y[1] - 1});
                        currentPiece.setRotationState(2);
                        setPieceCells();
                        board.repaint();
                        break;
                    }
                    if (isCellAvailable(x[2] - 1, y[2]) && isCellAvailable(x[2] + 1, y[2] + 1)) {
                        currentPiece.setX(new int[]{x[2] + 1, x[3], x[2], x[2] - 1});
                        currentPiece.setY(new int[]{y[2] + 1, y[3], y[2], y[2]});
                        currentPiece.setRotationState(2);
                        setPieceCells();
                        board.repaint();
                        break;
                    }
                } else if (currentPiece.getRotationState() == 2) {
                    if (isCellAvailable(x[1] + 1, y[1] - 1) && isCellAvailable(x[1], y[1] + 1)) {
                        currentPiece.setX(new int[]{x[1], x[1], x[0], x[1] + 1});
                        currentPiece.setY(new int[]{y[1] + 1, y[1], y[0], y[1] - 1});
                        currentPiece.setRotationState(3);
                        setPieceCells();
                        board.repaint();
                        break;
                    }
                    if (isCellAvailable(x[2], y[2] - 1) && isCellAvailable(x[2] - 1, y[2] + 1)) {
                        currentPiece.setX(new int[]{x[2] - 1, x[3], x[2], x[2]});
                        currentPiece.setY(new int[]{y[2] + 1, y[3], y[2], y[2] - 1});
                        currentPiece.setRotationState(3);
                        setPieceCells();
                        board.repaint();
                        break;
                    }
                } else {
                    if (isCellAvailable(x[1] - 1, y[1]) && isCellAvailable(x[1] + 1, y[1] + 1)) {
                        currentPiece.setX(new int[]{x[1] - 1, x[1], x[0], x[1] + 1});
                        currentPiece.setY(new int[]{y[1], y[1], y[0], y[1] + 1});
                        currentPiece.setRotationState(0);
                        setPieceCells();
                        board.repaint();
                        break;
                    }
                    if (isCellAvailable(x[2] + 1, y[2]) && isCellAvailable(x[2] - 1, y[2] - 1)) {
                        currentPiece.setX(new int[]{x[2] - 1, x[3], x[2], x[2] + 1});
                        currentPiece.setY(new int[]{y[2] - 1, y[3], y[2], y[2]});
                        currentPiece.setRotationState(0);
                        setPieceCells();
                        board.repaint();
                        break;
                    }
                }
                break;
            }
        }
    }
}
