package GUI;

import Game.Game;

import java.io.*;

public class ChessGame {

    private static Game game;
    public static void main(String[] args) {
        Game game = new Game(8, 8);
        ChessBoard gameBoard = new ChessBoard(game);
    }

    /**
     * Static function that saves the game to the place defined in the function using serialization
     * @param game The game that is saved
     * @throws IOException
     */
    public static void saveGame(Game game) throws IOException {
        FileOutputStream fos = new FileOutputStream("savegame/chess.out");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(game);
        oos.flush();
        oos.close();
    }

    /**
     * Static function to load a  game from the place specified in the function
     * @return the game loaded (deserialized) from the savegame folder
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Game loadGame() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream("savegame/chess.out");
        ObjectInputStream ios = new ObjectInputStream(fis);
        Game game = (Game)ios.readObject();
        ios.close();
        return game;
    }

}
