package Game;

public class Game {
    private final Board gameBoard;
    private Player p1, p2;
    private int x, y;

    public Game() {
        gameBoard = new Board(this, x, y);
    }

    /**
     * function returning the white player
     * @return the WHITE player
     */
    Player getPlayer1(){
        return p1;
    }

    /**
     * function returning the black player
     * @return the BLACK player
     */
    Player getPlayer2(){
        return p2;
    }
}
