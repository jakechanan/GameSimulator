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
import brown.platform.accounting.IAccountUpdate;
import brown.system.setup.ISetup;
import brown.user.agent.IAgent;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import brown.auction.type.valuation.library.BoSIIType;

public abstract class AbsFsmAgent extends AbsAgent implements IAgent {
  private Integer auctionID;
  
  public AbsFsmAgent(String host, int port, ISetup gameSetup,
      String name) {
    super(host, port, gameSetup, name);
    this.auctionID = null;
  }

  @Override
  public void onInformationMessage(IInformationMessage informationMessage) {
    //UserLogging.log(informationMessage.getPublicState().getTradeHistory());
  }
  
 
  public abstract Integer nextMove();
  public abstract void transitionState(Integer myMove, Integer opponentMove);

  @Override
  public void
      onActionRequestMessage(IActionRequestMessage tradeRequestMessage) {
	  Integer auctionID = tradeRequestMessage.getAuctionID();
	  this.auctionID = auctionID;
      IGameAction action = new GameAction(nextMove());
      IActionMessage actionMessage = new ActionMessage(-1, this.ID, auctionID, action);
      this.CLIENT.sendTCP(actionMessage);
  }
  
  
  @Override
  public void onTypeMessage(ITypeMessage valuationMessage) {
	  // noop
  }
  
  @Override
  public void onSimulationReportMessage(ISimulationReportMessage simulationMessage) {
	  Integer myMove = null, opponentMove = null;
	  for (IActionMessage act : simulationMessage.getMarketResults().get(this.auctionID).getTradeHistory().get(0)) {
		  if (act.getAgentID().equals(this.publicID)) {
			  myMove = ((GameAction)act.getBid()).getAction();
		  } else {
			  opponentMove = ((GameAction)act.getBid()).getAction();
		  }
	  }
	  if (myMove != null && opponentMove != null) {
		  transitionState(myMove, opponentMove);
	  }
  }
}
