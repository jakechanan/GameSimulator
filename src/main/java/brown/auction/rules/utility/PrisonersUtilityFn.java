package brown.auction.rules.utility;

import brown.auction.rules.IUtilityFn;
import brown.communication.action.IGameAction;
import brown.communication.messages.IActionMessage;
import brown.platform.accounting.IAccountUpdate;

import java.util.*;

public class PrisonersUtilityFn implements IUtilityFn {

    @Override
    public Map<Integer, Double> getAgentUtilities(List<IActionMessage> messages) {
        int COOPERATE = 0, DEFECT = 1;

        IActionMessage a1Message = messages.get(0);
        IActionMessage a2Message = messages.get(1);

        int agent1 = a1Message.getAgentID();
        int agent2 = a2Message.getAgentID();

        int a1Action = ((IGameAction) a1Message.getBid()).getAction();
        int a2Action = ((IGameAction) a2Message.getBid()).getAction();

        double a1Util;
        double a2Util;

        if (a1Action == COOPERATE && a2Action == COOPERATE) {
            a1Util = -1;
            a2Util = -1;
        } else if (a1Action == COOPERATE && a2Action == DEFECT) {
            a1Util = -3;
            a2Util = 0;
        } else if (a1Action == DEFECT && a2Action == COOPERATE) {
            a1Util = 0;
            a2Util = -3;
        } else {
            a1Util = -2;
            a2Util = -2;
        }

        Map<Integer, Double> result = new HashMap<>();
        result.put(agent1, a1Util);
        result.put(agent2, a2Util);
        return result;
    }

    @Override
    public List<Integer> getPossibleActions() {
        return Arrays.asList(0, 1);
    }
}
