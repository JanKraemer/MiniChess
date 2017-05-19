package ComponentsTests;

import com.sun.javaws.exceptions.MissingVersionResponseException;
import gamecomponents.Board;
import gamecomponents.Move;
import org.junit.Assert;
import org.junit.Test;
import players.AlphaBetaPlayer;
import players.NegamaxPlayer;
import players.Player;

import java.io.IOException;

import static java.lang.Math.E;

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

    @Test
    public void checkStateConstrutor(){
        String state = generateTestValue();
        Board board = new Board(state);
        Assert.assertEquals(state,board.toString());
    }

    @Test
    public void checkCopyConstructor(){
        Board board = new Board();
        Board second = new Board(board);

        Assert.assertEquals(second.getOnMove(),board.getOnMove());
        Assert.assertEquals(second.toString(),board.toString());
    }

    @Test
    public void CheckTimeForCopyBoard(){
        Board board = new Board();
        long nano = System.nanoTime();
        Board second = new Board(board);
        System.out.println(System.nanoTime()-nano);
        board.move(new Move("a2-a3"));
        nano = System.nanoTime();
        board.rerollBoard();
        System.out.println(System.nanoTime()-nano);
       // Assert.assertEquals(second.getOnMove(),board.getOnMove());
       // Assert.assertEquals(second.toString(),board.toString());
    }

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

    @Test
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
