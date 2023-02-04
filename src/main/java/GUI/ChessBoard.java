package GUI;

import Game.Game;
import Game.Board;
import Game.Square;
import Pieces.Piece;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

public class ChessBoard {

    private final JFrame boardFrame;
    private final BoardPanel boardPanel;
    private final Board board;
    private Square destination;
    private Piece movedPiece;

    public ChessBoard(Game game){
        boardFrame = new JFrame("Chess");
        boardFrame.setLayout(new BorderLayout());

        board = game.getBoard();

        JMenuBar boardMenuBar = new JMenuBar();
        boardMenuBar.add(createFileMenu());
        boardFrame.setJMenuBar(boardMenuBar);

        boardPanel = new BoardPanel(game);
        boardFrame.add(boardPanel, BorderLayout.CENTER);

        boardFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        boardFrame.setSize(800,800);
        boardFrame.setVisible(true);
    }

    private JMenu createFileMenu() {
        JMenu fileMenu = new JMenu("File");
        JMenuItem exitGame = new JMenuItem("Exit Game");
        exitGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        JMenuItem saveGame = new JMenuItem("Save Game");
        saveGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ChessGame.saveGame(board.getGame());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        fileMenu.add(exitGame);
        fileMenu.add(saveGame);
        return fileMenu;
    }

    private class BoardPanel extends JPanel{
        private Game game;
        private SquarePanel[][] squares;
        private int rows;
        private int cols;

        private BoardPanel(Game game) {
            this.game = game;
            rows = game.getBoard().getHeight();
            cols = game.getBoard().getWidth();
            setLayout(new GridLayout(rows, cols));

            // adding the square s to their individual places;
            squares = new SquarePanel[rows][cols];
            for(int i = 0; i < rows; i++){
                for(int j = 0; j < cols; j++){
                    SquarePanel squarePanel = new SquarePanel(i, j);
                    squares[i][j] = squarePanel;
                    this.add(squarePanel);
                }
            }
            setPreferredSize(new Dimension(400,400));
            validate();

        }

        public void reDrawBoard(){
            int rows = game.getBoard().getHeight();
            int cols = game.getBoard().getWidth();
            for(int i = 0; i < rows; i++){
                for(int j = 0; j < cols; j++){
                    squares[i][j].reDrawSquare(board);
                    squares[i][j].displayLegalMoves();
                }
            }
            //displayLegalMoves();
            validate();
            repaint();
        }

        /**
         * Function drawing the possible moves of a piece. To do this it adds a grey circle icon to the corresponding squares.
         */

        public void displayLegalMoves() {
            if(movedPiece != null){
                Vector<Square> moves = movedPiece.getPossibleMoves();
                for(Square possibleMove : moves){
                    for(int i = 0; i < rows; i++){
                        for(int j = 0; j < cols; j++){
                            if((possibleMove.getX() == i) && (possibleMove.getY() == j)){
                                BufferedImage image = null;
                                try {
                                    image = ImageIO.read(new File("textures/other/greydot.png"));
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                this.add(new JLabel(new ImageIcon(image)));
                            }
                        }
                    }

                }
            }
        }

    }

    private class SquarePanel extends JPanel{
        private static final Color LIGHT_COLOR = Color.decode("#eeeed2") ;
        private static final Color DARK_COLOR = Color.decode("#769656");
        private final int row;
        private final int col;

        private SquarePanel(int x, int y){
            row = x;
            col = y;

            setPreferredSize(new Dimension(50,50));
            setLayout(new BorderLayout());
            addSquareColor();
            addSquareIcon(board);

            addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    //cancelling a move
                    if(SwingUtilities.isRightMouseButton(e)){
                        destination = null;
                        movedPiece = null;
                        //boardPanel.reDrawBoard();
                    }
                    else if(SwingUtilities.isLeftMouseButton(e)){
                        if(movedPiece == null){
                            // first click - no piece was selected prior to this click
                            //If the Square has no piece, then it is the same as if there was no click, ie. movedPiece = null.
                            //Clicking on empty square therefore does nothing.
                            movedPiece = board.getSquareAt(x, y).getPiece();
                            // movedPiece is now the Piece on the square that was clicked on.
                            // if the selected piece(now movedPiece) does actually exist
                            if(movedPiece != null){
                                if(movedPiece.getColor().equals(board.getGame().getTurn())){
                                    // The right piece was selected, corresponding to the color.
                                }else{
                                    //The piece, that was selected has a different color, then the actual turn
                                    // -> deselect piece.
                                    movedPiece = null;
                                }
                            }
                        }else {// movedPiece != null -> a piece was already selected to be moved
                            // the square that's clicked on now is the square where we want to move the already selected piece to
                            destination = board.getSquareAt(row, col);
                            board.movePiece(destination, movedPiece);
                            destination = null;
                            movedPiece = null;
                        }

                        if(board.getGame().isGameOver()){
                            JOptionPane.showMessageDialog(null, "Game over: "+board.getGame().getWinner().toString()+" wins!",
                                    "wincondition reached",
                                    JOptionPane.INFORMATION_MESSAGE );
                            System.exit(0);

                        }
                        boardPanel.reDrawBoard();

                    }
                }
                @Override
                public void mousePressed(MouseEvent e) {
                }
                @Override
                public void mouseReleased(MouseEvent e) {
                }
                @Override
                public void mouseEntered(MouseEvent e) {
                }
                @Override
                public void mouseExited(MouseEvent e) {
                }
            });

            validate();
        }
        private void addSquareIcon(Board board) {
            Piece piece = board.getSquareAt(row, col).getPiece();
            removeAll();
            if(board.getSquareAt(row, col).isSquareOccupied()){
                BufferedImage image = null;
                try {
                    String piecePNGPath = "textures/pieces/";
                    image = ImageIO.read(new File(piecePNGPath + piece.getColor().toString() + "/"
                            + piece.getType().toString() + ".png"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                this.add(new JLabel(new ImageIcon(image.getScaledInstance(75,75, Image.SCALE_FAST))), BorderLayout.CENTER);
            }
            validate();
            repaint();
        }

        private void addSquareColor() {
            if((row % 2) == (col % 2)){
                setBackground(LIGHT_COLOR);
            }else{
                setBackground(DARK_COLOR);
            }
        }


        public void reDrawSquare(Board board) {
            removeAll();
            addSquareColor();
            addSquareIcon(board);
            validate();
            repaint();


        }


        public void displayLegalMoves() {
            if (movedPiece != null){
                Vector<Square> moves = movedPiece.getPossibleMoves();
                for (Square legalMove : moves) {
                    if ((legalMove.getX() == row) && (legalMove.getY() == col)) {
                        try{
                            BufferedImage image = ImageIO.read(new File("textures/other/greydot.png"));

                            this.add(new JLabel(new ImageIcon(image.getScaledInstance(30,30, Image.SCALE_FAST))), BorderLayout.CENTER);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
        }
    }


}
