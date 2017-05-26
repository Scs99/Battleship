package battleship;

import battleship.network.HitRequest;
import battleship.network.HitResponse;
import battleship.network.INetworker;
import battleship.network.NetworkPackage;
import battleship.network.StartGameRequest;
import java.util.ArrayList;
import java.util.Random;

/**
 * Game Dei Spiellogik des Spiels Battleship.
 *
 * @author Louis Rast
 */
public class Game implements IGame, IHitResponseReceived, IHitRequestReceived, IStartGameRequest {

    private final ArrayList<IGameChanged> gameChangedListeners = new ArrayList<>();

    public final INetworker myNetworker;
    private boolean myTurn;
    private String statusText;
    private GameState gameState;
    private Random rand;
    public final int myFirstTurnRandomNumber;
    private int opponentFirstTurnRandomNumber;
    private boolean opponentStartRequestReceived;

    /**
     * Das eigene Spielfeld, beeinhaltet die eigenen Schiffe.
     */
    public final Playfield myPlayfield;

    /**
     * Das gegnerische Spielfeld, beeinhaltet Schüsse und getroffene
     * Schiffsteile.
     */
    public final Playfield opponentPlayfield;

    /**
     * Die eigenen Schiffe.
     */
    public final ArrayList<Ship> ships;

    /**
     * Gibt den aktuellen Status des Spiels als Stirng zurück.
     *
     * @return Den aktuellen Spielstatus.
     */
    public String getStatusText() {
        return statusText;
    }

    /**
     * Startet den Spielzug.
     */
    public void startMyTurn() {
        myTurn = true;
        gameState = GameState.IS_MYTURN;
        gameChanged();
    }

    /**
     * Beendet den Spielzug.
     */
    public void endMyTurn() {
        myTurn = false;
        gameState = GameState.IS_NOT_MYTURN;
        gameChanged();
    }

    /**
     * Fragt ab ob der Spieler aktuell am Zug ist oder nicht.
     *
     * @return TRUE wenn der Spieler am Zug ist, FALSE wenn nicht.
     */
    public boolean isMyTurn() {
        return myTurn;
    }

    /**
     * Das eigentliche Spiel.
     *
     * @param networker Der Networker welcher zu diesem Spiel gehört.
     */
    public Game(final INetworker networker) {

        this.myNetworker = networker;

        this.myPlayfield = new Playfield(10);
        this.opponentPlayfield = new Playfield(10);
        this.ships = new ArrayList<>(0);
        this.ships.add(new Ship(4));
        this.ships.add(new Ship(3));
        this.ships.add(new Ship(2));
        this.ships.add(new Ship(2));
        this.ships.add(new Ship(2));
        this.myTurn = false;
        this.statusText = "Willkommen zum Spiel Battleship.";

        // Registrieren auf die Events des Networker wenn er HitReceived oder HitResponse Packete empfängt.
        this.myNetworker.registerHitRequest(this);
        this.myNetworker.registerHitResponse(this);
        this.myNetworker.registerStartGameRequest(this);
        this.gameState = GameState.IS_PLACING;

        this.rand = new Random();
        this.myFirstTurnRandomNumber = rand.nextInt(10000);
    }

    public void initialize() {
        gameChanged();
    }

    public void placeShip(final int x, final int y) {
        for (Ship ship : ships) {
            if (!ship.isCompleted()) {
                placeShipPart(ship, x, y);

                if (areAllShipsPlaced()) {
                    gameState = GameState.IS_WAITING_FOR_OPPONENT;
                    this.myNetworker.send(new NetworkPackage(new StartGameRequest(this.myFirstTurnRandomNumber), "StartGameRequest"));
                    determineFirstTurn();
                    gameChanged();
                }
                return;
            }
        }
        gameState = GameState.IS_MYTURN;
        gameChanged();
    }

    public void determineFirstTurn() {
        if (areAllShipsPlaced() && opponentStartRequestReceived) {
            if (myFirstTurnRandomNumber >= opponentFirstTurnRandomNumber) {
                // If same numbers are drawn, its first come first served.
                startMyTurn();
            } else {
                endMyTurn();
            }
        }
    }

    private boolean areAllShipsPlaced() {
        for (Ship ship : ships) {
            if (!ship.isCompleted()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Lässt auf dem Spielfeld ein Schiffteil platzieren.
     *
     * @param shipToPlace Das Schiff von welchem ein Schiffteil platziert werden
     * soll.
     * @param x Die X-Koordinate an welcher der Schiffteil platziert werden
     * soll.
     * @param y Die Y-Koordinate an welcher der Schiffteil platziert werden
     * soll.
     * @return TRUE falls platzieren erfolgtreich war, FALSE wenn nicht.
     */
    public boolean placeShipPart(final Ship shipToPlace, final int x, final int y) {
        if (shipToPlace.isCompleted()) {
            this.statusText = "Das ausgewählte Schiff wurde bereits platziert.";
            gameChanged();
            return false;
        }
        // Erstes Element darf nicht neben existierenden Schiffen platziert werden
        if (shipToPlace.nrOfPlacedParts() == 0) {
            ArrayList<Field> surrounding = myPlayfield.getSurrounding(x, y);
            for (Field field : surrounding) {
                if (field.getState() == FieldState.SHIP) {
                    this.statusText = "Sie können Schiffe nicht direkt nebeneinander platzieren. Versuchen Sie ein anderes Feld.";
                    gameChanged();
                    return false;
                }
            }
            addAndMarkShipPart(shipToPlace, myPlayfield.getFieldFromCoordinate(x, y));
            gameChanged();
            return true;
        }
        // Zweites Element darf nicht neben exisiterenden Schiffen platziert werden
        // und muss an das erste Element angrenzen.
        if (shipToPlace.nrOfPlacedParts() == 1) {
            Field toBePlaced = myPlayfield.getFieldFromCoordinate(x, y);
            ArrayList<Field> validNeighbours = myPlayfield.getValideNeighbours(shipToPlace.fields.get(0).x, shipToPlace.fields.get(0).y);
            if (!validNeighbours.contains(toBePlaced)) {
                this.statusText = "Der zweite Schiffteil muss an den ersten angrenzen. Versuchen Sie ein anderes Feld.";
                gameChanged();
                return false;
            }

            for (Ship ship : ships) {
                if (ship.isCompleted()) {
                    for (Field shipField : ship.fields) {
                        ArrayList<Field> surrounding = myPlayfield.getSurrounding(shipField.x, shipField.y);
                        if (surrounding.contains(toBePlaced)) {
                            this.statusText = "Sie können Schiffe nicht direkt nebeneinander platzieren. Versuchen Sie ein anderes Feld.";
                            gameChanged();
                            return false;
                        }
                    }
                }
            }

            addAndMarkShipPart(shipToPlace, toBePlaced);
            gameChanged();
            return true;
        }

        // Alle Elemente > 2 müssen auf der selben Achse wie die beiden platziert werden,
        // und dürfennicht neben existierenden Schiffen platziert werden.
        Field toBePlaced = myPlayfield.getFieldFromCoordinate(x, y);

        if (shipToPlace.fields.get(0).y == shipToPlace.fields.get(1).y) {

            if (toBePlaced.y != shipToPlace.fields.get(0).y) {
                this.statusText = "Sie müssen den Schiffsteil am Anfang oder am Ende des Schiffes platzieren. Versuchen Sie ein anderes Feld.";
                gameChanged();
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
                addAndMarkShipPart(shipToPlace, toBePlaced);
                gameChanged();
                return true;
            }
        } else if (shipToPlace.fields.get(0).x == shipToPlace.fields.get(1).x) {

            if (toBePlaced.x != shipToPlace.fields.get(0).x) {
                this.statusText = "Sie müssen den Schiffsteil am Anfang oder am Ende des Schiffes platzieren. Versuchen Sie ein anderes Feld.";
                gameChanged();
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
                addAndMarkShipPart(shipToPlace, toBePlaced);
                gameChanged();
                return true;
            }
        }
        return false;
    }

    /**
     * Schiesst auf das Feld des Gegners.
     *
     * @param x Die X-Koordinate an welcher der Schuss erfolgen soll.
     * @param y Die Y-Koordinate an welcher der Schuss erfolgen soll.
     */
    public HitRequest shootAtOpponent(final int x, final int y) {
        endMyTurn();
        opponentPlayfield.shootAt(x, y);
        HitRequest hitRequest = new HitRequest(x, y);
        this.statusText = "Schuss auf X:" + x + "| Y:" + y + ". Warte auf Rückmeldung des Gegners.";
        myNetworker.send(new NetworkPackage(hitRequest, "HitRequest"));
        gameChanged();
        return hitRequest;
    }

    public Ship getShipOfField(final Field myField) {
        for (Ship ship : ships) {
            if (ship.fields.contains(myField)) {
                return ship;
            }
        }
        return null;
    }

    private void addAndMarkShipPart(final Ship ship, final Field shipPart) {
        ship.addShipPart(shipPart);
        myPlayfield.placeAt(shipPart.x, shipPart.y);
        this.statusText = "Sie haben " + ship.nrOfPlacedParts() + "/" + ship.size + " Schiffteilen platziert.";
    }

    public boolean hasLost() {
        for (Ship ship : ships) {
            if (!ship.isDestroyed()) {
                return false;
            }
        }
        return true;
    }

    public boolean hasWon() {
        int nrOfShipParts = 0;
        int nrOfDestroyedShipParts = 0;
        for (Ship ship : ships) {
            nrOfShipParts += ship.size;
        }
        for (Field field : opponentPlayfield.fields) {
            if (field.getState() == FieldState.SHIP_HIT) {
                nrOfDestroyedShipParts++;
            }
        }
        if (nrOfDestroyedShipParts == nrOfShipParts) {
            return true;
        }
        return false;
    }

    private void setStatusText(String statusText, boolean isError) {
        this.statusText = statusText;
    }

    /**
     * Verarbeitet die Rückmeldung des gegners auf einen Schuss.
     *
     * @param hitResponse Die Antwort des Gegners auf den Schuss.
     */
    @Override
    public void onHitResponseReceived(final HitResponse hitResponse) {
        if (hitResponse.hit) {
            opponentPlayfield.placeAt(hitResponse.x, hitResponse.y);
            opponentPlayfield.shootAt(hitResponse.x, hitResponse.y);
            if (hasWon()) {
                this.statusText = "Gewonnen! Alle gegnerischen Schiffe zerstört!";
                this.gameState = GameState.IS_OVER;
            } else if (hitResponse.shipDestroyed) {
                this.statusText = "Gegnerisches Schiff zerstört! Schiessen Sie erneut.";
                startMyTurn();
            } else {
                this.statusText = "Treffer auf ein gegnerisches Schiff! Schiessen Sie erneut.";
                startMyTurn();
            }
        } else {
            this.statusText = "Schuss ins Wasser. Warten sie auf den Zug des Gegners.";
            endMyTurn();
        }
        gameChanged();
    }

    /**
     * Verarbeitet denn Schuss eines Gegners auf das eigene Feld.
     *
     * @param hitRequest Der Schuss des Gegners.
     */
    @Override
    public void onHitRequestReceived(HitRequest hitRequest) {
        HitResponse hitResponse;
        myPlayfield.shootAt(hitRequest.x, hitRequest.y);
        Ship possibleShip = getShipOfField(myPlayfield.getFieldFromCoordinate(hitRequest.x, hitRequest.y));

        if (possibleShip == null) {
            hitResponse = new HitResponse(hitRequest.x, hitRequest.y, false, false);
            this.statusText = "Der Gegner hat Ihre Schiffe verfehlt. Sie sind am Zug.";
            startMyTurn();
        } else {
            hitResponse = new HitResponse(hitRequest.x, hitRequest.y, true, possibleShip.isDestroyed());
            if (hasLost()) {
                this.statusText = "Verlorern! Alle Ihre Schiffe wurden zerstört.";
                this.gameState = GameState.IS_OVER;
            } else if (possibleShip.isDestroyed()) {
                this.statusText = "Der Gegner hat eines Ihrer Schiffe zerstört! Er darf erneut schiessen.";
                endMyTurn();
            } else {
                this.statusText = "Der Gegner hat eines Ihrer Schiffe getroffen! Er darf erneut schiessen.";
                endMyTurn();
            }
        }
        myNetworker.send(new NetworkPackage(hitResponse, "HitResponse"));
        gameChanged();
    }

    @Override
    public void registerGameChanged(IGameChanged receiver) {
        gameChangedListeners.add(receiver);
    }

    public void gameChanged() {
        for (IGameChanged receiver : gameChangedListeners) {
            receiver.onGameChanged(this.myPlayfield, this.opponentPlayfield, this.statusText, false, this.gameState);
        }
    }

    @Override
    public void onStartGameRequestReceived(StartGameRequest startGameRequest) {
        this.opponentFirstTurnRandomNumber = startGameRequest.randomFirstTurn;
        this.opponentStartRequestReceived = true;
        determineFirstTurn();
    }

}
