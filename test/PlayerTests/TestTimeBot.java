package PlayerTests;

import gamecomponents.Board;
import org.junit.Test;
import players.AlphaBetaPlayer;
import players.Player;
import players.TimeAlphaBetaPlayer;

import java.io.IOException;

public class TestTimeBot {

    @Test
    public void checkTime() throws IOException {
        double[] times = new double[3];
        for(int i = 0;i< 5;i++){
            Player player = new TimeAlphaBetaPlayer(7);
            long milis = System.nanoTime();
            Board board = new Board();
            times[0]+=System.nanoTime()-milis;
            milis = System.nanoTime();
            board.move(player.getMove(board));
            times[1]+=System.nanoTime()-milis;
            milis = System.nanoTime();
            board.rerollBoard();
            times[2]+=System.nanoTime()-milis;
        }

        System.out.println((times[0]/10)+ "ns Konstruktor");
        System.out.println((times[1]/10)%(1E9)+ "s Make Move");
        System.out.println((times[2]/10)+ "ns Reroll Move");
    }
}
