import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

public class Piece extends Area {
    private Rectangle2D sqr1, sqr2, sqr3, sqr4;
    private Type type;
    private int rotationState;
    private int[] X, Y;

    public Piece() {
        rotationState = 0;
        X = new int[4];
        Y = new int[4];
    }

    public void setSquares(Rectangle2D sqr1, Rectangle2D sqr2, Rectangle2D sqr3, Rectangle2D sqr4) {
        this.sqr1 = sqr1;
        this.sqr2 = sqr2;
        this.sqr3 = sqr3;
        this.sqr4 = sqr4;

    }

    public void merge() {
        this.reset();
        this.add(new Area(sqr1));
        this.add(new Area(sqr2));
        this.add(new Area(sqr3));
        this.add(new Area(sqr4));
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public int getRotationState() {
        return rotationState;
    }

    public void setRotationState(int rotationState) {
        this.rotationState = rotationState;
    }

    public int[] getX() {
        return X;
    }

    public void setX(int[] x) {
        X = x;
    }

    public int[] getY() {
        return Y;
    }

    public void setY(int[] y) {
        Y = y;
    }

    public void moveDown() {
        Y[0]++;
        Y[1]++;
        Y[2]++;
        Y[3]++;
    }

    public void moveLeft() {
        X[0]--;
        X[1]--;
        X[2]--;
        X[3]--;

    }

    public void moveRight() {
        X[0]++;
        X[1]++;
        X[2]++;
        X[3]++;
    }

    public boolean checkIntersection(Area area) {
        if (area.contains(sqr1) || area.contains(sqr2) || area.contains(sqr3) || area.contains(sqr4))
            return true;
        return false;
    }
}
