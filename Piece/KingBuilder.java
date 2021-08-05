package Piece;

public class KingBuilder {
    private String pieceType;
    private String currCoordinates;
    private String startCoordinates;
    private boolean color;

    public KingBuilder setPieceType(String pieceType) {
        this.pieceType = pieceType;
        return this;
    }

    public KingBuilder setCurrCoordinates(String currCoordinates) {
        this.currCoordinates = currCoordinates;
        return this;
    }

    public KingBuilder setStartCoordinates(String startCoordinates) {
        this.startCoordinates = startCoordinates;
        return this;
    }

    public KingBuilder setColor(boolean color) {
        this.color = color;
        return this;
    }

    public King createKing() {
        return new King(pieceType, currCoordinates, startCoordinates, color);
    }
}