package brown.auction.type.generator; 

/**
 * IValuationGenerator produces doubles according to a specified distribution.
 * @author acoggins
 *
 */
public interface ITypeGenerator {
  
  /**
   * makes a valuation according to some specified distribution.
   * @return a valuation, represented as a double. 
   */
  public abstract Double makeValuation();
  
}