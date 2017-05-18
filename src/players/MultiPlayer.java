package players;

import gamecomponents.Board;
import gamecomponents.FutureMove;
import gamecomponents.Move;
import gamecomponents.StateEvaluator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MultiPlayer  extends Player{

    private int deep;
    private ExecutorService service;

    public MultiPlayer(int deep){
        this.deep = deep;
        service = Executors.newFixedThreadPool(10);
    }

    @Override
    public Move getMove(Board board) throws IOException {
        List<Future> futures = new ArrayList<Future>();
        ArrayList<FutureMove> moves = new ArrayList<>();
        for(Move move: board.genMoves()){
            AlphaBetaWorker worker = new AlphaBetaWorker(new Board(board),move,deep,500,-500);
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


    @Override
    public void print(Board board, Move move) {
        System.out.println(move + " ALPHABETA\n" + board);
    }

}
