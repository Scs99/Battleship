/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

import java.util.ArrayList;

/**
 *
 * @author Scs
 */
public class Ship {
    public final int size;
    public final ArrayList<Field> ListFields;
    
    public Ship(int size){
        this.size = size;
        ListFields = new ArrayList<>(size);
    }
    
    public boolean isDestroyed(){
        for(Field item : ListFields){
            if(item.getState() != FieldState.SHIP_HIT){
                return false;
            }
        }
        return true;
    }
    
    public boolean isCompleted(){
        for(Field item : ListFields){
            if(item.getState() != FieldState.SHIP){
                return false;
            }
        }
        return true;
    }

    
}
