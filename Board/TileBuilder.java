package Board;
import Piece.*;

public class TileBuilder {
    private boolean color;
    private String coordinates;
    private boolean isOccupied;
    private Piece piece;

    public TileBuilder setColor(boolean color) {
        this.color = color;
        return this;
    }

    public TileBuilder setCoordinates(String coordinates) {
        this.coordinates = coordinates;
        return this;
    }

    public TileBuilder setIsOccupied(boolean isOccupied) {
        this.isOccupied = isOccupied;
        return this;
    }

    public TileBuilder setPiece(Piece piece){
        this.piece = piece;
        return this;
    }

    public Tile build() {
        return new Tile(color, coordinates, isOccupied, piece);
    }
}