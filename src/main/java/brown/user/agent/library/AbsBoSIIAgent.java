package brown.user.agent.library;

import brown.communication.action.IGameAction;
import brown.communication.action.library.BoSIIAction;
import brown.communication.action.library.GameAction;
import brown.communication.messages.IActionMessage;
import brown.communication.messages.IActionRequestMessage;
import brown.communication.messages.IInformationMessage;
import brown.communication.messages.ISimulationReportMessage;
import brown.communication.messages.ITypeMessage;
import brown.communication.messages.library.ActionMessage;
import brown.logging.library.UserLogging;
import brown.system.setup.ISetup;
import brown.user.agent.IAgent;

import java.util.List;

import brown.auction.type.valuation.library.BoSIIType;

public abstract class AbsBoSIIAgent extends AbsAgent implements IAgent {
  
  private Integer auctionID;
  
  private Integer mood;
  
  public AbsBoSIIAgent(String host, int port, ISetup gameSetup,
      String name) {
    super(host, port, gameSetup, name);
    this.mood = null;
    this.auctionID = null;
  }

  @Override
  public void onInformationMessage(IInformationMessage informationMessage) {
    //UserLogging.log(informationMessage.getPublicState().getTradeHistory());
  }
  
  public boolean isRowPlayer() {
	  return this.mood == null;
  }
  
  public Integer getMood() {
	  return this.mood;
  }
  
  public abstract Integer nextMove();

  @Override
  public void
      onActionRequestMessage(IActionRequestMessage tradeRequestMessage) {
	  Integer auctionID = tradeRequestMessage.getAuctionID();
	  this.auctionID = auctionID;
      IGameAction action = new BoSIIAction(nextMove(), this.mood);
      IActionMessage actionMessage = new ActionMessage(-1, this.ID, auctionID, action);
      this.CLIENT.sendTCP(actionMessage);
  }
  
  
  @Override
  public void onTypeMessage(ITypeMessage valuationMessage) {
    this.mood = ((BoSIIType)(valuationMessage.getValuation())).getMood();
    if (this.mood == null) {
    	System.out.println("I AM THE ROW PLAYER");
    } else if (this.mood.intValue() == 0) {
    	System.out.println("I AM THE COLUMN PLAYER IN A GOOD MOOD");
    } else {
    	System.out.println("I AM THE COLUMN PLAYER IN A BAD MOOD");
    }
    
  }
  
  @Override
  public void
      onSimulationReportMessage(ISimulationReportMessage simulationMessage) {
	  List<IActionMessage> history = simulationMessage.getMarketResults().get(this.auctionID).getTradeHistory().get(0);
      for (IActionMessage msg : history) {
      	if (!msg.getAgentID().equals(this.publicID)) {
      		Integer bid = ((GameAction)msg.getBid()).getAction();
      		Integer mood = ((BoSIIAction)msg.getBid()).getMood();
      		//this.opponentLastMove = bid;
      		break;
      	}
      }
  }

}
