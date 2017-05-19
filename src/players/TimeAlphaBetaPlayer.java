package players;

import gamecomponents.Board;
import gamecomponents.FutureMove;
import gamecomponents.Move;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class TimeAlphaBetaPlayer extends Player{

    private int deep;
    private long time;

    public TimeAlphaBetaPlayer(int deep){
        this.deep = deep;
    }

    @Override
    public Move getMove(Board board) throws IOException {
        return null;
    }

  /*  private FutureMove getNextMove(Board board, int beta, int alpha) {
        int deep = this.deep;
        FutureMove move = alphabetanegamax(board,2,beta,alpha);
        try{
            while(true){
                checkTime();
                FutureMove next = alphabetanegamax(board,deep,beta,alpha);
                if( next.getScore() > 8500){
                    return next;
                }
                if( next.getScore() < -8500){
                    return move;
                }
                move = next;
                deep++;
            }
        }catch (TimeoutException e){
            return move;
        }*/
//}

    private void checkTime() throws TimeoutException {
        if (System.currentTimeMillis() > (time + 7E6)) {
            throw new TimeoutException();
        }
    }

    @Override
    public void print(Board board, Move move) {
        System.out.println(move + " TimePlayer\n" + board);
    }
}
