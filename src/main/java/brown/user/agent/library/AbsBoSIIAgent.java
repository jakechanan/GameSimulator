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

public abstract class AbsBoSIIAgent extends AbsAgent implements IAgent {
  private Integer auctionID;
  
  private Integer mood;
  private List<Move> moveHistory;
  
  private static final Integer GOOD_MOOD = 0, BAD_MOOD = 1;
  private static final Integer STUBBORN = 0, COMPROMISE = 1;
  
  public AbsBoSIIAgent(String host, int port, ISetup gameSetup,
      String name) {
    super(host, port, gameSetup, name);
    this.mood = null;
    this.auctionID = null;
    this.moveHistory = new LinkedList<>();
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
  
  public List<Move> getMoveHistory() {
	  return new ArrayList<>(this.moveHistory);
  }
  
  public Double opponentGoodMoodProbability() {
	  return (isRowPlayer()) ? (2.0 / 3) : null;
  }
  
  public Double rowPlayerRewardFrom(Integer rowPlayerMove, Integer columnPlayerMove) {
	  if (rowPlayerMove.equals(columnPlayerMove)) {
		  return 0.0;
	  } else {
		  return (rowPlayerMove.equals(STUBBORN)) ? 7.0 : 3.0;
	  }
  }
  
  public Double columnPlayerRewardFrom(Integer rowPlayerMove, Integer columnPlayerMove, Integer columnPlayerMood) {
	  if (columnPlayerMood.equals(GOOD_MOOD)) {
		  if (rowPlayerMove.equals(columnPlayerMove)) {
			  return 0.0;
		  } else {
			  return (columnPlayerMove.equals(STUBBORN)) ? 7.0 : 3.0;
		  }
	  } else {
		  if (!rowPlayerMove.equals(columnPlayerMove)) {
			  return 0.0;
		  } else {
			  return (columnPlayerMove.equals(STUBBORN)) ? 7.0 : 3.0;
		  }
	  }
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
	  this.moveHistory.add(new Move(simulationMessage, this));
  }
  
  protected static class Move {
	  private Integer myMove;
	  private Integer opponentMove;
	  private Integer columnPlayerMood;
	  private Double myReward;
	  private Double opponentReward;
	  
	  public Move(ISimulationReportMessage msg, AbsBoSIIAgent agent) {
		  List<IActionMessage> history = msg.getMarketResults().get(agent.auctionID).getTradeHistory().get(0);
	      for (IActionMessage act : history) {
	    	Integer bid = ((GameAction)act.getBid()).getAction();
	      	if (msg.getAgentID().equals(agent.publicID)) {
	      		this.myMove = bid;
	      	} else {
	      		this.opponentMove = bid;
	      	}

      		Integer mood = ((BoSIIAction)act.getBid()).getMood();
	      	if (mood != null) {
	      		this.columnPlayerMood = mood;
	      	}
	      }
	      
	      for (IAccountUpdate upd : msg.getMarketResults().get(agent.auctionID).getUtilities()) {
	        	if (upd.getTo().equals(agent.publicID)) {
	        		this.myReward = upd.getCost();
	        	} else {
	        		this.opponentReward = upd.getCost();
	        	}
	        }
	  }

	public Integer getMyMove() {
		return myMove;
	}

	public Integer getOpponentMove() {
		return opponentMove;
	}

	public Integer getColumnPlayerMood() {
		return columnPlayerMood;
	}

	public Double getMyReward() {
		return myReward;
	}

	public Double getOpponentReward() {
		return opponentReward;
	}
  }

}
