package Pieces;

import Game.*;

import java.util.Collection;
import java.util.Vector;

public abstract class Piece {
    protected Square position;
    protected Square previousPosition;
    protected final Player player;
    protected Vector<Square> possibleMoves;

    public Piece(Square position, Player player) {
        this.position = position;
        this.previousPosition = null;
        this.player = player;
        possibleMoves = new Vector<>();
    }

    /**
     * Function that calculates and returns the possible Squares that the piece can move to
     * @param chessBoard The chessboard of the actual game
     */
    public abstract void calculatePossibleMoves(Board chessBoard);

    /**
     * Function returning the possible moves in a vector of squares
     * @return the possibleMoves Vector
     */

    public Vector<Square> getPossibleMoves(){
        return possibleMoves;
    }


    /**
     * Funnction returning the type of the piece
     * @return Type of the piece
     */
    public abstract Type getType();

    /**
     * A function that creates a new clone of the piece, with all the same values as the original.
     * Made to check possibility of avoiding check mate, if king is in check.
     * @return A clone of the piece
     */
    public abstract Piece clone();

    /**
     * Function returning the player that the piece belong to.
     * @return The player that own the piece
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Function returning the color of the player that the piece belongs to
     * @return the color of the piece
     */
    public Color getColor() {
        return player.getColor();
    }

    /**
     * Function returning the position of the piece
     * @return the position of the piece
     */
    public Square getPosition() {
        return position;
    }

    /**
     * Function that sets the prevoius postition of the piece to the one given by the param
     * Helper function for cloning.
     * @param pos the previous position of the piece
     */
    public void setPreviousPosition(Square pos){
        previousPosition = pos;
    }

    /**
     * Function returning true or false based on whether the piece has moved from its original position on the board
     * @return True if the piece has moved, false otherwise
     */
    public boolean hasMoved(){
        return previousPosition != null;
    }

    /**
     * Function for capturing a piece.
     */
    public void capturePiece(Square target){
        if(target.isSquareOccupied()){
            Piece targetPiece = target.getPiece();
            player.captureEnemyPiece(targetPiece);
            targetPiece.getPlayer().removePiece(targetPiece);
            target.setPiece(null);
        }
    }

    /**
     * Function for moving a piece, each piece moves in its own way.
     * @param target the square where the piece moves to
     * @param chessBoard The chessboard of the current game
     */
    public void move(Square target, Board chessBoard){
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

            calculatePossibleMoves(chessBoard);
        }
    }

    /**
     * Function returning the previous position of the piece
     * @return the previous position of the piece
     */
    public Square getPreviousPosition(){
        return previousPosition;
    }

    /**
     * Function to remove unwanted/illegal moves from the possible moves collection
     * @param moves the collection of moves to be removed
     */
    public void removePossibleMove(Collection<Square> moves){
        possibleMoves.removeAll(moves);
    }
}
