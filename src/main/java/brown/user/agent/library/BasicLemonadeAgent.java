package brown.user.agent.library;

import java.util.LinkedList;
import java.util.List;

import brown.communication.action.IGameAction;
import brown.communication.action.library.GameAction;
import brown.communication.messages.IActionMessage;
import brown.communication.messages.IActionRequestMessage;
import brown.communication.messages.ISimulationReportMessage;
import brown.communication.messages.library.ActionMessage;
import brown.system.setup.ISetup;
import brown.user.agent.IAgent;

public class BasicLemonadeAgent extends AbsLemonadeAgent implements IAgent {
  
  private List<List<IActionMessage>> tradeHistory; 
  
  public BasicLemonadeAgent(String host, int port, ISetup gameSetup,
      String name) {
    super(host, port, gameSetup, name);
    tradeHistory = new LinkedList<List<IActionMessage>>(); 
  }

  @Override
  public void
      onActionRequestMessage(IActionRequestMessage tradeRequestMessage) { 
    if (this.lastInformationMessage != null) {
      List<List<IActionMessage>> tHist = this.lastInformationMessage.getPublicState().getTradeHistory();  
      this.tradeHistory.add(tHist.get(tHist.size() - 1)); 
    }
    System.out.println(lastInformationMessage); 
    System.out.println(this.tradeHistory.size()); 
    Integer auctionID = tradeRequestMessage.getAuctionID(); 
    IGameAction action = new GameAction(0); 
    IActionMessage actionMessage = new ActionMessage(-1, this.ID, auctionID, action); 
    this.CLIENT.sendTCP(actionMessage); 
  }

  @Override
  public void
      onSimulationReportMessage(ISimulationReportMessage simulationMessage) {
    // TODO Auto-generated method stub
    
  }

}
