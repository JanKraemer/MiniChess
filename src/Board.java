import com.sun.istack.internal.Nullable;

import java.io.*;

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
    public static char[] FREELINE = {'.', '.', '.', '.', '.'};
    public static char PRAWN_BLACK = 'p';
    public static char PRAWN_WHITE = 'P';
    public static int COLUMNS = 6;
    public static int ROWS = 5;
    private char[][] squares = new char[COLUMNS][ROWS];
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
            for (int column = 0; column < squares.length; column++) {
                if (column == 0)
                    squares[column] = FIRSTLINE;
                else if (column == 1 || column == 4)
                    addPrawns(column);
                else if (column == 5)
                    squares[column] = LASTLINE;
                else
                    squares[column] = FREELINE;
            }
        } else {
            String[] lines = state.split("\n");
            if(lines.length != 7 )
                throw  new IllegalArgumentException("The String has not the correct number of lines");
            String[] firstline = lines[0].split(" ");
            movNumber = Integer.valueOf(firstline[0]);
            onMove = firstline[1].charAt(0);
            for (int line = 0; line < squares.length; line++)
                squares[line] = lines[line + 1].toCharArray();
        }
    }

    private void addPrawns(int column) {
        for (int index = 0; index < squares[column].length; index++) {
            if (column == 1)
                squares[column][index] = PRAWN_BLACK;
            if (column == 4)
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
        for (int i = 0; i < squares.length; i++) {
            builder.append(String.valueOf(squares[i]) + "\n");
        }
        return builder.toString();
    }

    public void print(Writer writer) {
        try{
            writer.write(toString());
        }catch (IOException exception){

        }

    }

    private String getFirstLineString() {
        return movNumber + " " + onMove + "\n";
    }

    public static void main(String[] args) {
        Board board = new Board();
        System.out.println(board.toString());

        board = new Board(generateTestValue());
        System.out.println(board.toString());
    }

    private static String generateTestValue(){
        String firstline = "23 B\n";
        String[] field = {"K....","b....",".k...",".....",".....","....."};
        StringBuilder builder = new StringBuilder();
        builder.append(firstline);
        for(int i = 0;i< field.length;i++)
            builder.append(field[i]+"\n");
        return builder.toString();
    }
}
