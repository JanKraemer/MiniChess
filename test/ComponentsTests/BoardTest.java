package ComponentsTests;

import com.sun.javaws.exceptions.MissingVersionResponseException;
import gamecomponents.Board;
import gamecomponents.Move;
import org.junit.Assert;
import org.junit.Test;

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
}
