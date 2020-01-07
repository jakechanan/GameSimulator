package brown.auction.rules.activity;

import brown.auction.rules.AbsRule;
import brown.auction.rules.IActivityRule;
import brown.communication.messages.IActionMessage;

public abstract class AbsActivity extends AbsRule implements IActivityRule {
  
  
  protected boolean isWellFormed(IActionMessage aBid) {
    return true; 
  }
  
}
