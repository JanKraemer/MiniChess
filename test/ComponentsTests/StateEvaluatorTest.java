package ComponentsTests;

import gamecomponents.Board;
import gamecomponents.StateEvaluator;
import org.junit.Assert;
import org.junit.Test;

public class StateEvaluatorTest {

    @Test
    public void checkStateElavatorWithStartBoard(){
        Board board = new Board();
        int val = StateEvaluator.validateState(board);
        Assert.assertEquals(0,val);
    }

    @Test
    public void validateGivenBoard(){
        String state = BoardTest.generateTestValue();
        Board board = new Board(state);
        int val = StateEvaluator.validateState(board);
        Assert.assertEquals(-2,val);
    }

    @Test
    public void validateBoard1000(){
        String state = generateTestValue();
        Board board = new Board(state);
        int val = StateEvaluator.validateState(board);
        Assert.assertEquals(7,val);
    }

    /**
     * generate a special TestValue
     *
     * @return
     */
    public static String generateTestValue() {
        String[] field = {"20 W\n",
                "6 | . | . | . | . | k | ",
                "5 | . | . | P | . | . | ",
                "4 | p | . | . | K | . | ",
                "3 | P | . | . | . | . | ",
                "2 | . | . | . | . | B | ",
                "1 | . | . | . | . | . | ",
                "----------------------- ",
                "  | a | b | c | d | e | "};
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < field.length; i++) {
            builder.append(field[i] + "\n");
        }

        return builder.toString();
    }
}
