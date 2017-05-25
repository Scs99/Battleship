/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

import battleship.network.Networker;
import java.util.concurrent.TimeUnit;
import org.junit.Test;

/**
 *
 * @author admin
 */
public class ComputerGameTest {
    
    public ComputerGameTest(){
        
    }
    
    @Test
    public void testPlaceAllShips(){
        
        ComputerGame ComputerGame = new ComputerGame(new TestNetworker("TestPlayer"));
        
        ComputerGame.placeAllShips();
        
        ComputerGame.myPlayfield.Print();      
    }
    
    @Test
    public void testVersus() throws InterruptedException{
        Networker networker1, networker2;
        
        
        networker1 = new Networker("Player1");
        networker2 = new Networker("Player2");

        networker1.startServer(60010);     
        networker2.connect("localhost", 60010);
        networker2.startServer(60011);
        networker1.connect("localhost", 60011);
        
        Game game = new Game(networker1);
        ComputerGame computerGame = new ComputerGame(networker2);
        
        game.shootAtOpponent(0, 0);
        
        TimeUnit.SECONDS.sleep(2);
        
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
