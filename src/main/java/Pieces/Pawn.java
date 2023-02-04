package Pieces;

import Game.*;

import java.util.Vector;

public class Pawn extends Piece{
    private final Type type = Type.PAWN;
    public Pawn(Square position, Player player) {
        super(position, player);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void calculatePossibleMoves(Board chessBoard) {
        possibleMoves.clear();
        if (getColor() == Color.WHITE) {
            addWhiteBaseMoves(chessBoard);
            addWhitePawnJumpMoves(chessBoard);
            addWhiteEnPassantMoves(chessBoard);
        } else {
            addBlackBaseMoves(chessBoard);
            addBlackPawnJumpMoves(chessBoard);
            addBlackEnPassantMoves(chessBoard);
        }
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public Type getType() {
        return type;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public Piece clone() {
        Pawn newPawn = new Pawn(position, player);
        newPawn.setPreviousPosition(previousPosition);
        return newPawn;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void move(Square target, Board chessBoard) {
        calculatePossibleMoves(chessBoard);
        if (target != null) {
            if(!possibleMoves.contains(target)) return;
            //if the piece that is moving is a pawn,
            //and it is moving diagonally onto an empty square it must be an EnPassant move
            if (!target.isSquareOccupied()) {
                if (chessBoard.getSquareAt(target.getX() + 1, target.getY()).getPiece() != null) {
                    if (getColor().equals(Color.WHITE) && chessBoard.getSquareAt(target.getX() + 1, target.getY()).getPiece().getColor() != Color.WHITE) {
                        enPassantMove(target, chessBoard);
                        calculatePossibleMoves(chessBoard);
                        return;
                    }
                }
                if (chessBoard.getSquareAt(target.getX() - 1, target.getY()).getPiece() != null) {
                    if (getColor().equals(Color.BLACK) && chessBoard.getSquareAt(target.getX() - 1, target.getY()).getPiece().getColor() != Color.BLACK) {
                        enPassantMove(target, chessBoard);
                        calculatePossibleMoves(chessBoard);
                        return;
                    }
                }
            }
            if(isPawnPromotion(chessBoard)){
                pawnPromotionMove(target, chessBoard);
                calculatePossibleMoves(chessBoard);
                return;
            }
            //normal moves
            capturePiece(target);
            this.position.setPiece(null); // remove piece from current position(Square)
            setPreviousPosition(this.position); // set the previous position, as the one that the piece moves from
            this.position = target; //set the piece's new position
            this.position.setPiece(this); // set the new position's piece as this piece

            getPossibleMoves();
            calculatePossibleMoves(chessBoard);
        }
    }

    /**
     * function to make an en-passant move with the pawn and capture the corresponding piece.
     * @param target the square where the pawn moves to, not the square where the enemy piece is taken from
     * @param chessBoard the chessboard of the current game
     */
    private void enPassantMove(Square target, Board chessBoard){
        if (getColor() == Color.WHITE) {
            Square enPassantSquare = chessBoard.getSquareAt(target.getX() +1, target.getY());
            if (enPassantSquare != null) {
                capturePiece(enPassantSquare);
            }
            this.position.setPiece(null); // remove piece from current position(Square)
            setPreviousPosition(this.position); // set the previous position, as the one that the piece moves from
            this.position = target; //set the piece's new position
            this.position.setPiece(this); // set the new position's piece as this piece
        } else {
            Square enPassantSquare = chessBoard.getSquareAt(target.getX() -1, target.getY());
            if (enPassantSquare != null) {
                capturePiece(enPassantSquare);
            }
            this.position.setPiece(null); // remove piece from current position(Square)
            setPreviousPosition(this.position); // set the previous position, as the one that the piece moves from
            this.position = target; //set the piece's new position
            this.position.setPiece(this); // set the new position's piece as this piece
        }
    }

    /**
     * Function adding the base moves ( straight, diagonally 1) to the possibleMoves Vector if the piece is WHITE,
     * which means it is moving upwards
     * @param chessBoard the chessboard of the current game
     */
    private void addWhiteBaseMoves(Board chessBoard){
        if (getColor() == Color.WHITE) {
            Square target = chessBoard.getSquareAt(position.getX() - 1, position.getY()); // Square straight up
            if (chessBoard.isInBounds(position.getX() - 1 , position.getY()) && target.getPiece() == null) { // pawn can only move forward if the square ahead is empty
                possibleMoves.add(target);
            }
            target = chessBoard.getSquareAt(position.getX() - 1, position.getY() - 1); // Square up and left
            if (target != null) {
                if (target.getPiece() != null) {
                    if (chessBoard.isInBounds(position.getX() - 1, position.getY() - 1) && target.getPiece().getColor() != getColor()) {
                        possibleMoves.add(target);
                    }
                }
            }
            target = chessBoard.getSquareAt(position.getX() - 1, position.getY() + 1); // Square up and right
            if (target != null) {
                if (target.getPiece() != null) {
                    if (chessBoard.isInBounds(position.getX() -1, position.getY() + 1) && target.getPiece().getColor() != getColor()) {
                        possibleMoves.add(target);
                    }
                }
            }

        }
    }

    /**
     * Function adding the base moves ( straight, diagonally 1) to the possibleMoves Vector if the piece is BLACK,
     * which means it is moving downwards
     * @param chessBoard the chessboard of the current game
     */
    private void addBlackBaseMoves(Board chessBoard){
        if (getColor() == Color.BLACK) {
            Square target = chessBoard.getSquareAt(position.getX() + 1, position.getY()); // Square straight down
            if (chessBoard.isInBounds(position.getX() + 1, position.getY()) && target.getPiece() == null) {
                possibleMoves.add(target);
            }
            target = chessBoard.getSquareAt(position.getX() + 1, position.getY() - 1); // Square down and left
            if (target != null) {
                if (target.getPiece() != null) {
                    if (chessBoard.isInBounds(position.getX() + 1, position.getY() - 1) && target.getPiece().getColor() != getColor()) {
                        possibleMoves.add(target);
                    }
                }
            }
            target = chessBoard.getSquareAt(position.getX() + 1, position.getY() + 1);// Square down and right
            if (target != null) {
                if (target.getPiece() != null) {
                    if (chessBoard.isInBounds(position.getX() + 1, position.getY() + 1) && target.getPiece().getColor() != getColor()) {
                        possibleMoves.add(target);
                    }
                }
            }

        }
    }

    /**
     * Function adding the possible en-passant moves to all the possible moves (possibleMoves vector)
     * if the piece is BLACK, which means it is moving downwards
     * @param chessBoard the chessboard of the current game
     */
    private void addBlackEnPassantMoves(Board chessBoard) {
            if((getColor() == Color.BLACK)&&(position.getX() == 4)) {
                Square target = chessBoard.getSquareAt(position.getX() + 1, position.getY() - 1); // down and left
                if (target != null) {
                    Square enPassantSquare = chessBoard.getSquareAt(target.getX() - 1, target.getY());
                    if (enPassantSquare.getPiece() != null) {
                        int enPassantXDiff = Math.abs(enPassantSquare.getX() - enPassantSquare.getPiece().getPreviousPosition().getX());
                        if (enPassantXDiff == 2 && enPassantSquare.getPiece().getPreviousPosition().getX() == 6) {// the enemy pawn jumped
                            if ((enPassantSquare.getPiece().getColor() != getColor()) && (enPassantSquare.getPiece().getType() == Type.PAWN)) {
                                possibleMoves.add(target);
                            }
                        }
                    }
                }
                target = chessBoard.getSquareAt(position.getX() + 1, position.getY() + 1); // down and right
                if (target != null) {
                    Square enPassantSquare = chessBoard.getSquareAt(target.getX() - 1, target.getY());
                    if (enPassantSquare.getPiece() != null) {
                        int enPassantXDiff = Math.abs(enPassantSquare.getX() - enPassantSquare.getPiece().getPreviousPosition().getX());
                        if (enPassantXDiff == 2 && enPassantSquare.getPiece().getPreviousPosition().getX() == 6) {// the enemy pawn jumped
                            if ((enPassantSquare.getPiece().getColor() != getColor()) && (enPassantSquare.getPiece().getType() == Type.PAWN)) {
                                possibleMoves.add(target);
                            }
                        }
                    }
                }
            }
    }

    /**
     * Function adding the possible en-passant moves to all the possible moves (possibleMoves vector)
     * if the piece is WHITE, which means it is moving upwards
     * @param chessBoard the chessboard of the current game
     */
    private void addWhiteEnPassantMoves(Board chessBoard){
        if ((getColor() == Color.WHITE)&&(position.getX() == 3)) {
            Square target = chessBoard.getSquareAt(position.getX() - 1, position.getY() - 1); // up and left
            if (target != null) {
                Square enPassantSquare = chessBoard.getSquareAt(target.getX() + 1, target.getY());
                if (enPassantSquare.getPiece() != null) {
                    int enPassantXDiff = Math.abs(enPassantSquare.getX() - enPassantSquare.getPiece().getPreviousPosition().getX());
                    if (enPassantXDiff == 2 && enPassantSquare.getPiece().getPreviousPosition().getX() == 1) {// the enemy pawn jumped
                        if ((enPassantSquare.getPiece().getColor() != getColor()) && (enPassantSquare.getPiece().getType() == Type.PAWN)) {
                            possibleMoves.add(target);
                        }
                    }
                }
            }
            target = chessBoard.getSquareAt(position.getX() - 1, position.getY() + 1); // up and right
            if (target != null) {
                Square enPassantSquare = chessBoard.getSquareAt(target.getX() +1, target.getY());
                if (enPassantSquare.getPiece() != null) {
                    int enPassantXDiff = Math.abs(enPassantSquare.getX() - enPassantSquare.getPiece().getPreviousPosition().getX());
                    if (enPassantXDiff == 2 && enPassantSquare.getPiece().getPreviousPosition().getX() == 1) {// the enemy pawn jumped
                        if ((enPassantSquare.getPiece().getColor() != getColor()) && (enPassantSquare.getPiece().getType() == Type.PAWN)) {
                            possibleMoves.add(target);
                        }
                    }
                }
            }
        }
    }

    /**
     * Function adding the Pawn Jump moves to the possible moves Vector of the piece if the pawn is WHITE,
     * which means it is moving upwards
     * @param chessBoard The chessboard of the current game
     */
    private void addWhitePawnJumpMoves(Board chessBoard){
        //double step
        if (getColor() == Color.WHITE) {
            if ((this.getPreviousPosition() == null) && (chessBoard.getSquareAt(position.getX() - 2, position.getY()).getPiece() == null)
                    && (chessBoard.getSquareAt(position.getX() -1 , position.getY()).getPiece() == null)) {
                // if pawn hasn't moved yet and path is clear then move is possible
                possibleMoves.add(chessBoard.getSquareAt(position.getX() -2, position.getY()));
            }
        }
    }

    /**
     * Function adding the Pawn Jump moves to the possible moves Vector of the piece if the pawn is BLACK,
     * which means it is moving downwards
     * @param chessBoard The chessboard of the current game
     */
    private void addBlackPawnJumpMoves(Board chessBoard){
        if (getColor() == Color.BLACK) {
            if ((this.getPreviousPosition() == null) && (chessBoard.getSquareAt(position.getX() + 2, position.getY()).getPiece() == null)
                    && (chessBoard.getSquareAt(position.getX() + 1, position.getY()).getPiece() == null)) {
                // if pawn hasn't moved yet and path is clear then move is possible
                possibleMoves.add(chessBoard.getSquareAt(position.getX() + 2, position.getY()));
            }
        }
    }

    /**
     * Function determining if pawn is in position for next move to be a promotion
     * @param chessBoard The chessboard of the current game
     * @return True if the next move could result in pawn promotion, else false.
     */
    private boolean isPawnPromotion(Board chessBoard){
        int secondRow = 1; // row second from the top of the board
        int secondToLastRow = chessBoard.getHeight()-1 -1; // row second from the bottom,
        // -1 two times, one because it's the row before the last and one because of indexing
        return ((getColor() == Color.WHITE) && (getPosition().getX() == secondRow)) || ((getColor() == Color.BLACK) && (getPosition().getX() == secondToLastRow));
    }

    /**
     * Function to move the pawn and execute the promotion if the pawn is in position.
     * @param target the square where the pawn moves to, not the square where the enemy piece is taken from
     * @param chessBoard The chessboard of the current game
     */
    private void pawnPromotionMove(Square target, Board chessBoard){
        capturePiece(target);
        this.position.setPiece(null); // remove piece from current position(Square)
        setPreviousPosition(this.position); // set the previous position, as the one that the piece moves from
        this.position = target; //set the piece's new position
        //this.position.setPiece(this); // set the new position's piece as this piece
        //this.position.setPiece(null); // preparing to promote pawn, first it needs to be removed from the square
        Piece queen = new Queen(position, player);
        this.position.setPiece(queen); // Auto-Queen promotion, pawn gets replaced with a new queen
        player.addAlivePiece(queen);
        player.removeAlivePiece(this);
    }
}
