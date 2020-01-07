package brown.communication.messages;

import brown.communication.action.IBid;

public interface IActionMessage extends IAgentToServerMessage {

  public IBid getBid();
  
  public Integer getAuctionID();
  
  public Integer getAgentID();
  
}
