package Pieces;

import Game.Board;
import Game.Player;
import Game.Square;
import Game.Type;

import java.util.Vector;

public class Bishop extends Piece{

    private final Type type = Type.BISHOP;
    public Bishop(Square position, Player player) {
        super(position,player);
    }
    /**
     * Function that calculates the possible moves in the positive x and positive y directions
     * @param moves the moves list that gets added to by the function
     * @param checkDirections An array of the directions we need to check the moves in
     */
    private void addPosXPosYMoves(Vector<Square> moves, boolean[] checkDirections, Board chessBoard) {
        //positive x, positive y directions
        for (int i = 1; i < 8; i++) { // loop for x offset
            for (int j = 1; j < 8; j++) { // loop for y offset
                if (Math.abs(i) == Math.abs(j)) { // checking only diagonal moves, as the bishop can only move that way in chess
                    Square target = chessBoard.getSquareAt(position.getX() + i, position.getY() + j);
                    if (checkDirections[0] && target != null) { // check if target square is on the board or not, getSquareAt returns null if square is not on the board
                        if (target.getPiece() == null) { // if it has a clear path, = bishop doesn't collide with anything
                            moves.add(target);  // add move to list
                        } else if (target.getPiece() != null) { // bishop collides with something on target square
                            if (target.getPiece().getColor() != this.getColor()) { // if the piece that bishop collides with is the enemy's piece -> capture
                                moves.add(target); // add move to list, but stop the search for more moves in that direction
                            }
                            checkDirections[0] = false;// If bishop collides with other piece, stop search in that direction. Bishop cannot jump over other pieces.
                        }
                    }
                }
            }
        }
    }
    /**
     * Function that calculates the possible moves in the negative x and negative y directions
     * @param moves the moves list that gets added to by the function
     * @param checkDirections An array of the directions we need to check the moves in
     */
    private void addNegXNegYMoves(Vector<Square> moves, boolean[] checkDirections, Board chessBoard){
        //negative x , negative y directions
        for (int i = -1; i > -8; i--) {
            for (int j = -1; j > -8; j--) {
                if (Math.abs(i) == Math.abs(j)) {
                    Square target = chessBoard.getSquareAt(position.getX() + i, position.getY() + j);
                    if (checkDirections[1] && target != null) {
                        if (target.getPiece() == null) {
                            moves.add(target);
                        } else if (target.getPiece() != null) {
                            if (target.getPiece().getColor() != getColor()) {
                                moves.add(target);
                            }
                            checkDirections[1] = false;// If bishop collides with other piece, stop search in that direction
                        }
                    }
                }
            }
        }
    }
    /**
     * Function that calculates the possible moves in the negative x and positive y directions
     * @param moves the moves list that gets added to by the function
     * @param checkDirections An array of the directions we need to check the moves in
     */
    private void addNegXPosYMoves(Vector<Square> moves, boolean[] checkDirections, Board chessBoard){
        // negative x, positive y direction
        for (int i = -1; i > -8; i--) {
            for (int j = 1; j < 8; j++) {
                if (Math.abs(i) == Math.abs(j)) {
                    Square target = chessBoard.getSquareAt(position.getX() + i, position.getY() + j);
                    if (checkDirections[2] && target != null) {
                        if (target.getPiece() == null) {
                            moves.add(target);
                        } else if (target.getPiece() != null) {
                            if (target.getPiece().getColor() != getColor()) {
                                moves.add(target);
                            }
                            checkDirections[2] = false; // If bishop collides with other piece, stop search in that direction
                        }
                    }
                }
            }
        }
    }

    /**
     * Function that calculates the possible moves in the positive x and negative y directions
     * @param moves the moves list that gets added to by the function
     * @param checkDirections An array of the directions we need to check the moves in
     */
    private void addPosXNegYMoves(Vector<Square> moves, boolean[] checkDirections, Board chessBoard){
        //positive x, negative y directions
        for (int i = 1; i < 8; i++) {
            for (int j = -1; j > -8; j--) {
                if (Math.abs(i) == Math.abs(j)) {
                    Square target = chessBoard.getSquareAt(position.getX() + i, position.getY() + j);
                    if (checkDirections[3] && target != null) {
                        if (target.getPiece() == null) {
                            moves.add(target);
                        } else if (target.getPiece() != null) {
                            if (target.getPiece().getColor() != getColor()) {
                                moves.add(target);
                            }
                            checkDirections[3] = false;// If bishop collides with other piece, stop search in that direction
                        }
                    }
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vector<Square> calculatePossibleMoves(Board chessBoard) {
        Vector<Square> moves = new Vector<>();
        boolean[] checkDirections = { // directions to check in
                true, //positive x, positive y
                true, //negative x, negative y
                true, //negative x, positive y
                true  //positive x, negative y
        };
        addPosXPosYMoves(moves, checkDirections, chessBoard);
        addPosXNegYMoves(moves, checkDirections, chessBoard);
        addNegXPosYMoves(moves, checkDirections, chessBoard);
        addNegXNegYMoves(moves, checkDirections, chessBoard);
        possibleMoves = moves;
        return possibleMoves;
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public Type getType() {
        return type;
    }
    /**
     *{@inheritDoc}
     */
    @Override
    public Bishop clone(){
        return new Bishop(position, player);
    }

}
