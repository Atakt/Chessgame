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

    /**
     * Function that returns the opposing player to the player that is given as parameter
     * @param p the player that we would like the opposing player to
     * @return the opposing player to the p parameter
     */
    Player getOtherPlayer(Player p){
        if(p == p1) return p2;
        else {
            return p1;
        }
    }
}
