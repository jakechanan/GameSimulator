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

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class BasicPrisonersDilemmaAgent extends AbsLemonadeAgent implements IAgent {
    public BasicPrisonersDilemmaAgent(String host, int port, ISetup gameSetup,
                              String name) {
        super(host, port, gameSetup, name);
    }

    @Override
    public void
    onActionRequestMessage(IActionRequestMessage tradeRequestMessage) {
        Integer auctionID = tradeRequestMessage.getAuctionID();
        Random r = new Random();
        // some basic unbalanced probabilities.
        IGameAction action = new GameAction((Math.random() < 0.6) ? 0 : 1);
        IActionMessage actionMessage = new ActionMessage(-1, this.ID, auctionID, action);
        this.CLIENT.sendTCP(actionMessage);
    }

    @Override
    public void
    onSimulationReportMessage(ISimulationReportMessage simulationMessage) {
        UserLogging.log(simulationMessage);

    }

}
