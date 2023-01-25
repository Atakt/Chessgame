package Game;

import Pieces.Piece;

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
     * @param chessBoard The chessboard of the current game
     * @return All the possible moves of the player's pieces' moves in one collection(Vector)
     */
    public Vector<Square> getAllPossibleMoves(Board chessBoard){
        Vector<Square> allMoves = new Vector<>();
        for (Piece p :alivePieces){
            allMoves.addAll(p.calculatePossibleMoves(chessBoard));
        }
        return allMoves;
    }

    /**
     * Function returning true or false based on whether the square is in check or not. A square is in check if
     * an enemy piece can move to its position. To check if a square is in check we need to check all the moves of the opposing player.
     * @param square The square we are examining whether it is in check or not.
     * @param chessBoard The board of the current game.
     * @return True if the square is in check, false if it is not.
     */
    public boolean isSquareInCheck(Square square, Board chessBoard){
        Player opposition = game.getOtherPlayer(this);
        return opposition.getAllPossibleMoves(chessBoard).contains(square);
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
}
