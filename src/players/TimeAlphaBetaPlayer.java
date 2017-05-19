package players;

import gamecomponents.Board;
import gamecomponents.FutureMove;
import gamecomponents.Move;
import gamecomponents.StateEvaluator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

public class TimeAlphaBetaPlayer extends Player {

    private int deep;
    private long time;
    private int counter;

    public TimeAlphaBetaPlayer(int deep) {
        this.deep = deep;
    }

    @Override
    public Move getMove(Board board) throws IOException {
        time = System.currentTimeMillis();
        counter = 0;
        FutureMove move = getNextMove(new Board(board), 8500, -8500);
        if (client != null && move != null)
            client.send(move.getMove().toString(), false);
        return move.getMove();
    }

    private FutureMove getNextMove(Board board, int beta, int alpha) {
        int deep = this.deep;
        FutureMove move = null;
        try {
        move = alphabetanegamax(board, 4, beta, alpha);

            while (true) {
                FutureMove next = alphabetanegamax(board, deep, beta, alpha);
                if (next.getScore() > 8500) {
                    return next;
                }
                if (next.getScore() < -8500) {
                    return move;
                }
                move = next;
                deep++;
            }
        } catch (TimeoutException e) {
            System.out.println(deep);
            return move;
        }
    }

    private FutureMove alphabetanegamax(Board board, int deep, int beta, int alpha) throws TimeoutException {
        counter++;
        if (deep == 0)
            return new FutureMove(StateEvaluator.validateState(board), null);
        FutureMove move = new FutureMove(Integer.MIN_VALUE, null);
        ArrayList<Move> moves = board.genMoves();
        for (Move actualMove : moves) {
            if(counter == 10000){
                counter = 0;
                checkTime();
            }
            int score;
            board.move(actualMove);
            int value_ = (int) ((StateEvaluator.validateState(board) * (-1)) * 0.9);
            if (value_ > 8500 || value_ < -8500) {
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
            if (score > move.getScore()) {
                move.setScore(score);
                move.setMove(actualMove);
            }
        }
        return move;
    }

    private void checkTime() throws TimeoutException {
        long times = System.currentTimeMillis();
        if (times >= (time + 7000)) {
            throw new TimeoutException();
        }
    }

    @Override
    public void print(Board board, Move move) {
        System.out.println(move + " TimePlayer\n" + board);
    }
}
