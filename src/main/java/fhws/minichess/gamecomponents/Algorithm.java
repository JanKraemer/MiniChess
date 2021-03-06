package fhws.minichess.gamecomponents;

import fhws.minichess.gamecomponents.Board;
import fhws.minichess.gamecomponents.Square;

import java.util.ArrayList;

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

/**
 * Provides the Algorithm to get all value Moves for the actual positon on the board.
 */
public class Algorithm {

    public static Board gameBoard;

    public enum Capture {
        TRUE, FALSE, ONLY
    }

    /**
     * Gets all possible Moves for these position.
     *
     * @param board  actual board
     * @param row    row of the piece
     * @param column column of the piece
     * @return list of all possible Moves of these piece
     */
    public static ArrayList<Move> moveList(Board board, int row, int column) {
        ArrayList<Move> moves = new ArrayList<>();
        gameBoard = board;
        char[][] square = board.getSquares();

        if (square[row][column] != '.') {
            if (isKingOrQueen(square[row][column])) {
                boolean stop = isKing(square[row][column]);

                moves.addAll(symmscan(column, row, 0, 1, stop, null));

                moves.addAll(symmscan(column, row, 1, 1, stop, null));

            } else if (isRookOrBishop(square[row][column])) {
                boolean stop = isBishop(square[row][column]);

                if (isRook(square[row][column])) {

                    moves.addAll(symmscan(column, row, 0, 1, stop, Capture.TRUE));

                } else {

                    moves.addAll(symmscan(column, row, 0, 1, stop, Capture.FALSE));

                }
                if (isBishop(square[row][column])) {

                    moves.addAll(symmscan(column, row, 1, 1, false, Capture.TRUE));
                }
            } else if (isKnight(square[row][column])) {

                moves.addAll(symmscan(column, row, 1, 2, true, null));

                moves.addAll(symmscan(column, row, -1, 2, true, null));

            } else if (isPrawn(square[row][column])) {
                int dir = 1;

                if (isBlack(square[row][column])) {
                    dir = -1;
                }
                moves.addAll(scan(column, row, -1, dir, true, Capture.ONLY));

                moves.addAll(scan(column, row, 1, dir, true, Capture.ONLY));

                moves.addAll(scan(column, row, 0, dir, true, Capture.FALSE));
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

    /**
     * Check all possible directions, if the piece can walk to it.
     *
     * @param x         actual column
     * @param y         actual row
     * @param dx        direction on column
     * @param dy        direction on row
     * @param stopShort is it short stops
     * @param capture   enum of three states
     * @return all possilbe Moves for input
     */
    private static ArrayList<Move> symmscan(int x, int y, int dx, int dy, boolean stopShort, Capture capture) {
        ArrayList<Move> moves = new ArrayList<>();
        moves.addAll(scan(x, y, dx, dy, stopShort, capture));
        moves.addAll(scan(x, y, dy, -dx, stopShort, capture));
        moves.addAll(scan(x, y, -dx, -dy, stopShort, capture));
        moves.addAll(scan(x, y, -dy, dx, stopShort, capture));
        return moves;
    }

    /**
     * Check all possible directions, if the piece can walk to it.
     *
     * @param x0        actual column position
     * @param y0        actual row position
     * @param dx        direction on column
     * @param dy        direction on row
     * @param stopShort is it short stops
     * @param capture   enum of three states
     * @return all possilbe Moves for input
     */
    private static ArrayList<Move> scan(int x0, int y0, int dx, int dy, boolean stopShort, Capture capture) {
        ArrayList<Move> moves = new ArrayList<>();
        int x = x0;
        int y = y0;
        char color = gameBoard.getOnMove();
        do {
            x += dx;
            y += dy;
            if (outOfBounds(y, x))
                break;
            if (isPiece(y, x)) {
                if (isSameColor(y, x, color))
                    break;
                if (capture == Capture.FALSE)
                    break;
                stopShort = true;
            } else if (capture == Capture.ONLY)
                break;
            moves.add(new Move(new Square(x0, y0), new Square(x, y)));
        } while (!stopShort);
        return moves;
    }

    private static boolean isSameColor(int y, int x, char color) {
        if (color == 'W') {
            return gameBoard.getSquares()[y][x] > 'A' && gameBoard.getSquares()[y][x] < 'Z';
        }
        return gameBoard.getSquares()[y][x] > 'a' && gameBoard.getSquares()[y][x] < 'z';
    }

    private static boolean isPiece(int y, int x) {
        return gameBoard.getSquares()[y][x] != '.';
    }

    private static boolean outOfBounds(int y, int x) {
        return (x < 0 || y < 0 || x > 4 || y > 5);
    }
}
