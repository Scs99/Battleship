/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

/**
 *
 * @author Maï
 */
public class Participant {

    private final String ipAddress;
    private final String hostname;

    public Participant(String ipAddress, String hostname) {
        this.ipAddress = ipAddress;
        this.hostname = hostname;
    }
      
}
