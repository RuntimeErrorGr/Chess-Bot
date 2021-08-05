package Piece;

public class BishopBuilder {
    private String pieceType;
    private String currCoordinates;
    private String startCoordinates;
    private boolean color;

    public BishopBuilder setPieceType(String pieceType) {
        this.pieceType = pieceType;
        return this;
    }

    public BishopBuilder setCurrCoordinates(String currCoordinates) {
        this.currCoordinates = currCoordinates;
        return this;
    }

    public BishopBuilder setStartCoordinates(String startCoordinates) {
        this.startCoordinates = startCoordinates;
        return this;
    }

    public BishopBuilder setColor(boolean color) {
        this.color = color;
        return this;
    }

    public Bishop createBishop() {
        return new Bishop(pieceType, currCoordinates, startCoordinates, color);
    }
}