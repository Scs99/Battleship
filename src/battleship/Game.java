/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

import java.util.Arrays;

/**
 * Game
 *
 * @author Louis Rast
 */
public class Game {

    public final Participant opponent;
    public final Playfield myPlayfield;
    public final Playfield opponentPlayfield;
    public final Ship[] ships;

    public Game(final Participant opponent) {

        this.opponent = opponent;
        this.myPlayfield = new Playfield(10);
        this.opponentPlayfield = new Playfield(10);
        this.ships = new Ship[]{
            new Ship(4),
            new Ship(3),
            new Ship(2),
            new Ship(2),
            new Ship(2)
        };
    }

    private Ship getShipOfField(final Field myField) {
        for (Ship ship : ships) {
            if (Arrays.asList(ship.fields).contains(myField)) {
                return ship;
            }
        }
        return null;
    }

    public void shootAt(final int x, final int y) {
        opponentPlayfield.shootAt(x, y);
        // to Networker HitRequest(x, y);        
    }

    public void shootAtReponse(final HitResponse hitResponse) {
        if(hitResponse.hit){
            opponentPlayfield.placeAt(hitResponse.x, hitResponse.y);
            opponentPlayfield.shootAt(hitResponse.x, hitResponse.y);
        }      
    }

    public void hitReceived(final HitRequest hitRequest) {
        myPlayfield.shootAt(hitRequest.x, hitRequest.y);
        Ship possibleShip = getShipOfField(myPlayfield.getFieldFromCoordinate(hitRequest.x, hitRequest.y));

        if (possibleShip == null) {
            // to Networker HitResponse(x, y, false, false);
        }

        // to Networker HitResponse(x, y, true, possibleShip.isDestroyed());
    }
}
