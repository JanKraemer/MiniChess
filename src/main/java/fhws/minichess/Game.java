package fhws.minichess;

import fhws.minichess.gamecomponents.Board;
import fhws.minichess.gamecomponents.Move;
import fhws.minichess.players.*;

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

/**
 * Game class with FluentInterface
 */
public class Game {

    private Board board;
    private HashMap<Character, Player> playerHashMap;

    protected Game(Board board, HashMap<Character, Player> playerHashMap) {
        this.board = board;
        this.playerHashMap = playerHashMap;
    }

    /**
     * provides a static method for getting a GameBuilder
     *
     * @return given back a new GameBuilder
     */
    public static GameBuilder getInstance() {
        return new GameBuilder();
    }

    /**
     * start a normal game
     *
     * @throws IOException if the client has some problems
     */
    public void start() throws IOException {
        char value = '?';
        while (value == '?') {
            Player actual = playerHashMap.get(board.getOnMove());
            Move move = actual.getMove(board);
            if (move == null)
                break;
            value = board.move(move);
            actual.print(board, move);
        }
        System.out.println(value+ " WINS");
    }

    /**
     * provides a network game agains a clientPlayer
     *
     * @throws IOException if the client has some problems
     */
    public void startNetworkGame()throws IOException {
        while (true) {
            Player actual = playerHashMap.get(board.getOnMove());
            Move move = actual.getMove(board);
            if (move == null)
                break;
            board.move(move);
            actual.print(board, move);
        }
    }

    /**
     * Provides several games for the number of rounds
     *
     * @param rounds given rounds to play
     * @return a array with game ending values
     * @throws IOException if the client is broken
     */
    public int[] startForRounds(int rounds)throws IOException {
        int[] score = new int[3];
        int index = 0;
        while (index++ < rounds) {
            Board board = new Board();
            playGame(board, score);
        }
        return score;
    }

    /**
     * playing a game up to the point that a game is over
     *
     * @param board actual board
     * @param count game results
     * @return game results
     * @throws IOException if the client has some problems
     */
    private int[] playGame(Board board, int[] count) throws IOException {
        char value = '?';
        while (value == '?') {
            Player actual = playerHashMap.get(board.getOnMove());
            Move move = actual.getMove(board);
            if (move == null)
                break;
            value = board.move(move);
            //actual.print(board, move);
        }
        if (value == 'W')
            count[0]++;
        if (value == 'B')
            count[1]++;
        if (value == '=')
            count[2]++;

        return count;
    }

    /**
     * static class for FluentInterface
     */
    public static class GameBuilder {

        private Client client;
        private HashMap<Character, Player> players = new HashMap<>();
        private Board board;

        private String method;
        private String id;
        private char color;

        private static String URL = "imcs.svcs.cs.pdx.edu";
        private static String PORT = "3589";
        private static String USERNAME = "QuickAndDirty";
        private static String PASSWORD = "password";

        public GameBuilder() {
            board = new Board();
        }

        /**
         * setting up the client for the server
         *
         * @param args given args from the command line
         * @return the class
         */
        public GameBuilder withClient(String[] args) {
            method = args[0];
            if (args.length == 2) {
                color = args[1].charAt(0);
            } else {
                id = args[1];
                color = args[2].charAt(0);
            }
            try {
                initClient();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return this;
        }

        /**
         * setting up a game without a client,
         * its only for games agains myself or a random player
         *
         * @return myself
         */
        public GameBuilder withoutClient() {
            client = null;
            return this;
        }

        /**
         * Initialise the Client depending on the input.
         *
         * @throws IOException
         */
        private void initClient() throws IOException {
            client = new Client(URL, PORT, USERNAME, PASSWORD);
            if (client != null) {
                if (method.equalsIgnoreCase("accept"))
                    client.accept(id, color);
                else
                    client.offer(color);
            }
        }

        /**
         * set a player to the board and the other player will be
         * a client player
         *
         * @param myPlayer the player the user choose
         * @return myself
         */
        public GameBuilder setPlayer(Player myPlayer) {
            myPlayer.setClient(client);
            ClientPlayer clientPlayer = new ClientPlayer();
            clientPlayer.setClient(client);
            if (color == 'W') {
                this.setPlayers(myPlayer, clientPlayer);
            } else {
                this.setPlayers(clientPlayer, myPlayer);
            }
            return this;
        }

        /**
         * Setting up both players to the game
         *
         * @param player1 white player
         * @param player2 black player
         * @return
         */
        public GameBuilder setPlayers(Player player1, Player player2) {
            players.put('W', player1);
            players.put('B', player2);

            return this;
        }

        /**
         * finish the building
         *
         * @return a Game
         */
        public Game finish() {
            return new Game(board, players);
        }
    }

}


