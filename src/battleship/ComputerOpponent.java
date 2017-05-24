/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

import battleship.network.INetworker;
import battleship.network.HitResponse;
import java.util.ArrayList;

/**
 *
 * @author admin
 */
public class ComputerOpponent implements INetworker{
    
    private ArrayList<IHitResponseReceived> hitResponseReceivedListeners = new ArrayList<>();
    

    @Override
    public void registerHitResponse(IHitResponseReceived receiver) {
        hitResponseReceivedListeners.add(receiver);
    }

    @Override
    public void registerHitRequest(IHitRequestReceived receiver) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void sendTestHitResponse(){
        
        HitResponse test = new HitResponse(0, 0, true, false); //
        
        for(IHitResponseReceived receiver : hitResponseReceivedListeners){
            receiver.onHitResponseReceived(test);
        }
        
    }
    
}
