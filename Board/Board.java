package Board;

import Piece.*;
import Parser.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Chess Board class - Singleton implementation
 * matrix - the internal representation of the chess board
 */
public class Board {
    private static Board instance = null;
    private Tile[][] matrix;
    private static List<Piece> blackPieces;
    private static List<Piece> whitePieces;

    public static Board getInstance() {
        if (instance == null) {
            instance = new Board();
        }
        return instance;
    }

    /**
     * Method that helps us to find a specific tile on our board,
     * being given a set of coordinates.
     *
     * @param coordinates - given coordinates
     * @return - the searched tile
     */
    public Tile findTileOnBoard(String coordinates) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (Tile.getLetter(matrix[i][j].getCoordinates()) == Tile.getLetter(coordinates)
                        && Tile.getNum(matrix[i][j].getCoordinates()) == Tile.getNum(coordinates)) {
                    return matrix[i][j];
                }
            }
        }
        return null;
    }

    /**
     * Method that helps to find a specific chess piece on our
     * board, being given a start coordinates.
     * @param coordinates - given coordinates ex. a2
     * @return - the searched piece
     */

    public Piece findPieceOnBoard(String coordinates) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (matrix[i][j].getPiece() != null) {
                    if (matrix[i][j].getPiece().getStartCoordinates().equals(coordinates)) {
                        return matrix[i][j].getPiece();
                    }
                }
            }
        }
        return null;
    }

    /**
     * Methos that helps us to make the necessary changes in order to place a Piece object on
     * the chess board.
     *
     * @param pieceType   - the type of the piece we want to create and place
     * @param color       -  the color of the piece we want to create and place
     * @param coordinates -  the coordinates of the tile we want to place the piece on
     * @param i           - the i coordinate in matrix
     * @param j           - the j coordinate in matrix
     */

    private void placePiece(String pieceType, Boolean color, String coordinates, int i, int j) {
        Piece newPiece = new PieceFactory(pieceType).createPiece(coordinates, color);
        if (color && (!newPiece.getPieceType().equals("king"))) {
            whitePieces.add(newPiece);
        } else if (!color && (!newPiece.getPieceType().equals("king"))) {
            blackPieces.add(newPiece);
        }
        matrix[i][j].setPiece(newPiece);
        matrix[i][j].setOccupied(true);
    }

    /**
     * Method that helps us to correctly place the pieces on the margin of our chess board.
     *
     * @param color       - the color
     * @param coordinates - the coordinates
     * @param i           - i coordinate in matrix
     * @param j           - j coordinate in matrix
     */

    private void fillBorder(Boolean color, String coordinates, int i, int j) {
        if (j == 0 || j == 7) {
            placePiece("rook", color, coordinates, i, j);
        } else if (j == 2 || j == 5) {
            placePiece("bishop", color, coordinates, i, j);
        } else if (j == 1 || j == 6) {
            placePiece("knight", color, coordinates, i, j);
        } else if (j == 3) {
            placePiece("queen", color, coordinates, i, j);
        } else {
            placePiece("king", color, coordinates, i, j);
        }
    }

    /**
     * Method used to fill the matrix with the correct configuration of a starting chess game.
     */
    private void placePiecesOnBoard() {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 8; j++) {
                String coordinates = Tile.getTileChar(j) + String.valueOf(8 - i);   // getting the right coordinates
                Boolean color = false;
                if (i == 0) {
                    fillBorder(color, coordinates, i, j);                           // placing on the lower margin
                } else {
                    placePiece("pawn", color, coordinates, i, j);          // filling the second row with pawns
                }
            }
        }
        for (int i = 6; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                String coordinates = Tile.getTileChar(j) + String.valueOf(8 - i);   // getting the right coordinates
                Boolean color = true;
                if (i == 7) {
                    fillBorder(color, coordinates, i, j);                           // placing on the upper margin
                } else {
                    placePiece("pawn", color, coordinates, i, j);         // filling the second rown with pawns
                }
            }
        }
    }

    /**
     * Method used to initialize the chess board.
     * First of all, we initialize the afferent matrix with a block of 8x8 units.
     * Each Tile object is assigned a fixed coordinate and color ( black or white).
     * The Piece objects are placed on our chess board.
     */

    public void initializeBoard() {
        blackPieces = new ArrayList<>();
        whitePieces = new ArrayList<>();
        this.matrix = new Tile[8][8];
        for (int i = 7; i >= 0; i--) {
            for (int j = 0; j <= 7; j++) {
                boolean color = (j % 2 == 0 && i % 2 == 0) || (j % 2 == 1 && i % 2 == 1);   // choosing the color
                String coordinates = Tile.getTileChar(j) + String.valueOf(8 - i);
                matrix[i][j] = new TileBuilder()                                            // creating the Tile object
                        .setColor(color)
                        .setCoordinates(coordinates)
                        .build();
            }
        }
        this.placePiecesOnBoard();                                                          // placing pieces
    }

    public void printMatrix() {
        System.out.print("  ");
        for (char i = 'a'; i <= 'h'; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        System.out.print("  ");
        for (int i = 0; i <= 14; i++) {
            System.out.print("-");
        }
        System.out.println();
        for (int i = 0; i < 8; i++) {
            System.out.print((9 - (i + 1)) + "|");
            for (int j = 0; j < 8; j++) {
                if (matrix[i][j].getPiece() != null) {
                        System.out.print(String.valueOf(matrix[i][j].getPiece().getPieceType()).charAt(0) + " ");
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println();
        }
    }

    /**
     * Method used in order to replace the pawn that reached the border of our chess with a queen.
     * @param tile - finish tile
     * @param piece - the piece that is checked in order to be replaced
     */
    private boolean promotePawn(Tile tile, Piece piece, String promoteTo) {
        int num = Character.getNumericValue(Tile.getNum(tile.getCoordinates()));
        if (piece instanceof Pawn && (num == 8 || num == 1)) {
            Piece newPiece = new PieceFactory("queen").createPiece(tile.getCoordinates(), piece.getColor());
            switch (promoteTo) {
                case "b":
                    newPiece = new PieceFactory("bishop").createPiece(tile.getCoordinates(), piece.getColor());
                    break;
                case "n":
                    newPiece = new PieceFactory("knight").createPiece(tile.getCoordinates(), piece.getColor());
                    break;
                case "r":
                    newPiece = new PieceFactory("rook").createPiece(tile.getCoordinates(), piece.getColor());
                    break;
            }
            newPiece.setStartCoordinates(piece.getStartCoordinates());
            tile.setPiece(newPiece);
            if (newPiece.getColor()) {
                Board.removeWhite(piece);
                Board.addWhite(newPiece);
            } else {
                Board.removeBlack(piece);
                Board.addBlack(newPiece);
            }
            return true;
        } else {
            tile.setPiece(piece);
            return false;
        }
    }

    /**
     * Pawn class has an attribute named isFirstMove. If this is activated ( the value contained is "true") , our pawn
     * will move 2 units instead of one.
     * @param piece - the pawn to be moved.
     */

    private void updateFirstMove(Piece piece) {
        if (piece instanceof Pawn) {
            if (((Pawn) piece).isFirstMove()) {
                ((Pawn) piece).setFirstMove(false);
            }
        }
    }

    private void updateEnPass(Piece piece, String coordinates) {
        if (piece instanceof Pawn) {
            if (((Pawn) piece).eligibleEnPassR()) {                                          // current move is 2nd move
                ((Pawn) piece).setEnPassR(false);
            } else {                                                                        // no en pass for the moment
                if (((Pawn) piece).isFirstMove()) {                                         // pawn at his first move
                    String initialCoord = coordinates.substring(0, 2);
                    int initialNumber = Integer.parseInt(initialCoord.substring(1));
                    char initialLetter = initialCoord.charAt(0);
                    String finalCoord = coordinates.substring(2);
                    int finalNumber = Integer.parseInt(finalCoord.substring(1));
                    char finalLetter = finalCoord.charAt(0);
                    if (initialLetter == finalLetter && finalNumber == initialNumber + 2 || finalNumber == initialNumber - 2) {  // it jumped
                        Tile left = findTileOnBoard((char)(finalLetter + 1) + String.valueOf(finalNumber));
                        Tile right = findTileOnBoard((char)(finalLetter - 1) + String.valueOf(finalNumber));
                        if (Parser.getEnginesColor()) {
                            left = findTileOnBoard((char)(finalLetter - 1) + String.valueOf(finalNumber));
                            right = findTileOnBoard((char)(finalLetter + 1) + String.valueOf(finalNumber));
                        }
                        if (left != null && left.isOccupied() && left.getPiece().getColor() != piece.getColor()) {
                            if (left.getPiece().getPieceType().equals("pawn")) {
                                ((Pawn) piece).setEnPassL(true);
                            }
                        } else if (left == null) {
                            ((Pawn) piece).setEnPassL(false);
                        }
                        if (right != null && right.isOccupied() && right.getPiece().getColor() != piece.getColor()) {
                            if (right.getPiece().getPieceType().equals("pawn")) {
                                ((Pawn) piece).setEnPassR(true);
                            }
                        } else if (right == null) {
                            ((Pawn) piece).setEnPassR(false);
                        }
                    }
                }
            }
        }
    }

    /**
     * Method used to updated the internal representation of the chess board according to past commands.
     * @param coordinates - string which contains the initial and final coordinates ex: a1a3
     */
    public void updateMatrix(String coordinates) {

        Tile initialTile = findTileOnBoard(coordinates.substring(0, 2));        // get the tile with the initial coordinates
        Tile finishTile = findTileOnBoard(coordinates.substring(2, 4));         // get the tile with the final coordinates

        if (initialTile.isOccupied() && initialTile.getPiece().getPieceType().equals("king")) {
            if (initialTile.getCoordinates().equals("e1") && finishTile.getCoordinates().equals("g1")) {
                updateKingSideCastling(!Parser.getEnginesColor());
            } else if (initialTile.getCoordinates().equals("e1") && finishTile.getCoordinates().equals("c1")) {
                updateQueenSideCastling(!Parser.getEnginesColor());
            } else if (initialTile.getCoordinates().equals("e8") && finishTile.getCoordinates().equals("g8")) {
                updateKingSideCastling(!Parser.getEnginesColor());
            } else if (initialTile.getCoordinates().equals("e8") && finishTile.getCoordinates().equals("c8")) {
                updateQueenSideCastling(!Parser.getEnginesColor());
            }
        }
        String promoteTo = "q";
        if (Parser.checkIfPromote(coordinates)) {
            finishTile = findTileOnBoard(coordinates.substring(2, 4));
            promoteTo = coordinates.substring(4);
        }
        Piece piece = initialTile.getPiece();
        if (piece == null)
            return;
        String initialCoord = coordinates.substring(0, 2);
        int initialNumber = Integer.parseInt(initialCoord.substring(1));
        char initialLetter = initialCoord.charAt(0);
        String finalCoord = coordinates.substring(2);
        int finalNumber = Integer.parseInt(finalCoord.substring(1, 2));
        char finalLetter = finalCoord.charAt(0);
        Tile leftTile;
        Tile rightTile;
        if (piece instanceof Pawn && !Parser.getEnginesColor()) {                   // update en pass black
            leftTile = findTileOnBoard((char)(initialLetter - 1) + String.valueOf(initialNumber));
            rightTile = findTileOnBoard((char)(initialLetter + 1) + String.valueOf(initialNumber));
            if (finalLetter == initialLetter + 1 && finalNumber == initialNumber + 1) {
                if (!finishTile.isOccupied()) {
                    Board.removeBlack(rightTile.getPiece());
                    rightTile.setPiece(null);
                    rightTile.setOccupied(false);
                }
            }
            if (finalLetter == initialLetter - 1 && finalNumber == initialNumber + 1) {
                if (!finishTile.isOccupied()) {
                    Board.removeBlack(leftTile.getPiece());
                    leftTile.setPiece(null);
                    leftTile.setOccupied(false);
                }
            }
        }      // en pass for black
        if (piece instanceof Pawn && Parser.getEnginesColor()) {                    // update en pass white
            leftTile = findTileOnBoard((char)(initialLetter - 1) + String.valueOf(initialNumber));
            rightTile = findTileOnBoard((char)(initialLetter + 1) + String.valueOf(initialNumber));
            if (finalLetter == initialLetter - 1 && finalNumber == initialNumber - 1) {
                if (!finishTile.isOccupied()) {
                    Board.removeWhite(leftTile.getPiece());
                    leftTile.setPiece(null);
                    leftTile.setOccupied(false);
                }
            }
            if (finalLetter == initialLetter + 1 && finalNumber == initialNumber - 1) {
                if (!finishTile.isOccupied()) {
                    Board.removeWhite(rightTile.getPiece());
                    rightTile.setPiece(null);
                    rightTile.setOccupied(false);
                }
            }
        }       // en pass for white

//        if (finishTile.isOccupied()) {                                           // if the finish tile is occupied
//            if (Parser.getEnginesColor()) {
//                Board.removeWhite(finishTile.getPiece());
//            } else {
//                Board.removeBlack(finishTile.getPiece());
//            }
//        }//ma-ta


        if(finishTile.isOccupied()) {
            if(finishTile.getPiece().getColor() != Parser.getEnginesColor()) {
                if(Parser.getEnginesColor()){
                    Board.removeBlack(finishTile.getPiece());
                } else {
                    Board.removeWhite(finishTile.getPiece());
                }
            } else {
                if(Parser.getEnginesColor()){
                    Board.removeWhite(finishTile.getPiece());
                } else {
                    Board.removeBlack(finishTile.getPiece());
                }
            }
        }



        piece.setCurrCoordinates(finishTile.getCoordinates());             // updating the current coordinates of the piece
        initialTile.setPiece(null);
        initialTile.setOccupied(false);                                    // set as unoccupied

        updateEnPass(piece, coordinates);
        updateFirstMove(piece);                                            // updating the isFirstMove parameter

        if (!promotePawn(finishTile, piece, promoteTo))                    // check if the pawn needs promoting
            finishTile.setPiece(piece);
        finishTile.setOccupied(true);

        List<Piece> enemyPieces = getEnemyPieces(Parser.getEnginesColor());
        for (Piece p : enemyPieces) {
            if (p.givesChess()) {
                Parser.setInChess(true);
                break;
            }
        }

        if (piece instanceof Rook) {
            ((Rook) piece).setMoved(true);
        }
        if (piece instanceof King) {
            ((King) piece).setMoved(true);
        }
    }

    public void updateKingSideCastling(boolean enginesColor) {
        if (enginesColor) {
            Tile initialKingTile = findTileOnBoard("e1");
            Tile initialRookTile = findTileOnBoard("h1");
            Tile finishKingTile = findTileOnBoard("g1");
            Tile finishRookTile = findTileOnBoard("f1");

            Piece king = initialKingTile.getPiece();
            Piece rook = initialRookTile.getPiece();

            initialKingTile.setPiece(null);
            initialKingTile.setOccupied(false);

            initialRookTile.setPiece(null);
            initialRookTile.setOccupied(false);

            finishKingTile.setPiece(king);
            finishKingTile.setOccupied(true);

            finishRookTile.setPiece(rook);
            finishRookTile.setOccupied(true);

            ((King)king).setMoved(true);
            king.setCurrCoordinates(finishKingTile.getCoordinates());
            if (rook != null) {
                ((Rook)rook).setMoved(true);
                rook.setCurrCoordinates(finishRookTile.getCoordinates());
            }

        } else {
            Tile initialKingTile = findTileOnBoard("e8");
            Tile initialRookTile = findTileOnBoard("h8");
            Tile finishKingTile = findTileOnBoard("g8");
            Tile finishRookTile = findTileOnBoard("f8");
            Piece king = initialKingTile.getPiece();
            Piece rook = initialRookTile.getPiece();
            initialKingTile.setPiece(null);
            initialKingTile.setOccupied(false);
            initialRookTile.setPiece(null);
            initialRookTile.setOccupied(false);
            finishKingTile.setPiece(king);
            finishKingTile.setOccupied(true);
            finishRookTile.setPiece(rook);
            finishRookTile.setOccupied(true);
            ((King)king).setMoved(true);
            king.setCurrCoordinates(finishKingTile.getCoordinates());
            if (rook != null) {
                ((Rook)rook).setMoved(true);
                rook.setCurrCoordinates(finishRookTile.getCoordinates());
            }
        }
    }

    public void updateQueenSideCastling(boolean enginesColor) {
        if (enginesColor) {
            Tile initialKingTile = findTileOnBoard("e1");
            Tile initialRookTile = findTileOnBoard("a1");
            Tile finishKingTile = findTileOnBoard("c1");
            Tile finishRookTile = findTileOnBoard("d1");
            Piece king = initialKingTile.getPiece();
            Piece rook = initialRookTile.getPiece();
            initialKingTile.setPiece(null);
            initialKingTile.setOccupied(false);
            initialRookTile.setPiece(null);
            initialRookTile.setOccupied(false);
            finishKingTile.setPiece(king);
            finishKingTile.setOccupied(true);
            finishRookTile.setPiece(rook);
            finishRookTile.setOccupied(true);
            ((King)king).setMoved(true);
            king.setCurrCoordinates(finishKingTile.getCoordinates());
            if (rook != null) {
                ((Rook)rook).setMoved(true);
                rook.setCurrCoordinates(finishRookTile.getCoordinates());
            }
        } else {
            Tile initialKingTile = findTileOnBoard("e8");
            Tile initialRookTile = findTileOnBoard("a8");
            Tile finishKingTile = findTileOnBoard("c8");
            Tile finishRookTile = findTileOnBoard("d8");
            Piece king = initialKingTile.getPiece();
            Piece rook = initialRookTile.getPiece();
            initialKingTile.setPiece(null);
            initialKingTile.setOccupied(false);
            initialRookTile.setPiece(null);
            initialRookTile.setOccupied(false);
            finishKingTile.setPiece(king);
            finishKingTile.setOccupied(true);
            finishRookTile.setPiece(rook);
            finishRookTile.setOccupied(true);
            ((King)king).setMoved(true);
            king.setCurrCoordinates(finishKingTile.getCoordinates());
            if (rook != null) {
                ((Rook)rook).setMoved(true);
                rook.setCurrCoordinates(finishRookTile.getCoordinates());
            }
        }
    }



    /**
     * Method used to reinitialize the board.
     */
    public void resetBoard() {
        matrix = null;
        initializeBoard();
    }

    /**
     * Singleton implementation, setter for instance attribute.
     * @param instance -  instance for our Chess Board
     */
    public static void setInstance(Board instance) {
        Board.instance = instance;
    }

    /**
     * Getter for matrix attribute.
     * @return - the matrix
     */
    public Tile[][] getMatrix() {
        return matrix;
    }

    /**
     * Setter for matrix attribute.
     * @param matrix - the new value for the matrix attribute
     */
    public void setMatrix(Tile[][] matrix) {
        this.matrix = matrix;
    }

    public Piece getKing(boolean engineColor) {
        if (engineColor) {
            return Board.getInstance().findPieceOnBoard("e1");
        } else {
            return Board.getInstance().findPieceOnBoard("e8");
        }
    }

    public Piece getRandomPiece(boolean engineColor) {
        Random rand = new Random();
        if (engineColor) {
            if (whitePieces.size() == 0) {
                System.out.println("resign");
                return null;
            } else if (whitePieces.size() == 1) {
                return whitePieces.get(0);
            } else {
                return whitePieces.get(rand.nextInt(whitePieces.size()));
            }
        } else {
            if (blackPieces.size() == 0) {
                System.out.println("resign");
                return null;
            } else if (blackPieces.size() == 1) {
                return blackPieces.get(0);
            } else {
                return blackPieces.get(rand.nextInt(blackPieces.size()));
            }
        }
    }

    public boolean checkKingSideCastling() {
        if (Parser.getEnginesColor()) {
            King king = (King)getKing(true);
            Rook rook = (Rook)findPieceOnBoard("h1");
            Tile f1 = findTileOnBoard("f1");
            Tile g1 = findTileOnBoard("g1");

            if (king == null || rook == null){
                return false;
            }

            if (king != null && king.hasBeenMoved()) {
                return false;
            }
            if (rook != null && rook.hasBeenMoved()) {
                return false;
            }
            if (f1.isOccupied() || f1.isGuarded()) {
                return false;
            }
            if (g1.isOccupied() || g1.isGuarded()) {
                return false;
            }
            if (Parser.isInChess()) {
                return false;
            }
        } else {
            King king = (King)getKing(false);
            Rook rook = (Rook)findPieceOnBoard("h8");
            Tile f8 = findTileOnBoard("f8");
            Tile g8 = findTileOnBoard("g8");

            if (king == null || rook == null){
                return false;
            }

            if (king != null && king.hasBeenMoved()) {
                return false;
            }
            if (rook != null && rook.hasBeenMoved()) {
                return false;
            }
            if (f8.isOccupied() || f8.isGuarded()) {
                return false;
            }
            if (g8.isOccupied() || g8.isGuarded()) {
                return false;
            }
            if (Parser.isInChess()) {
                return false;
            }
        }
        return true;
    }

    public boolean checkQueenSideCastling() {
        if (Parser.getEnginesColor()) {
            King king = (King)getKing(true);
            Rook rook = (Rook)findPieceOnBoard("a1");
            Tile b1 = findTileOnBoard("b1");
            Tile c1 = findTileOnBoard("c1");
            Tile d1 = findTileOnBoard("d1");

            if (king == null || rook == null){
                return false;
            }

            if (king != null && king.hasBeenMoved()) {
                return false;
            }
            if (rook != null && rook.hasBeenMoved()) {
                return false;
            }
            if (b1.isOccupied() || b1.isGuarded()) {
                return false;
            }
            if (c1.isOccupied() || c1.isGuarded()) {
                return false;
            }
            if (d1.isOccupied() || d1.isGuarded()) {
                return false;
            }
            if (Parser.isInChess()) {
                return false;
            }
        } else {
            King king = (King)getKing(false);
            Rook rook = (Rook)findPieceOnBoard("a8");
            Tile b8 = findTileOnBoard("b8");
            Tile c8 = findTileOnBoard("c8");
            Tile d8 = findTileOnBoard("d8");

            if (king == null || rook == null){
                return false;
            }

            if (king != null && king.hasBeenMoved()) {
                return false;
            }
            if (rook != null && rook.hasBeenMoved()) {
                return false;
            }
            if (b8.isOccupied() || b8.isGuarded()) {
                return false;
            }
            if (c8.isOccupied() || c8.isGuarded()) {
                return false;
            }
            if (d8.isOccupied() || d8.isGuarded()) {
                return false;
            }
            if (Parser.isInChess()) {
                return false;
            }
        }
        return true;
    }


    public static void addBlack(Piece piece) {
        blackPieces.add(piece);
    }

    public static void addWhite(Piece piece) {
        whitePieces.add(piece);
    }

    public static void removeBlack(Piece piece) {
        blackPieces.remove(piece);
    }

    public static void removeWhite(Piece piece) {
        whitePieces.remove(piece);
    }

    public static List<Piece> getBlackPieces() {
        return blackPieces;
    }

    public static void setBlackPieces(List<Piece> blackPieces) {
        Board.blackPieces = blackPieces;
    }

    public static List<Piece> getWhitePieces() {
        return whitePieces;
    }

    public static void setWhitePieces(List<Piece> whitePieces) {
        Board.whitePieces = whitePieces;
    }

    public static List<Piece> getPieces(boolean engineColor) {
        if (engineColor) {
            return whitePieces;
        } else return blackPieces;
    }

    public static List<Piece> getEnemyPieces(boolean engineColor) {
        if (!engineColor) {
            return whitePieces;
        } else return blackPieces;
    }
}