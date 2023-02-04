package Pieces;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import Game.*;

import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;

class PawnTest {

    @Test
    void addBlackBaseMovesTest1(){
        Game game = new Game(8, 8);
        Board b = game.getBoard();
        Piece pawn = b.getSquareAt(1,1).getPiece();
        pawn.move(b.getSquareAt(3,1), b);
        pawn.calculatePossibleMoves(b);

        Vector<Square> moves = pawn.getPossibleMoves();

        assertTrue(moves.contains(b.getSquareAt(4,1)));
        assertFalse(moves.contains(b.getSquareAt(3,1)));
        assertFalse(moves.contains(b.getSquareAt(2,1)));
    }
    @Test
    void addBlackBaseMovesTest2(){
        Game game = new Game(8, 8);
        Board b = game.getBoard();
        Piece pawn = b.getSquareAt(1,1).getPiece();
        pawn.calculatePossibleMoves(b);
        Vector<Square> moves = pawn.getPossibleMoves();

        assertFalse(moves.contains(b.getSquareAt(4,1)));
        assertTrue(moves.contains(b.getSquareAt(3,1)));
        assertTrue(moves.contains(b.getSquareAt(2,1)));
    }

    @Test
    void addWhiteBaseMovesTest1(){
        Game game = new Game(8, 8);
        Board b = game.getBoard();
        Piece pawn = b.getSquareAt(6,6).getPiece();
        pawn.calculatePossibleMoves(b);
        Vector<Square> moves = pawn.getPossibleMoves();

        assertFalse(moves.contains(b.getSquareAt(3,6)));
        assertFalse(moves.contains(b.getSquareAt(5,5)));
        assertFalse(moves.contains(b.getSquareAt(5,7)));
        assertTrue(moves.contains(b.getSquareAt(5,6)));
        assertTrue(moves.contains(b.getSquareAt(4,6)));
    }

    @Test
    void addWhiteBaseMovesTest2(){
        Game game = new Game(8, 8);
        Board b = game.getBoard();
        Piece pawn = b.getSquareAt(6,6).getPiece();
        pawn.move(b.getSquareAt(4,6), b);
        pawn.calculatePossibleMoves(b);
        Vector<Square> moves = pawn.getPossibleMoves();

        assertFalse(moves.contains(b.getSquareAt(4,6)));
        assertFalse(moves.contains(b.getSquareAt(3,7)));
        assertFalse(moves.contains(b.getSquareAt(3,5)));
        assertFalse(moves.contains(b.getSquareAt(5,6)));
        assertTrue(moves.contains(b.getSquareAt(3,6)));

    }

    @Test
    void addBlackEnPassantMovesTest(){
        Game game = new Game(8, 8);
        Board b = game.getBoard();
        Piece pawn = b.getSquareAt(1,6).getPiece();
        Piece pawnTaken = b.getSquareAt(6,5).getPiece();

        pawn.move(b.getSquareAt(3,6), b);
        pawn.move(b.getSquareAt(4,6), b);
        pawnTaken.move(b.getSquareAt(4,5), b);
        pawn.calculatePossibleMoves(b);

        Vector<Square> moves = pawn.getPossibleMoves();

        assertEquals(pawnTaken.getPosition().getX(), pawn.getPosition().getX());
        assertTrue(moves.contains(b.getSquareAt(5,5)));
        assertFalse(moves.contains(b.getSquareAt(5,7)));
        assertTrue(moves.contains(b.getSquareAt(5,6)));

    }

    @Test
    void addWhiteEnPassantMovesTest(){
        Game game = new Game(8, 8);
        Board b = game.getBoard();
        Piece pawn = b.getSquareAt(6,5).getPiece();
        Piece pawnTaken = b.getSquareAt(1,6).getPiece();

        pawn.move(b.getSquareAt(4,5), b);
        pawn.move(b.getSquareAt(3,5), b);
        pawnTaken.move(b.getSquareAt(3,6), b);
        pawn.calculatePossibleMoves(b);

        Vector<Square> moves = pawn.getPossibleMoves();

        assertEquals(pawnTaken.getPosition().getX(), pawn.getPosition().getX());
        assertTrue(moves.contains(b.getSquareAt(2,6)));
        assertFalse(moves.contains(b.getSquareAt(2,4)));
        assertTrue(moves.contains(b.getSquareAt(2,5)));

    }

    @Test
    void pawnMoveTest(){
        Game game = new Game(8, 8);
        Board b = game.getBoard();
        Piece pawn = b.getSquareAt(6,5).getPiece();

        pawn.move(b.getSquareAt(5,5),b);
        Piece piece = b.getSquareAt(6,5).getPiece();
        assertNull(b.getSquareAt(6,5).getPiece());
        assertTrue(b.getSquareAt(5,5).getPiece().equals(pawn));
    }

    @Test
    void setPieceTest(){
        Game game = new Game(8, 8);
        Board b = game.getBoard();
        Square target = b.getSquareAt(6,5);

        target.setPiece(null);
        assertNull(target.getPiece());

    }

}