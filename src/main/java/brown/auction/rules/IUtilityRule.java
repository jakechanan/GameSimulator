package brown.auction.rules;

import java.util.List;
import java.util.Map;

import brown.auction.marketstate.IMarketState;
import brown.auction.type.valuation.IType;
import brown.communication.messages.IActionMessage;

/**
 * An allocation rule allocates tradeables to agents.
 * @author andrew
 */
public interface IUtilityRule {

  /**
   * Sets an allocation in the market internal state.
   * @param state market state.
   */
   void setAllocation(IMarketState state, List<IActionMessage> messages, Map<Integer, IType> agentTypes);

}
