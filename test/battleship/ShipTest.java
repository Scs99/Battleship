package battleship;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Scs
 */
public class ShipTest {

    public ShipTest() {
    }

    /**
     * Test of isDestroyed method, of class Ship.
     */
    @Test
    public void testIsDestroyed() {
        System.out.println("isDestroyed");
        Playfield playfield = new Playfield(10);
        Ship ship = new Ship(3);

        ship.addShipPart(playfield.fields[0]);
        ship.addShipPart(playfield.fields[1]);
        ship.addShipPart(playfield.fields[2]);

        playfield.fields[0].setShip();
        playfield.fields[1].setShip();
        playfield.fields[2].setShip();

        playfield.fields[0].setHit();
        playfield.fields[1].setHit();
        playfield.fields[2].setHit();

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

        ship.addShipPart(playfield.fields[0]);
        ship.addShipPart(playfield.fields[1]);
        ship.addShipPart(playfield.fields[2]);

        playfield.fields[0].setShip();
        playfield.fields[1].setShip();
        playfield.fields[2].setShip();

        assertEquals(true, ship.isCompleted());
    }
}
