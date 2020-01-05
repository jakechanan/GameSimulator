package brown.platform.game;

import java.util.List;

import brown.auction.marketstate.IMarketState;
import brown.communication.messages.IActionMessage;
import brown.communication.messages.IActionRequestMessage;
import brown.platform.accounting.IAccountUpdate;

/**
 * IMarket is the interface for the Market class. 
 * @author andrewcoggins
 *
 */
public interface IGame {

  /**
   * Get the ID of the market. 
   * @return
   */
  public Integer getMarketID();

  /**
   * Construct a trade request for the market. 
   * @param agentID
   * ID of the agent the trade request is intended for.
   * @return
   * TradeRequestMessage. 
   */
  public IActionRequestMessage constructTradeRequest(Integer agentID);

  /**
   * Process a ITradeMessage as a bid. 
   * @param bid
   * @return
   * boolean determining whether or not the bid was accepted. 
   */
  public boolean processBid(IActionMessage bid);
  
  /**
   * Construct account updates once the market has completed. 
   * @return
   */
  public List<IAccountUpdate> constructAccountUpdates();

  /**
   * indicate that a timestep has passed if the auction is discrete. 
   */
  public void tick();
  
  
  public Integer getTimestep(); 
  
  
  /**
   * update some inner information of the market. 
   * Occurs per Inner IRPolicy
   */
  public void updateInnerInformation(); 
  
  /**
   * update some outer information of the market. 
   * Occurs per IR Policy. 
   */
  public void updateOuterInformation(); 
  
  /**
   * clear cache of bids within the market. 
   */
  public void clearBidCache();

  /**
   * signals whether or not the market is open. 
   * @return
   */
  public boolean isOpen(); 
  
  /**
   * get the MarketPublicState of the market. 
   * this represents the information allowed by the 
   * IR Policies. 
   * @return
   */
  public IMarketState getPublicState(); 
  
  public void updateTradeHistory(); 
  
}
