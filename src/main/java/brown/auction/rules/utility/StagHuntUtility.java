package brown.auction.rules.utility;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import brown.auction.marketstate.IMarketState;
import brown.auction.rules.AbsRule;
import brown.auction.rules.IUtilityRule;
import brown.auction.type.valuation.IType;
import brown.communication.action.IGameAction;
import brown.communication.messages.IActionMessage;
import brown.platform.accounting.IAccountUpdate;
import brown.platform.accounting.library.AccountUpdate;

public class StagHuntUtility extends AbsRule implements IUtilityRule {

  @Override
  public void setAllocation(IMarketState state, List<IActionMessage> messages,
      Map<Integer, IType> types) {
    List<IAccountUpdate> acctUpdates = new LinkedList<IAccountUpdate>();
    Map<Integer, Double> utils = new HashMap<>();

    int STAG = 0, HARE = 1;

    if (messages.size() == 2) {
      IActionMessage a1Message = messages.get(0);
      IActionMessage a2Message = messages.get(1);

      int agent1 = a1Message.getAgentID();
      int agent2 = a2Message.getAgentID();

      int a1Action = ((IGameAction) a1Message.getBid()).getAction();
      int a2Action = ((IGameAction) a2Message.getBid()).getAction();

      double a1Util;
      double a2Util;

      if (a1Action == STAG && a2Action == STAG) {
        a1Util = 4;
        a2Util = 4;
      } else if (a1Action == STAG && a2Action == HARE) {
        a1Util = 0;
        a2Util = 3;
      } else if (a1Action == HARE && a2Action == STAG) {
        a1Util = 3;
        a2Util = 0;
      } else {
        a1Util = 3;
        a2Util = 3;
      }

      utils.put(agent1, a1Util);
      utils.put(agent2, a2Util);
    } else {
      utils.put(messages.get(0).getAgentID(), 0.0);
    }

    // for each agent...
    for (Integer agentID : utils.keySet()) {
      acctUpdates.add(new AccountUpdate(agentID, -1, utils.get(agentID)));
    }

    state.setUtilities(acctUpdates);
  }

}
