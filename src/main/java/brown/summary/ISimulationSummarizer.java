package brown.summary;

import java.util.List;
import java.util.Map;

import brown.accounting.library.Account;
import brown.value.valuation.IValuation;

/**
 * ISimulation summary collects information across simulations.
 * @author andrew
 *
 */
public interface ISimulationSummarizer {
  
  /**
   * collects information for summary.
   * @param agentAccounts all agent accounts. 
   * @param privateValuations all agent private valuations.
   */
  public void collectInformation(List<Account> agentAccounts, Map<Integer, IValuation> privateValuations);
}