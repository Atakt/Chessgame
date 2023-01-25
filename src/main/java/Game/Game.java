package Game;

public class Game {
    private final Board gameBoard;
    private Player p1, p2;
    private int x, y;
    private boolean gameOver;
    private Player winner;

    public Game() {
        gameBoard = new Board(this, x, y);
        winner = null;
        gameOver = false;
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

    /**
     * Function to set the winner of the game
     * @param player the winner of the game
     */
    public void setWinner(Player player) {
        winner = player;
    }

    /**
     * function to set the game to a game-over state
     * @param b boolean parameter should be true if game is over
     */
    public void setGameOver(boolean b) {
        gameOver = b;
    }

    /**
     * Function to return true if the game is over, and false otherwise
     * @return true is game is over, false otherwise
     */
    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * Function to return winner of the game
     * @return the winner of the game
     */
    public Player getWinner() {
        return winner;
    }
}
