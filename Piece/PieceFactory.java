package Piece;

public class PieceFactory {
    private String pieceType;

    public PieceFactory(String pieceType) {
        this.pieceType = pieceType;
    }

    public String getPieceType() {
        return pieceType;
    }

    public void setPieceType(String pieceType) {
        this.pieceType = pieceType;
    }

    public Piece createPiece(String coordinates, boolean color) {
        switch (pieceType) {
            case "pawn":
                return new PawnBuilder()
                        .setPieceType("pawn")
                        .setColor(color)
                        .setStartCoordinates(coordinates)
                        .setCurrCoordinates(coordinates)
                        .createPawn();
            case "king":
                return new KingBuilder()
                        .setPieceType("king")
                        .setColor(color)
                        .setStartCoordinates(coordinates)
                        .setCurrCoordinates(coordinates)
                        .createKing();
            case "queen":
                return new QueenBuilder()
                        .setPieceType("queen")
                        .setColor(color)
                        .setStartCoordinates(coordinates)
                        .setCurrCoordinates(coordinates)
                        .createQueen();
            case "bishop":
                return new BishopBuilder()
                        .setPieceType("bishop")
                        .setColor(color)
                        .setStartCoordinates(coordinates)
                        .setCurrCoordinates(coordinates)
                        .createBishop();
            case "rook":
                return new RookBuilder()
                        .setPieceType("rook")
                        .setColor(color)
                        .setStartCoordinates(coordinates)
                        .setCurrCoordinates(coordinates)
                        .createRook();
            case "knight":
                return new KnightBuilder()
                        .setPieceType("knight")
                        .setColor(color)
                        .setStartCoordinates(coordinates)
                        .setCurrCoordinates(coordinates)
                        .createKnight();
            default:
                return null;
        }
    }
}
