package fhws.minichess.players;

import fhws.minichess.gamecomponents.Board;
import fhws.minichess.gamecomponents.FutureMove;
import fhws.minichess.gamecomponents.Move;
import fhws.minichess.gamecomponents.StateEvaluator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * MultiPlayer for MultiThreading
 */
public class MultiPlayer  extends Player{

    private int deep;
    private ExecutorService service;

    public MultiPlayer(int deep){
        this.deep = deep;
        service = Executors.newFixedThreadPool(10);
    }

    /**
     * Getting the best move from the MultiTaskPlayer
     *
     * @param board actual state == board
     * @return best Move
     * @throws IOException
     */
    @Override
    public Move getMove(Board board) throws IOException {
        List<Future> futures = new ArrayList<Future>();
        ArrayList<FutureMove> moves = new ArrayList<>();
        for(Move move: board.genMoves()){
            AlphaBetaWorker worker = new AlphaBetaWorker(board,move,deep,500,-500);
           Future<FutureMove> f = service.submit(worker);
           futures.add(f);
        }
        try {
            for (Future<FutureMove> f : futures) {
                moves.add(f.get());
            }
        }catch (Exception e){
        }
        Collections.sort(moves, new Comparator<FutureMove>() {
            @Override
            public int compare(FutureMove o1, FutureMove o2) {
                return Integer.compare(o1.getScore(),o2.getScore());
            }
        });

        return moves.get(moves.size()-1).getMove();
    }

    /**
     * Printing the actual Move and board
     * @param board actual board
     * @param move  actual move from the player
     */
    @Override
    public void print(Board board, Move move) {
        System.out.println(move + " ALPHABETA\n" + board);
    }

}
