package GUI;

import Game.Game;
import Game.Player;
import Game.Board;
import Game.Square;
import Pieces.Piece;
//import Game.Color;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

public class ChessBoard {

    private final JFrame boardFrame;
    private final BoardPanel boardPanel;
    private final Board board;
    private Square destination;
    private Piece movedPiece;
    private final ImageIcon BLACKPAWN = new ImageIcon(ImageIO.read(new File("textures/pieces/BLACK/PAWN.png")).getScaledInstance(75,75, Image.SCALE_SMOOTH));
    private final ImageIcon BLACKKING = new ImageIcon(ImageIO.read(new File("textures/pieces/BLACK/KING.png")).getScaledInstance(75,75, Image.SCALE_SMOOTH));
    private final ImageIcon BLACKKNIGHT = new ImageIcon(ImageIO.read(new File("textures/pieces/BLACK/KNIGHT.png")).getScaledInstance(75,75, Image.SCALE_SMOOTH));
    private final ImageIcon BLACKBIHSOP = new ImageIcon(ImageIO.read(new File("textures/pieces/BLACK/BISHOP.png")).getScaledInstance(75,75, Image.SCALE_SMOOTH));
    private final ImageIcon BLACKROOK = new ImageIcon(ImageIO.read(new File("textures/pieces/BLACK/ROOK.png")).getScaledInstance(75,75, Image.SCALE_SMOOTH));
    private final ImageIcon BLACKQUEEN = new ImageIcon(ImageIO.read(new File("textures/pieces/BLACK/QUEEN.png")).getScaledInstance(75,75, Image.SCALE_SMOOTH));
    private final ImageIcon WHITEPAWN = new ImageIcon(ImageIO.read(new File("textures/pieces/WHITE/PAWN.png")).getScaledInstance(75,75, Image.SCALE_SMOOTH));
    private final ImageIcon WHITEKING = new ImageIcon(ImageIO.read(new File("textures/pieces/WHITE/KING.png")).getScaledInstance(75,75, Image.SCALE_SMOOTH));
    private final ImageIcon WHITEKNIGHT = new ImageIcon(ImageIO.read(new File("textures/pieces/WHITE/KNIGHT.png")).getScaledInstance(75,75, Image.SCALE_SMOOTH));
    private final ImageIcon WHITEBIHSOP = new ImageIcon(ImageIO.read(new File("textures/pieces/WHITE/BISHOP.png")).getScaledInstance(75,75, Image.SCALE_SMOOTH));
    private final ImageIcon WHITEROOK = new ImageIcon(ImageIO.read(new File("textures/pieces/WHITE/ROOK.png")).getScaledInstance(75,75, Image.SCALE_SMOOTH));
    private final ImageIcon WHITEQUEEN = new ImageIcon(ImageIO.read(new File("textures/pieces/WHITE/QUEEN.png")).getScaledInstance(75,75, Image.SCALE_SMOOTH));
    private final ImageIcon GREYDOT = new ImageIcon(ImageIO.read(new File("textures/other/greydot.png")).getScaledInstance(30,30,Image.SCALE_SMOOTH));
    private final ImageIcon[] BlackIcons;
    private final ImageIcon[] WhiteIcons;

    private final PiecePanel piecePanel;


    public ChessBoard(Game game) throws IOException {
        boardFrame = new JFrame("Chess");
        boardFrame.setLayout(new BorderLayout());

        board = game.getBoard();

        BlackIcons = new ImageIcon[6];
        WhiteIcons = new ImageIcon[6];
        addBlackPieceIcons();
        addWhitePieceIcons();

        JMenuBar boardMenuBar = new JMenuBar();
        boardMenuBar.add(createFileMenu());
        boardFrame.setJMenuBar(boardMenuBar);

        boardPanel = new BoardPanel(game);
        boardFrame.add(boardPanel, BorderLayout.CENTER);

        piecePanel = new PiecePanel(game);
        boardFrame.add(piecePanel, BorderLayout.EAST);

        boardFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        //boardFrame.pack();
        boardFrame.setSize(1000,800);
        boardFrame.setVisible(true);
    }
    private void addBlackPieceIcons(){
        BlackIcons[0] = BLACKPAWN;
        BlackIcons[1] = BLACKROOK;
        BlackIcons[2] = BLACKKNIGHT;
        BlackIcons[3] = BLACKBIHSOP;
        BlackIcons[4] = BLACKKING;
        BlackIcons[5] = BLACKQUEEN;
    }
    private void addWhitePieceIcons(){
        WhiteIcons[0] = WHITEPAWN;
        WhiteIcons[1] = WHITEROOK;
        WhiteIcons[2] = WHITEKNIGHT;
        WhiteIcons[3] = WHITEBIHSOP;
        WhiteIcons[4] = WHITEKING;
        WhiteIcons[5] = WHITEQUEEN;
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
        private final Game game;
        private SquarePanel[][] squares;
        private final int rows;
        private final int cols;

        private BoardPanel(Game game) {
            this.game = game;
            rows = game.getBoard().getHeight();
            cols = game.getBoard().getWidth();
            setLayout(new GridLayout(rows, cols));

            // adding the squares to their individual places;
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
            validate();
            repaint();
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
                        boardPanel.reDrawBoard();
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
                        piecePanel.addTakenPieceIcons();
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
                if(piece.getColor().toString().equals("WHITE")){
                    this.add(new JLabel(WhiteIcons[piece.getType().getNumber()]), BorderLayout.CENTER);
                }else if(piece.getColor().toString().equals("BLACK")){
                    this.add(new JLabel(BlackIcons[piece.getType().getNumber()]), BorderLayout.CENTER);
                }
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

        /**
         * Function drawing the possible moves of a piece. To do this it adds a grey circle icon to the corresponding squares.
         */
        public void displayLegalMoves() {
            if (movedPiece != null){
                Vector<Square> moves = movedPiece.getPossibleMoves();
                for (Square legalMove : moves) {
                    if ((legalMove.getX() == row) && (legalMove.getY() == col)) {
                            this.add(new JLabel(GREYDOT), BorderLayout.CENTER);
                    }
                }
            }
        }

    }

    /**
     * Class to display the pieces taken during the game on the side
     */
    private class PiecePanel extends JPanel{
        Game game;
        TakenPiecePanel p1Panel;
        TakenPiecePanel p2Panel;


        public PiecePanel(Game game){
            this.game = game;
            this.setPreferredSize(new Dimension(200,800));

            p1Panel = new TakenPiecePanel(game.getPlayer1());
            p2Panel = new TakenPiecePanel(game.getPlayer2());

            p1Panel.setBackground(Color.red);
            p2Panel.setBackground(Color.green);
            p1Panel.add(new JLabel("WHITE"), BorderLayout.CENTER);
            p2Panel.add(new JLabel("BLACK"), BorderLayout.CENTER);

            setLayout(new BorderLayout());
            add(p1Panel, BorderLayout.EAST);
            add(p2Panel, BorderLayout.WEST);

        }
        public void addTakenPieceIcons(){
            p1Panel.addPieceIcons();
            p2Panel.addPieceIcons();
        }

        /**
         * Class to display the pieces taken by one player on the Pieces panel
         */
        private class TakenPiecePanel extends JPanel{
            private final Vector<Piece> takenPieces;
            Player player;

            public TakenPiecePanel(Player player){

                setPreferredSize(new Dimension(100,800));
                this.player = player;
                takenPieces = player.getTakenEnemyPieces();


                setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

                validate();

            }
            public void addPieceIcons(){
                ImageIcon[] icons;
                if(player.getColor().toString().equals("WHITE")){
                    icons = BlackIcons;
                }else {
                    icons = WhiteIcons;
                }

                removeAll();
                for(Piece piece : takenPieces) {
                    Image img = icons[piece.getType().getNumber()].getImage();
                    Image newimg = img.getScaledInstance(45,45, Image.SCALE_SMOOTH);
                    ImageIcon icon = new ImageIcon(newimg);
                    this.add(new JLabel(icon));
                }
                validate();
                repaint();
            }
        }

    }

}
