package brown.auction.rules;

import brown.auction.marketstate.IMarketState;
import brown.communication.messages.IActionMessage;

import java.util.List;
import java.util.Map;

public interface IUtilityFn {
    Map<Integer, Double> getAgentUtilities(List<IActionMessage> messages);

    List<Integer> getPossibleActions();
}
