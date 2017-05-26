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
public class StartGameRequest implements Serializable {
    
    public final int randomFirstTurn;
    
    public StartGameRequest(int randomFirstTurn){
        this.randomFirstTurn = randomFirstTurn;
    }    
}
