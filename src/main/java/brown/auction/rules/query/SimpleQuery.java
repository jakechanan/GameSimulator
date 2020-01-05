package brown.auction.rules.query;

import java.util.List;

import brown.auction.marketstate.IMarketState;
import brown.auction.rules.IQueryRule;
import brown.communication.messages.IActionMessage;
import brown.communication.messages.IActionRequestMessage;
import brown.communication.messages.library.ActionRequestMessage;

public class SimpleQuery implements IQueryRule {

  @Override
  public void makeTradeRequest(Integer marketID, IMarketState state,
      List<IActionMessage> bids, Integer agentID) {
    IActionRequestMessage actionRequestMessage = new ActionRequestMessage(-1, marketID, agentID); 
    state.setTRequest(actionRequestMessage);
  }

}
