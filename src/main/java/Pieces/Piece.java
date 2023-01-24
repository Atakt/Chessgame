package Pieces;

import Game.*;

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
    }

    /**
     * Function that calculates and returns the possible Squares that the piece can move to
     * @param chessBoard The chessboard of the actual game
     * @return All the possible moves in a Vector
     */
    public abstract Vector<Square> calculatePossibleMoves(Board chessBoard);

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
        if( previousPosition == null){
            return false;
        }else{
            return true;
        }
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
     */
    public abstract void movePiece(Square target, Board chessBoard);

    /**
     * Function returning the previous position of the piece
     * @return the previous position of the piece
     */
    public Square getPreviousPosition(){
        return previousPosition;
    }
}
