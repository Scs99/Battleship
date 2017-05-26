/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship.network;

import battleship.IGameCanStart;

/**
 *
 * @author admin
 */
public interface IGui {
    void registerGameCanStart(IGameCanStart receiver);
}
