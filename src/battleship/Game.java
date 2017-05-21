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

/**
 * Game
 *
 * @author Louis Rast
 */
public class Game {

    public final Participant opponent;
    public final Playfield myPlayfield;
    public final Playfield opponentPlayfield;
    public final ArrayList<Ship> ships;

    public Game(final Participant opponent) {

        this.opponent = opponent;
        this.myPlayfield = new Playfield(10);
        this.opponentPlayfield = new Playfield(10);
        this.ships = new ArrayList<>(0);
        this.ships.add(new Ship(4));
        this.ships.add(new Ship(3));
        this.ships.add(new Ship(2));
        this.ships.add(new Ship(2));
        this.ships.add(new Ship(2));

    }

    private Ship getShipOfField(final Field myField) {
        for (Ship ship : ships) {
            if (ship.fields.contains(myField)) {
                return ship;
            }
        }
        return null;
    }

    public boolean placeShip(Ship shipToPlace, int x, int y) {
        if (shipToPlace.isCompleted()) {
            return true;
        }
        // Erstes Element darf nicht neben existierenden Schiffen platziert werden
        if (shipToPlace.nrOfPlacedParts() == 0) {
            ArrayList<Field> surrounding = myPlayfield.getSurrounding(x, y);
            for (Field field : surrounding) {
                if (field.getState() == FieldState.SHIP) {
                    return false;
                }
            }
            shipToPlace.addShipPart(myPlayfield.getFieldFromCoordinate(x, y));
            myPlayfield.placeAt(x, y);
            return true;
        }
        // Zweites Element darf nicht neben exisiterenden Schiffen platziert werden
        // und muss an das erste Element angrenzen.
        if (shipToPlace.nrOfPlacedParts() == 1) {
            Field toBePlaced = myPlayfield.getFieldFromCoordinate(x, y);
            ArrayList<Field> validNeighbours = myPlayfield.getValideNeighbours(shipToPlace.fields.get(0).x, shipToPlace.fields.get(0).y);
            if (!validNeighbours.contains(toBePlaced)) {
                return false;
            }

            for (Ship ship : ships) {
                if (ship.isCompleted()) {
                    for (Field shipField : ship.fields) {
                        ArrayList<Field> surrounding = myPlayfield.getSurrounding(shipField.x, shipField.y);
                        if (surrounding.contains(toBePlaced)) {
                            return false;
                        }
                    }
                }
            }

            shipToPlace.addShipPart(myPlayfield.getFieldFromCoordinate(x, y));
            myPlayfield.placeAt(x, y);
            return true;
        }

        // Alle Elemente > 2 müssen auf der selben Achse wie die beiden platziert werden,
        // und dürfennicht neben existierenden Schiffen platziert werden.
        Field toBePlaced = myPlayfield.getFieldFromCoordinate(x, y);

        if (shipToPlace.fields.get(0).y == shipToPlace.fields.get(1).y) {

            if (toBePlaced.y != shipToPlace.fields.get(0).y) {
                return false;
            }

            int xMax = shipToPlace.fields.get(0).x;
            int xMin = shipToPlace.fields.get(0).x;

            for (Field field : shipToPlace.fields) {
                if (field.x > xMax) {
                    xMax = field.x;
                }
                if (field.x < xMin) {
                    xMin = field.x;
                }
            }

            if ((toBePlaced.x == (xMax + 1)) || (toBePlaced.x == (xMin - 1))) {
                shipToPlace.addShipPart(toBePlaced);
                myPlayfield.placeAt(x, y);
                return true;
            }
        } else if (shipToPlace.fields.get(0).x == shipToPlace.fields.get(1).x) {

            if (toBePlaced.x != shipToPlace.fields.get(0).x) {
                return false;
            }

            int yMax = shipToPlace.fields.get(0).y;
            int yMin = shipToPlace.fields.get(0).y;

            for (Field field : shipToPlace.fields) {
                if (field.y > yMax) {
                    yMax = field.y;
                }
                if (field.y < yMin) {
                    yMin = field.y;
                }
            }

            if ((toBePlaced.y == (yMax + 1)) || (toBePlaced.y == (yMin - 1))) {
                shipToPlace.addShipPart(toBePlaced);
                myPlayfield.placeAt(x, y);
                return true;
            }
        }
        return false;
    }

    public void shootAt(final int x, final int y) {
        opponentPlayfield.shootAt(x, y);
        // to Networker HitRequest(x, y);        
    }

    public void shootAtReponse(final HitResponse hitResponse) {
        if (hitResponse.hit) {
            opponentPlayfield.placeAt(hitResponse.x, hitResponse.y);
            opponentPlayfield.shootAt(hitResponse.x, hitResponse.y);
        }
    }

    public void hitReceived(final HitRequest hitRequest) {
        myPlayfield.shootAt(hitRequest.x, hitRequest.y);
        Ship possibleShip = getShipOfField(myPlayfield.getFieldFromCoordinate(hitRequest.x, hitRequest.y));

        if (possibleShip == null) {
            // to Networker HitResponse(x, y, false, false);
        } else {
            // to Networker HitResponse(x, y, true, possibleShip.isDestroyed());   
        }
    }
}
