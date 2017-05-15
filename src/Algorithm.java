import java.util.LinkedList;

/**
 * Copyright © 2017 Jan Krämer
 * <p>
 * [This program is licensed under the "MIT License"]
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.Copyright © 2017 Jan Krämer
 * <p>
 * [This program is licensed under the "MIT License"]
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
public class Algorithm {

    public static Board gameBoard;

    public enum Capture {
        TRUE, FALSE, ONLY
    }

    public static LinkedList<Move> moveList(Board board, int row, int column) {
        LinkedList<Move> moves = new LinkedList<>();
        gameBoard = board;
        char[][] square = board.getSquares();

        if (square[row][column] != '.') {
            if (isKingOrQueen(square[row][column])) {
                boolean stop = isKing(square[row][column]);

                moves.addAll(symmscan(row, column, 0, 1, stop, null));

                moves.addAll(symmscan(row, column, 1, 1, stop, null));

            } else if (isRookOrBishop(square[row][column])) {
                boolean stop = isBishop(square[row][column]);

                if (isRook(square[row][column])) {

                    moves.addAll(symmscan(row, column, 0, 1, stop, Capture.TRUE));

                } else {

                    moves.addAll(symmscan(row, column, 0, 1, stop, Capture.FALSE));

                }
                if (isBishop(square[row][column])) {

                    moves.addAll(symmscan(row, column, 1, 1, false, Capture.TRUE));
                }
            } else if (isKnight(square[row][column])) {

                moves.addAll(symmscan(row, column, 1, 2, true, null));

                moves.addAll(symmscan(row, column, -1, 2, true, null));

            } else if (isPrawn(square[row][column])) {
                int dir = 1;

                if (isBlack(square[row][column])) {
                    dir = -1;
                }
                moves.addAll(symmscan(row, column, -1, dir, true, Capture.ONLY));

                moves.addAll(symmscan(row, column, 1, dir, true, Capture.ONLY));

                moves.addAll(symmscan(row, column, 0, dir, true, Capture.FALSE));
            }

        }
        return moves;
    }

    private static boolean isBlack(char c) {
        return c == 'p';
    }

    private static boolean isPrawn(char c) {
        return c == 'p' || c == 'P';
    }

    private static boolean isKnight(char c) {
        return c == 'n' || c == 'N';
    }

    private static boolean isRook(char c) {
        return c == 'r' || c == 'R';
    }

    private static boolean isBishop(char c) {
        return c == 'b' || c == 'B';
    }

    private static boolean isKing(char c) {
        return c == 'k' || c == 'K';
    }

    private static boolean isRookOrBishop(char piece) {
        return piece == 'r' || piece == 'R' || piece == 'b' || piece == 'B';
    }

    private static boolean isKingOrQueen(char piece) {
        return piece == 'q' || piece == 'Q' || piece == 'k' || piece == 'K';
    }

    private static LinkedList<Move> symmscan(int x, int y, int dx, int dy, boolean stopShort, Capture capture) {
        LinkedList<Move> moves = new LinkedList<>();
        moves.addAll(scan(x, y, dx, dy, stopShort, capture));
        moves.addAll(scan(x, y, dy, -dx, stopShort, capture));
        moves.addAll(scan(x, y, -dx, -dy, stopShort, capture));
        moves.addAll(scan(x, y, -dy, dx, stopShort, capture));
        return moves;
    }

    private static LinkedList<Move> scan(int x0, int y0, int dx, int dy, boolean stopShort, Capture capture) {
        LinkedList<Move> moves = new LinkedList<>();
        int x = x0;
        int y = y0;
        char color = gameBoard.getOnMove();
        do {
            x += dx;
            y += dy;
            if (outOfBounds(x, y))
                break;
            if (isPiece(x, y)) {
                if (isSameColor(x, y, color))
                    break;
                if (capture == Capture.FALSE)
                    break;
                stopShort = true;
            } else if (capture == Capture.ONLY)
                break;
            moves.add(new Move(new Square(x0, y0), new Square(x, y))); // Tauschen von x und y fürs hinzufügen.
        } while (!stopShort);
        return moves;
    }

    private static boolean isSameColor(int x, int y, char color) {
        if (color == 'W') {
            return gameBoard.getSquares()[x][y] > 'A' || gameBoard.getSquares()[x][y] < 'Z';
        }
        return gameBoard.getSquares()[x][y] > 'a' || gameBoard.getSquares()[x][y] < 'z';
    }

    private static boolean isPiece(int x, int y) {
        return gameBoard.getSquares()[x][y] != '.';
    }

    private static boolean outOfBounds(int x, int y) {
        return (x < 0 || y < 0 || x > 5 || y > 4);
    }
}
