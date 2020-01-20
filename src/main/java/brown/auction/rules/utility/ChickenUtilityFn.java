package brown.auction.rules.utility;

import brown.auction.rules.IUtilityFn;
import brown.communication.action.IGameAction;
import brown.communication.messages.IActionMessage;
import brown.platform.accounting.IAccountUpdate;

import java.util.*;

public class ChickenUtilityFn implements IUtilityFn {

    @Override
    public Map<Integer, Double> getAgentUtilities(List<IActionMessage> messages) {
        int SWERVE = 0, CONTINUE = 1;

        IActionMessage a1Message = messages.get(0);
        IActionMessage a2Message = messages.get(1);

        int agent1 = a1Message.getAgentID();
        int agent2 = a2Message.getAgentID();

        int a1Action = ((IGameAction) a1Message.getBid()).getAction();
        int a2Action = ((IGameAction) a2Message.getBid()).getAction();

        double a1Util;
        double a2Util;

        if (a1Action == SWERVE && a2Action == SWERVE) {
            a1Util = 0;
            a2Util = 0;
        } else if (a1Action == SWERVE && a2Action == CONTINUE) {
            a1Util = -1;
            a2Util = 1;
        } else if (a1Action == CONTINUE && a2Action == SWERVE) {
            a1Util = 1;
            a2Util = -1;
        } else {
            a1Util = -5;
            a2Util = -5;
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
