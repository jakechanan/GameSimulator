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

import java.util.Random;

public class BasicRPSAgent extends AbsLemonadeAgent implements IAgent {
    public BasicRPSAgent(String host, int port, ISetup gameSetup,
                         String name) {
        super(host, port, gameSetup, name);
    }

    @Override
    public void
    onActionRequestMessage(IActionRequestMessage tradeRequestMessage) {
        Integer auctionID = tradeRequestMessage.getAuctionID();
        Random r = new Random();
        // some basic unbalanced probabilities.
        IGameAction action = new GameAction((Math.random() < 0.25) ? 0 : (Math.random() < 0.55) ? 1 : 2);
        IActionMessage actionMessage = new ActionMessage(-1, this.ID, auctionID, action);
        this.CLIENT.sendTCP(actionMessage);
    }

    @Override
    public void
    onSimulationReportMessage(ISimulationReportMessage simulationMessage) {
        UserLogging.log(simulationMessage);

    }

}
