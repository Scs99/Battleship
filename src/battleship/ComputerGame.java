/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

import battleship.network.INetworker;
import java.util.Random;

/**
 *
 * @author admin
 */
public class ComputerGame extends Game {
    private Random rand;
    
    public ComputerGame(INetworker networker) {
        super(networker);
        rand = new Random();
    }
    
    public void placeAllShips(){
        for(Ship ship : ships){
            
            for(Field field : ship.fields){
                int x = rand.nextInt(9);
                int y = rand.nextInt(9);
                               
                placeShipPart(ship, x, y);
                
                if()
                
            }
        }
    }
    
}
