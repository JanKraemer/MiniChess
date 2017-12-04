package fhws.minichess.PlayerTests;

import fhws.minichess.gamecomponents.Board;
import fhws.minichess.gamecomponents.Move;
import org.junit.Ignore;
import org.junit.Test;
import fhws.minichess.players.AlphaBetaPlayer;
import fhws.minichess.players.MultiPlayer;

import java.io.IOException;

/**
 * testclas for the multithread player
 */
public class TestMulti {

    /**
     * print the given move from the MultiPlayer
     * @throws IOException
     */
    @Test
    public void testMultiPlayer() throws IOException{
        Board board = new Board();
        MultiPlayer player = new MultiPlayer(5);
        Move move = player.getMove(board);
        System.out.println(move);
    }

    /**
     * measuring the time for two players with the same depth
     * @throws IOException
     */
    @Test
    @Ignore
    public void measureTime() throws IOException{
         creatingaBoard();
         getMultiPlayerTime(9);
         getAlphaBetaTime(9);
    }

    /**
     * create board function
     */
    private void creatingaBoard() {
        long milis = System.currentTimeMillis();
        Board board = new Board();
        System.out.println("Board created:\t\t\t\t" + (System.currentTimeMillis() - milis));
    }

    /**
     * print the time of getMove method from the multitaskplayer
     * @param deep given depth
     * @throws IOException
     */
    private void getMultiPlayerTime(int deep) throws IOException{
        Board board = new Board();
        MultiPlayer player = new MultiPlayer(deep);
        long milis = System.currentTimeMillis();
        player.getMove(board);
        System.out.println("Multi move generated:\t\t" + (System.currentTimeMillis() - milis));
    }

    /**
     * print the time of getMove method from the alphabetaplayer
     * @param deep given depth
     * @throws IOException
     */
    private void getAlphaBetaTime(int deep) throws IOException{
        Board board = new Board();
        AlphaBetaPlayer player = new AlphaBetaPlayer(deep);
        long milis = System.currentTimeMillis();
        player.getMove(board);
        System.out.println("Multi move generated:\t\t" + (System.currentTimeMillis() - milis));
    }
}
