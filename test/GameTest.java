
import org.junit.Assert;
import org.junit.Test;
import players.HeuristicPlayer;
import players.NegamaxPlayer;
import players.RandomPlayer;

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
    public void testNegaMaxPlayerDeepFOUR() throws IOException{
        Game game = Game
                .getInstance()
                .withoutClient()
                .setPlayers(new NegamaxPlayer(4),new HeuristicPlayer())
                .finish();

        int[] result = game.startForRounds(25);
        Assert.assertTrue(result[0]== 25 );
    }
    @Test
    public void testNegaMaxPlayerDeepFourAgainTwo() throws IOException{
        Game game = Game
                .getInstance()
                .withoutClient()
                .setPlayers(new NegamaxPlayer(2),new NegamaxPlayer(5))
                .finish();

        int[] result = game.startForRounds(10);
        System.out.println(result[0]);
        System.out.println(result[1]);
        System.out.println(result[2]);

    }

    @Test
    public void test2VS4Nega() throws IOException{
        Game game = Game
                .getInstance()
                .withoutClient()
                .setPlayers(new RandomPlayer(),new NegamaxPlayer(6))
                .finish();

        int[] result = game.startForRounds(1);
        System.out.println(result[0]);
        System.out.println(result[1]);
        System.out.println(result[2]);

    }
}
