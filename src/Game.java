import java.io.IOException;
import java.util.concurrent.TimeUnit;

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

    public static void main(String[] args){
        String response = "";
        char value =' ';
        Board board = new Board();
        Player player = new RandomPlayer();
        try{
            initClient();
            while (true){
                doOwnMove(player,board);
                response = client.getMove();
                if(response != null){
                  doOtherMove(player,board,response);
                }else{
                    break;
                }
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }

    private static void doOtherMove(Player player, Board board, String response) {
        char va = board.move(new Move(response));
        System.out.println(response+" SERVER\n");
        System.out.println(board);
    }

    private static void initClient() throws IOException {
        client = new Client(URL,PORT,USERNAME,PASSWORD);
        if(client != null) {
            client.accept("12858", 'W');
        }
    }

    private static void doOwnMove(Player player,Board board){
        Move move = player.getMove(board);
        client.send(move.toString(),true);
        char value = board.move(move);
        System.out.println(board);
    }
}
