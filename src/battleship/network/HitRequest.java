/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship.network;

import java.io.Serializable;

/**
 *
 * @author Maï
 */
public class HitRequest implements Serializable{
    
    public final int x;
    public final int y;

    public HitRequest(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
    
    
}
