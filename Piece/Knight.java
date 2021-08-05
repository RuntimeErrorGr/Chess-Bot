package Piece;

import Board.Board;
import Board.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Knight class extends ChessPiece.
 */
public class Knight extends Piece {
    /**
     * Constructor.
     * @param pieceType - the type of the chess piece
     * @param currCoordinates - the current coordinates
     * @param startCoordinates - the start coordinates
     * @param color - the color
     */
    public Knight(String pieceType, String currCoordinates, String startCoordinates, boolean color) {
        super(pieceType, currCoordinates, startCoordinates, color);
    }

    public List<Tile> computeEligiblePositions(){
        int[] cellCoord = Tile.getMatrixCell(this.currCoordinates);
        int i = cellCoord[0];
        int j = cellCoord[1];

        List <Tile> eligiblePositions = new ArrayList<>();
        if(j - 1 >= 0 && i + 2 <= 7) {
            Tile temp = Board.getInstance().getMatrix()[7 - (i + 2)][(j - 1)];
            if(!temp.isOccupied() || (temp.isOccupied() && this.getColor() != temp.getPiece().getColor())) {
                eligiblePositions.add(temp);
            }
        }
        if((j + 1) <= 7 && (i + 2) <= 7){
            Tile temp = Board.getInstance().getMatrix()[7 -(i + 2) ][(j + 1)];
            if(!temp.isOccupied() || (temp.isOccupied() && this.getColor() != temp.getPiece().getColor())) {
                eligiblePositions.add(temp);
            }

        }
        if((j - 1) >= 0 && (i - 2) >= 0){
            Tile temp = Board.getInstance().getMatrix()[7 -(i - 2)][ (j - 1)];
            if(!temp.isOccupied() || (temp.isOccupied() && this.getColor() != temp.getPiece().getColor())) {
                eligiblePositions.add(temp);
            }
        }
        if((j + 1) <= 7 && (i - 2) >= 0){
            Tile temp = Board.getInstance().getMatrix()[7 -(i - 2)][(j + 1)];
            if(!temp.isOccupied() || (temp.isOccupied() && this.getColor() != temp.getPiece().getColor())) {
                eligiblePositions.add(temp);
            }
        }
        if((j - 2) >= 0 && (i + 1) <= 7){
            Tile temp = Board.getInstance().getMatrix()[7 - (i + 1)][ (j - 2)];
            if(!temp.isOccupied() || (temp.isOccupied() && this.getColor() != temp.getPiece().getColor())) {
                eligiblePositions.add(temp);
            }
        }
        if((j - 2) >= 0 && (i - 1) >= 0){
            Tile temp = Board.getInstance().getMatrix()[7 - (i - 1)][ (j - 2)];
            if(!temp.isOccupied() || (temp.isOccupied() && this.getColor() != temp.getPiece().getColor())) {
                eligiblePositions.add(temp);
            }
        }
        if((j + 2) <= 7 && (i + 1) <= 7){
            Tile temp = Board.getInstance().getMatrix()[7 - (i + 1)][ (j + 2)];
            if(!temp.isOccupied() || (temp.isOccupied() && this.getColor() != temp.getPiece().getColor())) {
                eligiblePositions.add(temp);
            }
        }
        if((j + 2) <= 7 && (i - 1) >= 0){
            Tile temp = Board.getInstance().getMatrix()[7 - (i - 1)][(j + 2)];
            if(!temp.isOccupied() || (temp.isOccupied() && this.getColor() != temp.getPiece().getColor())) {
                eligiblePositions.add(temp);
            }
        }

        return eligiblePositions;
    }

    public String computeMove(){
        List<Tile> eligiblePositions;
        eligiblePositions = computeEligiblePositions();
        if(eligiblePositions.size() > 0) {
            return eligiblePositions.get(new Random().nextInt(eligiblePositions.size())).getCoordinates();
        } else {
            return "";
        }
    }

    /**
     * Method used to move the Knight.
     */
    @Override
    public boolean move() {
        String initialCoord = currCoordinates;
        String finishCoord = computeMove();

        if(finishCoord.equals("")) {
            return false;
        }

        this.setCurrCoordinates(finishCoord);
        Board.getInstance().updateMatrix(initialCoord + finishCoord);    // update matrix with bot move
        System.out.println("move " + initialCoord + finishCoord);
        return true;
    }

    public boolean givesChess() {
        int[] cellCoord = Tile.getMatrixCell(this.currCoordinates);
        int i = cellCoord[0];
        int j = cellCoord[1];

        if(j - 1 >= 0 && i + 2 <= 7) {
            Tile attTile = Board.getInstance().getMatrix()[7 - (i + 2)][(j - 1)];
            if (attTile.isOccupied()) {
                Piece attPiece = attTile.getPiece();
                if (attPiece.getPieceType().equals("king") && this.color != attPiece.getColor()) {
                    return true;
                }
            }
        }
        if((j + 1) <= 7 && (i + 2) <= 7){
            Tile attTile = Board.getInstance().getMatrix()[7 - (i + 2)][(j + 1)];
            if (attTile.isOccupied()) {
                Piece attPiece = attTile.getPiece();
                if (attPiece.getPieceType().equals("king") && this.color != attPiece.getColor()) {
                    return true;
                }
            }
        }
        if((j - 1) >= 0 && (i - 2) >= 0){
            Tile attTile = Board.getInstance().getMatrix()[7 -(i - 2)][(j - 1)];
            if (attTile.isOccupied()) {
                Piece attPiece = attTile.getPiece();
                if (attPiece.getPieceType().equals("king") && this.color != attPiece.getColor()) {
                    return true;
                }
            }
        }
        if((j + 1) <= 7 && (i - 2) >= 0){
            Tile attTile = Board.getInstance().getMatrix()[7 -(i - 2)][(j + 1)];
            if (attTile.isOccupied()) {
                Piece attPiece = attTile.getPiece();
                if (attPiece.getPieceType().equals("king") && this.color != attPiece.getColor()) {
                    return true;
                }
            }
        }
        if((j - 2) >= 0 && (i + 1) <= 7){
            Tile attTile = Board.getInstance().getMatrix()[7 -(i + 1)][ (j - 2)];
            if (attTile.isOccupied()) {
                Piece attPiece = attTile.getPiece();
                if (attPiece.getPieceType().equals("king") && this.color != attPiece.getColor()) {
                    return true;
                }
            }
        }
        if((j - 2) >= 0 && (i - 1) >= 0){
            Tile attTile = Board.getInstance().getMatrix()[7 -(i - 1)][ (j - 2)];
            if (attTile.isOccupied()) {
                Piece attPiece = attTile.getPiece();
                if (attPiece.getPieceType().equals("king") && this.color != attPiece.getColor()) {
                    return true;
                }
            }
        }
        if((j + 2) <= 7 && (i + 1) <= 7){
            Tile attTile = Board.getInstance().getMatrix()[7 -(i + 1)][ (j + 2)];
            if (attTile.isOccupied()) {
                Piece attPiece = attTile.getPiece();
                if (attPiece.getPieceType().equals("king") && this.color != attPiece.getColor()) {
                    return true;
                }
            }
        }
        if((j + 2) <= 7 && (i - 1) >= 0){
            Tile attTile = Board.getInstance().getMatrix()[7 -(i - 1)][(j + 2)];
            if (attTile.isOccupied()) {
                Piece attPiece = attTile.getPiece();
                if (attPiece.getPieceType().equals("king") && this.color != attPiece.getColor()) {
                    return true;
                }
            }
        }
        return false;
    }
}