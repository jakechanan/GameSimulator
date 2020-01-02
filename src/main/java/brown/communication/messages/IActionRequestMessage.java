package brown.communication.messages;

import brown.auction.marketstate.IMarketState;

public interface IActionRequestMessage extends IServerToAgentMessage {
  
  public Integer getAgentID(); 
  
  public Integer getAuctionID();  
  
  public void addInformation(IMarketState publicState); 
  
}
