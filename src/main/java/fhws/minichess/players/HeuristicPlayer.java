package fhws.minichess.players;

import fhws.minichess.gamecomponents.Board;
import fhws.minichess.gamecomponents.Move;
import fhws.minichess.gamecomponents.StateEvaluator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
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
 * Provides a HeuristicPlayer
 */
public class HeuristicPlayer extends Player {

    public HeuristicPlayer() {

    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Client getClient() {
        return client;
    }

    /**
     * Get a valid gamecomponents.Move from the KI .
     *
     * @param board actual board with all Pieces
     * @return a valid gamecomponents.Move from the Server
     * @throws IOException
     */
    @Override
    public Move getMove(Board board) {
        ArrayList<Move> moves = getBestMove(board, board.genMoves());
        int random = new Random().nextInt(moves.size());
        Move move = moves.get(random);
        if (client != null)
            client.send(move.toString(), false);
        return move;
    }

    /**
     * provides the best move for the acutal board
     *
     * @param board actual board
     * @param moves list of all moves
     * @return the best move
     */
    private ArrayList<Move> getBestMove(Board board, ArrayList<Move> moves) {
        int score = Integer.MIN_VALUE;
        int actualScore = 0;
        ArrayList<Move> possiblesMoves = new ArrayList<>();
        for (Move actualMove : moves) {
            board.move(actualMove);
            actualScore = -StateEvaluator.validateState(board);
            if (actualScore > score) {
                possiblesMoves.clear();
                score = actualScore;
                possiblesMoves.add(actualMove);
            } else if (score == actualScore) {
                possiblesMoves.add(actualMove);
            }
            board.rerollBoard();
        }
        return possiblesMoves;
    }

    /**
     * Print the actuval move with the actual state of the board after the move.
     *
     * @param board actual board
     * @param move  actual gamecomponents.Move from the KI
     */
    @Override
    public void print(Board board, Move move) {
        System.out.println(move + " Heuristic\n" + board);
    }

}

