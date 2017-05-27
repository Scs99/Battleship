/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

import battleship.network.HitRequest;
import battleship.network.HitResponse;
import battleship.network.NetworkPackage;
import battleship.network.INetworker;
import battleship.network.StartGameRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author admin
 */
public class ComputerGame extends Game {

    private ArrayList<Field> shootingPool;
    private Random rand;

    public ComputerGame(INetworker networker) {
        super(networker);
        rand = new Random();

        shootingPool = new ArrayList<>();
        for (Field f : myPlayfield.fields) {
            shootingPool.add(f);
        }
    }

    public void placeAllShips() {
        ArrayList<Field> freeField = new ArrayList<>();
        for (Field f : myPlayfield.fields) {
            freeField.add(f);
        }

        for (Ship shipToPlace : ships) {

            while (!shipToPlace.isCompleted()) {

                if (shipToPlace.nrOfPlacedParts() == 0) {
                    int number = rand.nextInt(freeField.size());
                    Field fieldToPlace = freeField.get(number);

                    while (!placeShipPart(shipToPlace, fieldToPlace.x, fieldToPlace.y)) {
                        freeField.remove(number);
                        number = rand.nextInt(freeField.size());
                        fieldToPlace = freeField.get(number);
                    }
                    freeField.remove(fieldToPlace);
                }
                if (shipToPlace.nrOfPlacedParts() == 1) {
                    ArrayList<Field> possibleForPlacement = myPlayfield.getValideNeighbours(shipToPlace.fields.get(0).x, shipToPlace.fields.get(0).y);
                    int number = rand.nextInt(possibleForPlacement.size());

                    while (!placeShipPart(shipToPlace, possibleForPlacement.get(number).x, possibleForPlacement.get(number).y)) {
                        possibleForPlacement.remove(number);
                        number = rand.nextInt(possibleForPlacement.size());
                    }
                    freeField.remove(possibleForPlacement.get(number));
                }

                if (shipToPlace.nrOfPlacedParts() < shipToPlace.size) {

                    if (shipToPlace.fields.get(0).y == shipToPlace.fields.get(1).y) {
                        ArrayList<Field> possibleForPlacement = new ArrayList<>();

                        int y = shipToPlace.fields.get(0).y;
                        int xMax = shipToPlace.fields.get(0).x;
                        int xMin = shipToPlace.fields.get(0).x;

                        for (Field f : shipToPlace.fields) {
                            if (f.x > xMax) {
                                xMax = f.x;
                            }
                            if (f.x < xMin) {
                                xMin = f.x;
                            }
                        }

                        if ((xMax + 1) <= 9) {
                            possibleForPlacement.add(myPlayfield.getFieldFromCoordinate(xMax + 1, y));
                        }
                        if ((xMin - 1) >= 0) {
                            possibleForPlacement.add(myPlayfield.getFieldFromCoordinate(xMin - 1, y));
                        }

                        int number = rand.nextInt(possibleForPlacement.size());

                        while (!placeShipPart(shipToPlace, possibleForPlacement.get(number).x, possibleForPlacement.get(number).y)) {
                            possibleForPlacement.remove(number);
                            number = rand.nextInt(possibleForPlacement.size());
                        }
                        freeField.remove(possibleForPlacement.get(number));
                    }

                    if (shipToPlace.fields.get(0).x == shipToPlace.fields.get(1).x) {
                        ArrayList<Field> possibleForPlacement = new ArrayList<>();

                        int x = shipToPlace.fields.get(0).x;
                        int yMax = shipToPlace.fields.get(0).y;
                        int yMin = shipToPlace.fields.get(0).y;

                        for (Field f : shipToPlace.fields) {
                            if (f.y > yMax) {
                                yMax = f.y;
                            }
                            if (f.y < yMin) {
                                yMin = f.y;
                            }
                        }

                        if ((yMax + 1) <= 9) {
                            possibleForPlacement.add(myPlayfield.getFieldFromCoordinate(x, yMax + 1));
                        }
                        if ((yMin - 1) >= 0) {
                            possibleForPlacement.add(myPlayfield.getFieldFromCoordinate(x, yMin - 1));
                        }

                        int number = rand.nextInt(possibleForPlacement.size());

                        while (!placeShipPart(shipToPlace, possibleForPlacement.get(number).x, possibleForPlacement.get(number).y)) {
                            possibleForPlacement.remove(number);
                            number = rand.nextInt(possibleForPlacement.size());
                        }
                        freeField.remove(possibleForPlacement.get(number));
                    }
                }
            }
            //System.out.println("");
            //myPlayfield.Print();
            //System.out.println("");
            myNetworker.send(new NetworkPackage(new StartGameRequest(myFirstTurnRandomNumber), "StartGameRequest"));
        }
    }

    private void shoot() {
        try {
            TimeUnit.MILLISECONDS.sleep(rand.nextInt(1000) + 500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int number = rand.nextInt(shootingPool.size());
        shootAtOpponent(shootingPool.get(number).x, shootingPool.get(number).y);
        shootingPool.remove(number);
    }

    @Override
    public void onHitResponseReceived(final HitResponse hitResponse) {
        if (hitResponse.hit) {
            opponentPlayfield.placeAt(hitResponse.x, hitResponse.y);
            opponentPlayfield.shootAt(hitResponse.x, hitResponse.y);
            if (hasWon()) {
                //setStatusText("Gewonnen! Alle gegnerischen Schiffe zerstört!", false);
            } else if (hitResponse.shipDestroyed) {
                //setStatusText("Gegnerisches Schiff zerstört! Schiessen Sie erneut.", false);
            } else {
                //setStatusText("Treffer auf ein gegnerisches Schiff! Schiessen Sie erneut.", false);
            }
            startMyTurn();
            shoot();

        } else {
            //setStatusText("dsjhfgjfgjj Schuss ins Wasser. Warten sie auf den Zug des Gegners.", false);
            endMyTurn();
        }
        gameChanged();
    }

    @Override
    public void onHitRequestReceived(HitRequest hitRequest) {
        try {
            TimeUnit.MILLISECONDS.sleep(rand.nextInt(1000) + 500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        HitResponse hitResponse;
        Ship possibleShip = getShipOfField(myPlayfield.getFieldFromCoordinate(hitRequest.x, hitRequest.y));

        if (myPlayfield.getFieldFromCoordinate(hitRequest.x, hitRequest.y).getState() == FieldState.SHIP_HIT) {
            hitResponse = new HitResponse(hitRequest.x, hitRequest.y, false, false);
            myNetworker.send(new NetworkPackage(hitResponse, "HitResponse"));
            gameChanged();
            startMyTurn();
            shoot();
        } else {
            myPlayfield.shootAt(hitRequest.x, hitRequest.y);

            if (possibleShip == null) {
                hitResponse = new HitResponse(hitRequest.x, hitRequest.y, false, false);
                myNetworker.send(new NetworkPackage(hitResponse, "HitResponse"));
                gameChanged();
                startMyTurn();
                shoot();
            } else {
                hitResponse = new HitResponse(hitRequest.x, hitRequest.y, true, possibleShip.isDestroyed());
                myNetworker.send(new NetworkPackage(hitResponse, "HitResponse"));
                gameChanged();
                endMyTurn();
            }
        }
    }

    @Override
    public void onStartGameRequestReceived(StartGameRequest startGameRequest) {
        System.out.println("Determining first turn: My Nr.: " + myFirstTurnRandomNumber + " Opponent Nr. " + startGameRequest.randomFirstTurn);
        if (myFirstTurnRandomNumber >= startGameRequest.randomFirstTurn) {
            System.out.println("I get the first turn.");
            shoot();
        }
    }

}
