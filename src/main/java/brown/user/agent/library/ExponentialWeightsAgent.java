package brown.user.agent.library;

import brown.auction.rules.IUtilityFn;
import brown.auction.rules.utility.RPSUtilityFn;
import brown.communication.action.IGameAction;
import brown.communication.action.library.GameAction;
import brown.communication.messages.*;
import brown.communication.messages.library.ActionMessage;
import brown.system.setup.ISetup;
import brown.user.agent.IAgent;

import java.util.*;

public class ExponentialWeightsAgent extends AbsAgent implements IAgent {

    // the information we want is...
    // who did what

    // how much utility has been gained per action
    private Map<Integer, Double> actionRewards;

    private IUtilityFn utilFn;
    private Integer lastBid;

    // TODO (jake): comment this out for lab
    private Map<Integer, Double> actionProbs;

    public ExponentialWeightsAgent(String host, int port, ISetup gameSetup,
                                   String name) {
        super(host, port, gameSetup, name);

        this.lastBid = null;

        // TODO (jake): comment this out for lab
        this.actionProbs = null;

        // TODO(jake, andrew): implement this somewhere other than utilityFn
        this.utilFn = new RPSUtilityFn();

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
        // Hint: actionRewards maps each move to the total reward you've gained from it

        this.actionProbs = new HashMap<>();

        Double sum = 0.0;
        for (Integer action : this.utilFn.getPossibleActions()) {
            Double expReward = Math.exp(this.actionRewards.get(action));
            this.actionProbs.put(action, expReward);
            sum += expReward;
        }

        for (Integer action : this.utilFn.getPossibleActions()) {
            Double expReward = this.actionProbs.get(action);
            this.actionProbs.put(action, expReward / sum);
        }
    }

    public Integer sampleActions() {
        // TODO: TASK 2
        // Using your probability distribution, sample from the possible actions to choose your next move

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


    @Override
    public void onInformationMessage(IInformationMessage informationMessage) {
        //UserLogging.log(informationMessage.getPublicState().getTradeHistory());
        List<IActionMessage> history = informationMessage.getPublicState().getTradeHistory().get(0);
        Integer bid1 = ((GameAction)history.get(0).getBid()).getAction();
        Integer bid2 = ((GameAction)history.get(1).getBid()).getAction();
        if (bid1.equals(this.lastBid)) {
            this.actionRewards.put(this.lastBid, informationMessage.getPublicState().getUtilities().get(0).getCost());
        } else {
            this.actionRewards.put(this.lastBid, informationMessage.getPublicState().getUtilities().get(1).getCost());
        }
    }

    @Override
    public void
    onActionRequestMessage(IActionRequestMessage tradeRequestMessage) {
        calculateMoveProbabilities();
        int bid = sampleActions();

        Integer auctionID = tradeRequestMessage.getAuctionID();
        IGameAction action = new GameAction(bid);
        IActionMessage actionMessage = new ActionMessage(-1, this.ID, auctionID, action);
        this.lastBid = bid;
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
