/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

import battleship.network.INetworker;
import battleship.network.HitRequest;
import battleship.network.HitResponse;

/**
 *
 * @author admin
 */
public class TestOpponent implements INetworker {

    @Override
    public void registerHitResponse(IHitResponseReceived receiver) {
    }

    @Override
    public void registerHitRequest(IHitRequestReceived receiver) {
    }
    
    public void replyWith(HitResponse response) {
        // loop and send reply
    }
    
    public void sends(HitRequest request) {
        // look and send request
    }
}
