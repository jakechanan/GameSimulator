package brown.platform.managers;

import brown.platform.domain.IDomain;

/**
 * A domain manager creates domains.
 */
public interface IDomainManager {

    /**
     * creates a domain
     * @param manager domain tradeable manager
     * @param valuation domain valuation distribution
     * @param acctManager domain accountManager
     */
    void createDomain(ITypeManager valuation,
                      IEndowmentManager endowmentManager, IAccountManager acctManager);

    /**
     * get the domain inside the mananger
     * @return
     */
    IDomain getDomain();

}
