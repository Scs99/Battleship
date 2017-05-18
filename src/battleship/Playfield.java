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
public class Playfield {
    public final Field[] fields;
    public final int playFieldWidth;
    
    public Playfield(int playFieldWidth){
        
        this.playFieldWidth = playFieldWidth;
        fields = new Field[playFieldWidth*playFieldWidth];
        
        for(int y = 0; y < playFieldWidth; y++){
            for(int x = 0; x < playFieldWidth; x++){
                fields[y*10+x] = new Field(x,y);
            }
        }
    }
    
    public void shootAt(int x, int y){
        fields[y*10+x].setHit();
      
    }
    
    public void placeAt(int x, int y){
        fields[y*10+x].setShip();
        
    }
    
    public Field[] getSurrounding(int x, int y){
        Field[] surroundedFields = new Field[8];
        surroundedFields[0] = fields[(y-1)*10+(x-1)];
        surroundedFields[1] = fields[(y-1)*10+(x)];
        surroundedFields[2] = fields[(y-1)*10+(x+1)];
        surroundedFields[3] = fields[(y)*10+(x+1)];
        surroundedFields[4] = fields[(y+1)*10+(x+1)];
        surroundedFields[5] = fields[(y+1)*10+(x)];
        surroundedFields[6] = fields[(y+1)*10+(x-1)];
        surroundedFields[7] = fields[(y)*10+(x-1)];
        return surroundedFields;
    
    }
    
    public Field[] getValideNeighbours(int x, int y){
        Field[] valideNeigboursFields = new Field[4];
        valideNeigboursFields[0] = fields[(y)*10+(x-1)];
        valideNeigboursFields[1] = fields[(y-1)*10+(x)];
        valideNeigboursFields[2] = fields[(y+1)*10+(x)];
        valideNeigboursFields[3] = fields[(y)*10+(x+1)];
        return valideNeigboursFields;
    
    }
    
    
    
    public Field getFieldFromCoordinate(int x, int y){
        for(Field field : this.fields)
        {
            if((field.x == x) && (field.y == y)){
                return field;
            }
        }
        return null;
    }
}
