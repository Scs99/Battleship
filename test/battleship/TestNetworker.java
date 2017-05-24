/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

import battleship.network.HitRequest;
import battleship.network.HitResponse;
import battleship.network.INetworker;
import battleship.network.NetworkPackage;
import java.util.ArrayList;

/**
 *
 * @author admin
 */
public class TestNetworker implements INetworker {

    private final ArrayList<IHitResponseReceived> hitResponseReceivedListeners = new ArrayList<>();
    private final ArrayList<IHitRequestReceived> hitRequestReceivedListeners = new ArrayList<>();

    public final String myName;

    public TestNetworker(String myName) {
        this.myName = myName;
    }

    @Override
    public void registerHitResponse(IHitResponseReceived receiver) {
        //hitResponseReceivedListeners.add(receiver);
    }

    @Override
    public void registerHitRequest(IHitRequestReceived receiver) {
       // hitRequestReceivedListeners.add(receiver);
    }

    @Override
    public void send(NetworkPackage netPackage) {
        // Do nothing
    }

}
