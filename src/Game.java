import gamecomponents.Board;
import gamecomponents.Move;
import players.*;

import java.io.IOException;
import java.util.HashMap;

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
public class Game {

    public static String URL = "imcs.svcs.cs.pdx.edu";
    public static String PORT = "3589";
    public static String USERNAME = "QuickAndDirty";
    public static String PASSWORD = "password";

    public static Client client;

    public static HashMap<Character, Player> players = new HashMap<Character, Player>(2);

    public static String method;
    public static String id;
    public static char color;

    /**
     * Main Methode for a gamecomponents
     *
     * @param args given Arguments from the user
     */
    public static void main(String[] args) {
        //initComponents(args);
        try {
            //   initClient();
            setPlayers();
            int index = 0;
            int[] score = new int[3];
            while (index++ < 20) {
                Board board = new Board();
                //   System.out.println(board);
                playGame(board, score);
            }

            System.out.println(score[0] + " B WINS");
            System.out.println(score[1] + " W WINS");
            System.out.println(score[2] + " DRAWS");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * the methode does all the work and stops, when 1 player has won.
     *
     * @param board startBoard
     * @throws IOException
     */
    private static int[] playGame(Board board, int[] score) throws IOException {
        char value = '?';
        while (value == '?') {
            Player actual = players.get(board.getOnMove());
            Move move = actual.getMove(board);
            if (move == null)
                break;
            value = board.move(move);
            //  actual.print(board, move);
        }
        if (value == 'B')
            score[0]++;
        if (value == 'W')
            score[1]++;
        if (value == '=')
            score[2]++;
        return score;
    }

    /**
     * Add Players to HashMap with their Color as Key.
     */
    private static void setPlayers() {
     /*   char otherColor = 'W';
        if (color == otherColor)
            otherColor = 'B';
        players.put(otherColor, new ClientPlayer(client));
        players.put(color, new NegamaxPlayer(5, client));*/
        players.put('B', new NegamaxPlayer(2));
        players.put('W', new HeuristicPlayer());
    }

    /**
     * initialise the three String from the user input.
     * Is needed to initialise the client right.
     *
     * @param args input arguments from the user
     */
    private static void initComponents(String[] args) {
        method = args[0];
        if (args.length == 2) {
            color = args[1].charAt(0);
        } else {
            id = args[1];
            color = args[2].charAt(0);
        }
    }

    /**
     * Initialise the players.Client depending on the input.
     *
     * @throws IOException
     */
    private static void initClient() throws IOException {
        client = new Client(URL, PORT, USERNAME, PASSWORD);
        if (client != null) {
            if (method.equalsIgnoreCase("accept"))
                client.accept(id, color);
            else
                client.offer(color);
        }
    }

}
