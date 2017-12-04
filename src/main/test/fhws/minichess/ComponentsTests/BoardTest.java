package fhws.minichess.ComponentsTests;

import fhws.minichess.gamecomponents.Board;
import fhws.minichess.gamecomponents.Move;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import fhws.minichess.players.AlphaBetaPlayer;
import fhws.minichess.players.NegamaxPlayer;
import fhws.minichess.players.Player;

import java.io.IOException;

import static java.lang.Math.E;

/**
 * Test the board class
 */
public class BoardTest {

    /**
     * generate a special TestValue
     *
     * @return
     */
    public static String generateTestValue() {
        String[] field = {"1 B\n",
                "6 | k | q | b | n | r | ",
                "5 | p | . | . | p | p | ",
                "4 | . | . | . | . | . | ",
                "3 | . | . | . | . | . | ",
                "2 | P | P | P | P | P | ",
                "1 | R | N | B | Q | K | ",
                "----------------------- ",
                "  | a | b | c | d | e | "};
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < field.length; i++) {
            builder.append(field[i] + "\n");
        }

        return builder.toString();
    }

    /**
     * check if the state constructor working right
     */
    @Test
    public void checkStateConstrutor(){
        String state = generateTestValue();
        Board board = new Board(state);
        Assert.assertEquals(state,board.toString());
    }


    /**
     * checking if the reroll function works fine
     */
    @Test
    public void checkReroll(){
        Board board = new Board();
        String check = board.toString();
        board.move(new Move("a2-a3"));
        board.move(new Move("a5-a4"));
        board.rerollBoard();
        board.rerollBoard();
        Assert.assertEquals(check,board.toString());
    }

    /**
     * check the needed time for some functions of the board class
     * @throws IOException
     */
    @Test
    @Ignore
    public void checkTime() throws IOException {
        double[] times = new double[3];
        for(int i = 0;i< 10;i++){
            Player player = new AlphaBetaPlayer(7);
            long milis = System.nanoTime();
            Board board = new Board();
            times[0]+=System.nanoTime()-milis;
            milis = System.nanoTime();
            board.move(player.getMove(board));
            System.out.println(System.nanoTime()-milis);
            times[1]+=System.nanoTime()-milis;
            milis = System.nanoTime();
            board.rerollBoard();
            times[2]+=System.nanoTime()-milis;
        }

        System.out.println((times[0]/10)+ "ns Konstruktor");
        System.out.println((times[1]/10)%(1E9)+ "s Make Move");
        System.out.println((times[2]/10)+ "ns Reroll Move");
    }
}
