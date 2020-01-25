package brown.user.agent.library;

import brown.communication.action.IGameAction;
import brown.communication.action.library.GameAction;
import brown.communication.messages.IActionMessage;
import brown.communication.messages.IActionRequestMessage;
import brown.communication.messages.ISimulationReportMessage;
import brown.communication.messages.library.ActionMessage;
import brown.logging.library.UserLogging;
import brown.system.setup.ISetup;
import brown.user.agent.IAgent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class BasicRPSAgent extends AbsLemonadeAgent implements IAgent {
	private List<Integer> moveOrder;
	
    public BasicRPSAgent(String host, int port, ISetup gameSetup,
                         String name) {
        super(host, port, gameSetup, name);
        
        moveOrder = new ArrayList<>();
        moveOrder.add(0);
        moveOrder.add(1);
        moveOrder.add(2);
        Collections.shuffle(moveOrder);
    }

    @Override
    public void
    onActionRequestMessage(IActionRequestMessage tradeRequestMessage) {
        Integer auctionID = tradeRequestMessage.getAuctionID();
        // some basic unbalanced probabilities.
        double a = Math.random();
        Integer moveIdx = (a < 0.125) ? 0 : (a < 0.25) ? 1 : 2;
        IGameAction action = new GameAction(moveOrder.get(moveIdx));
        IActionMessage actionMessage = new ActionMessage(-1, this.ID, auctionID, action);
        this.CLIENT.sendTCP(actionMessage);
    }

    @Override
    public void
    onSimulationReportMessage(ISimulationReportMessage simulationMessage) {
        UserLogging.log(simulationMessage);

    }

}
