import com.sun.istack.internal.Nullable;
import sun.plugin.javascript.navig.Link;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

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
public class Board {

    public static char[] FIRSTLINE = {'k', 'q', 'b', 'n', 'r'};
    public static char[] LASTLINE = {'R', 'N', 'B', 'Q', 'K'};
    public static char FREEPOSITION = '.';
    public static char PRAWN_BLACK = 'p';
    public static char PRAWN_WHITE = 'P';
    public static int ROWS = 6;
    public static int COLUMNS = 5;
    private char[][] squares = new char[ROWS][COLUMNS];
    private int movNumber;
    private char onMove;


    public Board() {
        movNumber = 1;
        onMove = 'W';
        initBoard(null);
    }

    public Board(String state) {
        initBoard(state);
    }

    private void initBoard(@Nullable String state) {
        if (state == null) {
            for (int row = squares.length - 1; row >= 0; row--) {
                if (row == squares.length - 1)
                    squares[row] = FIRSTLINE;
                else if (row == 1 || row == 4)
                    addPrawns(row);
                else if (row == 0)
                    squares[row] = LASTLINE;
                else
                    addFreeLine(row);
            }
        } else {
            String[] lines = state.split("\n");
            if (lines.length != 7)
                throw new IllegalArgumentException("The String has not the correct number of lines");
            String[] firstline = lines[0].split(" ");
            movNumber = Integer.valueOf(firstline[0]);
            onMove = firstline[1].charAt(0);
            for (int line = squares.length - 1; line >= 0; line--)
                squares[line] = lines[line + 1].toCharArray();
        }
    }

    private void addFreeLine(int row) {
        for (int index = 0; index < squares[row].length; index++) {
            squares[row][index] = FREEPOSITION;
        }
    }

    private void addPrawns(int column) {
        for (int index = 0; index < squares[column].length; index++) {
            if (column == 4)
                squares[column][index] = PRAWN_BLACK;
            if (column == 1)
                squares[column][index] = PRAWN_WHITE;
        }
    }

    public Board(Reader reader) {
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line = null;
        try {
            line = bufferedReader.readLine();
            String[] parts = line.split(" ");
            movNumber = Integer.getInteger(parts[0]);
            onMove = parts[1].charAt(0);
            int index = 0;
            while ((line = bufferedReader.readLine()) != null) {
                squares[index++] = line.toCharArray();
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder()
                .append(getFirstLineString());
        for (int i = squares.length - 1; i >= 0; i--) {
            builder.append(String.valueOf(squares[i]) + "\n");
        }
        return builder.toString();
    }

    public void print(Writer writer) {
        try {
            writer.write(toString());
        } catch (IOException exception) {

        }

    }

    private String getFirstLineString() {
        return movNumber + " " + onMove + "\n";
    }


    public void move(Move move) {
        if (isPieceFromActualColor(squares[move.getFrom().getRow()][move.getFrom().getCol()])) {
            char objekt = squares[move.getFrom().getRow()][move.getFrom().getCol()];
            squares[move.getFrom().getRow()][move.getFrom().getCol()] = '.';
            squares[move.getTo().getRow()][move.getTo().getCol()] = objekt;
            if (onMove == 'W') {
                onMove = 'B';
            } else {
                onMove = 'W';
                movNumber++;
            }
        } else {
            throw new IllegalArgumentException("Move is not possible!");
        }
    }

    public void move(String move) {
        Move actualMove = new Move(move);
        if (isNotFree(actualMove.getFrom()) &&
                isPieceFromActualColor(squares[actualMove.getFrom().getRow()][actualMove.getFrom().getCol()])) {
            LinkedList<Move> possibleMoves = Algorithm.moveList(this,
                    actualMove.getFrom().getRow(), actualMove.getFrom().getCol());
            if (isMovePossible(possibleMoves, actualMove)) {
                this.move(actualMove);
                return;
            }
        }
        throw new IllegalArgumentException("Move is not possible!");

    }

    private boolean isMovePossible(LinkedList<Move> possibleMoves, Move actualMove) {
        if (possibleMoves != null && !possibleMoves.isEmpty()) {
            for (Move move : possibleMoves) {
                if (move.getTo().getRow() == actualMove.getTo().getRow() &&
                        move.getTo().getCol() == actualMove.getTo().getCol())
                    return true;
            }
        }
        return false;
    }

    private boolean isNotFree(Square from) {
        return squares[from.getRow()][from.getCol()] != '.';
    }


    public LinkedList<Move> genMoves() {
        LinkedList<Move> moves = new LinkedList<>();
        for (int row = 0; row < squares.length; row++) {
            for (int column = 0; column < squares[row].length; column++) {
                moves.addAll(Algorithm.moveList(this, row, column));
            }
        }
        return moves;
    }

    private boolean isPieceFromActualColor(char c) {
        if (onMove == 'W') {
            if (c != '.' && (c > 'A' && c < 'Z'))
                return true;
        } else if (onMove == 'B') {
            if (c != '.' && (c > 'a' && c < 'z'))
                return true;
        }
        return false;
    }


    private static String generateTestValue() {
        String firstline = "23 B\n";
        String[] field = {".....", ".....", ".....", ".....", ".....", "B...."};
        StringBuilder builder = new StringBuilder();
        builder.append(firstline);
        for (int i = field.length - 1; i >= 0; i--) {
            builder.append(field[i] + "\n");
        }

        return builder.toString();
    }

    public char getOnMove() {
        return onMove;
    }

    public char[][] getSquares() {
        return squares;
    }

    public static void main(String[] args) {
        Board board = new Board();
        System.out.println(board.toString());

        board.move("a2-a3");
        System.out.println(board.toString());

        board = new Board();
        board.move(new Move(new Square(0,1),new Square(0,2)));
        System.out.println(board.toString());


        board = new Board();
        LinkedList<Move> moves = board.genMoves();
        for (Move current : moves) {
            System.out.println(current);
        }
    }
}
