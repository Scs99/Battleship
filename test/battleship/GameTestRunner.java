/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
/**
 *
 * @author admin
 */
public class GameTestRunner {
    public static void main(String[] args) {
      System.out.println("###################################################");
      System.out.println("#          Starting game tests...                 #");
      System.out.println("###################################################");   
      
      System.out.println("");  
      
      System.out.println("---------------------------------------------------");  
      System.out.println("Ship placement tests");  
      System.out.println("---------------------------------------------------");       
      Result ShipPlacementTest = JUnitCore.runClasses(ShipPlacementTest.class);

      for (Failure failure : ShipPlacementTest.getFailures()) {
         System.out.println(failure.toString());
      }
      
      System.out.println("---------------------------------------------------");  
      System.out.println("Ship position placement tests");  
      System.out.println("---------------------------------------------------");  
      Result ShipPositionPlacementTest = JUnitCore.runClasses(ShipPositionPlacementTest.class);

      for (Failure failure : ShipPositionPlacementTest.getFailures()) {
         System.out.println(failure.toString());
      }
		

      System.out.println("---------------------------------------------------");  
      System.out.println("Mutliple ship placement tests");  
      System.out.println("---------------------------------------------------");       
      Result MultipleShipPlacementTest = JUnitCore.runClasses(MultipleShipPlacementTest.class);

      for (Failure failure : MultipleShipPlacementTest.getFailures()) {
         System.out.println(failure.toString());
      }
      
      System.out.println("");  
      System.out.println("TEST RESULTS:");  
      System.out.println("Ship placement tests: " + ShipPlacementTest.wasSuccessful());
      System.out.println("Ship position placement tests: " + ShipPositionPlacementTest.wasSuccessful());
      System.out.println("Mutliple ship placement tests: " + MultipleShipPlacementTest.wasSuccessful()); 
   }   
}