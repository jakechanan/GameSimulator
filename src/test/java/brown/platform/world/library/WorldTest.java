package brown.platform.world.library;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import brown.platform.managers.IDomainManager;
import brown.platform.managers.IGameManager;
import brown.platform.managers.library.DomainManager;
import brown.platform.managers.library.GameManager;
import brown.platform.world.IWorld;

public class WorldTest {

  @Test 
  public void testWorld() {
    IDomainManager aManager = new DomainManager(); 
    IGameManager mManager = new GameManager() ;
    
    IWorld aWorld = new World(aManager, mManager); 
    
    assertEquals(aWorld.getDomainManager(), aManager); 
    assertEquals(aWorld.getMarketManager(), mManager); 
  }
  
}
