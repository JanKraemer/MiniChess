package players;

import gamecomponents.Board;
import gamecomponents.FutureMove;
import gamecomponents.Move;
import gamecomponents.StateEvaluator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class AlphaBetaPlayer extends Player {

    private int deep;

    public AlphaBetaPlayer(int deep) {
        this.deep = deep;
    }

    @Override
    public Move getMove(Board board) throws IOException {
        FutureMove move = alphabetanegamax(board, this.deep, 8500, -8500);
        if (client != null && move != null)
            client.send(move.getMove().toString(), false);
        return move.getMove();
    }


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


    @Override
    public void print(Board board, Move move) {
        System.out.println(move + " ALPHABETA\n" + board);
    }
}
