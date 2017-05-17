package players;

import gamecomponents.Board;
import gamecomponents.FutureMove;
import gamecomponents.Move;
import gamecomponents.StateEvaluator;

import java.io.IOException;
import java.util.LinkedList;

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

    @Override
    public Move getMove(Board board) throws IOException {
        FutureMove move = getNextMoveAlgorithm(board, this.deep);
        if (client != null)
            client.send(move.getMove().toString(), false);
        return move.getMove();
    }

    private FutureMove getNextMoveAlgorithm(Board board, int deep) {
        if (deep == 0)
            return new FutureMove(StateEvaluator.validateState(board), null);
        LinkedList<FutureMove> moves = new LinkedList<>();
        FutureMove move = new FutureMove(Integer.MIN_VALUE, null);
        for (Move actualMove : board.genMoves()) {
            int value;
            Board copy = new Board(board);
            copy.move(actualMove);
            int value_ = StateEvaluator.validateState(copy) * (-1);
            if (value_ > 500 || value_ < -500) {
                return new FutureMove(value_, actualMove);
            } else {
                FutureMove next = getNextMoveAlgorithm(copy, deep - 1);
                value = (-1) * next.getScore();
            }
            if (value > move.getScore()) {
                move.setMove(actualMove);
                move.setScore(value);
            }
        }
        return move;
    }


    @Override
    public void print(Board board, Move move) {
        System.out.println(move + " Negamx\n" + board);
    }
}
