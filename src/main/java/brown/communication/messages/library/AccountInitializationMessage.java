package brown.communication.messages.library;

import brown.communication.messages.IUtilityUpdateMessage;

/**
 * Message provided to agents when their accounts change
 */
public class AccountInitializationMessage extends AbsBankUpdateMessage
    implements IUtilityUpdateMessage {

  private Integer agentID;
  private Double money;

  public AccountInitializationMessage() {
    super(null, null, null);
  }

  public AccountInitializationMessage(Integer messageID, Integer agentID,
      Double money) {
    super(messageID, agentID, money);
    this.agentID = agentID;
    this.money = money;
  }

  @Override
  public String toString() {
    return "AccountInitializationMessage [agentID=" + agentID + ", money="
        + money + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((agentID == null) ? 0 : agentID.hashCode());
    result = prime * result + ((money == null) ? 0 : money.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!super.equals(obj))
      return false;
    if (getClass() != obj.getClass())
      return false;
    AccountInitializationMessage other = (AccountInitializationMessage) obj;
    if (agentID == null) {
      if (other.agentID != null)
        return false;
    } else if (!agentID.equals(other.agentID))
      return false;
    if (money == null) {
      if (other.money != null)
        return false;
    } else if (!money.equals(other.money))
      return false;
    return true;
  }

}
