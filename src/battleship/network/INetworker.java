/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship.network;

import battleship.IHitRequestReceived;
import battleship.IHitResponseReceived;

/**
 *
 * @author admin
 */
public interface INetworker {
    
    void registerHitResponse(IHitResponseReceived receiver);
    void registerHitRequest(IHitRequestReceived receiver);
    void send(NetworkPackage netPackage);
    
}
