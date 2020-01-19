package brown.user.agent.library; // TODO: change this to the correct package name

import brown.auction.rules.IUtilityFn;
import brown.auction.rules.utility.PrisonersUtilityFn;
import brown.auction.rules.utility.RPSUtilityFn;
import brown.communication.action.IBid;
import brown.communication.action.IGameAction;
import brown.communication.action.library.GameAction;
import brown.communication.messages.*;
import brown.communication.messages.library.ActionMessage;
import brown.logging.library.UserLogging;
import brown.simulations.RPSSimulation;
import brown.system.setup.ISetup;
import brown.system.setup.library.Setup;
import brown.user.agent.IAgent;

import java.util.*;

public class RpsFictitiousPlayAgent extends AbsAgent implements IAgent {
    private List<Integer> opponentActions;

    private Integer lastBid;
    private Integer auctionID;

    private Random random;

    // HINT: use these as your actions!
    private static final Integer ROCK = 0, PAPER = 1, SCISSORS = 2;

    public RpsFictitiousPlayAgent(String host, int port, ISetup gameSetup,
                               String name) {
        super(host, port, gameSetup, name);
        this.opponentActions = new LinkedList<>();
        this.lastBid = null;
        this.auctionID = null;

        this.random = new Random();
    }

    /**
     * Create a probability distribution over the opponent's possible actions.
     * @return A Map from ROCK, PAPER, and SCISSORS to their corresponding probabilities.
     */
    public Map<Integer, Double> predict() {
        // TODO: TASK 1

        // Hint: Opponent action history is stored in this.opponentActions.
        // This is a List<Integer> where each element is either ROCK, PAPER, or SCISSORS.

        Map<Integer, Double> probs = new HashMap<>(); // Ex: probs.put(ROCK, <probability of opponent playing rock>)

        // TODO: compute exponential weights probabilities here

        return probs;
    }


    /**
     * Using your probability distribution, calculate this agent's (expected) best action.
     * @param opponentProbs A Map from ROCK, PAPER, and SCISSORS to their corresponding probabilities.
     * @return The expected best move, either ROCK, PAPER, or SCISSORS.
     */
    public Integer optimize(Map<Integer, Double> opponentProbs) {
        // TODO: TASK 2
        // Use your predictions to implement Fictitious Play!

        // Hint: use opponentProbs to get the probability of an opponent action.
        // Example: opponentProbs.get(ROCK) returns the probability that the opponent plays rock.

        // Hint: use rewardFrom() to get the utility of a hypothetical action profile.
        // Example: rewardFrom(ROCK, SCISSORS) returns your payoff if you play rock and the opponent plays scissors.

        return null; // return either ROCK, PAPER, or SCISSORS
    }

    /**
     * Calculates this agent's utility from a hypothetical action profile.
     * @param myMove This agent's move.
     * @param opponentMove The opponent's move.
     * @return This agent's reward if this action profile is played.
     */
    public Double rewardFrom(Integer myMove, Integer opponentMove) {
        if (myMove == opponentMove) {
            return 0.0;
        } else if (myMove == (opponentMove + 1) % 3) {
            return 1.0;
        } else {
            return -1.0;
        }
    }


    @Override
    public void onInformationMessage(IInformationMessage informationMessage) {
        // noop
    }

    @Override
    public void
    onActionRequestMessage(IActionRequestMessage tradeRequestMessage) {
        Integer bid = random.nextInt(3);
        if (this.lastBid != null) {
            bid = optimize(predict());
        }

        Integer auctionID = tradeRequestMessage.getAuctionID();
        this.auctionID = auctionID;
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
        // TODO(jake): fix once public ID is available
        List<IActionMessage> history = simulationMessage.getMarketResults().get(this.auctionID).getTradeHistory().get(0);
        Integer bid1 = ((GameAction)history.get(0).getBid()).getAction();
        Integer bid2 = ((GameAction)history.get(1).getBid()).getAction();
        if (bid1.equals(this.lastBid)) {
            this.opponentActions.add(bid2);
        } else {
            this.opponentActions.add(bid1);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        List<String> agents = Arrays.asList(RpsFictitiousPlayAgent.class.getCanonicalName(), BasicRPSAgent.class.getCanonicalName());
        new RPSSimulation(agents, "input_configs/rock_paper_scissors.json", "outfile", false).run();
    }
}
