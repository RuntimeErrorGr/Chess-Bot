package Piece;

public class KnightBuilder {
    private String pieceType;
    private String currCoordinates;
    private String startCoordinates;
    private boolean color;

    public KnightBuilder setPieceType(String pieceType) {
        this.pieceType = pieceType;
        return this;
    }

    public KnightBuilder setCurrCoordinates(String currCoordinates) {
        this.currCoordinates = currCoordinates;
        return this;
    }

    public KnightBuilder setStartCoordinates(String startCoordinates) {
        this.startCoordinates = startCoordinates;
        return this;
    }

    public KnightBuilder setColor(boolean color) {
        this.color = color;
        return this;
    }

    public Knight createKnight() {
        return new Knight(pieceType, currCoordinates, startCoordinates, color);
    }
}