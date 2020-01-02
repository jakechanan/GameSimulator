package brown.communication.messages.library;

import brown.auction.marketstate.IMarketState;
import brown.communication.messages.IActionRequestMessage;
import brown.user.agent.IAgent;

/**
 * Trade request message is sent by an open market to an agent to prompt bidding
 * in that market. An agent typically sends a trademessage in response to a
 * TradeRequestMessage
 * 
 * @author andrew
 *
 */
public class ActionRequestMessage extends AbsServerToAgentMessage
    implements IActionRequestMessage {

  private Integer auctionID;

  public ActionRequestMessage() {
    super(null, null);
  }

  // TODO: add a price?
  public ActionRequestMessage(Integer messageID, Integer auctionID,
      Integer agentID) {
    super(messageID, agentID);
    this.auctionID = auctionID;
  }

  @Override
  public Integer getAuctionID() {
    return this.auctionID;
  }

  @Override
  public void addInformation(IMarketState publicState) {
    // TODO:
  }

  @Override
  public void agentDispatch(IAgent agent) {
    agent.onActionRequestMessage(this);
  }

  @Override
  public String toString() {
    return "ActionRequestMessage [auctionID=" + auctionID + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((auctionID == null) ? 0 : auctionID.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    ActionRequestMessage other = (ActionRequestMessage) obj;
    if (auctionID == null) {
      if (other.auctionID != null)
        return false;
    } else if (!auctionID.equals(other.auctionID))
      return false;
    return true;
  }

}
