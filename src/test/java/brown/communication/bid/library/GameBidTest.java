package brown.communication.bid.library;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import brown.communication.action.library.GameAction;

public class GameBidTest {

  @Test
  public void testGameBid() {
    GameAction g = new GameAction(0); 
    assertTrue(g.getAction() == 0); 
    
    GameAction gTwo = new GameAction(9237498); 
    assertTrue(gTwo.getAction() == 9237498); 
    
    GameAction gThree = new GameAction(-12); 
    assertTrue(gThree.getAction() == -12); 
  }
}
