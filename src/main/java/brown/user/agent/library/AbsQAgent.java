package brown.user.agent.library;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import brown.communication.action.IGameAction;
import brown.communication.action.library.GameAction;
import brown.communication.messages.IActionMessage;
import brown.communication.messages.IActionRequestMessage;
import brown.communication.messages.IInformationMessage;
import brown.communication.messages.ISimulationReportMessage;
import brown.communication.messages.ITypeMessage;
import brown.communication.messages.library.ActionMessage;
import brown.platform.accounting.IAccountUpdate;
import brown.system.setup.ISetup;
import brown.user.agent.IAgent;

public abstract class AbsQAgent extends AbsAgent implements IAgent {
	private int saveEvery;
	private int numRounds;
	private String filename;
	private Integer auctionID;
	
	public AbsQAgent(String host, int port, ISetup gameSetup, String name, int saveEvery, String filename) {
		super(host, port, gameSetup, name);
		this.saveEvery = saveEvery;
		this.numRounds = 0;
		this.auctionID = null;
	}
	
	private synchronized void save(String filename) {
		this.saveWeights(filename);
	}
	
	public abstract void loadWeights(String filename);
	public abstract void saveWeights(String filename);
	public abstract Integer nextMove();
	public abstract void updateQ(List<Integer> opponentActions, Double reward);

	@Override
	public void onInformationMessage(IInformationMessage informationMessage) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onActionRequestMessage(IActionRequestMessage tradeRequestMessage) {
		// TODO Auto-generated method stub
		Integer auctionID = tradeRequestMessage.getAuctionID(); 
		this.auctionID = auctionID;
	    IGameAction action = new GameAction(this.nextMove());
	    IActionMessage actionMessage = new ActionMessage(-1, this.ID, auctionID, action); 
	    this.CLIENT.sendTCP(actionMessage); 
	}

	@Override
	public void onTypeMessage(ITypeMessage valuationMessage) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSimulationReportMessage(ISimulationReportMessage simReportMessage) {
		// TODO Auto-generated method stub
		Map<Integer, Integer> actions = new HashMap<>();
		List<Integer> opponents = new ArrayList<>();
		for (IActionMessage act : simReportMessage.getMarketResults().get(this.auctionID).getTradeHistory().get(0)) {
			if (!act.getAgentID().equals(this.publicID)) {
				actions.put(act.getAgentID(), ((GameAction)act.getBid()).getAction()); 
				opponents.add(act.getAgentID());
			}
		}
		
		Collections.sort(opponents);
		
		List<Integer> moves = new ArrayList<>();
		for (Integer id : opponents) {
			moves.add(actions.get(id));
		}
		
		Double reward = 0.0;
		for (IAccountUpdate upd : simReportMessage.getMarketResults().get(this.auctionID).getUtilities()) {
        	if (upd.getTo().equals(this.publicID)) {
        		reward = upd.getCost();
        	}
        }
		
		this.updateQ(moves, reward);
	}
}
