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
    public Vector<Square> calculatePossibleMoves(Board chessBoard) {
        if (getColor().equals(Color.WHITE)) {

            addWhiteBaseMoves(chessBoard);
            addWhitePawnJumpMoves(chessBoard);
            addWhiteEnPassantMoves(chessBoard);
        } else {
            addBlackBaseMoves(chessBoard);
            addBlackPawnJumpMoves(chessBoard);
            addBlackEnPassantMoves(chessBoard);
        }
        return possibleMoves; // the functions called above all (possibly) alter the contents of the possibleMoves vector
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
    public void movePiece(Square target, Board chessBoard) {
        if (target != null) {
            if(!calculatePossibleMoves(chessBoard).contains(target)) return;
            //if the piece that is moving is a pawn,
            //and it is moving diagonally onto an empty square it must be an EnPassant move
            if (!target.isSquareOccupied()) {
                if (chessBoard.getSquareAt(target.getX() + 1, target.getY()).getPiece() != null) {
                    if (getColor().equals(Color.WHITE) && chessBoard.getSquareAt(target.getX() + 1, target.getY()).getPiece().getColor() != Color.WHITE) {
                        enPassantMove(target, chessBoard);
                        return;
                    }
                }
                if (chessBoard.getSquareAt(target.getX() - 1, target.getY()).getPiece() != null) {
                    if (getColor().equals(Color.BLACK) && chessBoard.getSquareAt(target.getX() - 1, target.getY()).getPiece().getColor() != Color.BLACK) {
                        enPassantMove(target, chessBoard);
                        return;
                    }
                }
            }
            //normal moves
            capturePiece(target);
            this.position.setPiece(null); // remove piece from current position(Square)
            setPreviousPosition(this.position); // set the previous position, as the one that the piece moves from
            this.position = target; //set the piece's new position
            this.position.setPiece(this); // set the new position's piece as this piece
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
    private void addBlackEnPassantMoves(Board chessBoard){
        if (getColor() == Color.BLACK) {
            Square target = chessBoard.getSquareAt(position.getX() + 1, position.getY() - 1); // down and left
            if (target != null) {
                Square enPassantSquare = chessBoard.getSquareAt(target.getX() - 1, target.getY());
                if (enPassantSquare.getPiece() != null) {
                    int enPassantXDiff = Math.abs(enPassantSquare.getX() - enPassantSquare.getPiece().getPreviousPosition().getX());
                    if (enPassantXDiff == 2 && enPassantSquare.getPiece().getPosition().getX() == 6) {// the enemy pawn jumped
                        if ((target.getPiece().getColor() != getColor()) && (target.getPiece().getType() == Type.PAWN)) {
                            possibleMoves.add(target);
                        }
                    }
                }
            }
            target = chessBoard.getSquareAt(position.getX() + 1, position.getY() + 1); // down and right
            if (target != null) {
                Square enPassantSquare = chessBoard.getSquareAt(target.getX() -1, target.getY());
                if (enPassantSquare.getPiece() != null) {
                    int enPassantXDiff = Math.abs(enPassantSquare.getX() - enPassantSquare.getPiece().getPreviousPosition().getX());
                    if (enPassantXDiff == 2 && enPassantSquare.getPiece().getPosition().getX() == 6) {// the enemy pawn jumped
                        if ((target.getPiece().getColor() != getColor()) && (target.getPiece().getType() == Type.PAWN)) {
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
        if (getColor() == Color.WHITE) {
            Square target = chessBoard.getSquareAt(position.getX() - 1, position.getY() - 1); // up and left
            if (target != null) {
                Square enPassantSquare = chessBoard.getSquareAt(target.getX() + 1, target.getY());
                if (enPassantSquare.getPiece() != null) {
                    int enPassantXDiff = Math.abs(enPassantSquare.getX() - enPassantSquare.getPiece().getPreviousPosition().getX());
                    if (enPassantXDiff == 2 && enPassantSquare.getPiece().getPreviousPosition().getX() == 1) {// the enemy pawn jumped
                        if ((target.getPiece().getColor() != getColor()) && (target.getPiece().getType() == Type.PAWN)) {
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
                    if (enPassantXDiff == 2 && enPassantSquare.getPiece().getPosition().getX() == 1) {// the enemy pawn jumped
                        if ((target.getPiece().getColor() != getColor()) && (target.getPiece().getType() == Type.PAWN)) {
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

}
