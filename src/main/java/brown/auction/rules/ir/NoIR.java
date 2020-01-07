package brown.auction.rules.ir;

import brown.auction.marketstate.IMarketState;
import brown.auction.rules.AbsRule;
import brown.auction.rules.IInformationRevelationPolicy;

public class NoIR extends AbsRule implements IInformationRevelationPolicy {

  @Override
  public void updatePublicState(IMarketState state, IMarketState publicState) {
    // noop. no information here. 
  }


}
