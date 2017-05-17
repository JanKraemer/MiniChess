package ComponentsTests;

import gamecomponents.Board;
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
}
