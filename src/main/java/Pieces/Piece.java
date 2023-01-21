package Pieces;

import Game.Player;
import Game.Square;

import java.util.Vector;

public class Piece {
    private Square position, previousPosition;
    private final Player player;
    private Vector<Square> possibleMoves;

    public Piece(Square position, Player player) {
        this.position = position;
        this.previousPosition = null;
        this.player = player;
    }
}
