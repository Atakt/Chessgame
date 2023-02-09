package Game;

public enum Type {
    BISHOP(3), KING(4), ROOK(1), KNIGHT(2), QUEEN(5), PAWN(0);

    private final int number;
    Type(int num){
        number = num;
    }

    public int getNumber() {
        return number;
    }
}
