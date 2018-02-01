package brown.server.library; 

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import brown.market.preset.AbsMarketPreset;
import brown.market.preset.library.SSSPRules;
import brown.setup.library.SSSPSetup;
import brown.tradeable.ITradeable;
import brown.tradeable.library.SimpleTradeable;
import brown.value.config.SSSPConfig;

/**
 * Use this class to run the server side of your game.
 * Just edit the rules to the game that you'd like to play.
 * 
 * This is the only file the server-side user should have to edit.
 * 
 * hmm... what to do about special registrations?
 * repeated registrations?
 */
public class SSSPSimulationTest {

  public static void main(String[] args) throws InterruptedException {
    // Create _ tradeables
    Set<ITradeable> allTradeables = new HashSet<ITradeable>(); 
    List<ITradeable> allTradeablesList = new LinkedList<ITradeable>();
    int numTradeables = 3;
    int i = 0;
    while (i<numTradeables){
      SimpleTradeable toAdd = new SimpleTradeable(i);            
      allTradeables.add(toAdd);
      allTradeablesList.add(toAdd);
      i++;
    }
    
    List<AbsMarketPreset> oneMarket = new LinkedList<AbsMarketPreset>();
    oneMarket.add(new SSSPRules(2));    
    SimulMarkets markets = new SimulMarkets(oneMarket);

    List<SimulMarkets> seq = new LinkedList<SimulMarkets>();  
    seq.add(markets);
    
    Simulation testSim = new Simulation(seq,new SSSPConfig(allTradeables),allTradeablesList,0.,new LinkedList<ITradeable>());    
    RunServer testServer = new RunServer(2121, new SSSPSetup());
    
    int numSims = 3;
    int delayTime = 5; 
    int lag = 150;
    
    testServer.runSimulation(testSim, numSims, delayTime, lag);
  }
}