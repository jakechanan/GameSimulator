package brown.user.agent.library;

import brown.auction.rules.IUtilityFn;
import brown.auction.rules.utility.PrisonersUtilityFn;
import brown.auction.rules.utility.RPSUtilityFn;
import brown.communication.action.IBid;
import brown.communication.action.IGameAction;
import brown.communication.action.library.GameAction;
import brown.communication.messages.*;
import brown.communication.messages.library.ActionMessage;
import brown.logging.library.UserLogging;
import brown.system.setup.ISetup;
import brown.user.agent.IAgent;

import java.util.*;

public class FictitiousPlayAgent extends AbsAgent implements IAgent {

    // the information we want is...
    // who did what
    private List<Integer> opponentActions;
    private IUtilityFn utilFn;

    private Integer lastBid;

    // TODO (jake): comment this out for lab
    private Map<Integer, Double> opponentProbs;

    public FictitiousPlayAgent(String host, int port, ISetup gameSetup,
                               String name) {
        super(host, port, gameSetup, name);
        this.opponentActions = new LinkedList<>();
        this.lastBid = null;

        // TODO (jake): comment this out for lab
        this.opponentProbs = null;

        // TODO(jake, andrew): implement this somewhere other than utilityFn
        this.utilFn = new RPSUtilityFn();
    }

    public Double calculateUtilityFromActions(Integer myMove, Integer opponentMove) {
        Integer SELF = 0, OPPONENT = 1;

        Integer auctionID = -1;

        IGameAction action1 = new GameAction(myMove);
        IActionMessage actionMessage1 = new ActionMessage(-1, SELF, auctionID, action1);

        IGameAction action2 = new GameAction(opponentMove);
        IActionMessage actionMessage2 = new ActionMessage(-1, OPPONENT, auctionID, action2);

        return this.utilFn.getAgentUtilities(Arrays.asList(actionMessage1, actionMessage2)).get(SELF);
    }

    public void predictOpponentNextMove() {
        // TODO: TASK 1
        // Create an instance variable to store your probabilities.
        // Hint: Opponent action history is stored in this.opponentActions.
        // Use this.utilFn.getPossibleActions() for a complete list of possible actions.

        // TODO (jake): comment this out for lab
        this.opponentProbs = new HashMap<>();
        for (Integer action : this.utilFn.getPossibleActions()) {
            this.opponentProbs.put(action, 0.0);
        }
        for (Integer action : this.opponentActions) {
            this.opponentProbs.put(action, this.opponentProbs.get(action) + (1.0 / this.opponentActions.size()));
        }
    }


    public Integer calculateExpectedBestBid() {
        // TODO: TASK 2
        // Use your predictions to implement Fictitious Play
        // Hint: use calculateUtilityFromActions()

        Integer bestMove = 0;

        // TODO (jake): comment this out for lab
        Double maxExpectedUtility = Double.NEGATIVE_INFINITY;
        for (Integer myAction : this.utilFn.getPossibleActions()) {
            Double expectedUtility = 0.0;
            for (Integer opponentAction : this.utilFn.getPossibleActions()) {
                Double opponentMoveProb = this.opponentProbs.getOrDefault(opponentAction, 0.0);
                expectedUtility += opponentMoveProb * calculateUtilityFromActions(myAction, opponentAction);
            }
            if (expectedUtility > maxExpectedUtility) {
                bestMove = myAction;
                maxExpectedUtility = expectedUtility;
            }
        }

        return bestMove;
    }


    @Override
    public void onInformationMessage(IInformationMessage informationMessage) {
        //UserLogging.log(informationMessage.getPublicState().getTradeHistory());
        List<IActionMessage> history = informationMessage.getPublicState().getTradeHistory().get(0);
        Integer bid1 = ((GameAction)history.get(0).getBid()).getAction();
        Integer bid2 = ((GameAction)history.get(1).getBid()).getAction();
        if (bid1.equals(this.lastBid)) {
            this.opponentActions.add(bid2);
        } else {
            this.opponentActions.add(bid1);
        }
    }

    @Override
    public void
    onActionRequestMessage(IActionRequestMessage tradeRequestMessage) {
        int bid = 0;
        if (this.lastBid != null) {
            predictOpponentNextMove();
            bid = calculateExpectedBestBid();
        }

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
