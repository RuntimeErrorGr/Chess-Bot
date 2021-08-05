package Board;

import Parser.Parser;
import Piece.*;

/**
 * color - true - white false - black
 * coordinates - e2 e4
 * isOccupied - true - occupied, false - empty
 */
public class Tile {
    private boolean color;
    private String coordinates;
    private boolean isOccupied;
    private Piece piece;

    public Tile(boolean color, String coordinates, boolean isOccupied, Piece piece) {
        this.color = color;
        this.coordinates = coordinates;
        this.isOccupied = isOccupied;
        this.piece = piece;
    }

    public static char getLetter(String coordinates) {
        return coordinates.charAt(0);
    }

    public static char getNum(String coordinates) {
        return coordinates.charAt(1);
    }

    public static int[] getMatrixCell(String coordinates) {
        int[] result = new int[2];
        result[0] = Character.getNumericValue(getNum(coordinates)) - 1;
        switch (getLetter(coordinates)) {
            case 'a':
                break;
            case 'b':
                result[1] = 1;
                break;
            case 'c':
                result[1] = 2;
                break;
            case 'd':
                result[1] = 3;
                break;
            case 'e':
                result[1] = 4;
                break;
            case 'f':
                result[1] = 5;
                break;
            case 'g':
                result[1] = 6;
                break;
            case 'h':
                result[1] = 7;
                break;
            default:
                break;
        }
        return result;
    }

    public boolean isGuarded() {

        int[] cellCoord = Tile.getMatrixCell(this.getCoordinates());
        int cellLine = cellCoord[0];
        int cellCol = cellCoord[1];
        boolean botColor = Parser.getEnginesColor();
        int i = cellLine;
        int j = cellCol;

        if (!botColor) {                    // pion negru
            if (i - 1 >= 0 && j - 1 >= 0) { // pion stanga jos
                Tile tile = Board.getInstance().getMatrix()[7 - (i - 1)][j - 1];
                if (tile.isOccupied() && tile.getPiece().getPieceType().equals("pawn")
                        && tile.getPiece().getColor() != botColor) {
                    return true;
                }
            }

            if (i - 1 >= 0 && j + 1 <= 7) { // pion dreapta jos
                Tile tile = Board.getInstance().getMatrix()[7 - (i - 1)][j + 1];
                if (tile.isOccupied() && tile.getPiece().getPieceType().equals("pawn")
                        && tile.getPiece().getColor() != botColor) {
                    return true;
                }
            }
        } else {                            // pion alb
            if (i + 1 <= 7 && j - 1 >= 0) { // pion stanga jos
                Tile tile = Board.getInstance().getMatrix()[7 - (i + 1)][j - 1];
                if (tile.isOccupied() && tile.getPiece().getPieceType().equals("pawn")
                        && tile.getPiece().getColor() != botColor) {
                    return true;
                }
            }
            if (i + 1 <= 7 && j + 1 <= 7) { // pion dreapta jos
                Tile tile = Board.getInstance().getMatrix()[7 - (i + 1)][j + 1];
                if (tile.isOccupied() && tile.getPiece().getPieceType().equals("pawn")
                        && tile.getPiece().getColor() != botColor) {
                    return true;
                }
            }
        }


        //pentru nebuni / regina
        while (i > 0 && j < 7) { //diag dreapta sus
            i--;
            j++;
            Tile tile = Board.getInstance().getMatrix()[7 - i][j];
            if (tile.isOccupied() && tile.getPiece().getColor() != botColor &&
                    (tile.getPiece().getPieceType().equals("bishop") ||
                            tile.getPiece().getPieceType().equals("queen"))) {
                return true;
            }
            if ((tile.isOccupied() && tile.getPiece().getColor() == botColor && !tile.getPiece().getPieceType().equals("king")) ||
                    (tile.isOccupied && tile.getPiece().getColor() != botColor && !tile.getPiece().getPieceType().equals("bishop")) ||
                    (tile.isOccupied && tile.getPiece().getColor() != botColor && !tile.getPiece().getPieceType().equals("queen"))) {
                break;
            }
        }
        i = cellLine;
        j = cellCol;
        while (i > 0 && j > 0) { // diag stanga sus
            i--;
            j--;
            Tile tile = Board.getInstance().getMatrix()[7 - i][j];
            if (tile.isOccupied() && tile.getPiece().getColor() != botColor &&
                    (tile.getPiece().getPieceType().equals("bishop") ||
                            tile.getPiece().getPieceType().equals("queen"))) {
                return true;
            }
            if ((tile.isOccupied() && tile.getPiece().getColor() == botColor && !tile.getPiece().getPieceType().equals("king")) ||
                    (tile.isOccupied && tile.getPiece().getColor() != botColor && !tile.getPiece().getPieceType().equals("bishop")) ||
                    (tile.isOccupied && tile.getPiece().getColor() != botColor && !tile.getPiece().getPieceType().equals("queen"))) {
                break;
            }
        }

        i = cellLine;
        j = cellCol;
        while (j > 0 && i < 7) { // diag stanga jos
            i++;
            j--;
            Tile tile = Board.getInstance().getMatrix()[7 - i][j];
            if (tile.isOccupied() && tile.getPiece().getColor() != botColor &&
                    (tile.getPiece().getPieceType().equals("bishop") ||
                            tile.getPiece().getPieceType().equals("queen"))) {
                return true;
            }
            if ((tile.isOccupied() && tile.getPiece().getColor() == botColor && !tile.getPiece().getPieceType().equals("king")) ||
                    (tile.isOccupied && tile.getPiece().getColor() != botColor && !tile.getPiece().getPieceType().equals("bishop")) ||
                    (tile.isOccupied && tile.getPiece().getColor() != botColor && !tile.getPiece().getPieceType().equals("queen"))) {
                break;
            }
        }
        i = cellLine;
        j = cellCol;
        while (i < 7 && j < 7) { // diag dreapta jos
            j++;
            i++;
            Tile tile = Board.getInstance().getMatrix()[7 - i][j];
            if (tile.isOccupied() && tile.getPiece().getColor() != botColor &&
                    (tile.getPiece().getPieceType().equals("bishop") ||
                            tile.getPiece().getPieceType().equals("queen"))) {
                return true;
            }
            if ((tile.isOccupied() && tile.getPiece().getColor() == botColor && !tile.getPiece().getPieceType().equals("king")) ||
                    (tile.isOccupied && tile.getPiece().getColor() != botColor && !tile.getPiece().getPieceType().equals("bishop")) ||
                    (tile.isOccupied && tile.getPiece().getColor() != botColor && !tile.getPiece().getPieceType().equals("queen"))) {
                break;
            }
        }

        // ture / regina
        i = cellLine;
        j = cellCol;
        while (i > 0) { //sus
            i--;
            Tile tile = Board.getInstance().getMatrix()[7 - i][j];
            if (tile.isOccupied() && tile.getPiece().getColor() != botColor &&
                    (tile.getPiece().getPieceType().equals("rook") ||
                            tile.getPiece().getPieceType().equals("queen"))) {
                return true;
            }
            if ((tile.isOccupied() && tile.getPiece().getColor() == botColor && !tile.getPiece().getPieceType().equals("king")) ||
                    (tile.isOccupied && tile.getPiece().getColor() != botColor && !tile.getPiece().getPieceType().equals("rook")) ||
                    (tile.isOccupied && tile.getPiece().getColor() != botColor && !tile.getPiece().getPieceType().equals("queen"))) {
                break;
            }
        }
        i = cellLine;
        j = cellCol;
        while (i < 7) { // jos
            i++;
            Tile tile = Board.getInstance().getMatrix()[7 - i][j];
            if (tile.isOccupied() && tile.getPiece().getColor() != botColor &&
                    (tile.getPiece().getPieceType().equals("rook") ||
                            tile.getPiece().getPieceType().equals("queen"))) {
                return true;
            }
            if ((tile.isOccupied() && tile.getPiece().getColor() == botColor && !tile.getPiece().getPieceType().equals("king")) ||
                    (tile.isOccupied && tile.getPiece().getColor() != botColor && !tile.getPiece().getPieceType().equals("rook")) ||
                    (tile.isOccupied && tile.getPiece().getColor() != botColor && !tile.getPiece().getPieceType().equals("queen"))) {
                break;
            }
        }
        i = cellLine;
        j = cellCol;
        while (j > 0) { //stanga
            j--;
            Tile tile = Board.getInstance().getMatrix()[7 - i][j];
            if (tile.isOccupied() && tile.getPiece().getColor() != botColor &&
                    (tile.getPiece().getPieceType().equals("rook") ||
                            tile.getPiece().getPieceType().equals("queen"))) {
                return true;
            }
            if ((tile.isOccupied() && tile.getPiece().getColor() == botColor && !tile.getPiece().getPieceType().equals("king")) ||
                    (tile.isOccupied && tile.getPiece().getColor() != botColor && !tile.getPiece().getPieceType().equals("rook")) ||
                    (tile.isOccupied && tile.getPiece().getColor() != botColor && !tile.getPiece().getPieceType().equals("queen"))) {
                break;
            }
        }
        i = cellLine;
        j = cellCol;
        while (j < 7) { //dreapta
            j++;
            Tile tile = Board.getInstance().getMatrix()[7 - i][j];
            if (tile.isOccupied() && tile.getPiece().getColor() != botColor &&
                    (tile.getPiece().getPieceType().equals("rook") ||
                            tile.getPiece().getPieceType().equals("queen"))) {
                return true;
            }
            if ((tile.isOccupied() && tile.getPiece().getColor() == botColor && !tile.getPiece().getPieceType().equals("king")) ||
                    (tile.isOccupied && tile.getPiece().getColor() != botColor && !tile.getPiece().getPieceType().equals("rook")) ||
                    (tile.isOccupied && tile.getPiece().getColor() != botColor && !tile.getPiece().getPieceType().equals("queen"))) {
                break;
            }
        }
        //cai
        i = cellLine;
        j = cellCol;
        if (j - 1 >= 0 && i + 2 <= 7) {
            Tile tile = Board.getInstance().getMatrix()[7 - (i + 2)][(j - 1)];
            if (tile.isOccupied() && tile.getPiece().getPieceType().equals("knight")
                    && tile.getPiece().getColor() != botColor) {
                return true;
            }
        }
        if ((j + 1) <= 7 && (i + 2) <= 7) {
            Tile tile = Board.getInstance().getMatrix()[7 - (i + 2)][(j + 1)];
            if (tile.isOccupied() && tile.getPiece().getPieceType().equals("knight")
                    && tile.getPiece().getColor() != botColor) {
                return true;
            }

        }
        if ((j - 1) >= 0 && (i - 2) >= 0) {
            Tile tile = Board.getInstance().getMatrix()[7 - (i - 2)][(j - 1)];
            if (tile.isOccupied() && tile.getPiece().getPieceType().equals("knight")
                    && tile.getPiece().getColor() != botColor) {
                return true;
            }
        }
        if ((j + 1) <= 7 && (i - 2) >= 0) {
            Tile tile = Board.getInstance().getMatrix()[7 - (i - 2)][(j + 1)];
            if (tile.isOccupied() && tile.getPiece().getPieceType().equals("knight")
                    && tile.getPiece().getColor() != botColor) {
                return true;
            }
        }
        if ((j - 2) >= 0 && (i + 1) <= 7) {
            Tile tile = Board.getInstance().getMatrix()[7 - (i + 1)][(j - 2)];
            if (tile.isOccupied() && tile.getPiece().getPieceType().equals("knight")
                    && tile.getPiece().getColor() != botColor) {
                return true;
            }
        }
        if ((j - 2) >= 0 && (i - 1) >= 0) {
            Tile tile = Board.getInstance().getMatrix()[7 - (i - 1)][(j - 2)];
            if (tile.isOccupied() && tile.getPiece().getPieceType().equals("knight")
                    && tile.getPiece().getColor() != botColor) {
                return true;
            }
        }
        if ((j + 2) <= 7 && (i + 1) <= 7) {
            Tile tile = Board.getInstance().getMatrix()[7 - (i + 1)][(j + 2)];
            if (tile.isOccupied() && tile.getPiece().getPieceType().equals("knight")
                    && tile.getPiece().getColor() != botColor) {
                return true;
            }
        }
        if ((j + 2) <= 7 && (i - 1) >= 0) {
            Tile tile = Board.getInstance().getMatrix()[7 - (i - 1)][(j + 2)];
            if (tile.isOccupied() && tile.getPiece().getPieceType().equals("knight")
                    && tile.getPiece().getColor() != botColor) {
                return true;
            }
        }

        //rege
        i = cellLine;
        j = cellCol;

        if (i > 0 && j < 7) { //diag dreapta sus
            i--;
            j++;
            Tile attTile = Board.getInstance().getMatrix()[7 - i][j];
            if (attTile.isOccupied() && attTile.getPiece().getPieceType().equals("king")
                    && attTile.getPiece().getColor() != botColor) {
                return true;
            }
        }
        i = cellLine;
        j = cellCol;

        if (i > 0 && j > 0) {// diag stanga sus
            i--;
            j--;
            Tile attTile = Board.getInstance().getMatrix()[7 - i][j];
            if (attTile.isOccupied() && attTile.getPiece().getPieceType().equals("king")
                    && attTile.getPiece().getColor() != botColor) {
                return true;
            }
        }
        i = cellLine;
        j = cellCol;

        if (i < 7 && j < 7) { //diag dreapta jos
            j++;
            i++;
            Tile attTile = Board.getInstance().getMatrix()[7 - i][j];
            if (attTile.isOccupied() && attTile.getPiece().getPieceType().equals("king")
                    && attTile.getPiece().getColor() != botColor) {
                return true;
            }
        }
        i = cellLine;
        j = cellCol;
        if (i < 7 && j > 0) { //diag stanga jos
            i++;
            j--;
            Tile attTile = Board.getInstance().getMatrix()[7 - i][j];
            if (attTile.isOccupied() && attTile.getPiece().getPieceType().equals("king")
                    && attTile.getPiece().getColor() != botColor) {
                return true;
            }
        }

        i = cellLine;
        j = cellCol;
        if (i > 0) { // sus
            i--;
            Tile attTile = Board.getInstance().getMatrix()[7 - i][j];
            if (attTile.isOccupied() && attTile.getPiece().getPieceType().equals("king")
                    && attTile.getPiece().getColor() != botColor) {
                return true;
            }
        }
        i = cellLine;
        j = cellCol;

        if (i < 7) { // jos
            i++;
            Tile attTile = Board.getInstance().getMatrix()[7 - i][j];
            if (attTile.isOccupied() && attTile.getPiece().getPieceType().equals("king")
                    && attTile.getPiece().getColor() != botColor) {
                return true;
            }
        }
        i = cellLine;
        j = cellCol;

        if (j > 0) { // stanga
            j--;
            Tile attTile = Board.getInstance().getMatrix()[7 - i][j];
            if (attTile.isOccupied() && attTile.getPiece().getPieceType().equals("king")
                    && attTile.getPiece().getColor() != botColor) {
                return true;
            }
        }
        i = cellLine;
        j = cellCol;

        if (j < 7) { //dreapta
            j++;
            Tile attTile = Board.getInstance().getMatrix()[7 - i][j];
            if (attTile.isOccupied() && attTile.getPiece().getPieceType().equals("king")
                    && attTile.getPiece().getColor() != botColor) {
                return true;
            }
        }

        return false;
    }

    public static char getTileChar(int i) {
        return (char) ('a' + i);
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public boolean isColor() {
        return color;
    }

    public void setColor(boolean color) {
        this.color = color;
    }

    public Boolean getColor() {
        return this.color;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

}