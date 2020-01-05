package brown.user.agent.library;

import brown.communication.action.IGameAction;
import brown.communication.action.library.GameAction;
import brown.communication.messages.IActionMessage;
import brown.communication.messages.IActionRequestMessage;
import brown.communication.messages.library.ActionMessage;
import brown.system.setup.ISetup;
import brown.user.agent.IAgent;

public class BasicLemonadeAgent extends AbsLemonadeAgent implements IAgent {

  public BasicLemonadeAgent(String host, int port, ISetup gameSetup,
      String name) {
    super(host, port, gameSetup, name);
  }

  @Override
  public void
      onActionRequestMessage(IActionRequestMessage tradeRequestMessage) {
    
    Integer auctionID = tradeRequestMessage.getAuctionID(); 
    IGameAction action = new GameAction(0); 
    IActionMessage actionMessage = new ActionMessage(-1, this.ID, auctionID, action); 
    this.CLIENT.sendTCP(actionMessage); 
    
  }

}
