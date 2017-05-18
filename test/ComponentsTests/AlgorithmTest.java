package ComponentsTests;

import gamecomponents.Board;
import gamecomponents.Move;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;

public class AlgorithmTest {


    @Test
    public void checkCorrectAnswerfromAlgorithm(){
        Board board = new Board();
        ArrayList<Move> moves = board.genMoves();
        Assert.assertEquals(7,moves.size());
    }

    @Test
    public void checkAnswerFromAlgorithm(){
        Board board = new Board(BoardTest.generateTestValue());
        ArrayList<Move> moves = board.genMoves();
        Assert.assertEquals(17,moves.size());
    }

}

