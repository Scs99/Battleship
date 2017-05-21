/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

import java.util.ArrayList;

/**
 *
 * @author Scs
 */
public class Ship {

    public final int size;
    public final ArrayList<Field> fields;

    public Ship(int size) {
        this.size = size;
        fields = new ArrayList<>(0);
    }

    public boolean isDestroyed() {
        for (Field item : fields) {
            if (item.getState() != FieldState.SHIP_HIT) {
                return false;
            }
        }
        return true;
    }

    public boolean isCompleted() {
        if (nrOfPlacedParts() < this.size) {
            return false;
        }
        return true;
    }

    public void addShipPart(Field shipPart) {
        fields.add(shipPart);
    }

    public int nrOfPlacedParts() {
        int nrOfPlacedParts = 0;
        for (Field item : fields) {
            if (item.getState() == FieldState.SHIP) {
                nrOfPlacedParts++;
            }
        }
        return nrOfPlacedParts;
    }
}
