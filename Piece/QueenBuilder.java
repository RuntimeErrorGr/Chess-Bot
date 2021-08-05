package Piece;

public class QueenBuilder {
    private String pieceType;
    private String currCoordinates;
    private String startCoordinates;
    private boolean color;

    public QueenBuilder setPieceType(String pieceType) {
        this.pieceType = pieceType;
        return this;
    }

    public QueenBuilder setCurrCoordinates(String currCoordinates) {
        this.currCoordinates = currCoordinates;
        return this;
    }

    public QueenBuilder setStartCoordinates(String startCoordinates) {
        this.startCoordinates = startCoordinates;
        return this;
    }

    public QueenBuilder setColor(boolean color) {
        this.color = color;
        return this;
    }

    public Queen createQueen() {
        return new Queen(pieceType, currCoordinates, startCoordinates, color);
    }
}