package players;

import gamecomponents.Board;
import gamecomponents.FutureMove;
import gamecomponents.Move;
import gamecomponents.StateEvaluator;

import java.io.IOException;
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
 * Player with the given AlphaBeta Algorithm
 */
public class AlphaBetaPlayer extends Player {

    private int deep;

    public AlphaBetaPlayer(int deep) {
        this.deep = deep;
    }

    /**
     * returning a valid Move
     * @param board actual state == board
     * @return a valid Move
     * @throws IOException
     */
    @Override
    public Move getMove(Board board) throws IOException {
        FutureMove move = alphabetanegamax(board, this.deep, 8500, -8500);
        if (client != null && move != null)
            client.send(move.getMove().toString(), false);
        return move.getMove();
    }

    /**
     * ALphaBeta algorithm for get the best Move for the Board
     * @param board actual board
     * @param deep deep for the search
     * @param beta beta value
     * @param alpha alpha value
     * @return the best FutureMove for our side
     */
    private FutureMove alphabetanegamax(Board board, int deep, int beta, int alpha) {
        if (deep == 0)
            return new FutureMove(StateEvaluator.validateState(board), null);
        FutureMove move = new FutureMove(Integer.MIN_VALUE, null);
        ArrayList<Move> moves = board.genMoves();

        for (Move actualMove : moves) {
            int score;
            board.move(actualMove);
            int value_ = (int) ((StateEvaluator.validateState(board) * (-1))*0.9);
            if (value_ > 8500 || value_ < -8500 ) {
                score = value_;
            } else {
                FutureMove next = alphabetanegamax(board, deep - 1, (-1) * alpha, (-1) * beta);
                score = (-1) * next.getScore();
            }
            board.rerollBoard();
            if (score > beta)
                return new FutureMove(value_, actualMove);
            if (score > alpha)
                alpha = score;
            if (score > move.getScore()){
                move.setScore(score);
                move.setMove(actualMove);
            }
        }
        return move;
    }

    /**
     * Print function
     * @param board actual board
     * @param move  actual move from the player
     */
    @Override
    public void print(Board board, Move move) {
        System.out.println(move + " ALPHABETA\n" + board);
    }
}
