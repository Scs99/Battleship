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
public class ConnectionDetails implements Serializable {
    
    public final String ip;
    public final int port;

    public ConnectionDetails(String ip, int port)
    {
        this.ip = ip;
        this.port = port;
    }
    
}
