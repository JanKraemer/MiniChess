package players;

import gamecomponents.Board;
import gamecomponents.FutureMove;
import gamecomponents.Move;
import gamecomponents.StateEvaluator;

import java.io.IOException;
import java.util.Random;

/**
 * Copyright © 2017 Jan Krämer
 * <p>
 * [This program is licensed under the "MIT License"]
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.Copyright © 2017 Jan Krämer
 * <p>
 * [This program is licensed under the "MIT License"]
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

/**
 * Provides a NegamaxPlayer
 */
public class NegamaxPlayer extends Player {

    private int deep;

    public NegamaxPlayer(int deep) {
        this.deep = deep;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Client getClient() {
        return client;
    }

    /**
     * returning the best Move for the actual board
     *
     * @param board actual state == board
     * @return best Move
     * @throws IOException
     */
    @Override
    public Move getMove(Board board) throws IOException {
        FutureMove move = getNextMoveAlgorithm(board, this.deep);
        if (client != null)
            client.send(move.getMove().toString(), false);
        return move.getMove();
    }

    /**
     * Doing the AlphaBetaAlgorithm for getting the best Move with its score
     *
     * @param board actual board
     * @param deep deep for the algorithm
     * @return the best Move
     */
    private FutureMove getNextMoveAlgorithm(Board board, int deep) {
        if (deep == 0)
            return new FutureMove(StateEvaluator.validateState(board), null);
        FutureMove move = new FutureMove(Integer.MIN_VALUE, null);
        for (Move actualMove : board.genMoves()) {
            int value;
            board.move(actualMove);
            int value_ = (int) ((StateEvaluator.validateState(board) * (-1))*0.9);
            if (value_ > 8500 || value_ < -8500) {
                value = value_;
            } else {
                FutureMove next = getNextMoveAlgorithm(board, deep - 1);
                value = (-1) * next.getScore();
            }
            board.rerollBoard();
            if (value > move.getScore() || ( value == move.getScore() && randomFunction())) {
                move.setMove(actualMove);
                move.setScore(value);
            }
        }
        return move;
    }

    /**
     * Randomize the function for algorithm
     * @return
     */
    private boolean randomFunction() {
        return new Random().nextInt(4) == 0 ? true : false;
    }


    /**
     * Printing the actual move and board
     * @param board actual board
     * @param move  actual move from the player
     */
    @Override
    public void print(Board board, Move move) {
        System.out.println(move + " Negamx\n" + board);
    }
}
