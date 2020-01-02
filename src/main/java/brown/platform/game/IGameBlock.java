package brown.platform.game;

import java.util.List;

/**
 * IMarketBlock encapsulates a collection of markets that run simultaneously. 
 * 
 * @author andrewcoggins
 *
 */
public interface IGameBlock {

  /**
   * Get the markets in the MarketBlock. 
   * 
   * @return
   */
  public List<IFlexibleRules> getMarkets();


}
