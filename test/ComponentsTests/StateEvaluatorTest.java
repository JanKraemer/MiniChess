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

}
