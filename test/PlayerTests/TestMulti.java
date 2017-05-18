package PlayerTests;

import gamecomponents.Board;
import gamecomponents.Move;
import org.junit.Test;
import players.AlphaBetaPlayer;
import players.MultiPlayer;

import java.io.IOException;

public class TestMulti {

    @Test
    public void testMultiPlayer() throws IOException{
        Board board = new Board();
        MultiPlayer player = new MultiPlayer(5);
        Move move = player.getMove(board);
        System.out.println(move);
    }
    @Test
    public void measureTime() throws IOException{
         creatingaBoard();
         getMultiPlayerTime(9);
         getAlphaBetaTime(9);
    }

    private void creatingaBoard() {
        long milis = System.currentTimeMillis();
        Board board = new Board();
        System.out.println("Board created:\t\t\t\t" + (System.currentTimeMillis() - milis));
    }
    private void getMultiPlayerTime(int deep) throws IOException{
        Board board = new Board();
        MultiPlayer player = new MultiPlayer(deep);
        long milis = System.currentTimeMillis();
        player.getMove(board);
        System.out.println("Multi move generated:\t\t" + (System.currentTimeMillis() - milis));
    }

    private void getAlphaBetaTime(int deep) throws IOException{
        Board board = new Board();
        AlphaBetaPlayer player = new AlphaBetaPlayer(deep);
        long milis = System.currentTimeMillis();
        player.getMove(board);
        System.out.println("Multi move generated:\t\t" + (System.currentTimeMillis() - milis));
    }
}
