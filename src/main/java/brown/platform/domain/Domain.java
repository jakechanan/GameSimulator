package brown.platform.domain;

import brown.platform.managers.IAccountManager;
import brown.platform.managers.IEndowmentManager;
import brown.platform.managers.ITypeManager;

/**
 * a domain consists of Items, an a valuation distribution, and endowments.
 */
public class Domain implements IDomain {

  private ITypeManager valuationManager;
  private IEndowmentManager endowmentManager;
  private IAccountManager accountManager;

  public Domain(ITypeManager valuationManager,
      IEndowmentManager endowmentManager, IAccountManager acctManager) {
    this.valuationManager = valuationManager;
    this.endowmentManager = endowmentManager;
    this.accountManager = acctManager;
  }

  @Override
  public ITypeManager getValuationManager() {
    return valuationManager;
  }

  @Override
  public IEndowmentManager getEndowmentManager() {
    return this.endowmentManager;
  }

  @Override
  public IAccountManager getAccountManager() {
    return this.accountManager;
  }

  @Override
  public String toString() {
    return "Domain [valuationManager=" + valuationManager
        + ", endowmentManager=" + endowmentManager + ", accountManager="
        + accountManager + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result
        + ((accountManager == null) ? 0 : accountManager.hashCode());
    result = prime * result
        + ((endowmentManager == null) ? 0 : endowmentManager.hashCode());
    result = prime * result
        + ((valuationManager == null) ? 0 : valuationManager.hashCode());
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
    Domain other = (Domain) obj;
    if (accountManager == null) {
      if (other.accountManager != null)
        return false;
    } else if (!accountManager.equals(other.accountManager))
      return false;
    if (endowmentManager == null) {
      if (other.endowmentManager != null)
        return false;
    } else if (!endowmentManager.equals(other.endowmentManager))
      return false;
    if (valuationManager == null) {
      if (other.valuationManager != null)
        return false;
    } else if (!valuationManager.equals(other.valuationManager))
      return false;
    return true;
  }

}
