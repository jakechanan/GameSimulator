package brown.user.agent.library;

import brown.communication.action.IBid;
import brown.communication.action.IGameAction;
import brown.communication.action.library.GameAction;
import brown.communication.messages.IActionMessage;
import brown.communication.messages.IActionRequestMessage;
import brown.communication.messages.IInformationMessage;
import brown.communication.messages.ITypeMessage;
import brown.communication.messages.library.ActionMessage;
import brown.logging.library.UserLogging;
import brown.system.setup.ISetup;
import brown.user.agent.IAgent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbsFpAgent extends AbsAgent implements IAgent {

    // the information we want is...
    // who did what

    protected ActionHistory history;

    public AbsFpAgent(String host, int port, ISetup gameSetup,
                      String name) {
        super(host, port, gameSetup, name);
        history = new ActionHistory();
    }

    @Override
    public void onInformationMessage(IInformationMessage informationMessage) {
        UserLogging.log(informationMessage.getPublicState().getTradeHistory());
        history.update(informationMessage.getPublicState().getTradeHistory());
    }

    @Override
    public void
    onActionRequestMessage(IActionRequestMessage tradeRequestMessage) {
        Integer auctionID = tradeRequestMessage.getAuctionID();
        IGameAction action = calcBestAction(history.calcAgentActionDistribution(auctionID));
        IActionMessage actionMessage = new ActionMessage(-1, this.ID, auctionID, action);
        this.CLIENT.sendTCP(actionMessage);
    }

    public abstract GameAction calcBestAction(AgentActionDistribution dist);

    @Override
    public void onTypeMessage(ITypeMessage valuationMessage) {
        // noop
    }

    protected static class AgentActionDistribution {
        private Map<Integer, Map<IBid, Double>> probs;

        public AgentActionDistribution(Map<Integer, Map<IBid, Integer>> counts) {
            probs = new HashMap<>();
            for (Integer agentID : counts.keySet()) {
                probs.put(agentID, new HashMap<>());

                Map<IBid, Integer> currAgentCounts = counts.get(agentID);
                int sum = 0;
                for (Integer count : currAgentCounts.values()) {
                    sum += count;
                }
                for (IBid bid : currAgentCounts.keySet()) {
                    probs.get(agentID).put(bid, (currAgentCounts.get(bid) + 0.) / sum);
                }
            }
        }

        public double probabilityOf(Integer agentId, IBid action) {
            return probs.get(agentId).get(action);
        }
    }

    protected static class ActionHistory {
        // AuctionID -> AgentID -> Bid -> Frequency
        private Map<Integer, Map<Integer, Map<IBid, Integer>>> history;

        public ActionHistory() {
            history = new HashMap<>();
        }

        public void update(List<List<IActionMessage>> tradeHistory) {
            for (IActionMessage msg : tradeHistory.get(0)) {
                if (!history.containsKey(msg.getAuctionID())) {
                    history.put(msg.getAuctionID(), new HashMap<>());
                }
                Map<Integer, Map<IBid, Integer>> currAuction = history.get(msg.getAuctionID());

                if (!currAuction.containsKey(msg.getAgentID())) {
                    currAuction.put(msg.getAgentID(), new HashMap<>());
                }
                Map<IBid, Integer> currAgent = currAuction.get(msg.getAgentID());

                if (!currAgent.containsKey(msg.getBid())) {
                    currAgent.put(msg.getBid(), 0);
                }
                currAgent.put(msg.getBid(), currAgent.get(msg.getBid()) + 1);
            }
        }

        public AgentActionDistribution calcAgentActionDistribution(int auctionID) {
            return new AgentActionDistribution(history.get(auctionID));
        }
    }
}
