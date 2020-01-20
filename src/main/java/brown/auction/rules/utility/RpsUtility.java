package brown.auction.rules.utility;

import brown.auction.marketstate.IMarketState;
import brown.auction.rules.AbsRule;
import brown.auction.rules.IUtilityFn;
import brown.auction.rules.IUtilityRule;
import brown.communication.messages.IActionMessage;
import brown.platform.accounting.IAccountUpdate;
import brown.platform.accounting.library.AccountUpdate;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class RpsUtility extends AbsRule implements IUtilityRule {
    private IUtilityFn utilFn;

    public RpsUtility() {
        this.utilFn = new RpsUtilityFn();
    }

    @Override
    public void setAllocation(IMarketState state, List<IActionMessage> messages) {
        List<IAccountUpdate> acctUpdates = new LinkedList<IAccountUpdate>();
        Map<Integer, Double> utils = utilFn.getAgentUtilities(messages);

        // for each agent...
        for (Integer agentID : utils.keySet()) {
            acctUpdates.add(new AccountUpdate(agentID, -1, utils.get(agentID)));
        }

        state.setUtilities(acctUpdates);
    }

}
