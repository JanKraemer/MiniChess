package fhws.minichess.ComponentsTests;

import fhws.minichess.gamecomponents.Board;
import fhws.minichess.gamecomponents.StateEvaluator;
import org.junit.Assert;
import org.junit.Test;

/**
 * test class for the stateevaluator class
 */
public class StateEvaluatorTest {

    /**
     * check if the stateelevator is zeor for the default board
     */
    @Test
    public void checkStateElavatorWithStartBoard(){
        Board board = new Board();
        int val = StateEvaluator.validateState(board);
        Assert.assertEquals(0,val);
    }

    /**
     * check the scoring with a generated board
     */
    @Test
    public void validateGivenBoard(){
        String state = BoardTest.generateTestValue();
        Board board = new Board(state);
        int val = StateEvaluator.validateState(board);
        Assert.assertEquals(-20,val);
    }

    /**
     * check score for another given board
     */
    @Test
    public void validateBoard(){
        String state = generateTestValue();
        Board board = new Board(state);
        int val = StateEvaluator.validateState(board);
        Assert.assertEquals(70,val);
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
