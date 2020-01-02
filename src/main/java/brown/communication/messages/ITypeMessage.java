package brown.communication.messages;

import brown.auction.value.valuation.IType;

public interface ITypeMessage extends IServerToAgentMessage {

  public IType getValuation(); 
}
