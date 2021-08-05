package Piece;

import Board.Board;
import Board.Tile;
import Parser.Parser;

import java.util.ArrayList;
import java.util.List;

/**
 * King class extends ChessPiece.
 */
public class King extends Piece {
	private boolean moved;
	/**
	 * Constructor.
	 *
	 * @param pieceType        - the type of the chess piece
	 * @param currCoordinates  - the current coordinates
	 * @param startCoordinates - the start coordinates
	 * @param color            - the color
	 */
	public King(String pieceType, String currCoordinates, String startCoordinates, boolean color) {
		super(pieceType, currCoordinates, startCoordinates, color);
		moved = false;
	}

	public List<Tile> computeEligiblePositions() {
		int[] cellCoord = Tile.getMatrixCell(this.currCoordinates);
		int cellLine = cellCoord[0];
		int cellCol = cellCoord[1];
		List<Tile> eligiblePositions = new ArrayList<>();

		if (cellLine + 1 <= 7) { // jos
			Tile temp = Board.getInstance().getMatrix()[7 - (cellLine + 1)][cellCol];
			if (!temp.isOccupied() || (temp.isOccupied() && this.getColor() != temp.getPiece().getColor())) {
				eligiblePositions.add(temp);
			}
		}
		if (cellLine - 1 >= 0) { // sus
			Tile temp = Board.getInstance().getMatrix()[7 - (cellLine - 1)][cellCol];
			if (!temp.isOccupied() || (temp.isOccupied() && this.getColor() != temp.getPiece().getColor())) {
				eligiblePositions.add(temp);
			}
		}
		if (cellCol - 1 >= 0) {// stanga
			Tile temp = Board.getInstance().getMatrix()[7 - cellLine][cellCol - 1];
			if (!temp.isOccupied() || (temp.isOccupied() && this.getColor() != temp.getPiece().getColor())) {
				eligiblePositions.add(temp);
			}
		}
		if (cellCol + 1 <= 7) {// dreapta
			Tile temp = Board.getInstance().getMatrix()[7 - cellLine][cellCol + 1];
			if (!temp.isOccupied() || (temp.isOccupied() && this.getColor() != temp.getPiece().getColor())) {
				eligiblePositions.add(temp);
			}
		}
		if (cellLine - 1 >= 0 && cellCol - 1 >= 0) { // diag stanga sus
			Tile temp = Board.getInstance().getMatrix()[7 - (cellLine - 1)][cellCol - 1];
			if (!temp.isOccupied() || (temp.isOccupied() && this.getColor() != temp.getPiece().getColor())) {
				eligiblePositions.add(temp);
			}
		}

		if (cellLine - 1 >= 0 && cellCol + 1 <= 7) { // diag dreapta sus
			Tile temp = Board.getInstance().getMatrix()[7 - (cellLine - 1)][cellCol + 1];
			if (!temp.isOccupied() || (temp.isOccupied() && this.getColor() != temp.getPiece().getColor())) {
				eligiblePositions.add(temp);
			}
		}

		if (cellLine + 1 <= 7 && cellCol + 1 <= 7) { // diag dreapta jos
			Tile temp = Board.getInstance().getMatrix()[7 - (cellLine + 1)][cellCol + 1];
			if (!temp.isOccupied() || (temp.isOccupied() && this.getColor() != temp.getPiece().getColor())) {
				eligiblePositions.add(temp);
			}
		}

		if (cellLine + 1 <= 7 && cellCol - 1 >= 0) { // diag stanga sus
			Tile temp = Board.getInstance().getMatrix()[7 - (cellLine + 1)][cellCol - 1];
			if (!temp.isOccupied() || (temp.isOccupied() && this.getColor() != temp.getPiece().getColor())) {
				eligiblePositions.add(temp);
			}
		}
		return eligiblePositions;
	}

	@Override
	public boolean givesChess() {
		int[] cellCoord = Tile.getMatrixCell(this.currCoordinates);
		int cellLine = cellCoord[0];
		int cellCol = cellCoord[1];

		int i = cellLine;
		int j = cellCol;

		if (i > 0 && j < 7) { //diag dreapta sus
			i--;
			j++;
			Tile attTile = Board.getInstance().getMatrix()[7 - i][j];
			if (attTile.isOccupied()) {
				Piece attPiece = attTile.getPiece();
				if (attPiece.getPieceType().equals("king") && this.color != attPiece.getColor()) {
					return true;
				}
			}
		}
		i = cellLine;
		j = cellCol;

		if (i > 0 && j > 0) {// diag stanga sus
			i--;
			j--;
			Tile attTile = Board.getInstance().getMatrix()[7 - i][j];
			if (attTile.isOccupied()) {
				Piece attPiece = attTile.getPiece();
				if (attPiece.getPieceType().equals("king") && this.color != attPiece.getColor()) {
					return true;
				}
			}
		}
		i = cellLine;
		j = cellCol;

		if (i < 7 && j < 7) { //diag dreapta jos
			j++;
			i++;
			Tile attTile = Board.getInstance().getMatrix()[7 - i][j];
			if (attTile.isOccupied()) {
				Piece attPiece = attTile.getPiece();
				if (attPiece.getPieceType().equals("king") && this.color != attPiece.getColor()) {
					return true;
				}
			}
		}
		i = cellLine;
		j = cellCol;
		if (i < 7 && j > 0) { //diag stanga jos
			i++;
			j--;
			Tile attTile = Board.getInstance().getMatrix()[7 - i][j];
			if (attTile.isOccupied()) {
				Piece attPiece = attTile.getPiece();
				if (attPiece.getPieceType().equals("king") && this.color != attPiece.getColor()) {
					return true;
				}
			}
		}

		i = cellLine;
		j = cellCol;
		if (i > 0) { // sus
			i--;
			Tile attTile = Board.getInstance().getMatrix()[7 - i][j];
			if (attTile.isOccupied()) {
				Piece attPiece = attTile.getPiece();
				if (attPiece.getPieceType().equals("king") && this.color != attPiece.getColor()) {
					return true;
				}
			}
		}
		i = cellLine;
		j = cellCol;

		if (i < 7) { // jos
			i++;
			Tile attTile = Board.getInstance().getMatrix()[7 - i][j];
			if (attTile.isOccupied()) {
				Piece attPiece = attTile.getPiece();
				if (attPiece.getPieceType().equals("king") && this.color != attPiece.getColor()) {
					return true;
				}
			}
		}
		i = cellLine;
		j = cellCol;

		if (j > 0) { // stanga
			j--;
			Tile attTile = Board.getInstance().getMatrix()[7 - i][j];
			if (attTile.isOccupied()) {
				Piece attPiece = attTile.getPiece();
				if (attPiece.getPieceType().equals("king") && this.color != attPiece.getColor()) {
					return true;
				}
			}
		}
		i = cellLine;
		j = cellCol;

		if (j < 7) { //dreapta
			j++;
			Tile attTile = Board.getInstance().getMatrix()[7 - i][j];
			if (attTile.isOccupied()) {
				Piece attPiece = attTile.getPiece();
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
			for (Tile pos : eligiblePositions) {
				if (!pos.isGuarded()) {
					return pos.getCoordinates();
				}
			}
		} else {
			return "";
		}
		return "";
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

	public boolean hasBeenMoved() {
		return moved;
	}

	public void setMoved(boolean moved) {
		this.moved = moved;
	}

	public void doKingSideCastling() {
		if (Parser.getEnginesColor()) {
			System.out.println("move e1g1");
		}
		else {
			System.out.println("move e8g8");
		}
	}


	public void doQueenSideCastling() {
		if (Parser.getEnginesColor()) {
			System.out.println("move e1c1");
		} else {
			System.out.println("move e8c8");
		}
	}
}
