package Pieces;

import Game.*;

import java.util.Vector;

public class King extends Piece{
    private final Type type = Type.KING;
    public King(Square position, Player player) {
        super(position, player);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vector<Square> calculatePossibleMoves(Board chessBoard) {
        Vector<Square> moves = new Vector<>();
        // adding base moves
        final int[][] offsets = {
                {0, 1},
                {1, 0},
                {1, 1},
                {0, -1},
                {-1, 0},
                {-1, -1},
                {1, -1},
                {-1, 1}
        };

        for (int[] o : offsets) {
            Square target = chessBoard.getSquareAt(o[0] + position.getX(), o[1] + position.getY());
            if (chessBoard.isInBounds(o[0] + position.getX(), o[1] + position.getY())) {
                if ((target.getPiece() == null || target.getPiece().getColor() != getColor())) {
                    moves.add(target);
                }
            }
        }
        addCastlingMoves(moves, chessBoard);

        possibleMoves = moves;
        return possibleMoves;
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
    public King clone() {
        King newKing = new King(position, player);
        newKing.setPreviousPosition(previousPosition);
        return newKing;
    }

    @Override
    public void movePiece(Square target,Board chessBoard) {
        if (target != null) {
        calculatePossibleMoves(chessBoard);
        //King can't move into check
        if (player.isSquareInCheck(target, chessBoard)) {
            possibleMoves.remove(target);
        }
        // if the king is moving 2 positions, then it must be a castling move
        if (Math.abs(target.getX() - getPosition().getX()) > 1) {
            castlingMove(target, chessBoard);
            return;
        }

        if (!possibleMoves.contains(target)) {
            return;
        }
        capturePiece(target);
        this.position.setPiece(null); // remove piece from current position(Square)
        setPreviousPosition(this.position); // set the previous position, as the one that the piece moves from
        this.position = target; //set the piece's new position
        this.position.setPiece(this); // set the new position's piece as this piece
        }
    }

    /**
     * Function that adds the possible castling moves to the moves vector
     * In a castling move the king's castling move in a direction moves the corresponding rook as well
     *
     * @param moves the vector of all possible moves
     */
    private void addCastlingMoves(Vector<Square> moves, Board chessBoard){
        // Castling moves
            if (!hasMoved()) {
                Piece leftPiece = chessBoard.getSquareAt(position.getX() - 4, position.getY()).getPiece();
                Piece rightPiece = chessBoard.getSquareAt(position.getX() + 3, position.getY()).getPiece();
                Rook leftRook = null;
                Rook rightRook = null;
                if (leftPiece != null && leftPiece.getType() == Type.ROOK) {
                    leftRook = (Rook) leftPiece;
                }
                if (rightPiece != null && rightPiece.getType() == Type.ROOK) {
                    rightRook = (Rook) rightPiece;
                }
                if (leftRook != null && !leftRook.hasMoved() && (chessBoard.getSquareAt((position.getX() - 1), (position.getY())).getPiece() == null)
                        && (chessBoard.getSquareAt((position.getX() - 2), (position.getY())).getPiece() == null)
                        && (chessBoard.getSquareAt(position.getX() - 3, position.getY()).getPiece() == null)) {
                    // if leftRook hasn't moved and there is no piece between them
                    moves.add(chessBoard.getSquareAt(position.getX() - 2, position.getY()));
                }
                if ((rightRook != null) && (!rightRook.hasMoved()) && (chessBoard.getSquareAt((position.getX() + 1), (position.getY())).getPiece() == null)
                        && (chessBoard.getSquareAt((position.getX() + 2), (position.getY())).getPiece() == null)) {
                    // if rightRook hasn't moved and there is no piece between them
                    moves.add(chessBoard.getSquareAt(position.getX() + 2, position.getY()));
                }
            }
    }
    public void castlingMove(Square target, Board chessBoard) { // when castling, rook has to move too

            if ((getPosition().getX() - target.getX()) > 0) { // king is moving left
                Rook leftRook = (Rook) chessBoard.getSquareAt(target.getX() - 2, target.getY()).getPiece();
                if (leftRook != null && leftRook.calculatePossibleMoves(chessBoard).contains(chessBoard.getSquareAt(target.getX() + 1, target.getY()))) {
                    movePiece(target, chessBoard);
                    leftRook.movePiece(chessBoard.getSquareAt(target.getX() - 1, target.getY()), chessBoard);
                }
            }
            else if ((getPosition().getX() - target.getX()) < 0) { // king is moving right
                Rook rightRook = (Rook) chessBoard.getSquareAt(target.getX() + 1, target.getY()).getPiece();
                if (rightRook != null && rightRook.calculatePossibleMoves(chessBoard).contains(chessBoard.getSquareAt(target.getX() - 1, target.getY()))) {
                    movePiece(target, chessBoard);
                    rightRook.movePiece(chessBoard.getSquareAt(target.getX() - 1, target.getY()), chessBoard);
                }

            }
    }


}
