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
public class Networker {

    private static Networker instance;

    public Networker()
    {
    }
    
    
    public static void initialize()
    {
        Networker.instance = new Networker();
    }
    
    
    public void discoverPlayers()
    {
        
    }
    
    
    public boolean askPlayerToJoin(String ipAddress, String hostname)
    {
        return true;     // vorläufig zum compilieren
    }
    
    
    public boolean connectToPlayer(String ipAddress,String hostname)
    {
        return true;     // vorläufig zum compilieren
    }
    
    
    
}
