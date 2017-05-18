package players;

import gamecomponents.Board;
import gamecomponents.FutureMove;
import gamecomponents.Move;
import gamecomponents.StateEvaluator;

import java.io.IOException;
import java.util.Random;

/**
 * Created by Jan on 18.05.2017.
 */
public class AlphaBetaPlayer extends Player {

    private int deep;

    public AlphaBetaPlayer(int deep) {
        this.deep = deep;
    }

    @Override
    public Move getMove(Board board) throws IOException {
        FutureMove move = alphabetanegamax(board, this.deep, 500, -500);
        if (client != null)
            client.send(move.getMove().toString(), false);
        return move.getMove();
    }


    private FutureMove alphabetanegamax(Board board, int deep, int beta, int alpha) {
        if (deep == 0)
            return new FutureMove(StateEvaluator.validateState(board), null);
        FutureMove move = new FutureMove(Integer.MIN_VALUE, null);
        for (Move actualMove : board.genMoves()) {
            int score;
            Board copy = new Board(board);
            copy.move(actualMove);
            int value_ = StateEvaluator.validateState(copy) * (-1);
            if (value_ > 500 || value_ < -500) {
                score = value_;
            } else {
                FutureMove next = alphabetanegamax(copy, deep - 1, (-1) * beta, (-1) * alpha);
                score = (-1) * next.getScore();
            }
            if (score > beta)
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

    private boolean randomFunction() {
        return new Random().nextInt(4) == 0 ? true : false;
    }


    @Override
    public void print(Board board, Move move) {
        System.out.println(move + " ALPHABETA\n" + board);
    }
}
