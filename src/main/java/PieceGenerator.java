import java.util.Random;

public class PieceGenerator {
    private Piece currentPiece, nextPiece;
    private Random random = new Random(System.nanoTime());

    public PieceGenerator() {
        generateNextPiece();
    }

    public Piece generatePiece() {
        currentPiece = nextPiece;
        generateNextPiece();
        HintBoard.getInstance().setNextPiece(nextPiece);
        return currentPiece;
    }

    private void generateNextPiece() {

        int type = random.nextInt(7);
        Type[] types=new Type[] {Type.Wood,Type.Mountain,Type.Window,Type.Right_Leg,Type.Left_Leg,Type.Right_Duck,Type.Left_Duck};
        nextPiece=generateSpecificPiece(types[type]);
//        int n = Scales.getBoard_Dimension_n(), m = Scales.getBoard_Dimension_m();
//        switch (type) {
//            case 0: { // wood
//                nextPiece.setType(Type.Wood);
//                nextPiece.setY(new int[]{0, 0, 0, 0});
//                nextPiece.setX(new int[]{n / 2 - 2, n / 2 - 1, n / 2, n / 2 + 1});
//                break;
//            }
//            case 1: {// mountain
//                nextPiece.setType(Type.Mountain);
//                nextPiece.setY(new int[]{0, 0, 0, 1});
//                nextPiece.setX(new int[]{n / 2 - 2, n / 2 - 1, n / 2, n / 2 - 1});
//                break;
//            }
//            case 2: { // window
//                nextPiece.setType(Type.Window);
//                nextPiece.setY(new int[]{0, 0, 1, 1});
//                nextPiece.setX(new int[]{n / 2 - 1, n / 2, n / 2 - 1, n / 2});
//                break;
//            }
//            case 3: { // right leg
//                nextPiece.setType(Type.Right_Leg);
//                nextPiece.setY(new int[]{0, 0, 0, 1});
//                nextPiece.setX(new int[]{n / 2, n / 2 - 1, n / 2 - 2, n / 2 - 2});
//                break;
//            }
//            case 4: { // left leg
//                nextPiece.setType(Type.Left_Leg);
//                nextPiece.setY(new int[]{0, 0, 0, 1});
//                nextPiece.setX(new int[]{n / 2 - 2, n / 2 - 1, n / 2, n / 2});
//                break;
//            }
//            case 5: { // right duck
//                nextPiece.setType(Type.Right_Duck);
//                nextPiece.setY(new int[]{0, 0, 1, 1});
//                nextPiece.setX(new int[]{n / 2, n / 2 - 1, n / 2 -1, n / 2 - 2});
//                break;
//            }
//            case 6: { // left duck
//                nextPiece.setType(Type.Left_Duck);
//                nextPiece.setY(new int[]{0, 0, 1, 1});
//                nextPiece.setX(new int[]{n / 2 - 2, n / 2 - 1, n / 2 - 1, n / 2});
//                break;
//
//            }
//        }
    }
    public Piece generateSpecificPiece(Type type){
        Piece piece=new Piece();
        int n = Scales.getBoard_Dimension_n(), m = Scales.getBoard_Dimension_m();
        switch (type) {
            case Wood: { // wood
                piece.setType(Type.Wood);
                piece.setY(new int[]{0, 0, 0, 0});
                piece.setX(new int[]{n / 2 - 2, n / 2 - 1, n / 2, n / 2 + 1});
                break;
            }
            case Mountain: {// mountain
                piece.setType(Type.Mountain);
                piece.setY(new int[]{0, 0, 0, 1});
                piece.setX(new int[]{n / 2 - 2, n / 2 - 1, n / 2, n / 2 - 1});
                break;
            }
            case Window: { // window
                piece.setType(Type.Window);
                piece.setY(new int[]{0, 0, 1, 1});
                piece.setX(new int[]{n / 2 - 1, n / 2, n / 2 - 1, n / 2});
                break;
            }
            case Right_Leg: { // right leg
                piece.setType(Type.Right_Leg);
                piece.setY(new int[]{0, 0, 0, 1});
                piece.setX(new int[]{n / 2, n / 2 - 1, n / 2 - 2, n / 2 - 2});
                break;
            }
            case Left_Leg: { // left leg
                piece.setType(Type.Left_Leg);
                piece.setY(new int[]{0, 0, 0, 1});
                piece.setX(new int[]{n / 2 - 2, n / 2 - 1, n / 2, n / 2});
                break;
            }
            case Right_Duck: { // right duck
                piece.setType(Type.Right_Duck);
                piece.setY(new int[]{0, 0, 1, 1});
                piece.setX(new int[]{n / 2, n / 2 - 1, n / 2 -1, n / 2 - 2});
                break;
            }
            case Left_Duck: { // left duck
                piece.setType(Type.Left_Duck);
                piece.setY(new int[]{0, 0, 1, 1});
                piece.setX(new int[]{n / 2 - 2, n / 2 - 1, n / 2 - 1, n / 2});
                break;

            }
        }
        return piece;
    }
}