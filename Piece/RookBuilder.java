package Piece;

public class RookBuilder {
    private String pieceType;
    private String currCoordinates;
    private String startCoordinates;
    private boolean color;

    public RookBuilder setPieceType(String pieceType) {
        this.pieceType = pieceType;
        return this;
    }

    public RookBuilder setCurrCoordinates(String currCoordinates) {
        this.currCoordinates = currCoordinates;
        return this;
    }

    public RookBuilder setStartCoordinates(String startCoordinates) {
        this.startCoordinates = startCoordinates;
        return this;
    }

    public RookBuilder setColor(boolean color) {
        this.color = color;
        return this;
    }

    public Rook createRook() {
        return new Rook(pieceType, currCoordinates, startCoordinates, color);
    }
}