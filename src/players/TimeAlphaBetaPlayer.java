package players;

import gamecomponents.Board;
import gamecomponents.Move;

import java.io.IOException;

public class TimeAlphaBetaPlayer extends Player{

    private int deep;

    public TimeAlphaBetaPlayer(int deep){
        this.deep = deep;
    }

    @Override
    public Move getMove(Board board) throws IOException {
        return null;
    }

    @Override
    public void print(Board board, Move move) {
        System.out.println(move + " TimePlayer\n" + board);
    }
}
