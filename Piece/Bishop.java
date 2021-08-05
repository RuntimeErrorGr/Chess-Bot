package Piece;

import Board.Board;
import Board.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Bishop class extends Piece
 */
public class Bishop extends Piece {
	/**
	 * Constructor.
	 *
	 * @param pieceType        - the type of the chess piece
	 * @param currCoordinates  - the current coordinates
	 * @param startCoordinates - the start coordinates
	 * @param color            - the color
	 */
	public Bishop(String pieceType, String currCoordinates, String startCoordinates, boolean color) {
		super(pieceType, currCoordinates, startCoordinates, color);
	}

	public List<Tile> computeEligiblePositions() {
		int[] cellCoord = Tile.getMatrixCell(this.currCoordinates);
		int cellLine = cellCoord[0];
		int cellCol = cellCoord[1];
		List<Tile> eligiblePositions = new ArrayList<>();

		int i = cellLine;
		int j = cellCol;

		while (i > 0 && j < 7) { //diag dreapta sus
			i--;
			j++;
			Tile temp = Board.getInstance().getMatrix()[7 - i][j];
			if (!temp.isOccupied()) {
				eligiblePositions.add(temp);
			}
			if (temp.isOccupied() && this.color != temp.getPiece().getColor()) {
				eligiblePositions.add(temp);
				break;
			}
			if (temp.isOccupied() && this.color == temp.getPiece().getColor()) {
				break;
			}

		}
		i = cellLine;
		j = cellCol;

		while (i > 0 && j > 0) {// diag stanga sus
			i--;
			j--;
			Tile temp = Board.getInstance().getMatrix()[7 - i][j];
			if (!temp.isOccupied()) {
				eligiblePositions.add(temp);
			}
			if (temp.isOccupied() && this.color != temp.getPiece().getColor()) {
				eligiblePositions.add(temp);
				break;
			}
			if (temp.isOccupied() && this.color == temp.getPiece().getColor()) {
				break;
			}
		}
		i = cellLine;
		j = cellCol;

		while (i < 7 && j < 7) { //diag dreapta jos
			j++;
			i++;
			Tile temp = Board.getInstance().getMatrix()[7 - i][j];
			if (!temp.isOccupied()) {
				eligiblePositions.add(temp);
			}
			if (temp.isOccupied() && this.color != temp.getPiece().getColor()) {
				eligiblePositions.add(temp);
				break;
			}
			if (temp.isOccupied() && this.color == temp.getPiece().getColor()) {
				break;
			}
		}
		i = cellLine;
		j = cellCol;
		while (i < 7 && j > 0) { //diag stanga jos
			i++;
			j--;
			Tile temp = Board.getInstance().getMatrix()[7 - i][j];
			if (!temp.isOccupied()) {
				eligiblePositions.add(temp);
			}
			if (temp.isOccupied() && this.color != temp.getPiece().getColor()) {
				eligiblePositions.add(temp);
				break;
			}
			if (temp.isOccupied() && this.color == temp.getPiece().getColor()) {
				break;
			}
		}
		return eligiblePositions;
	}

	public boolean givesChess() {
		int[] cellCoord = Tile.getMatrixCell(this.currCoordinates);
		int cellLine = cellCoord[0];
		int cellCol = cellCoord[1];

		int i = cellLine;
		int j = cellCol;

		while (i > 0 && j < 7) { //diag dreapta sus
			i--;
			j++;
			Tile attTile = Board.getInstance().getMatrix()[7 - i][j];
			if (attTile.isOccupied()) {
				Piece attPiece = Board.getInstance().getMatrix()[7 - i][j].getPiece();
				if (!attTile.getPiece().getPieceType().equals("king")) {
					break;
				}
				if (attPiece.getPieceType().equals("king") && this.color != attPiece.getColor()) {
					return true;
				}
			}
		}
		i = cellLine;
		j = cellCol;
		while (i > 0 && j > 0) {// diag stanga sus
			i--;
			j--;
			Tile attTile = Board.getInstance().getMatrix()[7 - i][j];
			if (attTile.isOccupied()) {
				Piece attPiece = Board.getInstance().getMatrix()[7 - i][j].getPiece();
				if (!attTile.getPiece().getPieceType().equals("king")) {
					break;
				}
				if (attPiece.getPieceType().equals("king") && this.color != attPiece.getColor()) {
					return true;
				}
			}
		}
		i = cellLine;
		j = cellCol;
		while (i < 7 && j < 7) { //diag dreapta jos
			j++;
			i++;
			Tile attTile = Board.getInstance().getMatrix()[7 - i][j];
			if (attTile.isOccupied()) {
				Piece attPiece = Board.getInstance().getMatrix()[7 - i][j].getPiece();
				if (!attTile.getPiece().getPieceType().equals("king")) {
					break;
				}
				if (attPiece.getPieceType().equals("king") && this.color != attPiece.getColor()) {
					return true;
				}
			}
		}
		i = cellLine;
		j = cellCol;
		while (i < 7 && j > 0) { //diag stanga jos
			i++;
			j--;
			Tile attTile = Board.getInstance().getMatrix()[7 - i][j];
			if (attTile.isOccupied()) {
				Piece attPiece = Board.getInstance().getMatrix()[7 - i][j].getPiece();
				if (!attTile.getPiece().getPieceType().equals("king")) {
					break;
				}
				if (attPiece.getPieceType().equals("king") && this.color != attPiece.getColor()) {
					return true;
				}
			}
		}
		return false;
	}

	public String computeMove() {
		List<Tile> eligiblePositions;
		eligiblePositions = computeEligiblePositions();
		if (eligiblePositions.size() > 0) {
			return eligiblePositions.get(new Random().nextInt(eligiblePositions.size())).getCoordinates();
		} else {
			return "";
		}
	}

	/**
	 * Method used to move the bishop.
	 */
	@Override
	public boolean move() {
		String initialCoord = currCoordinates;
		String finishCoord = computeMove();

		if (finishCoord.equals("")) {
			return false;
		}

		this.setCurrCoordinates(finishCoord);
		Board.getInstance().updateMatrix(initialCoord + finishCoord);    // update matrix with bot move
		System.out.println("move " + initialCoord + finishCoord);
		return true;
	}
}
