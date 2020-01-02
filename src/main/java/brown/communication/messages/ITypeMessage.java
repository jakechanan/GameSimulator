package brown.communication.messages;

import brown.auction.type.valuation.IType;

public interface ITypeMessage extends IServerToAgentMessage {

  public IType getValuation(); 
}
