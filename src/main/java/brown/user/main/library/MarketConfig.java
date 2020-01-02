package brown.user.main.library;

import brown.platform.game.IFlexibleRules;
import brown.user.main.IGameConfig;

public class MarketConfig implements IGameConfig {
  
  private IFlexibleRules rules; 
 
  public MarketConfig(IFlexibleRules marketRules) {
    this.rules = marketRules;  
  }
  
  @Override
  public IFlexibleRules getRules() {
    return this.rules;
  }

  @Override
  public String toString() {
    return "MarketConfig [rules=" + rules + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((rules == null) ? 0 : rules.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    MarketConfig other = (MarketConfig) obj;
    if (rules == null) {
      if (other.rules != null)
        return false;
    } else if (!rules.equals(other.rules))
      return false;
    return true;
  }
  
  
  
}