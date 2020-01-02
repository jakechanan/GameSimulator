package brown.platform.managers.library;

import brown.logging.library.PlatformLogging;
import brown.platform.domain.Domain;
import brown.platform.domain.IDomain;
import brown.platform.managers.IAccountManager;
import brown.platform.managers.IDomainManager;
import brown.platform.managers.IEndowmentManager;
import brown.platform.managers.ITypeManager;

/**
 * Domain Manager creates and stores domains. 
 * @author andrewcoggins
 *
 */
public class DomainManager implements IDomainManager {

    private IDomain domain;
    private boolean lock;

    /**
     * domainManager constructor instantiates and stores Domain
     */
    public DomainManager() {
        this.lock = false;
    }

    public void createDomain(ITypeManager valuation,
                             IEndowmentManager endowmentManager, IAccountManager acctManager) {
        if (!this.lock){
            this.domain = new Domain(valuation, endowmentManager, acctManager);
            this.lock = true;
        } else {
            PlatformLogging.log("Creation denied: domain manager locked.");
        }
    }

    public IDomain getDomain() {
        return this.domain;
    }

}
