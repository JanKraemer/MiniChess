
import gamecomponents.Board;
import gamecomponents.Move;
import org.junit.Assert;
import org.junit.Test;
import players.*;

import java.io.IOException;

public class GameTest {

    @Test
    public void testHeuristicPlayer() throws IOException{
        Game game = Game
                .getInstance()
                .withoutClient()
                .setPlayers(new HeuristicPlayer(),new RandomPlayer())
                .finish();

        int[] result = game.startForRounds(100);
        Assert.assertTrue(result[0]>= 97 );
    }


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

    @Test
    public void testAlphaBetaPlayer() throws IOException{
        Game game = Game
                .getInstance()
                .withoutClient()
                .setPlayers(new AlphaBetaPlayer(4),new RandomPlayer())
                .finish();

        int[] result = game.startForRounds(50);
        Assert.assertTrue(result[0]== 50 );
    }

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
    @Test
    public void testNegaMaxPlayerDeepFourAgainTwo() throws IOException{
        Game game = Game
                .getInstance()
                .withoutClient()
                .setPlayers(new NegamaxPlayer(5),new NegamaxPlayer(2))
                .finish();

        int[] result = game.startForRounds(1);
        Assert.assertTrue(result[0]== 1 );
    }

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
    @Test
    public void testAlphaBetaPlayer1000Times() throws IOException{
        Game game = Game
                .getInstance()
                .withoutClient()
                .setPlayers(new MultiPlayer(7),new RandomPlayer())
                .finish();

        int[] result = game.startForRounds(1);
        Assert.assertTrue(result[0]>= 1 );
    }

    @Test
    public void testAlphaAgainNega() throws IOException{
        Game game = Game
                .getInstance()
                .withoutClient()
                .setPlayers(new AlphaBetaPlayer(7),new NegamaxPlayer(2))
                .finish();

        game.start();

    }

    @Test
    public void checkMoveAway() throws IOException {
        Board board = new Board(generateResqueValue());
        Player player = new AlphaBetaPlayer(7);
        Move move = player.getMove(board);
        System.out.println(move);
        Move expected = new Move("e6-e5");
        Assert.assertEquals(move.toString(),expected.toString());
    }

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
     * @return
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
