package PlayerTests;

import gamecomponents.Board;
import gamecomponents.Move;
import org.junit.Assert;
import org.junit.Test;
import players.NegamaxPlayer;
import players.Player;

import java.io.IOException;

/**
 * testclass for the negamaxplayer
 */
public class TestNegaMaxPlayer {

    /**
     * check if NegamaxPlayer takes the given move for this board
     * @throws IOException
     */
    @Test
    public  void testNegamaxPlayer() throws IOException{
        Board board = new Board(generateTestValue());
        Player player = new NegamaxPlayer(3);

        Move move = player.getMove(board);
        Assert.assertEquals("b4-c2",move.toString());
    }

    /**
     * generate a board with given values
     * @return the board as string
     */
    public static String generateTestValue() {
        String[] field = {"1 B\n",
                "6 | k | q | b | . | r | ",
                "5 | p | p | p | p | p | ",
                "4 | . | n | . | . | . | ",
                "3 | . | . | . | . | . | ",
                "2 | . | . | . | . | . | ",
                "1 | R | . | . | . | K | ",
                "----------------------- ",
                "  | a | b | c | d | e | "};
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < field.length; i++) {
            builder.append(field[i] + "\n");
        }

        return builder.toString();
    }
}
