/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship.network;

/**
 *
 * @author Maï
 */
public class HitResponse extends HitRequest{
    
    public final boolean hit;
    public final boolean shipDestroyed;

    public HitResponse(int x, int y, boolean hit, boolean shipDestroyed)
    {
        super(x, y);
        this.hit = hit;
        this.shipDestroyed = shipDestroyed;
    }
    
    
    
}
