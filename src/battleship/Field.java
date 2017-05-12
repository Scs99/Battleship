/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

/**
 *
 * @author Scs
 */
public class Field {
    
    private FieldState state;
    public final int x;
    public final int y;
    
    public Field(int x, int y){
        this.x = x;
        this.y = y;
        this.state = FieldState.WATER;
    }

    public FieldState getState() {
        return state;
    }

    void setHit(){
        if(state == FieldState.WATER){
            state = FieldState.WATER_HIT;
        }
        if(state == FieldState.SHIP){
            state = FieldState.SHIP_HIT;
        }
    }
    
    void setShip(){
        state = FieldState.SHIP;
    }

  
    
    
}
