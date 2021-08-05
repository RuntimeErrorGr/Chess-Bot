package Piece;

public class PawnBuilder {
    private String pieceType;
    private String currCoordinates;
    private String startCoordinates;
    private boolean color;

    public PawnBuilder setPieceType(String pieceType) {
        this.pieceType = pieceType;
        return this;
    }

    public PawnBuilder setCurrCoordinates(String currCoordinates) {
        this.currCoordinates = currCoordinates;
        return this;
    }

    public PawnBuilder setStartCoordinates(String startCoordinates) {
        this.startCoordinates = startCoordinates;
        return this;
    }

    public PawnBuilder setColor(boolean color) {
        this.color = color;
        return this;
    }

    public Pawn createPawn() {
        return new Pawn(pieceType, currCoordinates, startCoordinates, color);
    }
}