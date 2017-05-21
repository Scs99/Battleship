/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

import battleship.network.Participant;
import org.junit.After;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author admin
 */
public class ShipPlacementTest {

    private Participant participant;
    private Game game;

    public ShipPlacementTest() {
    }

    @Before
    public void setUp() {
        participant = new Participant("192.0.0.1", "Participant");
        game = new Game(participant);
    }

    @After
    public void tearDown() {
        participant = null;
        game = null;
    }

    /**
     * Test of placeShip method, of class Game.
     */
    @Test
    public void testInvalidShipPartPlacementSecondPart() {
        System.out.println("testInvalidShipPartPlacementSecondPart");

        assertTrue("Placing 3,4", game.placeShip(game.ships.get(4), 3, 4));
              
        assertFalse("Placing 1,2", game.placeShip(game.ships.get(4), 1, 2));        
        assertFalse("Placing 1,3", game.placeShip(game.ships.get(4), 1, 3));
        assertFalse("Placing 1,4", game.placeShip(game.ships.get(4), 1, 4));
        assertFalse("Placing 1,5", game.placeShip(game.ships.get(4), 1, 5));
        assertFalse("Placing 1,6", game.placeShip(game.ships.get(4), 1, 6));
        
        assertFalse("Placing 5,2", game.placeShip(game.ships.get(4), 5, 2));        
        assertFalse("Placing 5,3", game.placeShip(game.ships.get(4), 5, 3));
        assertFalse("Placing 5,4", game.placeShip(game.ships.get(4), 5, 4));
        assertFalse("Placing 5,5", game.placeShip(game.ships.get(4), 5, 5));
        assertFalse("Placing 5,6", game.placeShip(game.ships.get(4), 5, 6));       

        assertFalse("Placing 2,2", game.placeShip(game.ships.get(4), 2, 2));        
        assertFalse("Placing 3,2", game.placeShip(game.ships.get(4), 3, 2));
        assertFalse("Placing 4,2", game.placeShip(game.ships.get(4), 4, 2));
        
        assertFalse("Placing 2,6", game.placeShip(game.ships.get(4), 2, 6));        
        assertFalse("Placing 3,6", game.placeShip(game.ships.get(4), 3, 6));
        assertFalse("Placing 4,6", game.placeShip(game.ships.get(4), 4, 6)); 

        game.myPlayfield.Print();
    }
    
        @Test
    public void testInvalidShipPartPlacementSecondPartDiagonally() {
        System.out.println("testInvalidShipPartPlacementSecondPartDiagonally");

        assertTrue("Placing 3,4", game.placeShip(game.ships.get(4), 3, 4));
              
        assertFalse("Placing 2,3", game.placeShip(game.ships.get(4), 2, 3));        
        assertFalse("Placing 4,3", game.placeShip(game.ships.get(4), 4, 3));
        assertFalse("Placing 2,5", game.placeShip(game.ships.get(4), 2, 5));
        assertFalse("Placing 4,5", game.placeShip(game.ships.get(4), 4, 5));
        
        game.myPlayfield.Print();
    }
    
        @Test
    public void testInvalidShipPartPlacementThirdPart() {
        System.out.println("testInvalidShipPartPlacementThirdPart");

        assertTrue("Placing 4,5", game.placeShip(game.ships.get(1), 4, 5));
        assertTrue("Placing 4,4", game.placeShip(game.ships.get(1), 4, 4));
             
        // Try invalid placements      
        assertFalse("Placing 3,2", game.placeShip(game.ships.get(1), 3, 2));
        assertFalse("Placing 3,3", game.placeShip(game.ships.get(1), 3, 3));
        assertFalse("Placing 3,4", game.placeShip(game.ships.get(1), 3, 4));    
        assertFalse("Placing 5,2", game.placeShip(game.ships.get(1), 5, 2));        
        assertFalse("Placing 5,3", game.placeShip(game.ships.get(1), 5, 3));
        assertFalse("Placing 5,4", game.placeShip(game.ships.get(1), 5, 4));
        
        assertFalse("Placing 4,5", game.placeShip(game.ships.get(1), 4, 5));        
        assertFalse("Placing 4,4", game.placeShip(game.ships.get(1), 4, 4));               

        game.myPlayfield.Print();
    }
}
