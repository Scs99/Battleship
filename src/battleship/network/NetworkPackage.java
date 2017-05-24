/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship.network;

import java.io.Serializable;

/**
 *
 * @author admin
 */
public class NetworkPackage implements Serializable {
    public final Object object;
    public final String typeOfObject;
    
    public NetworkPackage(Object objectToSend, String typeOfObject) {
        this.object = objectToSend;
        this.typeOfObject = typeOfObject;
    }       
}
