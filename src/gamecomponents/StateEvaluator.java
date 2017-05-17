package gamecomponents;

import gamecomponents.Square;

import java.util.ArrayList;
import java.util.Map;

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

public class StateEvaluator {

    static int prawn = 1;
    static int rook = 2;
    static int knight = 4;
    static int bishop = 6;
    static int queen = 9;
    static int king = 1000;

    public static int validateState(Board board) {
        Map<Character, ArrayList<Square>> pieces = board.getMap();
        char actualPlayerColor = board.getOnMove();
        char nextPlayerColor = 'W';
        if (actualPlayerColor == nextPlayerColor)
            nextPlayerColor = 'B';
        int actualPlayerScore = calcScore(board.getSquares(), pieces.get(actualPlayerColor));
        int nextPlayScore = calcScore(board.getSquares(), pieces.get(nextPlayerColor));
        return actualPlayerScore - nextPlayScore;
    }

    private static int calcScore(char[][] squares, ArrayList<Square> allPostitions) {
        int score = 0;
        for (Square square : allPostitions) {
            score += getScoreFromPosition(squares[square.getRow()][square.getCol()]);
        }
        return score;
    }

    private static int getScoreFromPosition(char c) {
        if( c == 'p' || c == 'P')
            return prawn;
        else if( c == 'r' || c == 'R')
            return rook;
        else if( c == 'n' || c == 'N')
            return knight;
        else if ( c == 'b' || c == 'B')
            return bishop;
        else if (c == 'k' || c == 'K')
            return king;
        else if ( c == 'q' || c == 'Q')
            return queen;

        return 0;
    }

}
