package brown.rules.library;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import brown.accounting.bid.AuctionBid;
import brown.accounting.bidbundle.library.BundleType;
import brown.accounting.bidbundle.library.AuctionBidBundle;
import brown.market.marketstate.ICompleteState;
import brown.market.marketstate.library.Allocation;
import brown.messages.library.TradeMessage;
import brown.rules.IAllocationRule;
import brown.tradeable.library.MultiTradeable;

/**
 * implements an allocation rule where the highest bidder is allocated the good(?)
 * @author andrew
 *
 */
public class SimpleHighestBidderAllocation implements IAllocationRule {


  @Override
  public void setAllocation(ICompleteState state) {
    Map<MultiTradeable, Double> highest = new HashMap<MultiTradeable, Double>();
    List<TradeMessage> allBids = state.getBids();
    for(TradeMessage bid : allBids) {
      if(bid.Bundle.getType().equals(BundleType.AUCTION)) {
        AuctionBidBundle bundle = (AuctionBidBundle) bid.Bundle; 
        for (MultiTradeable t : bundle.getBids().bids.keySet()) {
          if(highest.get(t) == null || highest.get(t) < bundle.getBids().bids.get(t)) { 
            highest.put(t, bundle.getBids().bids.get(t));
          }
        }
      }
    }
    state.setAllocation(new Allocation(new AuctionBid(highest)));
  }


  @Override
  public void reset() {
    // TODO Auto-generated method stub
    
  }
}

