package brown.user.agent.library;  // TODO: change this to the correct package name

import brown.communication.messages.IActionRequestMessage;
import brown.communication.messages.IInformationMessage;
import brown.communication.messages.ISimulationReportMessage;
import brown.communication.messages.ITypeMessage;
import brown.system.setup.ISetup;
import brown.system.setup.library.Setup;
import brown.user.agent.IAgent;
import brown.user.agent.library.AbsAgent;

public class ChickenAgent extends AbsAgent implements IAgent {
	private static final String HOST = "";  // TODO: fill this in upon TA instruction
	private static final String NAME = "";  // TODO: give your agent a name

	public ChickenAgent(String host, int port, ISetup gameSetup, String name) {
		super(host, port, gameSetup, name);
	}

	@Override
	public void onSimulationReportMessage(ISimulationReportMessage arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onActionRequestMessage(IActionRequestMessage arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onInformationMessage(IInformationMessage arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTypeMessage(ITypeMessage arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args) {
		new ChickenAgent("localhost", 2121, new Setup(), NAME);
		while (true) {}
	}
		
}
