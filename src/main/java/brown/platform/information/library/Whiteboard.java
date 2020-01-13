package brown.platform.information.library;

import java.util.HashMap;
import java.util.Map;

import brown.auction.marketstate.IMarketPublicState;
import brown.auction.marketstate.IMarketState;
import brown.auction.marketstate.library.MarketPublicState;
import brown.platform.information.IWhiteboard;

//TODO: still need to accomodate different agents having different info. 

public class Whiteboard implements IWhiteboard {

  // map from market IDs to a list of market public states, for the timesteps. 
  // TODO: make the market state something that remembers. 
	private Map<Integer, IMarketPublicState> innerMarketWhiteboard;
	private Map<Integer, IMarketPublicState> outerMarketWhiteboard; 
	private Map<Integer, IMarketPublicState> simulationReportWhiteboard; 
	  
	
	public Whiteboard() {
		this.innerMarketWhiteboard = new HashMap<Integer, IMarketPublicState>(); 
		this.outerMarketWhiteboard = new HashMap<Integer, IMarketPublicState>(); 
		this.simulationReportWhiteboard = new HashMap<Integer, IMarketPublicState>(); 
	}

  @Override
  public void postInnerInformation(Integer marketID, Integer agentID, 
      IMarketPublicState marketPublicState) {
    IMarketPublicState innerMarketStates; 
    if (this.innerMarketWhiteboard.containsKey(marketID)) {
      innerMarketStates = this.innerMarketWhiteboard.get(marketID); 
    } else {
      innerMarketStates = new MarketPublicState(); 
    }
    innerMarketStates = marketPublicState; 
    this.innerMarketWhiteboard.put(marketID, innerMarketStates); 
  }

  @Override
  public void postOuterInformation(Integer marketID,
      IMarketPublicState marketPublicState) {
    this.outerMarketWhiteboard.put(marketID, marketPublicState); 
  }

  @Override
  public void postSimulationInformation(Integer marketID,
      IMarketPublicState marketPublicState) {
    this.simulationReportWhiteboard.put(marketID, marketPublicState);
    
  }
  
  @Override
  public IMarketPublicState getInnerInformation(Integer marketID, Integer agentID, Integer timeStep) {
    // TODO: fix and uncomment
    //return this.innerMarketWhiteboard.get(marketID).get(timeStep); 
    return null; 
  }

  @Override
  public IMarketPublicState getOuterInformation(Integer marketID) {
    return this.outerMarketWhiteboard.get(marketID); 
  }
  
  @Override
  public IMarketPublicState getSimulationInformation(Integer marketID) {
    return this.simulationReportWhiteboard.get(marketID); 
  }

  @Override
  public void clear() {
    this.innerMarketWhiteboard.clear();
    this.outerMarketWhiteboard.clear(); 
    this.simulationReportWhiteboard.clear(); 
  }

}
