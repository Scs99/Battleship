/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

/**
 *
 * @author admin
 */
public interface IGameChanged {
    void onGameChanged(Playfield myPlayfield, Playfield opponentPlayfield, String statusText, boolean isErrorText, GameState gameState);
}
