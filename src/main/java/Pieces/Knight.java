package Pieces;

import Game.Board;
import Game.Player;
import Game.Square;
import Game.Type;

import java.util.Vector;

public class Knight extends Piece{
    private final Type type = Type.KNIGHT;
    public Knight(Square position, Player player) {
        super(position, player);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public Vector<Square> calculatePossibleMoves(Board chessBoard) {
        Vector<Square> possibleMoves = new Vector<>();
        int[][] offsets = {
                {-2, 1},
                {-1, 2},
                {1, 2},
                {2, 1},
                {2, -1},
                {1, -2},
                {-1, -2},
                {-2, -1}
        };
        for (int[] o : offsets) {
            Square target = chessBoard.getSquareAt(o[0]+ position.getX(), o[1]+position.getY());
            if(chessBoard.isInBounds(o[0]+ position.getX(), o[1]+position.getY()) && (target.getPiece() == null ||
                    target.getPiece().getColor() != getColor())) {
                possibleMoves.add(target);
            }
        }
        return possibleMoves;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public Type getType() {
        return type;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public Piece clone() {
        return new Knight(position, player);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void movePiece(Square target, Board chessBoard) {
        calculatePossibleMoves(chessBoard);
        if (target != null) {
            if (!possibleMoves.contains(target)) {
                return;
            }
            capturePiece(target);
            this.position.setPiece(null); // remove piece from current position(Square)
            setPreviousPosition(this.position); // set the previous position, as the one that the piece moves from
            this.position = target; //set the piece's new position
            this.position.setPiece(this); // set the new position's piece as this piece
        }
    }
}
