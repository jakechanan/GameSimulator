package brown.auction.rules.termination;

import java.util.List;

import brown.auction.marketstate.IMarketState;
import brown.auction.rules.AbsRule;
import brown.auction.rules.ITerminationCondition;
import brown.communication.messages.IActionMessage;

public class NoBidsTermination extends AbsRule implements ITerminationCondition {

  @Override
  public void checkTerminated(IMarketState state, List<IActionMessage> messages) {
    if (messages.isEmpty()) {
      state.close(); 
    }
  }
}
