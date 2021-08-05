package Piece;

import Board.*;
import Parser.Parser;

/**
 * ChessPiece class
 * color - the color of the chess piece
 * pieceType - the type of the chess piece
 * currCoordinates - the current coordinates of the chess piece
 * startCoordinates - the start coordinates for the chess piece
 */
public abstract class Piece {
	protected Boolean color;
	protected String pieceType;
	protected String currCoordinates;
	protected String startCoordinates;

	/**
	 * Constructor.
	 *
	 * @param pieceType        - the type of the chess piece
	 * @param currCoordinates  - the current coordinates
	 * @param startCoordinates - the start coordinates
	 * @param color            - the color
	 */

	public Piece(String pieceType, String currCoordinates, String startCoordinates, boolean color) {
		this.pieceType = pieceType;
		this.currCoordinates = currCoordinates;
		this.startCoordinates = startCoordinates;
		this.color = color;
	}

	/**
	 * Move method.
	 */
	public abstract boolean move();

	/**
	 * Getter for color attribute.
	 *
	 * @return - the color of the chess piece
	 */
	public Boolean getColor() {
		return color;
	}

	/**
	 * Setter for the color attribute.
	 *
	 * @param color - the new color
	 */

	public void setColor(Boolean color) {
		this.color = color;
	}

	/**
	 * Getter for pieceType attribute.
	 *
	 * @return - the piece type represented by a string
	 */
	public String getPieceType() {
		return pieceType;
	}

	/**
	 * Setter for the pieceType attribute.
	 *
	 * @param pieceType - the updated pieceType
	 */
	public void setPieceType(String pieceType) {
		this.pieceType = pieceType;
	}

	/**
	 * Getter for currCoordinates attribute.
	 *
	 * @return - the current coordinates represented by a string
	 */
	public String getCurrCoordinates() {
		return currCoordinates;
	}

	/**
	 * Setter for the currCoordinates attribute.
	 *
	 * @param currCoordinates - the updated coordinates
	 */
	public void setCurrCoordinates(String currCoordinates) {
		this.currCoordinates = currCoordinates;
	}

	/**
	 * Getter for startCoordinates attribute.
	 *
	 * @return - the start coordinates represented by a string
	 */
	public String getStartCoordinates() {
		return startCoordinates;
	}

	/**
	 * Setter for the startCoordinates attribute.
	 *
	 * @param startCoordinates - the updated coordinates
	 */
	public void setStartCoordinates(String startCoordinates) {
		this.startCoordinates = startCoordinates;
	}

	public abstract boolean givesChess();

	/**
	 *
	 */
	public boolean defending() {

		int[] cellCoord = Tile.getMatrixCell(currCoordinates);
		int cellLine = cellCoord[0];
		int cellCol = cellCoord[1];
		boolean botColor = Parser.getEnginesColor();
		int i = cellLine;
		int j = cellCol;

		//pentru nebuni / regina
		while (i > 0 && j < 7) { 			//diag dreapta sus
			i--;
			j++;
			Tile tile = Board.getInstance().getMatrix()[7 - i][j];
			if (tile.isOccupied() && tile.getPiece().getColor() != botColor &&
					(tile.getPiece().getPieceType().equals("bishop") || tile.getPiece().getPieceType().equals("queen"))) {
				int k = cellLine;
				int q = cellCol;
				while (q > 0 && k < 7) {	// diag stanga jos
					k++;
					q--;
					Tile auxTile = Board.getInstance().getMatrix()[7 - k][q];
					if (auxTile.isOccupied() && auxTile.getPiece().getColor() == botColor && auxTile.getPiece().getPieceType().equals("king")) {
						return true;
					}
					if (auxTile.isOccupied() && !auxTile.getPiece().getPieceType().equals("king")) {
						break;
					}
				}
			}
			if ((tile.isOccupied() && tile.getPiece().getColor() == botColor) ||
					(tile.isOccupied() && tile.getPiece().getColor() != botColor && !tile.getPiece().getPieceType().equals("bishop")) ||
					(tile.isOccupied() && tile.getPiece().getColor() != botColor && !tile.getPiece().getPieceType().equals("queen"))) {
				break;
			}
		}
		i = cellLine;
		j = cellCol;
		while (i > 0 && j > 0) { 			// diag stanga sus
			i--;
			j--;
			Tile tile = Board.getInstance().getMatrix()[7 - i][j];
			if (tile.isOccupied() && tile.getPiece().getColor() != botColor &&
					(tile.getPiece().getPieceType().equals("bishop") || tile.getPiece().getPieceType().equals("queen"))) {
				int k = cellLine;
				int q = cellCol;
				while (k < 7 && q < 7) {	// diag dreapta jos
					k++;
					q++;
					Tile auxTile = Board.getInstance().getMatrix()[7 - k][q];
					if (auxTile.isOccupied() && auxTile.getPiece().getColor() == botColor && auxTile.getPiece().getPieceType().equals("king")) {
						return true;
					}
					if (auxTile.isOccupied() && !auxTile.getPiece().getPieceType().equals("king")) {
						break;
					}
				}
			}
			if ((tile.isOccupied() && tile.getPiece().getColor() == botColor) ||
					(tile.isOccupied() && tile.getPiece().getColor() != botColor && !tile.getPiece().getPieceType().equals("bishop")) ||
					(tile.isOccupied() && tile.getPiece().getColor() != botColor && !tile.getPiece().getPieceType().equals("queen"))) {
				break;
			}
		}

		i = cellLine;
		j = cellCol;
		while (j > 0 && i < 7) { 			// diag stanga jos
			i++;
			j--;
			Tile tile = Board.getInstance().getMatrix()[7 - i][j];
			if (tile.isOccupied() && tile.getPiece().getColor() != botColor &&
					(tile.getPiece().getPieceType().equals("bishop") || tile.getPiece().getPieceType().equals("queen"))) {
				int k = cellLine;
				int q = cellCol;
				while (k > 0 && q < 7) {	// diag dreapta sus
					k--;
					q++;
					Tile auxTile = Board.getInstance().getMatrix()[7 - k][q];
					if (auxTile.isOccupied() && auxTile.getPiece().getColor() == botColor && auxTile.getPiece().getPieceType().equals("king")) {
						return true;
					}
					if (auxTile.isOccupied() && !auxTile.getPiece().getPieceType().equals("king")) {
						break;
					}
				}
			}
			if ((tile.isOccupied() && tile.getPiece().getColor() == botColor) ||
					(tile.isOccupied() && tile.getPiece().getColor() != botColor && !tile.getPiece().getPieceType().equals("bishop")) ||
					(tile.isOccupied() && tile.getPiece().getColor() != botColor && !tile.getPiece().getPieceType().equals("queen"))) {
				break;
			}
		}
		i = cellLine;
		j = cellCol;
		while (i < 7 && j < 7) { 			// diag dreapta jos
			j++;
			i++;
			Tile tile = Board.getInstance().getMatrix()[7 - i][j];
			if (tile.isOccupied() && tile.getPiece().getColor() != botColor &&
					(tile.getPiece().getPieceType().equals("bishop") || tile.getPiece().getPieceType().equals("queen"))) {
				int k = cellLine;
				int q = cellCol;
				while (k > 0 && q > 0) {	// diag stanga sus
					k--;
					q--;
					Tile auxTile = Board.getInstance().getMatrix()[7 - k][q];
					if (auxTile.isOccupied() && auxTile.getPiece().getColor() == botColor && auxTile.getPiece().getPieceType().equals("king")) {
						return true;
					}
					if (auxTile.isOccupied() && !auxTile.getPiece().getPieceType().equals("king")) {
						break;
					}
				}
			}
			if ((tile.isOccupied() && tile.getPiece().getColor() == botColor) ||
					(tile.isOccupied() && tile.getPiece().getColor() != botColor && !tile.getPiece().getPieceType().equals("bishop")) ||
					(tile.isOccupied() && tile.getPiece().getColor() != botColor && !tile.getPiece().getPieceType().equals("queen"))) {
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
					(tile.getPiece().getPieceType().equals("rook") || tile.getPiece().getPieceType().equals("queen"))) {
				int k = cellLine;
				int q = cellCol;
				while (k < 7) {	// jos
					k++;
					Tile auxTile = Board.getInstance().getMatrix()[7 - k][q];
					if (auxTile.isOccupied() && auxTile.getPiece().getColor() == botColor && auxTile.getPiece().getPieceType().equals("king")) {
						return true;
					}
					if (auxTile.isOccupied() && !auxTile.getPiece().getPieceType().equals("king")) {
						break;
					}
				}
			}
			if ((tile.isOccupied() && tile.getPiece().getColor() == botColor) ||
					(tile.isOccupied() && tile.getPiece().getColor() != botColor && !tile.getPiece().getPieceType().equals("rook")) ||
					(tile.isOccupied() && tile.getPiece().getColor() != botColor && !tile.getPiece().getPieceType().equals("queen"))) {
				break;
			}
		}
		i = cellLine;
		j = cellCol;
		while (i < 7) { // jos
			i++;
			Tile tile = Board.getInstance().getMatrix()[7 - i][j];
			if (tile.isOccupied() && tile.getPiece().getColor() != botColor &&
					(tile.getPiece().getPieceType().equals("rook") || tile.getPiece().getPieceType().equals("queen"))) {
				int k = cellLine;
				int q = cellCol;
				while (k > 0) {	// sus
					k--;
					Tile auxTile = Board.getInstance().getMatrix()[7 - k][q];
					if (auxTile.isOccupied() && auxTile.getPiece().getColor() == botColor && auxTile.getPiece().getPieceType().equals("king")) {
						return true;
					}
					if (auxTile.isOccupied() && !auxTile.getPiece().getPieceType().equals("king")) {
						break;
					}
				}
			}
			if ((tile.isOccupied() && tile.getPiece().getColor() == botColor) ||
					(tile.isOccupied() && tile.getPiece().getColor() != botColor && !tile.getPiece().getPieceType().equals("rook")) ||
					(tile.isOccupied() && tile.getPiece().getColor() != botColor && !tile.getPiece().getPieceType().equals("queen"))) {
				break;
			}
		}
		i = cellLine;
		j = cellCol;
		while (j > 0) { //stanga
			j--;
			Tile tile = Board.getInstance().getMatrix()[7 - i][j];
			if (tile.isOccupied() && tile.getPiece().getColor() != botColor &&
					(tile.getPiece().getPieceType().equals("rook") || tile.getPiece().getPieceType().equals("queen"))) {
				int k = cellLine;
				int q = cellCol;
				while (q < 7) {	// dreapta
					q++;
					Tile auxTile = Board.getInstance().getMatrix()[7 - k][q];
					if (auxTile.isOccupied() && auxTile.getPiece().getColor() == botColor && auxTile.getPiece().getPieceType().equals("king")) {
						return true;
					}
					if (auxTile.isOccupied() && !auxTile.getPiece().getPieceType().equals("king")) {
						break;
					}
				}
			}
			if ((tile.isOccupied() && tile.getPiece().getColor() == botColor) ||
					(tile.isOccupied() && tile.getPiece().getColor() != botColor && !tile.getPiece().getPieceType().equals("rook")) ||
					(tile.isOccupied() && tile.getPiece().getColor() != botColor && !tile.getPiece().getPieceType().equals("queen"))) {
				break;
			}
		}
		i = cellLine;
		j = cellCol;
		while (j < 7) { //dreapta
			j++;
			Tile tile = Board.getInstance().getMatrix()[7 - i][j];
			if (tile.isOccupied() && tile.getPiece().getColor() != botColor &&
					(tile.getPiece().getPieceType().equals("rook") || tile.getPiece().getPieceType().equals("queen"))) {
				int k = cellLine;
				int q = cellCol;
				while (q > 0) {	// stanga
					q--;
					Tile auxTile = Board.getInstance().getMatrix()[7 - k][q];
					if (auxTile.isOccupied() && auxTile.getPiece().getColor() == botColor && auxTile.getPiece().getPieceType().equals("king")) {
						return true;
					}
					if (auxTile.isOccupied() && !auxTile.getPiece().getPieceType().equals("king")) {
						break;
					}
				}
			}
			if ((tile.isOccupied() && tile.getPiece().getColor() == botColor) ||
					(tile.isOccupied() && tile.getPiece().getColor() != botColor && !tile.getPiece().getPieceType().equals("rook")) ||
					(tile.isOccupied() && tile.getPiece().getColor() != botColor && !tile.getPiece().getPieceType().equals("queen"))) {
				break;
			}
		}
		return false;
	}

	public abstract String computeMove();
}