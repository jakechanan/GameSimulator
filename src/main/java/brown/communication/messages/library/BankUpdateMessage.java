package brown.communication.messages.library;


import brown.communication.messages.IUtilityUpdateMessage;

/**
 * Message provided to agents when their accounts change
 */
public class BankUpdateMessage extends AbsBankUpdateMessage implements IUtilityUpdateMessage {

   
  public BankUpdateMessage() {
    super(null, null, null); 
  }
  
  public BankUpdateMessage(Integer messageID, Integer agentID, Double money) {
    super(messageID, agentID, money);
  }

}
