package brown.user.agent.library;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import brown.communication.messages.IActionRequestMessage;
import brown.communication.messages.IInformationMessage;
import brown.communication.messages.ISimulationReportMessage;
import brown.communication.messages.ITypeMessage;
import brown.communication.messages.IUtilityUpdateMessage;
import brown.communication.messages.library.AbsServerToAgentMessage;
import brown.communication.messages.library.RegistrationMessage;
import brown.logging.library.UserLogging;
import brown.system.client.library.TPClient;
import brown.system.setup.ISetup;
import brown.user.agent.IAgent;

/**
 * every agent class extends this class.
 * 
 * @author andrew
 *
 */
public abstract class AbsAgent extends TPClient implements IAgent {

  protected double utility;
  protected Double type;
  protected String name;  
  // public ID may be accessed as this.publicID
  // private ID may be accessed as this.ID

  /**
   * 
   * AbsAgent takes in a host, a port, an ISetup.
   * 
   * @param host
   * @param port
   * @param gameSetup
   * @throws AgentCreationException
   */
  public AbsAgent(String host, int port, ISetup gameSetup) {
    super(host, port, gameSetup);
    final AbsAgent agent = this;
    this.name = "default"; 
    // All agents listen for messages
    CLIENT.addListener(new Listener() {
      public void received(Connection connection, Object message) {
        synchronized (agent) {
          if (message instanceof AbsServerToAgentMessage) {
            AbsServerToAgentMessage theMessage =
                (AbsServerToAgentMessage) message;
            theMessage.agentDispatch(agent);
          }
        }
      }
    });

    CLIENT.sendTCP(new RegistrationMessage(-1));
    this.utility = 0.0;
    this.type = 0.0;
  }

  /**
   * 
   * AbsAgent takes in a host, a port, an ISetup.
   * 
   * @param host
   * @param port
   * @param gameSetup
   * @throws AgentCreationException
   */
  public AbsAgent(String host, int port, ISetup gameSetup, String name) {
    super(host, port, gameSetup);
    final AbsAgent agent = this;
    this.name = name; 
    // All agents listen for messages.
    CLIENT.addListener(new Listener() {
      public void received(Connection connection, Object message) {
        synchronized (agent) {
          if (message instanceof AbsServerToAgentMessage) {
            AbsServerToAgentMessage theMessage =
                (AbsServerToAgentMessage) message;
            theMessage.agentDispatch(agent);
          }
        }
      }
    });

    CLIENT.sendTCP(new RegistrationMessage(-1, name));
    this.utility = 0.0;
  }
  
  @Override
  public void onBankUpdate(IUtilityUpdateMessage bankUpdate) {
    UserLogging.log(this.name + ": " + bankUpdate.toString());
    this.utility += bankUpdate.getMoneyAddedLost();
  }
  
  @Override
  public abstract void onInformationMessage(IInformationMessage informationMessage); 

  @Override
  public abstract void onActionRequestMessage(IActionRequestMessage tradeRequestMessage); 
  
  @Override
  public abstract void onTypeMessage(ITypeMessage valuationMessage); 
  
  @Override
  public abstract void onSimulationReportMessage(ISimulationReportMessage simReportMessage); 
  

}
