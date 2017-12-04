package fhws.minichess;

import fhws.minichess.gamecomponents.Board;
import fhws.minichess.gamecomponents.Move;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import fhws.minichess.players.*;

import java.io.IOException;

/**
 * Checking some games
 */
public class GameTest {

    /**
     * Check if the Heuristicplayer always wins again the RandomPlayer
     * @throws IOException
     */
    @Test
    public void testHeuristicPlayer() throws IOException{
        Game game = Game
                .getInstance()
                .withoutClient()
                .setPlayers(new HeuristicPlayer(),new RandomPlayer())
                .finish();

        int[] result = game.startForRounds(100);
        Assert.assertTrue(result[0]>= 95 );
    }

    /**
     * check if the NegamaxPlayer with Depth Two always wins agains the randomPlayer
     * @throws IOException
     */
    @Test
    public void testNegaMaxPlayerDeepTWO() throws IOException{
        Game game = Game
                .getInstance()
                .withoutClient()
                .setPlayers(new NegamaxPlayer(2),new RandomPlayer())
                .finish();

        int[] result = game.startForRounds(100);
        Assert.assertTrue(result[0]>= 99 );
    }

    /**
     * test the NegamaxPlayer with deoth 1 agains the randomplayer
     * @throws IOException
     */
    @Test
    public void testNegaMaxPlayerDeepONE() throws IOException{
        Game game = Game
                .getInstance()
                .withoutClient()
                .setPlayers(new NegamaxPlayer(1),new RandomPlayer())
                .finish();

        int[] result = game.startForRounds(100);
        Assert.assertTrue(result[0]>= 97 );
    }

    /**
     * Check if the AlphaBetaPlayer wins all 50 games agains the Randomplayer
     * @throws IOException
     */
    @Test
    public void testAlphaBetaPlayer() throws IOException{
        Game game = Game
                .getInstance()
                .withoutClient()
                .setPlayers(new AlphaBetaPlayer(4),new RandomPlayer())
                .finish();

        int[] result = game.startForRounds(50);
        Assert.assertTrue(result[0]>= 48 );
    }

    /**
     * check if the negamaxplayer with deep Four will always wins agains
     * the RandomPlayer
     *
     * @throws IOException
     */
    @Test
    public void testNegaMaxPlayerDeepFOUR() throws IOException{
        Game game = Game
                .getInstance()
                .withoutClient()
                .setPlayers(new NegamaxPlayer(4),new RandomPlayer())
                .finish();

        int[] result = game.startForRounds(25);
        Assert.assertTrue(result[0]== 25 );
    }

    /**
     * check if a Negamaxplayer with depth 5 will wins agaings one with depth 2
     */
    @Test
    @Ignore
    public void testNegaMaxPlayerDeepFourAgainTwo() throws IOException{
        Game game = Game
                .getInstance()
                .withoutClient()
                .setPlayers(new NegamaxPlayer(5),new NegamaxPlayer(2))
                .finish();

        int[] result = game.startForRounds(1);
        Assert.assertTrue(result[0]== 1 );
    }

    /**
     * test Negamax two agains 4
     * @throws IOException
     */
    @Test
    public void test2VS4Nega() throws IOException{
        Game game = Game
                .getInstance()
                .withoutClient()
                .setPlayers(new NegamaxPlayer(2),new NegamaxPlayer(4))
                .finish();

        int[] result = game.startForRounds(1);
        Assert.assertTrue(result[1] == 1 );

    }

    /**
     * testing multithreadplayer with depth 7 agains randomplayer
     * @throws IOException
     */
    @Test
    @Ignore
    public void testMultiAgainsRandom() throws IOException{
        Game game = Game
                .getInstance()
                .withoutClient()
                .setPlayers(new MultiPlayer(7),new RandomPlayer())
                .finish();

        int[] result = game.startForRounds(1);
        Assert.assertTrue(result[0]>= 1 );
    }

    /**
     * test AlphabetaPlayer agains the NegamaxPlayer of depth two
     * @throws IOException
     */
    @Test
    @Ignore
    public void testAlphaAgainNega() throws IOException{
        Game game = Game
                .getInstance()
                .withoutClient()
                .setPlayers(new AlphaBetaPlayer(7),new NegamaxPlayer(2))
                .finish();

        game.start();

    }

    /**
     * check how deep the TimealphaBetaPlayer will come with a depth of 7 and 7 seconds of time
     * @throws IOException
     */
    @Test
    @Ignore
    public void testTimeAlphaBeta() throws IOException{
        Game game = Game
                .getInstance()
                .withoutClient()
                .setPlayers(new TimeAlphaBetaPlayer(7),new NegamaxPlayer(2))
                .finish();

        game.start();

    }

    /**
     * checks if the AlphabetaPlayer with depth 7 will rescue himself for a lose
     * @throws IOException
     */
    @Test
    public void checkMoveAway() throws IOException {
        Board board = new Board(generateResqueValue());
        Player player = new AlphaBetaPlayer(7);
        Move move = player.getMove(board);
        System.out.println(move);
        Move expected = new Move("e6-e5");
        Assert.assertEquals(move.toString(),expected.toString());
    }
    /**
     * checks if the AlphabetaPlayer kills the enemy king with the next move
     * @throws IOException
     */
    @Test
    public void checkKillKing() throws IOException {
        Board board = new Board(generateKillValue());
        Player player = new AlphaBetaPlayer(7);
        Move move = player.getMove(board);
        System.out.println(move);
        Move expected = new Move("d4-e6");
        Assert.assertEquals(move.toString(),expected.toString());
    }
    /**
     * generate a special TestValue
     *
     * @return
     */
    public static String generateResqueValue() {
        String[] field = {"10 B\n",
                "6 | . | . | . | . | k | ",
                "5 | . | . | . | . | . | ",
                "4 | . | . | P | . | . | ",
                "3 | . | . | . | R | . | ",
                "2 | . | . | . | . | . | ",
                "1 | K | . | . | . | . | ",
                "----------------------- ",
                "  | a | b | c | d | e | "};
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < field.length; i++) {
            builder.append(field[i] + "\n");
        }

        return builder.toString();
    }

    /**
     * generate a special TestValue
     *
     * @return a String with the board
     */
    public static String generateKillValue() {
        String[] field = {"10 W\n",
                "6 | . | . | . | . | k | ",
                "5 | . | . | . | . | . | ",
                "4 | . | . | P | N | . | ",
                "3 | . | . | . | R | . | ",
                "2 | . | . | . | . | . | ",
                "1 | K | . | . | . | . | ",
                "----------------------- ",
                "  | a | b | c | d | e | "};
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < field.length; i++) {
            builder.append(field[i] + "\n");
        }

        return builder.toString();
    }
}
