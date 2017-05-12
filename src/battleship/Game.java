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

    public Game(Participant opponent) {

        this.opponent = opponent;
        this.myPlayfield = new Playfield(10);
        this.opponentPlayfield = new Playfield(10);
        this.ships = new Ship[5];

    }

    Ship getShipOfField(Field myField) {
        for (Field field : myPlayfield.fields) {
            for (Ship ship : ships) {
                if (Arrays.asList(ship.fields).contains(myField)) {
                    return ship;
                }
            }
        }
    }

    void setUpShips() {
        this.ships[0] = new Ship(4);
        this.ships[1] = new Ship(3);
        this.ships[2] = new Ship(2);
        this.ships[3] = new Ship(2);
        this.ships[0] = new Ship(2);
    }

}
