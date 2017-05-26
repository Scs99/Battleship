package battleship;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Louis Rast
 */
public class MultipleShipPlacementTest {

    private Game game;

    public MultipleShipPlacementTest() {
    }

    @Before
    public void setUp() {
        game = new Game(new TestNetworker("Testplayer"));
    }

    /**
     * Test of placeShip method, of class Game.
     */
    @Test
    public void testPlaceTwoShipsTouching() {
        System.out.println("testPlaceTwoShipsNextToEachother");

        // Place first ship
        game.placeShipPart(game.ships.get(3), 4, 4);
        game.placeShipPart(game.ships.get(3), 4, 5);

        // Try invalid placements for second ship
        assertFalse("Placing 3,4", game.placeShipPart(game.ships.get(4), 3, 4));
        assertFalse("Placing 3,5", game.placeShipPart(game.ships.get(4), 3, 5));
        assertFalse("Placing 5,4", game.placeShipPart(game.ships.get(4), 5, 4));
        assertFalse("Placing 5,5", game.placeShipPart(game.ships.get(4), 5, 5));
        assertFalse("Placing 4,3", game.placeShipPart(game.ships.get(4), 4, 3));
        assertFalse("Placing 4,6", game.placeShipPart(game.ships.get(4), 4, 6));
        assertFalse("Placing 4,4", game.placeShipPart(game.ships.get(4), 4, 4));
        assertFalse("Placing 4,5", game.placeShipPart(game.ships.get(4), 4, 5));

        // Assert invalid placements have not changed the playfield
        assertTrue("Check for water 3,4", game.myPlayfield.getFieldFromCoordinate(3, 4).getState() == FieldState.WATER);
        assertTrue("Check for water 3,5", game.myPlayfield.getFieldFromCoordinate(3, 5).getState() == FieldState.WATER);
        assertTrue("Check for water 5,4", game.myPlayfield.getFieldFromCoordinate(5, 4).getState() == FieldState.WATER);
        assertTrue("Check for water 5,5", game.myPlayfield.getFieldFromCoordinate(5, 5).getState() == FieldState.WATER);
        assertTrue("Check for water 4,3", game.myPlayfield.getFieldFromCoordinate(4, 3).getState() == FieldState.WATER);
        assertTrue("Check for water 4,6", game.myPlayfield.getFieldFromCoordinate(4, 6).getState() == FieldState.WATER);

        game.myPlayfield.Print();
    }

    /**
     * Test of placeShip method, of class Game.
     */
    @Test
    public void testPlaceAllShipsValid() {
        System.out.println("testPlaceAllShipsValid");

        // Place first ship
        game.placeShipPart(game.ships.get(3), 4, 4);
        game.placeShipPart(game.ships.get(3), 4, 5);

        assertTrue("Ship 0, Placing 2,7", game.placeShipPart(game.ships.get(0), 2, 7));
        assertTrue("Ship 0, Placing 3,7", game.placeShipPart(game.ships.get(0), 3, 7));
        assertTrue("Ship 0, Placing 4,7", game.placeShipPart(game.ships.get(0), 4, 7));
        assertTrue("Ship 0, Placing 5,7", game.placeShipPart(game.ships.get(0), 5, 7));

        assertTrue("Ship 1, Placing 6,2", game.placeShipPart(game.ships.get(1), 6, 2));
        assertTrue("Ship 1, Placing 5,2", game.placeShipPart(game.ships.get(1), 5, 2));
        assertTrue("Ship 1, Placing 4,2", game.placeShipPart(game.ships.get(1), 4, 2));

        assertTrue("Ship 2, Placing 2,4", game.placeShipPart(game.ships.get(2), 2, 4));
        assertTrue("Ship 2, Placing 2,5", game.placeShipPart(game.ships.get(2), 2, 5));

        assertTrue("Ship 4, Placing 6,4", game.placeShipPart(game.ships.get(4), 6, 4));
        assertTrue("Ship 4, Placing 6,5", game.placeShipPart(game.ships.get(4), 6, 5));

        game.myPlayfield.Print();
    }

    /**
     * Test of placeShip method, of class Game.
     */
    @Test
    public void testPlaceTwoShipsTouchinDiagonally() {
        System.out.println("testPlaceToShipsDiagonally");

        // Place first ship
        game.placeShipPart(game.ships.get(3), 4, 4);
        game.placeShipPart(game.ships.get(3), 4, 5);

        // Try invalid placements for second ship
        assertFalse("Placing 3,3", game.placeShipPart(game.ships.get(4), 3, 3));
        assertFalse("Placing 3,6", game.placeShipPart(game.ships.get(4), 3, 6));
        assertFalse("Placing 5,3", game.placeShipPart(game.ships.get(4), 5, 3));
        assertFalse("Placing 5,6", game.placeShipPart(game.ships.get(4), 5, 6));

        // Assert invalid placements have not changed the playfield
        assertTrue("Check for water 3,3", game.myPlayfield.getFieldFromCoordinate(3, 3).getState() == FieldState.WATER);
        assertTrue("Check for water 3,6", game.myPlayfield.getFieldFromCoordinate(3, 6).getState() == FieldState.WATER);
        assertTrue("Check for water 5,3", game.myPlayfield.getFieldFromCoordinate(5, 3).getState() == FieldState.WATER);
        assertTrue("Check for water 5,6", game.myPlayfield.getFieldFromCoordinate(5, 6).getState() == FieldState.WATER);

        game.myPlayfield.Print();
    }
}
