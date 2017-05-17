package gamecomponents;

import com.sun.istack.internal.Nullable;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
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
public class Board {

    public static char king = 'k';
    public static char queen = 'q';
    public static char bishop = 'b';
    public static char night = 'n';
    public static char rook = 'r';
    public static char FREEPOSITION = '.';
    public static char PRAWN_BLACK = 'p';
    public static char PRAWN_WHITE = 'P';
    private HashMap<Character, ArrayList<Square>> map = new HashMap<Character, ArrayList<Square>>();
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

    public Board(Board board) {
        copyValues(board);
    }

    private void copyValues(Board board) {
        onMove = board.getOnMove();
        movNumber = board.movNumber;
        char[][] oldboard = board.getSquares();
        for (int y = 0; y < oldboard.length; y++) {
            for (int x = 0; x < oldboard[y].length; x++) {
                this.squares[y][x] = oldboard[y][x];
            }
        }
        generateMap();
    }

    /**
     * Initialise the board with / without a given state
     * if the state is null , the board will initialised with a default board.
     *
     * @param state actual state of a gamecomponents.
     */
    private void initBoard(@Nullable String state) {
        if (state == null) {
            for (int row = squares.length - 1; row >= 0; row--) {
                if (row == squares.length - 1)
                    squares[row] = makeBlackLine();
                else if (row == 1 || row == 4)
                    addPrawns(row);
                else if (row == 0)
                    squares[row] = makeWhiteLine();
                else
                    addFreeLine(row);
            }
        } else {
            String[] lines = state.split("\n");
            if (lines.length != 10)
                throw new IllegalArgumentException("The String has not the correct number of lines");
            String[] firstline = lines[0].split(" ");
            movNumber = Integer.valueOf(firstline[0]);
            onMove = firstline[1].charAt(0);
            for (int y = 0; y < squares.length; y++) {
                String[] parts = lines[y + 2].split(" | ");
                for (int x = 0; x < squares[y].length; x++) {
                    squares[ROWS - y - 1][x] = parts[2 + x * 2].charAt(0);
                }

            }
        }
        generateMap();
    }

    private char[] makeWhiteLine() {
        return new char[]{(char) (rook - 32), (char) (night - 32), (char) (bishop - 32), (char) (queen - 32), (char) (king - 32)};
    }

    private char[] makeBlackLine() {
        return new char[]{king, queen, bishop, night, rook};
    }

    /**
     * Generating the hashmap with all Squares of each side.
     * Is needed for faster searching.
     */
    private void generateMap() {
        ArrayList<Square> white = new ArrayList<>(10);
        ArrayList<Square> black = new ArrayList<>(10);

        for (int row = 0; row < ROWS; row++) {
            for (int column = 0; column < COLUMNS; column++) {
                if (isNotFree(squares[row][column])) {
                    if (squares[row][column] > 'A' && squares[row][column] <= 'Z') {
                        white.add(new Square(column, row));
                    } else {
                        black.add(new Square(column, row));
                    }
                }
            }
        }
        map.put('W', white);
        map.put('B', black);
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

    public int getMovNumber() {
        return movNumber;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder()
                .append(getFirstLineString() + "\n");
        for (int y = squares.length - 1; y >= 0; y--) {
            builder.append(y + 1 + " | ");
            for (int x = 0; x < squares[y].length; x++) {
                builder.append(squares[y][x] + " | ");
            }
            builder.append("\n");
        }
        builder.append("----------------------- \n");
        builder.append("  | a | b | c | d | e | \n");
        return builder.toString();
    }

    public HashMap<Character, ArrayList<Square>> getMap() {
        return map;
    }

    /**
     * Generate a String for the toString method to print the first Line with
     * moveNumber and actual turnColor.
     *
     * @return a string with the movenumber and the Color, who has the next turn.
     */
    private String getFirstLineString() {
        return movNumber + " " + onMove + "\n";
    }

    /**
     * Check if a piece from my color and not free.
     * If the check is ok, the board is doing the move.
     * If the check fails we threw a exception.
     * Also Changing the Color for the next move and bump the movNumber if required.
     *
     * @param move doing the move which is giving as parameter.
     */
    public char move(Move move) {
        char objekt = squares[move.getFrom().getRow()][move.getFrom().getCol()];
        char nextPosition = squares[move.getTo().getRow()][move.getTo().getCol()];
        updateOwnList(move);
        updateEnemyList(move.getTo());
        squares[move.getFrom().getRow()][move.getFrom().getCol()] = '.';
        if (onMove == 'W') {
            onMove = 'B';
        } else {
            onMove = 'W';
            movNumber++;
        }
        squares[move.getTo().getRow()][move.getTo().getCol()] = objekt;
        if (isSquareKing(nextPosition)) {
            if (onMove == 'W') {
                return 'B';
            }
            return 'W';
        }
        if (isPrawn(objekt) && isPrawnOnEdge(move.getTo().getRow())) {
            squares[move.getTo().getRow()][move.getTo().getCol()]++;
        }
        if (movNumber == 41)
            return '=';

        return '?';
    }

    /**
     * updating the oponents Arraylist of Squares.
     *
     * @param to gamecomponents.Move
     */
    private void updateEnemyList(Square to) {
        char enemy = 'B';
        if (onMove == enemy)
            enemy = 'W';
        for (Square square : map.get(enemy)) {
            if (square.getRow() == to.getRow() && square.getCol() == to.getCol()) {
                map.get(enemy).remove(square);
                return;
            }
        }
    }

    /**
     * update my Arraylist of Squares , to be always save
     *
     * @param move current move
     */
    private void updateOwnList(Move move) {
        for (Square square : map.get(onMove)) {
            if (square.getRow() == move.getFrom().getRow() && square.getCol() == move.getFrom().getCol()) {
                map.get(onMove).remove(square);
                map.get(onMove).add(move.getTo());
                return;
            }
        }
    }

    /**
     * check if it is a Prawn.
     *
     * @param objekt current piece
     * @return
     */
    private boolean isPrawn(char objekt) {
        return objekt == 'p' || objekt == 'P';
    }

    /**
     * check if it is a King..
     *
     * @param c current piece
     * @return
     */
    private boolean isSquareKing(char c) {
        return c == 'k' || c == 'K';
    }


    /**
     * check if the Prawn reaches the opponent edge
     *
     * @param row row of the prawn
     * @return
     */
    private boolean isPrawnOnEdge(int row) {
        return row == 0 || row == 5;
    }

    /**
     * Check if the move is Possible and than doing the move,
     * else we threw a Exception.
     *
     * @param move String with the next move
     */

    public char move(String move) {
        Move actualMove = new Move(move);
        if (isNotFree(actualMove.getFrom()) &&
                isPieceFromActualColor(squares[actualMove.getFrom().getRow()][actualMove.getFrom().getCol()])) {
            LinkedList<Move> possibleMoves = Algorithm.moveList(this,
                    actualMove.getFrom().getRow(), actualMove.getFrom().getCol());
            if (isMovePossible(possibleMoves, actualMove)) {
                return this.move(actualMove);
            }
        }
        throw new IllegalArgumentException("gamecomponents.Move is not possible!");

    }

    /**
     * Check if the move isPossible
     *
     * @param possibleMoves List of all possibleMoves for this piece.
     * @param actualMove    the giving move
     * @return boolean after checking the move
     */
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

    /**
     * Check if the actual position is not a free place
     *
     * @param from gamecomponents.Square with the actual position
     * @return boolean with result
     */
    private boolean isNotFree(Square from) {
        return squares[from.getRow()][from.getCol()] != '.';
    }

    private boolean isNotFree(char element) {
        return element != '.';
    }

    /**
     * Generate all possible Moves for all pieces
     *
     * @return List of all possible Pieces
     */
    public LinkedList<Move> genMoves() {
        LinkedList<Move> moves = new LinkedList<>();
        for (Square actual : map.get(onMove)) {
            moves.addAll(Algorithm.moveList(this, actual.getRow(), actual.getCol()));
        }
        return moves;
    }


    /**
     * giving a piece and check if he is on turn.
     *
     * @param c is the actual positon
     * @return boolean
     */
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

    public static int getCOLUMNS() {
        return COLUMNS;
    }



    public char getOnMove() {
        return onMove;
    }

    public char[][] getSquares() {
        return squares;
    }

}
