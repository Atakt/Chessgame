package Game;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {


    @Test
    void isInBoundTest(){
        Board b = new Board(new Game(8,8),8,8);
        assertFalse(b.isInBounds(8,8));
        assertFalse(b.isInBounds(9,9));
        assertTrue(b.isInBounds(7,7));
        assertTrue(b.isInBounds(6,7));
        assertTrue(b.isInBounds(5,7));
        assertTrue(b.isInBounds(4,7));
        assertTrue(b.isInBounds(3,7));
        assertTrue(b.isInBounds(2,7));
        assertTrue(b.isInBounds(1,7));
        assertTrue(b.isInBounds(0,7));
        assertTrue(b.isInBounds(0,0));
    }
    @Test
    void getSquareAtTest(){
        Game game = new Game(8,8);
        Board b = game.getBoard();

        assertNull(b.getSquareAt(9,9));
        assertNull(b.getSquareAt(8,8));
        assertNotNull(b.getSquareAt(7,7));
        assertNotNull(b.getSquareAt(6,7));
        assertNotNull(b.getSquareAt(5,7));
        assertNotNull(b.getSquareAt(4,7));
        assertNotNull(b.getSquareAt(3,7));
        assertNotNull(b.getSquareAt(2,7));
        assertNotNull(b.getSquareAt(1,7));
        assertNotNull(b.getSquareAt(0,7));
    }
}