package Piece;

import Parser.*;
import Board.*;

/**
 * Pawn class extends Piece
 */
public class Pawn extends Piece {
	private boolean firstMove;          // true -> 0 moves; false -> at least 1 move
	private boolean enPassR;            // true -> 1 move, 2 tiles; false -> or 0 moves, or 1 move, 1 tile, or more than 1 move
	private boolean enPassL;            // en pass from left

	/**
	 * Constructor.
	 *
	 * @param pieceType        - the type of the chess piece
	 * @param currCoordinates  - the current coordinates
	 * @param startCoordinates - the start coordinates
	 * @param color            - the color
	 */
	public Pawn(String pieceType, String currCoordinates, String startCoordinates, boolean color) {
		super(pieceType, currCoordinates, startCoordinates, color);
		this.firstMove = true;
		this.enPassR = false;
		this.enPassL = false;
	}

	/**
	 * Method used to move a black pawn on the chess board.
	 *
	 * @param letter - the orizontal coordinate, represented by a letter
	 * @param number - the vertical coordinate, represented by a digit
	 * @return the final coordinates for the pawn
	 */
	private String moveBlack(char letter, char number) {
		boolean conducted = false;                                              // already moved
		int currentNumber = Integer.parseInt(this.currCoordinates.substring(1));
		Board board = Board.getInstance();
		Tile front = board.findTileOnBoard(letter + String.valueOf(Character.getNumericValue(number) - 1));
		Tile frontfront = board.findTileOnBoard(letter + String.valueOf(Character.getNumericValue(number) - 2));
		Tile leftFront = board.findTileOnBoard((char) (letter - 1) + String.valueOf(Character.getNumericValue(number) - 1));
		Tile rightFront = board.findTileOnBoard((char) (letter + 1) + String.valueOf(Character.getNumericValue(number) - 1));
		Tile left = board.findTileOnBoard((char) (letter - 1) + String.valueOf(Character.getNumericValue(number)));
		Tile right = board.findTileOnBoard((char) (letter + 1) + String.valueOf(Character.getNumericValue(number)));

		if (left != null && currentNumber == 4) {
			if (left.isOccupied() && !leftFront.isOccupied() && left.getPiece().getColor() != color) {
				if (left.getPiece() instanceof Pawn) {
					if (((Pawn) left.getPiece()).eligibleEnPassL()) {
						conducted = true;
						letter = (char) (letter - 1);
						number -= 1;
						this.setEnPassR(false);
						Board.removeWhite(left.getPiece());
						left.setPiece(null);
						left.setOccupied(false);
					}
				}
			}
		} else if (right != null && currentNumber == 4) {
			if (right.isOccupied() && !rightFront.isOccupied() && right.getPiece().getColor() != color) {
				if (right.getPiece() instanceof Pawn) {
					if (((Pawn) right.getPiece()).eligibleEnPassR()) {
						conducted = true;
						letter = (char) (letter + 1);
						number -= 1;
						this.setEnPassR(false);
						Board.removeWhite(right.getPiece());
						right.setPiece(null);
						right.setOccupied(false);
					}
				}
			}
		}
		if (!conducted && leftFront != null && rightFront != null) {
			if (leftFront.isOccupied() && leftFront.getPiece().getColor() != color) {
				letter = (char) (letter - 1);
				number -= 1;
				this.setEnPassR(false);
			} else if (rightFront.isOccupied() && rightFront.getPiece().getColor() != color) {
				letter = (char) (letter + 1);
				number -= 1;
				this.setEnPassR(false);
			} else {
				if (isFirstMove() && !front.isOccupied() && !frontfront.isOccupied()) {
					number -= 2;
					this.setFirstMove(false);
					this.setEnPassR(true);
				} else if (!front.isOccupied()) {
					number -= 1;
					this.setEnPassR(false);
				}
			}
		} else if (!conducted && leftFront == null) {
			if (rightFront.isOccupied() && rightFront.getPiece().getColor() != color) {
				letter = (char) (letter + 1);
				number -= 1;
				this.setEnPassR(false);
			} else {
				if (isFirstMove() && !front.isOccupied() && !frontfront.isOccupied()) {
					number -= 2;
					this.setFirstMove(false);
					this.setEnPassR(true);
				} else if (!front.isOccupied()) {
					number -= 1;
					this.setEnPassR(false);
				}
			}
		} else if (!conducted) {
			if (leftFront.isOccupied() && leftFront.getPiece().getColor() != color) {
				letter = (char) (letter - 1);
				number -= 1;
				this.setEnPassR(false);
			} else {
				if (isFirstMove() && !front.isOccupied() && !frontfront.isOccupied()) {
					number -= 2;
					this.setFirstMove(false);
					this.setEnPassR(true);
				} else if (!front.isOccupied()) {
					number -= 1;
					this.setEnPassR(false);
				}
			}
		}
		return String.valueOf(letter) + number;
	}

	/**
	 * Method used to move a white pawn on the chess board.
	 *
	 * @param letter - the orizontal coordinate, represented by a letter
	 * @param number - the vertical coordinate, represented by a digit
	 * @return the final coordinates for the pawn
	 */
	private String moveWhite(char letter, char number) {
		boolean conducted = false;
		int currentNumber = Integer.parseInt(this.currCoordinates.substring(1));
		Board board = Board.getInstance();
		Tile front = board.findTileOnBoard(letter + String.valueOf(Character.getNumericValue(number) + 1));
		Tile frontfront = board.findTileOnBoard(letter + String.valueOf(Character.getNumericValue(number) + 2));
		Tile leftFront = board.findTileOnBoard((char) (letter + 1) + String.valueOf(Character.getNumericValue(number) + 1));
		Tile rightFront = board.findTileOnBoard((char) (letter - 1) + String.valueOf(Character.getNumericValue(number) + 1));
		Tile left = board.findTileOnBoard((char) (letter + 1) + String.valueOf(Character.getNumericValue(number)));
		Tile right = board.findTileOnBoard((char) (letter - 1) + String.valueOf(Character.getNumericValue(number)));

		if (left != null && currentNumber == 5) {
			if (left.isOccupied() && !leftFront.isOccupied() && left.getPiece().getColor() != color) {
				if (left.getPiece() instanceof Pawn) {
					if (((Pawn) left.getPiece()).eligibleEnPassL()) {
						conducted = true;
						letter = (char) (letter + 1);
						number += 1;
						this.setEnPassL(false);
						Board.removeBlack(left.getPiece());
						left.setPiece(null);
						left.setOccupied(false);
					}
				}
			}
		} else if (right != null && currentNumber == 5) {
			if (right.isOccupied() && !rightFront.isOccupied() && right.getPiece().getColor() != color) {
				if (right.getPiece() instanceof Pawn) {
					if (((Pawn) right.getPiece()).eligibleEnPassR()) {
						conducted = true;
						letter = (char) (letter - 1);
						number += 1;
						this.setEnPassR(false);
						Board.removeBlack(right.getPiece());
						right.setPiece(null);
						right.setOccupied(false);
					}
				}
			}
		}
		if (!conducted && leftFront != null && rightFront != null) {
			if (leftFront.isOccupied() && leftFront.getPiece().getColor() != color) {
				letter = (char) (letter + 1);
				number += 1;
				this.setEnPassR(false);
			} else if (rightFront.isOccupied() && rightFront.getPiece().getColor() != color) {
				letter = (char) (letter - 1);
				number += 1;
				this.setEnPassR(false);
			} else {
				if (isFirstMove() && !front.isOccupied() && !frontfront.isOccupied()) {
					number += 2;
					this.setFirstMove(false);
					this.setEnPassR(true);
				} else if (!front.isOccupied()) {
					number += 1;
					this.setEnPassR(false);
				}
			}
		} else if (!conducted && leftFront == null) {
			if (rightFront.isOccupied() && rightFront.getPiece().getColor() != color) {
				letter = (char) (letter - 1);
				number += 1;
				this.setEnPassR(false);
			} else {
				if (isFirstMove() && !front.isOccupied() && !frontfront.isOccupied()) {
					number += 2;
					this.setFirstMove(false);
					this.setEnPassR(true);
				} else if (!front.isOccupied()) {
					number += 1;
					this.setEnPassR(false);
				}
			}
		} else if (!conducted) {
			if (leftFront.isOccupied() && leftFront.getPiece().getColor() != color) {
				letter = (char) (letter + 1);
				number += 1;
				this.setEnPassR(false);
			} else {
				if (isFirstMove() && !front.isOccupied() && !frontfront.isOccupied()) {
					number += 2;
					this.setFirstMove(false);
					this.setEnPassR(true);
				} else if (!front.isOccupied()) {
					number += 1;
					this.setEnPassR(false);
				}
			}
		}
		return String.valueOf(letter) + number;
	}


	/**
	 * Method used to compute the final coordinates obtained by moving a pawn.
	 *
	 * @return string that contains the initial and final coordinates, concatenated
	 */
	public String computeMove() {

		String initialCoord = currCoordinates;
		char letter = Tile.getLetter(initialCoord);
		char number = Tile.getNum(initialCoord);

		String finishCoord;
		if (!Parser.getEnginesColor()) {
			finishCoord = moveBlack(letter, number);
		} else {
			finishCoord = moveWhite(letter, number);
		}

		this.setCurrCoordinates(finishCoord);                                      // update piece coord

		Board.getInstance().updateMatrix(initialCoord + finishCoord);    // update matrix with bot move
		return initialCoord + finishCoord;                                         // bot move
	}

	/**
	 * Method used to move the pawn. In case we are left with no moves, we resign, otherwise
	 * we will send to xboard a move command.
	 */
	@Override
	public boolean move() {
		String move = computeMove();
		if (move.substring(0, 2).equals(move.substring(2))) {
			return false;
		} else {
			System.out.println("move " + move);
			return true;
		}
	}

	public boolean givesChess() {
		int[] cellCoord = Tile.getMatrixCell(this.currCoordinates);
		int cellLine = cellCoord[0];
		int cellCol = cellCoord[1];

		int i = cellLine;
		int j = cellCol;

		if (!Parser.getEnginesColor()) {
			if (i + 1 <= 7 && j - 1 >= 0) { // pion stanga jos
				Tile attTile = Board.getInstance().getMatrix()[7 - (i + 1)][j - 1];
				if (attTile.isOccupied() && attTile.getPiece().getPieceType().equals("king")
						&& this.color != attTile.getPiece().getColor()) {
					return true;
				}
			}
			if (i + 1 <= 7 && j + 1 <= 7) { // pion dreapta jos
				Tile attTile = Board.getInstance().getMatrix()[7 - (i + 1)][j + 1];
				if (attTile.isOccupied() && attTile.getPiece().getPieceType().equals("king")
						&& this.color != attTile.getPiece().getColor()) {
					return true;
				}
			}
		}
		if (Parser.getEnginesColor()) {
			if (i - 1 >= 0 && j - 1 >= 0) { // pion stanga jos
				Tile attTile = Board.getInstance().getMatrix()[7 - (i - 1)][j - 1];
				if (attTile.isOccupied() && attTile.getPiece().getPieceType().equals("king")
						&& this.color != attTile.getPiece().getColor()) {
					return true;
				}
			}
			if (i - 1 >= 0 && j + 1 <= 7) { // pion dreapta jos
				Tile attTile = Board.getInstance().getMatrix()[7 - (i - 1)][j + 1];
				if (attTile.isOccupied() && attTile.getPiece().getPieceType().equals("king")
						&& this.color != attTile.getPiece().getColor()) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Getter for isFirstMove.
	 *
	 * @return the value of the attribute.
	 */
	public boolean isFirstMove() {
		return firstMove;
	}

	/**
	 * Setter for isFirstMove.
	 *
	 * @param first - the new value for our attribute
	 */
	public void setFirstMove(boolean first) {
		this.firstMove = first;
	}

	public boolean eligibleEnPassR() {
		return enPassR;
	}

	public void setEnPassR(boolean enPass) {
		this.enPassR = enPass;
	}

	public boolean eligibleEnPassL() {
		return enPassL;
	}

	public void setEnPassL(boolean enPassL) {
		this.enPassL = enPassL;
	}
}