/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

import battleship.network.Participant;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author admin
 */
public class ActionListenerTest {

    private ComputerOpponent opponent;
    private Game game;

    public ActionListenerTest() {
    }

    @Before
    public void setUp() {
        opponent = new ComputerOpponent();
        //game = new Game(opponent);
    }

    @After
    public void tearDown() {
        opponent = null;
        game = null;
    }

    @Test
    public void testCallbackReceived() {
        
        /*
        game shoot at (3 3)
        opponent replies (3 3 hit)
        game shot at (3 4)
        opponent replies (3 4 lul miss)
        
        oppenent shoots at (3 3)
        oppenent.sends(new HitRequest(3, 3))
                
        
        
        
        opponent.sendTestHitResponse();
        game.opponentPlayfield.Print();    
*/
    }

}
