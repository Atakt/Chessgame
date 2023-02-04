package Pieces;

import Game.Board;
import Game.Player;
import Game.Square;
import Game.Type;

import java.util.Vector;

public class Rook extends Piece{

    private final Type type = Type.ROOK;
    public Rook(Square position, Player player) {
        super(position, player);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void calculatePossibleMoves(Board chessBoard) {
        possibleMoves.clear();
        addPosXMoves(possibleMoves, chessBoard);
        addNegYMoves(possibleMoves, chessBoard);
        addNegXMoves(possibleMoves, chessBoard);
        addPosYMoves(possibleMoves, chessBoard);

    }
    /**
     * Function that add the moves in the positive x direction (straight) to the vector of all moves.
     * @param possibleMoves The vector of all possible moves
     * @param chessBoard The chessboard of the current game
     */
    private void addPosXMoves(Vector<Square> possibleMoves, Board chessBoard) {
        for (int i = 1; i < 8; i++) { // checking positive x direction (straight down)
            Square target = chessBoard.getSquareAt(position.getX() + i, position.getY());
            if (chessBoard.isInBounds(position.getX() + i, position.getY())) {
                if (target.getPiece() == null) {
                    possibleMoves.add(target);
                } else if (target.getPiece() != null) { // if rook collides with piece break out of loop
                    if (target.getPiece().getColor() != getColor()) {
                        possibleMoves.add(target);
                    }
                    break;
                }
            }
        }
    }

    /**
     * Function that add the moves in the positive y direction (straight) to the vector of all moves.
     * @param possibleMoves The vector of all possible moves
     * @param chessBoard The chessboard of the current game
     */
    private void addPosYMoves(Vector<Square> possibleMoves, Board chessBoard){
        for (int j = 1; j < 8; j++) {// checking positive y direction (down)
            Square target = chessBoard.getSquareAt(position.getX(), position.getY() + j);
            if (chessBoard.isInBounds(position.getX(), position.getY() + j)) {
                if (target.getPiece() == null) {
                    possibleMoves.add(target);
                } else if (target.getPiece() != null) {
                    if (target.getPiece().getColor() != getColor()) {
                        possibleMoves.add(target);
                    }
                    break;
                }
            }
        }
    }
    /**
     * Function that add the moves in the negative x direction (straight) to the vector of all moves.
     * @param possibleMoves The vector of all possible moves
     * @param chessBoard The chessboard of the current game
     */
    private void addNegXMoves(Vector<Square> possibleMoves, Board chessBoard){
        for (int i = -1; i > -8; i--) { // checking negative x direction (left)
            Square target = chessBoard.getSquareAt(position.getX() + i, position.getY());
            if (chessBoard.isInBounds(position.getX() + i, position.getY())) {
                if (target.getPiece() == null) {
                    possibleMoves.add(target);
                } else if (target.getPiece() != null) {
                    if (target.getPiece().getColor() != getColor()) {
                        possibleMoves.add(target);
                    }
                    break;
                }
            }
        }
    }

    /**
     * Function that add the moves in the negative y direction (straight) to the vector of all moves.
     * @param possibleMoves The vector of all possible moves
     * @param chessBoard The chessboard of the current game
     */
    private void addNegYMoves(Vector<Square> possibleMoves, Board chessBoard){
        for (int j = -1; j > -8; j--) { // checking negative y direction (up)
            Square target = chessBoard.getSquareAt(position.getX(), position.getY() + j);
            if (chessBoard.isInBounds(position.getX(), position.getY() + j)) {
                if (target.getPiece() == null) {
                    possibleMoves.add(target);
                } else if (target.getPiece() != null) {
                    if (target.getPiece().getColor() != getColor()) {
                        possibleMoves.add(target);
                    }
                    break;
                }
            }
        }
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
        return new Rook(position, player);
    }
}
