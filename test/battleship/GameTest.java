/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

import battleship.network.Networker;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author admin
 */
public class GameTest {

    Game player1;
    Game player2;
    Networker networker1;
    Networker networker2;

    public GameTest() {
    }

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {

    }

    @Test
    public void test() throws IOException, InterruptedException {

        networker1 = new Networker("Player1");
        networker2 = new Networker("Player2");

        networker1.startServer(60010);     
        networker2.connect("localhost", 60010);
        networker2.startServer(60011);
        networker1.connect("localhost", 60011);
        
        //networker2.startServer(60011);
        //networker1.connect("localhost", 60011);

        Game player1 = new Game(networker1);
        Game player2 = new Game(networker2);

        TimeUnit.MILLISECONDS.sleep(500);
        player1.shootAtOpponent(3, 3);
        TimeUnit.MILLISECONDS.sleep(200);
        player2.shootAtOpponent(2, 2);
        TimeUnit.MILLISECONDS.sleep(200);
        player1.shootAtOpponent(8, 8);
        
        TimeUnit.SECONDS.sleep(1);

        System.out.println("Player 1");
        player1.opponentPlayfield.Print();
        System.out.println("");
        player1.myPlayfield.Print();
        System.out.println("");
        System.out.println("Player 2");
        player2.opponentPlayfield.Print();
        System.out.println("");
        player2.myPlayfield.Print();
        System.out.println("");

        /*
        TimeUnit.SECONDS.sleep(1);

        networker2.send("Testnachricht1 von networker2 zu networker1");
        TimeUnit.SECONDS.sleep(1);
        networker2.send("Testnachricht2 von networker2 zu networker1");
        TimeUnit.SECONDS.sleep(3);

        networker1.send("Testnachricht3 von networker1 zu networker2");
        TimeUnit.SECONDS.sleep(1);
        networker1.send("Testnachricht4 von networker1 zu networker2");
        TimeUnit.SECONDS.sleep(1);

        //networker1.instance.send("Testnachricht3 von networker1 zu networker2");
         */
        networker1 = null;
        networker2 = null;

    }

}
