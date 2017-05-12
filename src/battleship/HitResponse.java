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
public class HitResponse extends HitRequest{
    
    private final boolean hit;
    private final boolean shipDestroyed;

    public HitResponse(boolean hit, boolean shipDestroyed, int x, int y)
    {
        super(x, y);
        this.hit = hit;
        this.shipDestroyed = shipDestroyed;
    }
    
    
    
}
