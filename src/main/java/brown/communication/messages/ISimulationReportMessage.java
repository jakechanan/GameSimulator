package brown.communication.messages;

import java.util.Map;

import brown.auction.marketstate.IMarketPublicState;

public interface ISimulationReportMessage extends IServerToAgentMessage  {

  /**
   * getMarketResults returns a map from MarketID to the
   * @return
   */
  public Map<Integer, IMarketPublicState> getMarketResults();
  
}
