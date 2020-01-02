package brown.platform.market.library;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import brown.platform.game.IFlexibleRules;
import brown.platform.game.IGameBlock;
import brown.platform.game.library.SimultaneousGame;

public class SimultaneousMarketTest {

  @Test
  public void testSimultaneousMarket() {
    
    List<IFlexibleRules> rules = new LinkedList<IFlexibleRules>(); 
 
    
    IGameBlock s = new SimultaneousGame(rules); 
    
    assertEquals(s.getMarkets(), rules); 
  }
}
