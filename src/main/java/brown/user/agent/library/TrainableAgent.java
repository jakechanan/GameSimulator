package brown.user.agent.library;

import java.util.Random;

import brown.communication.action.IGameAction;
import brown.communication.action.library.GameAction;
import brown.communication.messages.IActionMessage;
import brown.communication.messages.IActionRequestMessage;
import brown.communication.messages.IInformationMessage;
import brown.communication.messages.ISimulationReportMessage;
import brown.communication.messages.ITypeMessage;
import brown.communication.messages.library.ActionMessage;
import brown.system.setup.ISetup;
import brown.user.agent.IAgent;

public abstract class TrainableAgent extends AbsAgent implements IAgent {
	private int saveEvery;
	private int numRounds;
	private String filename;
	
	public TrainableAgent(String host, int port, ISetup gameSetup, String name, int saveEvery, String filename) {
		super(host, port, gameSetup, name);
		this.saveEvery = saveEvery;
		this.numRounds = 0;
		
	}
	
	private synchronized void save(String filename) {
		this.saveWeights(filename);
	}
	
	public abstract void train();
	public abstract void loadWeights(String filename);
	public abstract void saveWeights(String filename);
	public abstract Integer nextMove();
	public abstract void update(ISimulationReportMessage simReportMessage);

	@Override
	public void onInformationMessage(IInformationMessage informationMessage) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onActionRequestMessage(IActionRequestMessage tradeRequestMessage) {
		// TODO Auto-generated method stub
		Integer auctionID = tradeRequestMessage.getAuctionID(); 
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
		this.update(simReportMessage);
		if (++this.numRounds % saveEvery == 0) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					TrainableAgent.this.save(TrainableAgent.this.filename);
				}
			}).start();;
		}
	}

}
