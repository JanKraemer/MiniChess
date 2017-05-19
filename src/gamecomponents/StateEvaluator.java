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

/**
 * StateEvaluator with all scores
 */
public class StateEvaluator {

    static int prawn = 10;
    static int rook = 20;
    static int knight = 40;
    static int bishop = 60;
    static int queen = 90;
    static int king = 10000;
    static char actualPlayerColor;

    /**
     * validate the given board for the negamax algorithm
     *
     * @param board actual board
     * @return the score for the actual board
     */
    public static int validateState(Board board) {
        int score = 0;
        actualPlayerColor = board.getOnMove();
        char[][] squares = board.getSquares();
        for(int y = 0;y < squares.length;y++){
            for(int x = 0;x < squares[y].length;x++){
                if(squares[y][x] != '.'){
                   score = isPieceFromActualColor(squares[y][x]) ? score+getScoreFromPosition(squares[y][x]) :
                           score-getScoreFromPosition(squares[y][x]);
                }
            }
        }
        return score;
    }

    /**
     * Checking if the actual character is from the same color
     */
    private  static boolean isPieceFromActualColor(char c) {
        if (actualPlayerColor == 'W') {
            if (c != '.' && (c > 'A' && c < 'Z'))
                return true;
        } else if (actualPlayerColor == 'B') {
            if (c != '.' && (c > 'a' && c < 'z'))
                return true;
        }
        return false;
    }

    /**
     * given the score for this character back
     *
     * @param c actual piece
     * @return the value for the piece
     */
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
