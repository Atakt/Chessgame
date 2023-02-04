package Game;

import Pieces.*;
import org.jetbrains.annotations.NotNull;

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
        player1Pieces = new Vector<>();
        player2Pieces = new Vector<>();
        initializeBoard();
    }

    /**
     * Function to set the pieces on both sides of the board, for both players
     */
    public void initializeBoard(){
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                squares[i][j] = new Square(i, j, null);
            }
        }
        setPlayer2Pieces(height, width);
        setPlayer1Pieces(height, width);
        game.getPlayer1().setAlivePieces(player1Pieces);
        game.getPlayer2().setAlivePieces(player2Pieces);

        for(Piece piece : player1Pieces){
            piece.calculatePossibleMoves(this);
        }
        for (Piece piece : player2Pieces){
            piece.calculatePossibleMoves(this);
        }
    }
    /**
     * Function returning the height (x) of the board
     * @return the height of the board
     */
    public int getHeight(){
        return height;
    }

    /**
     * Function returning the width (y) of the board
     * @return the width of the board
     */
    public int getWidth() {
        return width;
    }

    /**
     * Function returning the current game - the game that is played on the board
     * @return the current game
     */
    public Game getGame() {
        return game;
    }

    /**
     * Function to set the pieces of the WHITE player on the board
     * @param x the number of rows of the chessboard
     * @param y the number of columns of the chessboard
     */
    private void setPlayer1Pieces(int x, int y) {
        for(int i = 0; i < y; i++){ // setting row of white pawns
            squares[x-2][i].setPiece(new Pawn(squares[x-2][i], game.getPlayer1()));
            //assert player1Pieces != null;
            player1Pieces.add(squares[x-2][i].getPiece());
        }
        squares[x-1][0].setPiece(new Rook(squares[x-1][0], game.getPlayer1()));
        squares[x-1][1].setPiece(new Knight(squares[x-1][1], game.getPlayer1()));
        squares[x-1][2].setPiece(new Bishop(squares[x-1][2], game.getPlayer1()));
        squares[x-1][3].setPiece(new Queen(squares[x-1][3], game.getPlayer1()));
        squares[x-1][4].setPiece(new King(squares[x-1][4], game.getPlayer1()));
        squares[x-1][5].setPiece(new Bishop(squares[x-1][5], game.getPlayer1()));
        squares[x-1][6].setPiece(new Knight(squares[x-1][6], game.getPlayer1()));
        squares[x-1][7].setPiece(new Rook(squares[x-1][7], game.getPlayer1()));

        if(squares[x-1].length > 8){// if board is not standard size, fill the rest of the space with pawns
            for(int i = 8; i < y; i++){
                squares[x-1][i].setPiece(new Pawn(squares[x-1][i], game.getPlayer1()));
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
            squares[1][i].setPiece(new Pawn(squares[1][i], game.getPlayer2()));
            //assert player2Pieces != null;
            player2Pieces.add(squares[1][i].getPiece());
        }
        squares[0][0].setPiece(new Rook(squares[0][0], game.getPlayer2()));
        squares[0][1].setPiece(new Knight(squares[0][1], game.getPlayer2()));
        squares[0][2].setPiece(new Bishop(squares[0][2], game.getPlayer2()));
        squares[0][3].setPiece(new Queen(squares[0][3], game.getPlayer2()));
        squares[0][4].setPiece(new King(squares[0][4], game.getPlayer2()));
        squares[0][5].setPiece(new Bishop(squares[0][5], game.getPlayer2()));
        squares[0][6].setPiece(new Knight(squares[0][6], game.getPlayer2()));
        squares[0][7].setPiece(new Rook(squares[0][7], game.getPlayer2()));

        if(squares[0].length > 8){// if board is not standard size, fill the rest of the space with pawns
            for(int i = 8; i < y; i++){
                squares[0][i].setPiece(new Pawn(squares[0][i], game.getPlayer2()));
            }
        }
        //adding the last row of pieces to the piece vector
        for(int i = 0; i < y; i++){
            player2Pieces.add(squares[0][i].getPiece());
        }
    }

    /**
     * Function that returns a reference for the square at the given coordinates of the board
     * @param x The row of the square
     * @param y The column of the square
     * @return The square at the coordinates or null, if the square is out of bounds
     */
    public Square getSquareAt(int x, int y){
        if(isInBounds(x, y))//x and y coordinates are on the board
             return squares[x][y];
        else {
            return null;
        }
    }

    /**
     * Function returns true or false based on whether the coordinates are on the board or not.
     * @param x Row of the square
     * @param y Column of the square
     * @return True if the square at the coordinates is on the board, false if it is not.
     */
    public boolean isInBounds(int x, int y){
        //x and y coordinates are on the board
        return (x < height && 0 <= x) && (y >= 0 && y < width);
    }

    /**
     * Function to move a piece to target square.
     * @param target the square where the piece moves to
     * @param piece the piece moved
     */
    public void movePiece(Square target, Piece piece){
        piece.calculatePossibleMoves(this);
        removeKingCompromiseMove(piece, piece.getPlayer()); // removing illegal moves that would put the king in check

        if(!piece.getPossibleMoves().contains(target)) return; // the move cant happen
        piece.move(target, this); // moving piece
        //need to check checkmate after a move
        Player enemyPlayer = game.getOtherPlayer(piece.getPlayer());
        if(isCheckMated(enemyPlayer, this)){// if the player that didn't move this turn is checkmated
            game.setGameOver(true);
            game.setWinner(piece.getPlayer());
        }
        game.endTurn(); // after the move it's the next player's turn
    }

    /**
     * Function that checks whether the player given as a parameter is checkmated
     * @param player the player examined to be checkmated or not
     * @param chessBoard the chessboard of the current game
     * @return true is the player is checkmated false if the player isn't checkmated
     */
    private boolean isCheckMated(@NotNull Player player, Board chessBoard){
        if(player.getAllPossibleMoves().isEmpty()){ // player has no moves left
            return true;
        }

        boolean checkMate = false;
        Piece king = player.getKing(); // the king piece of the player
        if(!king.getPossibleMoves().isEmpty()){// the king still has legal moves left
            return false;
        }
        if(player.isSquareInCheck(king.getPosition())){//the king is in check
            checkMate = true;
            // we need to check whether any possible moves would make it so that the king would not be in check
            for(Piece actualPiece : player.getAlivePieces()){// checking for each piece of the player
                Vector<Square> moves = actualPiece.getPossibleMoves();

                for (Square actualSquare : moves){
                    // clone board
                    Board boardCopy = null;
                    boardCopy = this.clone(); //cloning the board (this)

                    //make all possible (temporary) moves
                    //the piece that is moved is the in the same position as the piece in the vector that is being iterated over but not the same
                    Piece movedPiece = boardCopy.getSquareAt(actualPiece.getPosition().getX(), actualPiece.getPosition().getY()).getPiece();
                    //likewise we need to find the matching square on the copied board
                    Square targetSquare = boardCopy.getSquareAt(actualSquare.getX(), actualSquare.getY());
                    // the square where the piece is moving from, and where we need to move it back
                    Square prev = getSquareAt(movedPiece.getPosition().getX(), movedPiece.getPosition().getY());
                    movedPiece.move(targetSquare, boardCopy); // moving the piece to the target square (doing all on the cloned board)

                    Square kingSquare = boardCopy.getSquareAt(king.getPosition().getX(), king.getPosition().getY());
                    if(!player.isSquareInCheck(kingSquare)){ // if as a result of the move the square where the king stands is no longer in check
                        //it means the player is not checkmated
                        return false;

                    }
                    movedPiece.move(prev, boardCopy); // moving the piece back and continuing iteration
                }
            }
        }
        return checkMate;
    }

    /**
     * Function to help with the checking of checkmate. Creates a deep-copy of the Board object.
     * @return The copy of the object.
     */
    @Override
    protected Board clone(){
        Board copy = new Board(game, height, width); // create a new board named copy
        //Fill the same squares in the new board as in the original
        for(int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                copy.getSquareAt(i,j).setPiece(null);
                if (squares[i][j].isSquareOccupied()){//if there is a piece on the original board on the square
                    Piece piece = squares[i][j].getPiece().clone();
                    copy.getSquareAt(i,j).setPiece(piece);
                }
            }
        }
        return  copy;
    }

    /**
     * Function to remove all the moves that would be illegal because they would put the king into possible check, which is not allowed
     * You cannot "checkmate yourself"
     * @param piece the piece that's moves are removed
     * @param player the player that the piece belongs to
     */
    private void removeKingCompromiseMove(Piece piece, Player player){
        Board copyBoard = this.clone();
        Vector<Square> compromiseMoves = new Vector<>();
        Piece movedPiece = copyBoard.getSquareAt(piece.getPosition().getX(), piece.getPosition().getY()).getPiece();
        Piece king = player.getKing();
        Vector<Square> moves = movedPiece.getPossibleMoves();
        for(int i = 0; i < moves.size(); i++){
            Square move = moves.get(i);
            Square prev = getSquareAt(movedPiece.getPosition().getX(), movedPiece.getPosition().getY()); // the square where the piece is moving from, and where we need to move it back
            movedPiece.move(move, copyBoard);
            if(player.isSquareInCheck(king.getPosition())){
                compromiseMoves.add(move);
            }
            movedPiece.move(prev, copyBoard); // moving back and continuing iteration
        }
        piece.removePossibleMove(compromiseMoves);
    }

}
