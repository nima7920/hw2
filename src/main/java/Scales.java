import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashMap;

public class Scales {
    // dimensions:
    public static final int GameBoard_Height = 700;
    public static final int GameBoard_Width = 600;
    public static final int button_Height = 10;
    public static final int button_Width = 40;
    public static final int ScoreLabel_Width = 300, ScoreLabel_Height = 50;
    public static final int GameButtons_Width = GameBoard_Width, GameButtons_Height = 100;
    public static final int ScoreBoard_Width = 150;
    public static final int ScoreBoard_height = 500;
    public static final int HintBoard_Height = ScoreBoard_height, HintBoard_Width = 200;
    public static final int HintBoard_Label_Height = 100, HintBoard_Label_Width = 200;
    public static final int HintBoard_Cell_Size=40;
    public static final int Board_Height = ScoreBoard_height;
    public static final int Board_Width = (GameBoard_Width - (ScoreBoard_Width + HintBoard_Width));
    // locations:
    public static final int ScoreLabel_X = (GameBoard_Width - ScoreLabel_Width) / 2, ScoreLabel_Y = 0;
    public static final int ScoreBoard_X = 0, ScoreBoard_Y = (GameBoard_Height - ScoreBoard_height) / 2;
    public static final int GameButtons_X = 0, GameButtons_Y = (GameBoard_Height - GameButtons_Height);
    public static final int Board_X = ScoreBoard_Width, Board_Y = (GameBoard_Height - ScoreBoard_height) / 2;
    public static final int HintBoard_X = Scales.Board_Width + Scales.ScoreBoard_Width;
    public static final int HintBoard_Y = Scales.Board_Y;
    public static final int HintBoard_Label_X = 0, HintBoard_Label_Y = 0;
    public static final int HintBoard_Cell_X=10,HintBoard_Cell_Y=200;
    // colors:
    public static final Color GameBoard_Color = Color.MAGENTA;
    public static final Color ScoreBoard_Color = Color.BLACK;
    public static final Color ScoreLabel_Color = Color.BLUE;
    public static final Color GameButtons_Color = Color.pink;
    public static final Color Board_Color = Color.DARK_GRAY;
    public static final Color Board_Cell_Color = Color.LIGHT_GRAY;
    public static final Color HintBoard_Color = Color.BLACK;
    public static final Color HintBoard_Label_Color = Color.GREEN;
    public static final Color HintBoard_Cell_Color = Color.LIGHT_GRAY;
    public static final Color Board_usedArea_Color = new Color(80, 10, 160);

    // variable scales:
    private static int Board_Dimension_n = 10;
    private static int Board_Dimension_m = 20;

    // keys:
    public static final int Left_Key = KeyEvent.VK_LEFT;
    public static final int Right_Key = KeyEvent.VK_RIGHT;
    public static final int Rotate_Key = KeyEvent.VK_SPACE;

    // collections
    private static HashMap<Type, Color> PieceColor = new HashMap<>();

    public static int getBoard_Dimension_n() {
        return Board_Dimension_n;
    }

    public static void setBoard_Dimension_n(int board_Dimension_n) {
        Board_Dimension_n = board_Dimension_n;
    }

    public static int getBoard_Dimension_m() {
        return Board_Dimension_m;
    }

    public static void setBoard_Dimension_m(int board_Dimension_m) {
        Board_Dimension_m = board_Dimension_m;
    }

    public static Color getPieceColor(Type type) {
        return PieceColor.get(type);
    }

    // main function of class:
    public static void initialize() {
        PieceColor.put(Type.Wood, Color.RED);
        PieceColor.put(Type.Window, Color.GREEN);
        PieceColor.put(Type.Mountain, Color.ORANGE);
        PieceColor.put(Type.Right_Leg, Color.BLUE);
        PieceColor.put(Type.Left_Leg, Color.YELLOW);
        PieceColor.put(Type.Right_Duck, Color.CYAN);
        PieceColor.put(Type.Left_Duck, Color.MAGENTA);

    }
}
