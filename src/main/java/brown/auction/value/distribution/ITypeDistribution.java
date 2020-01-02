package brown.auction.value.distribution;

import brown.auction.value.valuation.IType;

/**
 * IValuationDistribution samples IValuations from a distribution. 
 * @author andrew
 */
public interface ITypeDistribution {
  
  /**
   * samples IValuations from a distribution
   * @return IValuation
   */
  public IType sample();
  
  
}