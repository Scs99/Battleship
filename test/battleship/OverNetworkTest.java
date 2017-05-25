/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

import battleship.network.Networker;
import org.junit.Test;

/**
 *
 * @author admin
 */
public class OverNetworkTest {

    @Test
    public void player1() {
        Networker networker1 = new Networker("Player1");
        networker1.startServer(60010);  
          
        networker1.connect("localhost", 60010);
        
        Game player1 = new Game(networker1);
        player1.shootAtOpponent(3, 3);
    }

    @Test
    public void player2() {
        Networker networker2 = new Networker("Player2");
        networker2.connect("10.155.96.231", 60010);
        
        networker2.startServer(60010);
        
        Game player2 = new Game(networker2);       
        player2.shootAtOpponent(0, 0);
    }

}
