package brown.communication.messages;

import brown.auction.marketstate.IMarketPublicState;

public interface IActionRequestMessage extends IServerToAgentMessage {
  
  public Integer getAgentID(); 
  
  public Integer getAuctionID();  
  
  public void addInformation(IMarketPublicState publicState); 
  
}
