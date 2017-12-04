package fhws.minichess.players;

import fhws.minichess.gamecomponents.Board;
import fhws.minichess.gamecomponents.FutureMove;
import fhws.minichess.gamecomponents.Move;
import fhws.minichess.gamecomponents.StateEvaluator;

import java.util.concurrent.Callable;
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
public class AlphaBetaWorker implements Callable<FutureMove> {

    private Board board;
    private Move firstMove;
    private int deep;
    private int alpha;
    private int beta;

    public AlphaBetaWorker(Board board,Move firstMove, int deep, int beta, int alpha){
        this.alpha = alpha;
        this.board = board;
        this.firstMove = firstMove;
        this.deep = deep;
        this.beta = beta;
    }

    @Override
    public FutureMove call() throws Exception {
        return firstMove();
    }

    /**
     * first depth of the algorithm
     * @return best FutureMove
     */
    private FutureMove firstMove() {
        FutureMove move = new FutureMove(Integer.MIN_VALUE, null);
        board.move(firstMove);
        int score;
        int value_ = StateEvaluator.validateState(board) * (-1);
        if (value_ > 500 || value_ < -500) {
            score = value_;
        } else {
            FutureMove next = alphabeta(board, deep - 1, (-1) * alpha, (-1) * beta);
            score = (-1) * next.getScore();
        }
        board.rerollBoard();
        if (score > beta)
            return new FutureMove(score, firstMove);
        if (score > alpha)
            alpha = score;
        if (score > move.getScore()){
            move.setScore(score);
            move.setMove(firstMove);
        }
        return move;

    }

    /**
     * returning the best FutureMove
     * @param board actual board
     * @param deep searched deep
     * @param beta actual beta
     * @param alpha actual alpha
     * @return best Futuremove
     */
    private FutureMove alphabeta(Board board, int deep, int beta, int alpha) {
        if (deep == 0)
            return new FutureMove(StateEvaluator.validateState(board), null);
        FutureMove move = new FutureMove(Integer.MIN_VALUE, null);
        for (Move actualMove : board.genMoves()) {
            int score;
            // Board copy = new Board(board);
            // copy.move(actualMove);
            board.move(actualMove);
            int value_ = StateEvaluator.validateState(board) * (-1);
            if (value_ > 500 || value_ < -500) {
                score = value_;
            } else {
                FutureMove next = alphabeta(board, deep - 1, (-1) * alpha, (-1) * beta);
                score = (-1) * next.getScore();
            }
            board.rerollBoard();
            if (score >= beta)
                return new FutureMove(score, actualMove);
            if (score > alpha)
                alpha = score;
            if (score > move.getScore()){
                move.setScore(score);
                move.setMove(actualMove);
            }
        }
        return move;
    }
}
