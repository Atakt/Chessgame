package Game;

import Pieces.Piece;

import java.util.Vector;

/**
 * class representing a player
 */
public class Player {
    private final Color color;
    private Vector<Piece> alivePieces;
    private Vector<Piece> takenEnemyPieces;


    public Player(Color c){
        color = c;
    }
}
