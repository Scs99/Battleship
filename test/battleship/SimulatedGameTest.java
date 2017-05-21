/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

import battleship.network.HitRequest;
import battleship.network.HitResponse;
import battleship.network.Participant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author admin
 */
public class SimulatedGameTest {
    
    public void SimulatedGameTest()
    {
        
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void simulateFullGameTest()
    {
        Participant participant1 = new Participant("192.1.1.0", "Player1");
        Participant participant2 = new Participant("192.2.2.0", "Player2");
        
        Game player1 = new Game(participant1);
        Game player2 = new Game(participant2);
        
        // Player1 places ships
        player1.placeShip(player1.ships.get(0), 2, 7);
        player1.placeShip(player1.ships.get(0), 3, 7);
        player1.placeShip(player1.ships.get(0), 4, 7);
        player1.placeShip(player1.ships.get(0), 5, 7);
        
        player1.placeShip(player1.ships.get(1), 7, 4);
        player1.placeShip(player1.ships.get(1), 7, 5);
        player1.placeShip(player1.ships.get(1), 7, 6);
        
        player1.placeShip(player1.ships.get(2), 1, 1);
        player1.placeShip(player1.ships.get(2), 1, 2);
        
        player1.placeShip(player1.ships.get(3), 5, 2);
        player1.placeShip(player1.ships.get(3), 6, 2);
        
        player1.placeShip(player1.ships.get(4), 8, 0);
        player1.placeShip(player1.ships.get(4), 9, 0);
        
        // Player2 places ships
        player2.placeShip(player2.ships.get(0), 3, 3);
        player2.placeShip(player2.ships.get(0), 3, 4);
        player2.placeShip(player2.ships.get(0), 3, 5);
        player2.placeShip(player2.ships.get(0), 3, 6);
        
        player2.placeShip(player2.ships.get(1), 4, 8);
        player2.placeShip(player2.ships.get(1), 5, 8);
        player2.placeShip(player2.ships.get(1), 6, 8);
        
        player2.placeShip(player2.ships.get(2), 1, 1);
        player2.placeShip(player2.ships.get(2), 2, 1);
        
        player2.placeShip(player2.ships.get(3), 1, 7);
        player2.placeShip(player2.ships.get(3), 1, 8);
        
        player2.placeShip(player2.ships.get(4), 9, 6);
        player2.placeShip(player2.ships.get(4), 9, 7);
        
        // Player1 starts
        player1.startMyTurn();
        assertTrue(player1.isMyTurn());
        assertFalse(player2.isMyTurn());
        
        shootingHandler(player1, player2, 1, 1);
        assertTrue("1", player1.isMyTurn());
        assertFalse("2",player2.isMyTurn());
        shootingHandler(player1, player2, 2, 1);   
        assertTrue("Player2 Ship 2 destroyed.",player2.ships.get(2).isDestroyed());
        assertTrue("4",player1.isMyTurn());
        assertFalse("5",player2.isMyTurn());     
        shootingHandler(player1, player2, 5, 2); // Shot in water 
        assertTrue("6", player2.isMyTurn());
        assertFalse("7", player1.isMyTurn());    
        
        // Player2 versenkt zwei Schiffe
        shootingHandler(player2, player1, 8, 0);   
        shootingHandler(player2, player1, 9, 0);   
        assertTrue("Player1 Ship 4 destroyed.",player1.ships.get(4).isDestroyed());
        
        shootingHandler(player2, player1, 2, 7);   
        shootingHandler(player2, player1, 3, 7);        
        shootingHandler(player2, player1, 4, 7);        
        shootingHandler(player2, player1, 5, 7); 
        assertTrue("Player1 Ship 0 destroyed.",player1.ships.get(0).isDestroyed());
        
        // Some shots in the water
        shootingHandler(player2, player1, 4, 3);  
        assertTrue("", player1.isMyTurn());
        assertFalse("", player2.isMyTurn());         
        shootingHandler(player1, player2, 5, 3);    
        assertTrue("", player2.isMyTurn());
        assertFalse("", player1.isMyTurn());    
        
        shootingHandler(player2, player1, 5, 3); 
        shootingHandler(player1, player2, 4, 4);   
        shootingHandler(player2, player1, 3, 5); 
        shootingHandler(player1, player2, 7, 5);   
        
        // Player2 destroys all leftover ships of Player1
        shootingHandler(player2, player1, 7, 4); 
        shootingHandler(player2, player1, 7, 5); 
        shootingHandler(player2, player1, 7, 6);
        assertTrue("Player1 Ship 1 destroyed.",player1.ships.get(1).isDestroyed());
        
        
        shootingHandler(player2, player1, 5, 2); 
        shootingHandler(player2, player1, 6, 2); 
                
        assertTrue("Player1 Ship 3 destroyed.",player1.ships.get(3).isDestroyed());
        
        shootingHandler(player2, player1, 1, 1); 
        shootingHandler(player2, player1, 1, 2); 
        assertTrue("Player1 Ship 2 destroyed.",player1.ships.get(2).isDestroyed());
        
        
        player1.opponentPlayfield.Print();
        System.out.println("");
        player1.myPlayfield.Print();
        System.out.println("");
        System.out.println("");
        player2.opponentPlayfield.Print();
        System.out.println("");
        player2.myPlayfield.Print();                    
    }
    
    void shootingHandler(Game from, Game to, int x, int y){
        from.shotAftermath(to.shotRecieved(from.shootAtOpponent(x, y)));  
    }
}
