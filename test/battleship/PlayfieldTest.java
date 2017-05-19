/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

import java.util.ArrayList;
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
        Playfield playfield = new Playfield(10);
        playfield.placeAt(5,5);
        playfield.shootAt(5, 5);
        playfield.shootAt(6, 6);
        
        assertEquals(FieldState.SHIP_HIT, playfield.fields[5*10 + 5].getState());
        assertEquals(FieldState.WATER_HIT, playfield.fields[6*10 + 6].getState());
    }

    /**
     * Test of placeAt method, of class Playfield.
     */
    @Test
    public void testPlaceAt() {
        System.out.println("placeAt");
        Playfield playfield = new Playfield(10);
        playfield.placeAt(5,5);
        
        assertEquals(FieldState.SHIP, playfield.fields[5*10 + 5].getState());
        
        
    }

    /**
     * Test of getSurrounding method, of class Playfield.
     */
    @Test
    public void testGetSurrounding() {
        System.out.println("getSurrounding");
        Playfield playfield = new Playfield(10);
        ArrayList<Field> fields = playfield.getSurrounding(0, 0);
        
        for(Field field : fields){
            System.out.println("x_Cord " + field.x + " / y_Cord " + field.y);
        }
        
        assertEquals(1, fields.get(0).x);
        assertEquals(0, fields.get(0).y);
        assertEquals(1, fields.get(1).x);
        assertEquals(1, fields.get(1).y);
        assertEquals(0, fields.get(2).x);
        assertEquals(1, fields.get(2).y);
        
        System.out.println("------------------------");
        
        ArrayList<Field> fields1 = playfield.getSurrounding(9, 9);
        
        for(Field field : fields1){
            System.out.println("x_Cord " + field.x + " / y_Cord " + field.y);
        }
        
        assertEquals(9, fields1.get(0).x);
        assertEquals(8, fields1.get(0).y);
        assertEquals(8, fields1.get(1).x);
        assertEquals(9, fields1.get(1).y);
        assertEquals(8, fields1.get(2).x);
        assertEquals(8, fields1.get(2).y);
        
        System.out.println("------------------------");
        
        ArrayList<Field> fields2 = playfield.getSurrounding(5, 9);
        
        for(Field field : fields2){
            System.out.println("x_Cord " + field.x + " / y_Cord " + field.y);
        }
        System.out.println("------------------------");
        
        assertEquals(5, fields2.get(0).x);
        assertEquals(8, fields2.get(0).y);
        assertEquals(6, fields2.get(1).x);
        assertEquals(8, fields2.get(1).y);
        assertEquals(6, fields2.get(2).x);
        assertEquals(9, fields2.get(2).y);
        assertEquals(4, fields2.get(3).x);
        assertEquals(9, fields2.get(3).y);
        assertEquals(4, fields2.get(4).x);
        assertEquals(8, fields2.get(4).y);
    
        ArrayList<Field> fields3 = playfield.getSurrounding(5, 5);
        
        for(Field field : fields3){
            System.out.println("x_Cord " + field.x + " / y_Cord " + field.y);
        }
        
        assertEquals(5, fields3.get(0).x);
        assertEquals(4, fields3.get(0).y);
        assertEquals(6, fields3.get(1).x);
        assertEquals(4, fields3.get(1).y);
        assertEquals(6, fields3.get(2).x);
        assertEquals(5, fields3.get(2).y);
        assertEquals(6, fields3.get(3).x);
        assertEquals(6, fields3.get(3).y);
        assertEquals(5, fields3.get(4).x);
        assertEquals(6, fields3.get(4).y);
        assertEquals(4, fields3.get(5).x);
        assertEquals(6, fields3.get(5).y);
        assertEquals(4, fields3.get(6).x);
        assertEquals(5, fields3.get(6).y);
        assertEquals(4, fields3.get(7).x);
        assertEquals(4, fields3.get(7).y);
        
    }

    /**
     * Test of getValideNeighbours method, of class Playfield.
     */
    @Test
    public void testGetValideNeighbours() {
        System.out.println("getValideNeighbours");
         Playfield playfield = new Playfield(10);
        ArrayList<Field> fields = playfield.getValideNeighbours(0, 0);
        
        for(Field field : fields){
            System.out.println("x_Cord " + field.x + " / y_Cord " + field.y);
        }
        
        assertEquals(1, fields.get(0).x);
        assertEquals(0, fields.get(0).y);
        assertEquals(0, fields.get(1).x);
        assertEquals(1, fields.get(1).y);
        
         ArrayList<Field> fields1 = playfield.getValideNeighbours(9, 0);
        
        for(Field field1 : fields){
            System.out.println("x_Cord " + field1.x + " / y_Cord " + field1.y);
        }
        
        assertEquals(9, fields1.get(0).x);
        assertEquals(1, fields1.get(0).y);
        assertEquals(8, fields1.get(1).x);
        assertEquals(0, fields1.get(1).y);
    }

    /**
     * Test of getFieldFromCoordinate method, of class Playfield.
     */
    @Test
    public void testGetFieldFromCoordinate() {
       /* System.out.println("getFieldFromCoordinate");
        int x = 0;
        int y = 0;
        Playfield instance = null;
        Field expResult = null;
        Field result = instance.getFieldFromCoordinate(x, y);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");*/
    }

    /**
     * Test of getFieldFromCoordinate method, of class Playfield.
     */
   
    
}
