package brown.communication.messages;

public interface IUtilityUpdateMessage extends IServerToAgentMessage {

  public Integer getAgentID(); 
  
  public Double getMoneyAddedLost(); 
  
}
