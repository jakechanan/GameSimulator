package brown.auction.rules.termination;

import java.util.List;

import brown.auction.marketstate.IMarketState;
import brown.auction.rules.AbsRule;
import brown.auction.rules.ITerminationCondition;
import brown.communication.messages.IActionMessage;

public class OneShotTermination extends AbsRule implements ITerminationCondition {

  @Override
  public void checkTerminated(IMarketState state, List<IActionMessage> bids) {
    if (state.getTicks() > 0) {
      state.close(); 
    }
  }
}
