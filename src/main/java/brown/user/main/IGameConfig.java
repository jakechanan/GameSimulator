package brown.user.main;

import java.util.List;

import brown.platform.game.IFlexibleRules;

/**
 * config for specifying markets. See implementation for details. 
 * @author andrewcoggins
 *
 */
public interface IGameConfig extends IInputConfig {
  
  /**
   * get rules
   * @return
   */
  public  IFlexibleRules getRules(); 
  
}