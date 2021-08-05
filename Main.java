import Board.Board;
import Parser.Parser;

public class Main {
    public static void main(String[] args) {
        Board board = Board.getInstance();
        board.initializeBoard();
        new Parser().parse();
    }
}
