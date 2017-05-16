import java.util.LinkedList;
import java.util.Scanner;

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
public class HumanPlayer extends Player {

    private Scanner scanner;

    public HumanPlayer() {
        scanner = new Scanner(System.in);
    }

    /**
     * Get a Move from a Human Player.
     * @param board actual board with all Pieces
     * @return a valid Move from the Server
     *
     */
    @Override
    Move getMove(Board board) {
        String input;
        Move move = null;
        boolean isMoveLegal = false;
        do {
            input = scanner.nextLine();
            try {
                move = new Move(input);
                isMoveLegal = isLegalMove(board.genMoves(), move);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        } while (!isMoveLegal);
        return move;
    }

    /**
     * Print the actuval move with the actual state of the board after the move.
     * @param board actual board
     * @param move actual Move from the player
     */
    @Override
    void print(Board board, Move move) {
        System.out.println(move + " Human\n"+board);
    }

    /**
     * Checks if the user input move is in the list of all possible moves.
     * @param moves all possibles moves for this piece
     * @param input the move from the user
     * @return
     */
    private boolean isLegalMove(LinkedList<Move> moves, Move input) {
        for (Move move : moves) {
            if (areMovesEqual(move,input)){
                return true;
            }
        }
        return false;
    }

    /**
     * Check if both moves have the some coordination pairs.
     * @param move move to check
     * @param input move from user input
     * @return
     */
    private boolean areMovesEqual(Move move, Move input) {
        return move.getFrom().getRow() == input.getFrom().getRow() &&
                move.getFrom().getCol() == input.getFrom().getCol() &&
                move.getTo().getRow() == input.getTo().getRow() &&
                move.getTo().getCol() == input.getTo().getCol();
    }

}
