/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

import battleship.gui.StartFrame;
import java.awt.EventQueue;

/**
 *
 * @author admin
 */
public class Main {
    
    public static void main(String[] args) {
         EventQueue.invokeLater(() -> new StartFrame());
    }
    
    
}
