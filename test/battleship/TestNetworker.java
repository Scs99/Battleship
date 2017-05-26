package battleship;

import battleship.network.INetworker;
import battleship.network.NetworkPackage;

/**
 *
 * @author Louis Rast
 */
public class TestNetworker implements INetworker {

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

    @Override
    public void registerStartGameRequest(IStartGameRequest receiver) {
        // Do nothing
    }

}
