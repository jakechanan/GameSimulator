package brown.platform.domain;

import brown.platform.managers.IAccountManager;
import brown.platform.managers.IEndowmentManager;
import brown.platform.managers.ITypeManager;

/**
 * Interface IDomain
 * Characterized by items, valuation distributions, and endowments.
 */
public interface IDomain {

  /**
   * get IDomainManager's ValuationManager
   * 
   * @return
   */
  public ITypeManager getValuationManager(); 
  
  /**
   * get IDomainManager's EndowmentManager
   * 
   * @return
   */
  public IEndowmentManager getEndowmentManager(); 
  
  /**
   * get IDomainManager's AccountManager
   * 
   * @return
   */
  public IAccountManager getAccountManager(); 
  
}
