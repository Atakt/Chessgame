package Game;

import Pieces.Piece;

import java.util.Collection;
import java.util.Vector;

/**
 * class representing a player
 */
public class Player {
    private final Game game;
    private Color color;
    private Vector<Piece> alivePieces;
    private Vector<Piece> takenEnemyPieces;


    public Player(Color c, Game game){
        color = c;
        this.game = game;
        alivePieces = new Vector<>();
        takenEnemyPieces = new Vector<>();
    }

    /**
     * Function returning the color of the player
     *
     * @return The color of the player
     */
    public Color getColor() {
        return color;
    }
    public void setColor(Color c){
        color = c;
    }
    public void captureEnemyPiece(Piece piece){
        takenEnemyPieces.add(piece);
    }

    /**
     * Function returning all the possible moves of the player's pieces in one Vector.
     * @return All the possible moves of the player's pieces' moves in one collection(Vector)
     */
    public Vector<Square> getAllPossibleMoves(){
        Vector<Square> allMoves = new Vector<>();
        for (Piece p :alivePieces){
            allMoves.addAll(p.getPossibleMoves());
        }
        return allMoves;
    }

    /**
     * Function returning true or false based on whether the square is in check or not. A square is in check if
     * an enemy piece can move to its position. To check if a square is in check we need to check all the moves of the opposing player.
     * @param square The square we are examining whether it is in check or not.
     * @return True if the square is in check, false if it is not.
     */
    public boolean isSquareInCheck(Square square){
        Player opposition = game.getOtherPlayer(this);
        return opposition.getAllPossibleMoves().contains(square);
    }

    /**
     * Funcrion that removes the give piece from the alivePieces Vector of the player
     * @param piece the piece to be removed
     */
    public void removePiece(Piece piece){
        alivePieces.remove(piece);
    }

    /**
     * function sets the alivePieces Vector with the parameter Vector
     * @param alivePieces The vector the alivePieces field is set to
     */
    public void setAlivePieces(Vector<Piece> alivePieces) {
        this.alivePieces = alivePieces;
    }

    /**
     * Function returning the king of the player
     * @return the King piece of the player
     */
    public Piece getKing(){
        Piece king = null;
        for(Piece p : alivePieces){
            if(p.getType() == Type.KING){
                king = p;
            }
        }
        return king;
    }

    /**
     * Function returning all the alive pieces of the player in a vector
     * @return alivePieces Vector
     */
    public Vector<Piece> getAlivePieces() {
        return alivePieces;
    }

    /**
     * Function to add a piece to the piece of the player
     * @param piece the piece added to the alivePieces vector
     */
    public void addAlivePiece(Piece piece) {
        alivePieces.add(piece);
    }

    /**
     * Function to remove a piece from the alive pieces of the player
     * @param piece the piece to be removed from the alivePieces vector
     */
    public void removeAlivePiece(Piece piece){
        alivePieces.remove(piece);
    }
}
