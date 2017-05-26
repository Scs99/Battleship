package battleship;

import battleship.network.Networker;
import java.util.concurrent.TimeUnit;
import org.junit.Test;

/**
 *
 * @author Louis Rast
 */
public class ComputerGameTest {

    public ComputerGameTest() {
    }

    @Test
    public void testPlaceAllShips() {

        ComputerGame ComputerGame = new ComputerGame(new TestNetworker("TestPlayer"));
        ComputerGame.placeAllShips();
        System.out.println("");
        ComputerGame.myPlayfield.Print();
        System.out.println("");
    }

    @Test
    public void testAutoconnect() throws InterruptedException {
        Networker networker1, networker2;

        networker1 = new Networker("Player1");
        networker2 = new Networker("Player2");

        networker1.startServer();
        networker2.startServer();
        TimeUnit.MILLISECONDS.sleep(200);
        networker1.connect("localhost", networker2.getMyPort());

        Game game = new Game(networker1);
        ComputerGame computerGame = new ComputerGame(networker2);

        game.shootAtOpponent(0, 0);

        TimeUnit.MILLISECONDS.sleep(200);

        System.out.println("");
        System.out.println("Player 1");
        game.opponentPlayfield.Print();
        System.out.println("");
        game.myPlayfield.Print();
        System.out.println("");
        System.out.println("Player 2");
        computerGame.opponentPlayfield.Print();
        System.out.println("");
        computerGame.myPlayfield.Print();
        System.out.println("");
    }
}
