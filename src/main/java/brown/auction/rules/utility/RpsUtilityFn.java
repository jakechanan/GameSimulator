package brown.auction.rules.utility;

import brown.auction.rules.IUtilityFn;
import brown.communication.action.IGameAction;
import brown.communication.messages.IActionMessage;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RpsUtilityFn implements IUtilityFn {

    @Override
    public Map<Integer, Double> getAgentUtilities(List<IActionMessage> messages) {
        int ROCK = 0, PAPER = 1, SCISSORS = 2;

        IActionMessage a1Message = messages.get(0);
        IActionMessage a2Message = messages.get(1);

        int agent1 = a1Message.getAgentID();
        int agent2 = a2Message.getAgentID();

        int a1Action = ((IGameAction) a1Message.getBid()).getAction();
        int a2Action = ((IGameAction) a2Message.getBid()).getAction();

        double a1Util;
        double a2Util;

        if (a1Action == a2Action) {
            a1Util = 0;
            a2Util = 0;
        } else if ((a1Action == ROCK && a2Action == SCISSORS) ||
                (a1Action == PAPER && a2Action == ROCK) || (a1Action == SCISSORS && a2Action == PAPER)) {
            a1Util = 1;
            a2Util = -1;
        } else {
            a1Util = -1;
            a2Util = 1;
        }

        Map<Integer, Double> result = new HashMap<>();
        result.put(agent1, a1Util);
        result.put(agent2, a2Util);
        return result;
    }

    @Override
    public List<Integer> getPossibleActions() {
        return Arrays.asList(0, 1, 2);
    }
}
