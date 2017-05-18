package players;

import gamecomponents.Board;
import gamecomponents.FutureMove;
import gamecomponents.Move;
import gamecomponents.StateEvaluator;

import java.util.concurrent.Callable;

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
