package Parser;

import Board.*;
import Piece.*;

import java.util.Scanner;
import java.util.List;

/**
 * Parser class
 * Attributes :
 * enginesColor - white or black, stores the current color
 * noMove -  counter that stores the number of moves made in the edit mode
 * scanner - stdin reader
 * board - board instance
 * isForced - boolean variabile
 * piece - the piece we move
 */
public class Parser {
    private static boolean enginesColor = false; // false -> black; true -> white
    private static int noMoves = 0;              // total number of moves in edit mode
    private static final Scanner scanner = new Scanner(System.in);
    private static final Board BOARD = Board.getInstance();
    private static boolean isForced = false;
    private static Piece piece;
    private static boolean inChess = false;
    private static String previous;

    /**
     * Method that holds the parser.
     */
    public void parse() {

        while (scanner.hasNext()) {
            boolean castlingThisRound = false;
            previous = scanner.nextLine();
            String[] lineItems = previous.split(" ");
            if (checkIfEnginesTurn(previous) || checkIfPromote(previous)) {
                BOARD.updateMatrix(previous);                          // update matrix with user move
                if (BOARD.checkKingSideCastling() && !isForced) {
                    piece = BOARD.getKing(enginesColor);
                    ((King) piece).doKingSideCastling();
                    BOARD.updateKingSideCastling(Parser.getEnginesColor());
                    castlingThisRound = true;
                } else if (BOARD.checkQueenSideCastling() && !isForced) {
                    piece = BOARD.getKing(enginesColor);
                    ((King) piece).doQueenSideCastling();
                    BOARD.updateQueenSideCastling(Parser.getEnginesColor());
                    castlingThisRound = true;
                } else {
                    if (!inChess) {
                        piece = BOARD.getRandomPiece(enginesColor);
                        int noPieces = 8;
                        if (piece != null) {
                            while (piece.defending()) {                         // verifica daca piesa apara regele
                                piece = BOARD.getRandomPiece(enginesColor);
                                noPieces--;
                                if (noPieces == 0) {                            // am ramas fara piese disponibile
                                    piece = BOARD.getKing(enginesColor);        // muta regele
                                }
                            }
                        }
                    } else {
                        piece = BOARD.getKing(enginesColor);
                    }
                }
            }
            if ("protover".equals(lineItems[0])) {
                feature();
            }
            if ("new".equals(lineItems[0])) {
                New();
            }
            if (previous.equals("go")) {
                castlingThisRound = go();
            }
            if (previous.equals("force")) {
                force();
            }
            if (!castlingThisRound) {
                move(previous);
            }

        }
        scanner.close();
    }

    /**
     * Method that checks if the previous command has a specific format.
     *
     * @param opponentsMove -  last xboard command
     * @return - a boolean value that tells us if the given string respects the format needed
     */

    public static Boolean checkIfEnginesTurn(String opponentsMove) {
        return opponentsMove.matches("\\w\\d\\w\\d"); // regex for char_int_char_int : c2c3 for example
    }

    public static Boolean checkIfPromote(String opponentsMove) {
        return opponentsMove.matches("\\w\\d\\w\\d\\w"); // regex for char_int_char_int_char : c2c3q for example
    }

    /**
     * Implementation for "feature" command.
     * We print a string which contains the needed parameters.
     */

    private void feature() {
        System.out.println("feature sigint=0 san=0 name=0xfff");
    }

    /**
     * Implementation for "force" command.
     * To know if we are in the edit mode we update isForced attribute.
     */

    private void force() {
        isForced = true;
    }

    /**
     * Implementation for "resign" command.
     * If the implemented chess piece is captured, we send to xboard the "resign" command.
     */

    private void resign() {
        if (piece.getStartCoordinates().equals("")) {
            System.out.println("resign");
        }
    }

    /**
     * Implementation for "new" command.
     * This method helps us to reset all parameters that we use in the implementation of our chess game.
     */

    private void New() {
        inChess = false;
        enginesColor = false;
        BOARD.resetBoard();
        noMoves = 0;
        isForced = false;
        piece = BOARD.getRandomPiece(enginesColor);
    }

    /**
     * Implementation for "go" command.
     * Go command is given when we exit the "Edit mode" or when we switch the players' colors.
     * We update the parameters accordingly. We move the piece when it's engines turn.
     */

    private boolean go() {
        boolean castlingThisTurn = false;
        if (isForced) {
            enginesColor = noMoves % 2 == 0;
            isForced = false;
        } else {
            enginesColor = !enginesColor;
        }
        List<Piece> enemyPieces = Board.getEnemyPieces(Parser.getEnginesColor());
        for (Piece p : enemyPieces) {
            if (p.givesChess()) {
                Parser.setInChess(true);
                break;
            }
        }
        if (BOARD.checkKingSideCastling() && !isForced) {
            piece = BOARD.getKing(enginesColor);
            ((King) piece).doKingSideCastling();
            BOARD.updateKingSideCastling(Parser.getEnginesColor());
            castlingThisTurn = true;
        } else if (BOARD.checkQueenSideCastling() && !isForced) {
            piece = BOARD.getKing(enginesColor);
            ((King) piece).doQueenSideCastling();
            BOARD.updateQueenSideCastling(Parser.getEnginesColor());
            castlingThisTurn = true;
        } else {
            if (!inChess) {
                piece = BOARD.getRandomPiece(enginesColor);
                int noPieces = 8;
                if (piece != null) {
                    while (piece.defending()) {                         // verifica daca piesa apara regele
                        piece = BOARD.getRandomPiece(enginesColor);
                        noPieces--;
                        if (noPieces == 0) {                            // am ramas fara piese disponibile
                            piece = BOARD.getKing(enginesColor);        // muta regele
                        }
                    }
                }
            } else {
                piece = BOARD.getKing(enginesColor);
            }
        }
        noMoves = 0;                                             // reset the number of moves
        return castlingThisTurn;
    }

    /**
     * Implementation for "move" command.
     * When the previous command respects the specified format for a move command
     * does not necessarily mean that its engines turn. When we are in the Edit mode we
     * should consider that all moves are made by the user.
     *
     * @param previous previous command
     */
    private void move(String previous) {
        if (!isForced) {                                                        // if we are not in the edit mode
            if (checkIfEnginesTurn(previous) || checkIfPromote(previous) || previous.equals("go")) {     // if its engines' turn
                if (piece != null) {
                    if (!piece.getStartCoordinates().equals("")) {
                        if (!inChess) {
                            boolean b = piece.move();                               // move
                            while (!b) {
                                piece = BOARD.getRandomPiece(enginesColor);
                                if (piece == null) {
                                    return;
                                }
                                int noPieces = 8;
                                while (piece.defending()) {                         // verifica daca piesa apara regele
                                    piece = BOARD.getRandomPiece(enginesColor);
                                    noPieces--;
                                    if (noPieces == 0) {                            // am ramas fara piese disponibile
                                        piece = BOARD.getKing(enginesColor);        // muta regele
                                    }
                                }
                                b = piece.move();
                            }
                            cancelEnPass();                                     // if en pass was no used, cancel it
                        } else {
                            boolean b = piece.move();
                            if (!b) {
                                System.out.println("resign");
                            }
                            inChess = false;
                        }
                    }
                }
            }
        } else {
            if (checkIfEnginesTurn(previous) || checkIfPromote(previous)) {
                enginesColor = !enginesColor;
                BOARD.updateMatrix(previous);                 // update matrix with user move
                noMoves++;                                    // the number of moves made in the edit mode increases
                cancelEnPass();
            }
        }
        assert piece != null;
    }

    private void cancelEnPass() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Tile tmp = BOARD.getMatrix()[i][j];
                if (tmp.isOccupied() && tmp.getPiece().getColor() != piece.getColor()) {
                    if (tmp.getPiece() instanceof Pawn) {
                        ((Pawn) tmp.getPiece()).setEnPassL(false);
                        ((Pawn) tmp.getPiece()).setEnPassR(false);
                    }
                }
            }
        }
    }

    /**
     * Getter for enginesColor.
     *
     * @return - the value of enginesColor
     */
    public static boolean getEnginesColor() {
        return enginesColor;
    }

    /**
     * Setter for enginesColor.
     *
     * @param enginesColor -  the new value for enginesColor
     */
    public static void setEnginesColor(boolean enginesColor) {
        Parser.enginesColor = enginesColor;
    }

    public static boolean isInChess() {
        return inChess;
    }

    public static void setInChess(boolean inChess) {
        Parser.inChess = inChess;
    }

    public static String getPrevious() {
        return previous;
    }

    public static void setPrevious(String previous) {
        Parser.previous = previous;
    }
}