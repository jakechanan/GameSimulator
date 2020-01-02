package brown.auction.rules.activity;

import brown.auction.rules.AbsRule;
import brown.auction.rules.IActivityRule;
import brown.communication.messages.ITradeMessage;

public abstract class AbsActivity extends AbsRule implements IActivityRule {
  
  
  protected boolean isWellFormed(ITradeMessage aBid) {
    return true; 
  }
  
}
