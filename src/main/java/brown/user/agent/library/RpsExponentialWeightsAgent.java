package brown.user.agent.library; // TODO: change this to the correct package name

import brown.auction.rules.IUtilityFn;
import brown.auction.rules.utility.LemonadeUtilityFn;
import brown.communication.action.IGameAction;
import brown.communication.action.library.GameAction;
import brown.communication.messages.*;
import brown.communication.messages.library.ActionMessage;
import brown.system.setup.ISetup;
import brown.user.agent.IAgent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RpsExponentialWeightsAgent extends AbsAgent implements IAgent {
    private Map<Integer, Double> actionRewards;
    private Map<Integer, Integer> actionCounts;

    private Integer lastBid;
    private Integer auctionID;

    private static final Integer ROCK = 0, PAPER = 1, SCISSORS = 2;

    public RpsExponentialWeightsAgent(String host, int port, ISetup gameSetup,
                                      String name) {
        super(host, port, gameSetup, name);

        this.lastBid = null;
        this.auctionID = null;

        this.actionRewards = new HashMap<>();
        this.actionRewards.put(ROCK, 0.0);
        this.actionRewards.put(PAPER, 0.0);
        this.actionRewards.put(SCISSORS, 0.0);

        this.actionCounts = new HashMap<>();
        this.actionCounts.put(ROCK, 0);
        this.actionCounts.put(PAPER, 0);
        this.actionCounts.put(SCISSORS, 0);
    }

    /**
     * Create a probability distribution for your next action, using Exponential Weights.
     * @return A Map from ROCK, PAPER, and SCISSORS to their corresponding probabilities.
     */
    public Map<Integer, Double> calcMoveProbabilities() {
        // TODO: TASK 1

        Map<Integer, Double> averageRewardsPerAction = this.averageRewards();

        // averageRewardsPerAction maps the values ROCK, PAPER, and SCISSORS to their historical average rewards.
        // Ex: averageRewardsPerAction.get(ROCK) returns the average reward from playing rock.

        Map<Integer, Double> probs = new HashMap<>(); // Ex: probs.put(ROCK, <probability of playing rock>)

        // TODO: compute exponential weights probabilities here

        // Hint: use Math.exp(a) to get e^a.

        return probs;
    }

    /**
     * From a probability distribution instance variable, samples your the move.
     * @param probs A probability distribution over ROCK, PAPER, and SCISSORS.
     * @return The agent's next move: ROCK, PAPER, or SCISSORS.
     */
    public Integer sample(Map<Integer, Double> probs) {
        Double a = Math.random();
        Double total = 0.0;
        for (Integer action : probs.keySet()) {
            total += probs.get(action);
            if (a < total) {
                return action;
            }
        }

        return 0;
    }

    public Map<Integer, Double> averageRewards() {
        Map<Integer, Double> result = new HashMap<>();
        for (Integer k : this.actionCounts.keySet()) {
            if (this.actionCounts.get(k) == 0) {
                result.put(k, 0.0);
            } else {
                result.put(k, (this.actionRewards.get(k) + 0.) / this.actionCounts.get(k));
            }
        }
        return result;
    }


    @Override
    public void onInformationMessage(IInformationMessage informationMessage) {
        // noop
    }

    @Override
    public void
    onActionRequestMessage(IActionRequestMessage tradeRequestMessage) {
        int bid = sample(calcMoveProbabilities());

        Integer auctionID = tradeRequestMessage.getAuctionID();
        this.auctionID = auctionID;
        IGameAction action = new GameAction(bid);
        IActionMessage actionMessage = new ActionMessage(-1, this.ID, auctionID, action);
        this.lastBid = bid;
        this.actionCounts.put(bid, this.actionCounts.get(bid) + 1);
        this.CLIENT.sendTCP(actionMessage);
    }

    @Override
    public void onTypeMessage(ITypeMessage valuationMessage) {
        // noop
    }

    @Override
    public void onSimulationReportMessage(ISimulationReportMessage simulationMessage) {
        List<IActionMessage> history = simulationMessage.getMarketResults().get(this.auctionID).getTradeHistory().get(0);

        for (int i = 0; i < history.size(); i++) {
            Integer bid = ((GameAction)history.get(i).getBid()).getAction();
            if (bid.equals(this.lastBid)) {
                this.actionRewards.put(this.lastBid, simulationMessage.getMarketResults().get(this.auctionID).getUtilities().get(i).getCost());
                return;
            }
        }
    }
}
