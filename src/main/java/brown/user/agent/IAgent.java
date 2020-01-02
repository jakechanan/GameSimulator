package brown.user.agent;

import brown.communication.messages.IUtilityUpdateMessage;
import brown.communication.messages.IInformationMessage;
import brown.communication.messages.IActionRequestMessage;
import brown.communication.messages.ITypeMessage;
import brown.system.client.IClient;

/**
 * Agents are responsible for receiving messages from the server,
 * constructing bids, and sending them back.
 * @author andrew
 */
public interface IAgent extends IClient { 

  /**
   * Sent whenever an agent's bank account changes
   * @param bankUpdate - contains the old and new bank accounts
   */
  public void onBankUpdate(IUtilityUpdateMessage bankUpdate);

  public void onInformationMessage(IInformationMessage informationMessage); 
  
  public void onActionRequestMessage(IActionRequestMessage tradeRequestMessage); 
  
  public void onTypeMessage(ITypeMessage valuationMessage); 

}
