package fhws.minichess.players;

import fhws.minichess.gamecomponents.Board;
import fhws.minichess.gamecomponents.FutureMove;
import fhws.minichess.gamecomponents.Move;
import fhws.minichess.gamecomponents.StateEvaluator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

/**
 * TimedAlphaBetaPlayer
 */
public class TimeAlphaBetaPlayer extends Player {

    private int deep;
    private long time;
    private int counter;

    public TimeAlphaBetaPlayer(int deep) {
        this.deep = deep;
    }

    /**
     *  Provides the best move for this board
     *
     * @param board actual state == board
     * @return best Move
     * @throws IOException
     */
    @Override
    public Move getMove(Board board) throws IOException {
        time = System.currentTimeMillis();
        counter = 0;
        FutureMove move = getNextMove(board, 8500, -8500);
        if (client != null && move != null)
            client.send(move.getMove().toString(), false);
        return move.getMove();
    }

    /**
     * Given back the best FutureMove back with the Score and Move
     *
     * @param board actual board
     * @param beta given beta
     * @param alpha given alpha
     * @return best FutureMove with score and move
     */
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

    /**
     * AlphaBeta Algorithm which provides the best FutureMove
     *
     * @param board actual board
     * @param deep depth for the algorithm
     * @param beta beta value
     * @param alpha alpha value
     * @return best FutureMove for the board
     * @throws TimeoutException given when the time is over
     */
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

    /**
     * method to check if the time is over
     * @throws TimeoutException when the time is up
     */
    private void checkTime() throws TimeoutException {
        long times = System.currentTimeMillis();
        if (times >= (time + 7000)) {
            throw new TimeoutException();
        }
    }

    /**
     * printing the move with the actual board
     * @param board actual board
     * @param move  actual move from the player
     */
    @Override
    public void print(Board board, Move move) {
        System.out.println(move + " TimePlayer\n" + board);
    }
}
