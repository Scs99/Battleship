/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

import battleship.network.StartGameRequest;

/**
 *
 * @author admin
 */
public interface IStartGameRequest {
    void onStartGameRequestReceived(StartGameRequest startGameRequest);
}
