package brown.user.agent.library;

import brown.auction.rules.IUtilityFn;
import brown.auction.rules.utility.LemonadeUtilityFn;
import brown.auction.rules.utility.RPSUtilityFn;
import brown.communication.action.IGameAction;
import brown.communication.action.library.GameAction;
import brown.communication.messages.*;
import brown.communication.messages.library.ActionMessage;
import brown.platform.accounting.IAccountUpdate;
import brown.system.setup.ISetup;
import brown.user.agent.IAgent;

import java.util.*;

public class ExponentialWeightsAgent extends AbsAgent implements IAgent {

    // the information we want is...
    // who did what

    // how much utility has been gained per action
    private Map<Integer, Double> actionRewards;
    private int numRounds;

    private IUtilityFn utilFn;
    private Integer lastBid;

    // TODO (jake): comment this out for lab
    private Map<Integer, Double> actionProbs;

    public ExponentialWeightsAgent(String host, int port, ISetup gameSetup,
                                   String name) {
        super(host, port, gameSetup, name);

        this.lastBid = null;
        this.numRounds = 0;

        // TODO (jake): comment this out for lab
        this.actionProbs = null;

        // TODO(jake, andrew): implement this somewhere other than utilityFn
        this.utilFn = new LemonadeUtilityFn();

        this.actionRewards = new HashMap<>();
        for (Integer action : this.utilFn.getPossibleActions()) {
            this.actionRewards.put(action, 0.0);
        }
    }


    public void calculateMoveProbabilities() {
        // TODO: TASK 1
        // Use your previous actions and rewards
        // to create a probability distribution for your next move using Exponential Weights.
        // Create an instance variable for your probability distribution.

        List<Integer> possibleActions = this.utilFn.getPossibleActions();
        Map<Integer, Double> averageRewardPerAction = this.averageRewards();

        // TODO(jake): comment this out for lab
        this.actionProbs = new HashMap<>();

        Double sum = 0.0;
        for (Integer action : possibleActions) {
            Double expReward = Math.exp(averageRewardPerAction.get(action));
            this.actionProbs.put(action, expReward);
            sum += expReward;
        }

        for (Integer action : possibleActions) {
            Double expReward = this.actionProbs.get(action);
            this.actionProbs.put(action, expReward / sum);
        }
    }

    public Integer sample() {
        // TODO: TASK 2
        // Using your probability distribution, sample from the possible actions to choose your next move

        // TODO(jake): comment this out for lab
        Double a = Math.random();
        Double total = 0.0;
        for (Integer action : this.actionProbs.keySet()) {
            total += this.actionProbs.get(action);
            if (a < total) {
                return action;
            }
        }

        return 0;
    }

    public Map<Integer, Double> averageRewards() {
        Map<Integer, Double> result = new HashMap<>();
        for (Integer k : this.actionRewards.keySet()) {
            if (this.numRounds == 0) {
                result.put(k, 0.0);
            } else {
                result.put(k, (this.actionRewards.get(k) + 0.) / this.numRounds);
            }
        }
        return result;
    }


    @Override
    public void onInformationMessage(IInformationMessage informationMessage) {
        //UserLogging.log(informationMessage.getPublicState().getTradeHistory());
        List<IActionMessage> history = informationMessage.getPublicState().getTradeHistory().get(0);

        for (int i = 0; i < history.size(); i++) {
            Integer bid = ((GameAction)history.get(i).getBid()).getAction();
            if (bid.equals(this.lastBid)) {
                this.actionRewards.put(this.lastBid, informationMessage.getPublicState().getUtilities().get(i).getCost());
                return;
            }
        }
    }

    @Override
    public void
    onActionRequestMessage(IActionRequestMessage tradeRequestMessage) {
        calculateMoveProbabilities();
        int bid = sample();

        Integer auctionID = tradeRequestMessage.getAuctionID();
        IGameAction action = new GameAction(bid);
        IActionMessage actionMessage = new ActionMessage(-1, this.ID, auctionID, action);
        this.lastBid = bid;
        this.numRounds++;
        this.CLIENT.sendTCP(actionMessage);
    }

    @Override
    public void onTypeMessage(ITypeMessage valuationMessage) {
        // noop
    }

    @Override
    public void onSimulationReportMessage(ISimulationReportMessage simulationMessage) {
    }
}
