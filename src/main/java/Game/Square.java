package Game;

import Pieces.Piece;

/**
 * Class representing one Square of the chessboard
 */
public class Square {
    private int x_coordinate, y_coordinate;
    private Piece piece;

    /**
     * Constructor
     * @param x row of chessboard
     * @param y column of chessboard
     * @param piece Piece of on the square
     */
    public Square(int x, int y, Piece piece){
        x_coordinate = x;
        y_coordinate = y;
        this.piece = piece;
    }

    /**
     * A function returning the piece on the Square
     * @return the current piece on the Square
     */
    public Piece getPiece() {
        return piece;
    }

    /**
     * @return true if there is a piece on the squre, false if there is no piece on the square
     */
    public boolean isSquareOccupied(){
        return piece != null;
    }

    /**
     * Function returning which row the square is in
     * @return x coordinate of the square
     */
    public int getX() {
        return x_coordinate;
    }

    /**
     * Function returning which column the square is in
     * @return y coordinate of the square
     */
    public int getY() {
        return y_coordinate;
    }

    /**
     * Function setting the piece of the square
     * @param piece the piece on the square
     */
    public void setPiece(Piece piece){
        this.piece = piece;

    }
}
