/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Scs
 */
public class ShipTest {
    
    public ShipTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of isDestroyed method, of class Ship.
     */
    @Test
    public void testIsDestroyed() {
        System.out.println("isDestroyed");
        Playfield playfield = new Playfield(10);
        Ship ship = new Ship(3);
        /*
        ship.fields[0] = playfield.fields[0];
        ship.fields[1] = playfield.fields[1];
        ship.fields[2] = playfield.fields[2];
        
        ship.fields[0].setShip();
        ship.fields[1].setShip();
        ship.fields[2].setShip();
        
        ship.fields[0].setHit();
        ship.fields[1].setHit();
        ship.fields[2].setHit();
*/
        assertEquals(true, ship.isDestroyed()); 
    }

    /**
     * Test of isCompleted method, of class Ship.
     */
    @Test
    public void testIsCompleted() {
        System.out.println("isCompleted");
        Playfield playfield = new Playfield(10);
        Ship ship = new Ship(3);
        /*
        ship.fields[0] = playfield.fields[0];
        ship.fields[1] = playfield.fields[1];
        ship.fields[2] = playfield.fields[2];
        
        ship.fields[0].setShip();
        ship.fields[1].setShip();
        ship.fields[2].setShip();
*/
        assertEquals(true, ship.isCompleted()); 
    }
    
}
