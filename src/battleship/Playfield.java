/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

import java.util.ArrayList;
import java.util.Collections;

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
    
    private boolean checkXBound(int x){
        if((x >= 0) && (x < this.playFieldWidth)){
            return true;
        }
        else{
            return false;
        }
    }
    
     private boolean checkYBound(int y){
        if((y >= 0) && (y < this.playFieldWidth)){
            return true;
        }
        else{
            return false;
        }
    }
    
    public ArrayList<Field> getSurrounding(int x, int y){
        ArrayList<Field> surroundedFields = new ArrayList<>();
       
        if(checkXBound(x) && checkYBound(y-1)){
            surroundedFields.add(fields[(y-1)*10+(x)]);
        }
        if(checkXBound(x+1) && checkYBound(y-1)){
            surroundedFields.add(fields[(y-1)*10+(x+1)]);
        }
        if(checkXBound(x+1) && checkYBound(y)){
            surroundedFields.add(fields[(y)*10+(x+1)]);
        }
        if(checkXBound(x+1) && checkYBound(y+1)){
            surroundedFields.add(fields[(y+1)*10+(x+1)]);
        }
        if(checkXBound(x) && checkYBound(y+1)){
            surroundedFields.add(fields[(y+1)*10+(x)]);
        }
        if(checkXBound(x-1) && checkYBound(y+1)){
            surroundedFields.add(fields[(y+1)*10+(x-1)]);
        }
        if(checkXBound(x-1) && checkYBound(y)){
             surroundedFields.add(fields[(y)*10+(x-1)]); 
        }
        if(checkXBound(x-1) && checkYBound(y-1)){
             surroundedFields.add(fields[(y-1)*10+(x-1)]); 
        }
        return surroundedFields;
    
    }
    
    public ArrayList<Field> getValideNeighbours(int x, int y){
        ArrayList<Field> valideNeigboursFields = new ArrayList<>();
        if(checkXBound(x) && checkYBound(y-1)){
            valideNeigboursFields.add(fields[(y-1)*10+(x)]);
        }
        if(checkXBound(x+1) && checkYBound(y)){
            valideNeigboursFields.add(fields[(y)*10+(x+1)]);
        }
        if(checkXBound(x) && checkYBound(y+1)){
            valideNeigboursFields.add(fields[(y+1)*10+(x)]);
        }
        if(checkXBound(x-1) && checkYBound(y)){
            valideNeigboursFields.add(fields[(y)*10+(x-1)]);
        }
       
        
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
