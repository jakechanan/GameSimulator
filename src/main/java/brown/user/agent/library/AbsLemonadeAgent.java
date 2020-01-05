package brown.user.agent.library;

import brown.communication.messages.IActionRequestMessage;
import brown.communication.messages.IInformationMessage;
import brown.communication.messages.ITypeMessage;
import brown.logging.library.UserLogging;
import brown.system.setup.ISetup;
import brown.user.agent.IAgent;

public abstract class AbsLemonadeAgent extends AbsAgent implements IAgent {
  
  // the information we want is... 
  // who did what
  
  protected IInformationMessage lastInformationMessage; 
  
  public AbsLemonadeAgent(String host, int port, ISetup gameSetup,
      String name) {
    super(host, port, gameSetup, name);
  }

  @Override
  public void onInformationMessage(IInformationMessage informationMessage) {
    this.lastInformationMessage = informationMessage; 
    UserLogging.log(informationMessage.getPublicState().getTradeHistory());
  }

  @Override
  public abstract void
      onActionRequestMessage(IActionRequestMessage tradeRequestMessage); 
  
  
  @Override
  public void onTypeMessage(ITypeMessage valuationMessage) {
    // noop 
  }

}
