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
public class FieldTest {
    
    public FieldTest() {
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
     * Test of getState method, of class Field.
     */
    @Test
    public void testGetState() {
        System.out.println("getState");
        Field field = new Field(0,0);
        assertEquals(FieldState.WATER, field.getState());
    }

    /**
     * Test of setHit method, of class Field.
     */
    @Test
    public void testSetHit() {
        System.out.println("setHit");
        Field field = new Field(0,0);
        field.setHit();
        assertEquals(FieldState.WATER_HIT, field.getState());
        field.setShip();
        field.setHit();
        assertEquals(FieldState.SHIP_HIT, field.getState());
        
    }

    /**
     * Test of setShip method, of class Field.
     */
    @Test
    public void testSetShip() {
        System.out.println("setShip");
        Field field = new Field(0,0);
        field.setShip();
        assertEquals(FieldState.SHIP, field.getState());
    }
    
}
