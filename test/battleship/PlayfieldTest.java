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
public class PlayfieldTest {
    
    public PlayfieldTest() {
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
     * Test of shootAt method, of class Playfield.
     */
    @Test
    public void testShootAt() {
        System.out.println("shootAt");
        int x = 0;
        int y = 0;
        Playfield instance = null;
        instance.shootAt(x, y);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of placeAt method, of class Playfield.
     */
    @Test
    public void testPlaceAt() {
        System.out.println("placeAt");
        int x = 0;
        int y = 0;
        Playfield instance = null;
        instance.placeAt(x, y);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSurrounding method, of class Playfield.
     */
    @Test
    public void testGetSurrounding() {
        System.out.println("getSurrounding");
        int x = 0;
        int y = 0;
        Playfield instance = null;
        Field[] expResult = null;
        Field[] result = instance.getSurrounding(x, y);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFieldFromCoordinate method, of class Playfield.
     */
    @Test
    public void testGetFieldFromCoordinate() {
        System.out.println("getFieldFromCoordinate");
        int x = 0;
        int y = 0;
        Playfield instance = null;
        Field expResult = null;
        Field result = instance.getFieldFromCoordinate(x, y);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
