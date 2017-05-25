/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

import battleship.network.Networker;
import battleship.network.Participant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 *
 * @author admin
 */
@RunWith(Parameterized.class)
public class ShipPositionPlacementTest {

    private Game game;
    private ArrayList<Field> shipLocation;
    private boolean expectedResult;
    private int shipNr;
    private String testCaseDescription;

    // Each parameter should be placed as an argument here
    // Every time runner triggers, it will pass the arguments
    // from parameters we defined in primeNumbers() method
    public ShipPositionPlacementTest(ArrayList<Field> shipLocation, int shipNr, boolean expectedResult, String testCaseDescription) {
        this.shipLocation = shipLocation;
        this.expectedResult = expectedResult;
        this.shipNr = shipNr;
        this.testCaseDescription = testCaseDescription;
    }

    @Parameterized.Parameters
    public static Collection shipsToTest() {
        return Arrays.asList(new Object[][]{
            {new ArrayList<>(Arrays.asList(new Field(4, 4), new Field(5, 4), new Field(6, 4), new Field(7, 4))), 0, true, "Middle horizontal, left to right placement"},
            {new ArrayList<>(Arrays.asList(new Field(0, 0), new Field(1, 0), new Field(2, 0), new Field(3, 0))), 0, true, "Top left horizontal, left to right placement"},
            {new ArrayList<>(Arrays.asList(new Field(0, 0), new Field(0, 1), new Field(0, 2), new Field(0, 3))), 0, true, "Top left vertical, downwards placement"},
            {new ArrayList<>(Arrays.asList(new Field(9, 0), new Field(8, 0), new Field(7, 0), new Field(6, 0))), 0, true, "Top right horizontal, right to left placement"},
            {new ArrayList<>(Arrays.asList(new Field(9, 0), new Field(9, 1), new Field(9, 2), new Field(9, 3))), 0, true, "Top right vertical, downwards placement"},
            {new ArrayList<>(Arrays.asList(new Field(9, 3), new Field(9, 2), new Field(9, 1), new Field(9, 0))), 0, true, "Top right vertical, upwards placement"},
            {new ArrayList<>(Arrays.asList(new Field(9, 1), new Field(9, 2), new Field(9, 0), new Field(9, 3))), 0, true, "Top right vertical, in to out placement"},
            {new ArrayList<>(Arrays.asList(new Field(0, 9), new Field(1, 9), new Field(2, 9), new Field(3, 9))), 0, true, "Bottom left horizontal, left to right placement"},
            {new ArrayList<>(Arrays.asList(new Field(0, 9), new Field(0, 8), new Field(0, 7), new Field(0, 6))), 0, true, "Bottom left vertical, upwards placement"},
            {new ArrayList<>(Arrays.asList(new Field(9, 9), new Field(8, 9), new Field(7, 9), new Field(6, 9))), 0, true, "Bottom right horizontal, right to left placement"},
            {new ArrayList<>(Arrays.asList(new Field(9, 9), new Field(9, 8), new Field(9, 7), new Field(9, 6))), 0, true, "Bottom right vertical, upwards placement"},
            {new ArrayList<>(Arrays.asList(new Field(4, 4), new Field(5, 4))), 4, true, "Middle horizontal, left to right placement"},
            {new ArrayList<>(Arrays.asList(new Field(0, 0), new Field(1, 0))), 4, true, "Top left horizontal, left to right placement"},
            {new ArrayList<>(Arrays.asList(new Field(0, 0), new Field(0, 1))), 4, true, "Top left vertical, downwards placement"},
            {new ArrayList<>(Arrays.asList(new Field(9, 0), new Field(8, 0))), 4, true, "Top right horizontal, right to left placement"},
            {new ArrayList<>(Arrays.asList(new Field(9, 0), new Field(9, 1))), 4, true, "Top right vertical, downwards placement"},
            {new ArrayList<>(Arrays.asList(new Field(9, 1), new Field(9, 0))), 4, true, "Top right vertical, upwards placement"},
            {new ArrayList<>(Arrays.asList(new Field(0, 9), new Field(1, 9))), 4, true, "Bottom left horizontal, left to right placement"},
            {new ArrayList<>(Arrays.asList(new Field(0, 9), new Field(0, 8))), 4, true, "Bottom left vertical, upwards placement"},
            {new ArrayList<>(Arrays.asList(new Field(9, 9), new Field(8, 9))), 4, true, "Bottom right horizontal, right to left placement"},
            {new ArrayList<>(Arrays.asList(new Field(9, 9), new Field(9, 8))), 4, true, "Bottom right vertical, upwards placement"},});
    }

    @Before
    public void setUp() {
        game = new Game(new TestNetworker("Testplayer"));
    }

    /**
     * Test of placeShip method, of class Game.
     */
    @Test
    public void testPlaceShipValid() {
        System.out.println("testPlaceShipValid. Ship Size: " + game.ships.get(shipNr).size + " Case: " + testCaseDescription);
        int counter = 0;
        for (Field field : shipLocation) {
            assertTrue(field.x + "," + field.y + " is not WATER", game.myPlayfield.getFieldFromCoordinate(field.x, field.y).getState() == FieldState.WATER);
            game.placeShipPart(game.ships.get(shipNr), field.x, field.y);
            assertSame(field.x + "," + field.y + " doesn´t match SHIP field", game.myPlayfield.getFieldFromCoordinate(field.x, field.y), game.ships.get(shipNr).fields.get(counter));
            assertTrue(field.x + "," + field.y + " is not SHIP", game.myPlayfield.getFieldFromCoordinate(field.x, field.y).getState() == FieldState.SHIP);
            counter++;
        }

        assertTrue("Ship is not completed", game.ships.get(shipNr).isCompleted());
        game.myPlayfield.Print();
    }
}
