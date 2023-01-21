package Game;

import Pieces.*;

import java.util.Vector;

/**
 * Class representing the chessboard
 */
public class Board {
    private Square[][] squares;
    private final Game game;
    private final int height, width;
    private final Vector<Piece> player1Pieces;
    private final Vector<Piece> player2Pieces;

    /**
     * Constructor
     * @param game the current game
     * @param x the number of rows of the board
     * @param y the number of columns of the board
     */
    public Board(Game game, int x, int y){
        squares = new Square[x][y];
        this.game = game;
        height = x;
        width = y;
        player1Pieces = null;
        player2Pieces = null;
    }

    /**
     * Function to set the pieces on both sides of the board, for both players
     */
    public void initializeBoard(){
        setPlayer2Pieces(height, width);
        setPlayer1Pieces(height, width);
    }

    /**
     * Function to set the pieces of the WHITE player on the board
     * @param x the number of rows of the chessboard
     * @param y the number of columns of the chessboard
     */
    private void setPlayer1Pieces(int x, int y) {
        for(int i = 0; i < y; i++){ // setting row of white pawns
            squares[x-2][i] = new Square(x-2, i, new Pawn(squares[x-2][i], game.getPlayer1()));
            player1Pieces.add(squares[x-2][i].getPiece());
        }
        squares[x-1][0] = new Square(x-1, 0, new Rook(squares[x-1][0], game.getPlayer1()));
        squares[x-1][1] = new Square(x-1, 1, new Knight(squares[x-1][1], game.getPlayer1()));
        squares[x-1][2] = new Square(x-1, 2, new Bishop(squares[x-1][2], game.getPlayer1()));
        squares[x-1][3] = new Square(x-1, 3, new Queen(squares[x-1][3], game.getPlayer1()));
        squares[x-1][4] = new Square(x-1, 4, new King(squares[x-1][4], game.getPlayer1()));
        squares[x-1][5] = new Square(x-1, 5, new Bishop(squares[x-1][5], game.getPlayer1()));
        squares[x-1][6] = new Square(x-1, 6, new Knight(squares[x-1][6], game.getPlayer1()));
        squares[x-1][7] = new Square(x-1, 7, new Rook(squares[x-1][7], game.getPlayer1()));

        if(squares[x-1].length > 8){// if board is not standard size, fill the rest of the space with pawns
            for(int i = 8; i < y; i++){
                squares[x-1][i] = new Square(x-1, i, new Pawn(squares[x-1][i], game.getPlayer1()));
            }

        }
        //adding the last row of pieces to the piece vector
        for(int i = 0; i < y; i++){
            player1Pieces.add(squares[x-1][i].getPiece());
        }
    }

    /**
     * Function to set the pieces of the BLACK player on the board
     * @param x the number of rows of the chessboard
     * @param y the number of columns of the chessboard
     */
    private void setPlayer2Pieces(int x, int y) {
        for(int i = 0; i < y; i++){ // setting row of black pawns and adding them to the piece vector
            squares[1][i] = new Square(1, i, new Pawn(squares[1][i], game.getPlayer2()));
            player2Pieces.add(squares[1][i].getPiece());
        }
        squares[0][0] = new Square(0, 0, new Rook(squares[0][0], game.getPlayer2()));
        squares[0][1] = new Square(0, 1, new Knight(squares[0][1], game.getPlayer2()));
        squares[0][2] = new Square(0, 2, new Bishop(squares[0][2], game.getPlayer2()));
        squares[0][3] = new Square(0, 3, new Queen(squares[0][3], game.getPlayer2()));
        squares[0][4] = new Square(0, 4, new King(squares[0][4], game.getPlayer2()));
        squares[0][5] = new Square(0, 5, new Bishop(squares[0][5], game.getPlayer2()));
        squares[0][6] = new Square(0, 6, new Knight(squares[0][6], game.getPlayer2()));
        squares[0][7] = new Square(0, 7, new Rook(squares[0][7], game.getPlayer2()));

        if(squares[0].length > 8){// if board is not standard size, fill the rest of the space with pawns
            for(int i = 8; i < y; i++){
                squares[0][i] = new Square(0, i, new Pawn(squares[0][i], game.getPlayer2()));
            }

        }
        //adding the last row of pieces to the piece vector
        for(int i = 0; i < y; i++){
            player2Pieces.add(squares[0][i].getPiece());
        }
    }


}
