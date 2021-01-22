package brown.auction.type.distribution;

import java.util.List;

import brown.auction.type.valuation.IType;

/**
 * IValuationDistribution samples IValuations from a distribution. 
 * @author andrew
 */
public interface ITypeDistribution {
  
  /**
   * samples IValuations from a distribution
   * @return IValuation
   */
  public IType sample(Integer agentID, List<List<Integer>> agentGroups);
  
}