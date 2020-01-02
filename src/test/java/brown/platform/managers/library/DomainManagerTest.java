package brown.platform.managers.library;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import org.junit.Test;

import brown.platform.domain.Domain;
import brown.platform.domain.IDomain;
import brown.platform.managers.IAccountManager;
import brown.platform.managers.IDomainManager;
import brown.platform.managers.IEndowmentManager;
import brown.platform.managers.ITypeManager;

public class DomainManagerTest {
  
  @Test
  public void testDomainManager() {
    ITypeManager mockedVManager = mock(TypeManager.class); 
    IEndowmentManager mockedEManager = mock(EndowmentManager.class); 
    IAccountManager mockedAManager = mock(AccountManager.class); 
    
    IDomainManager dManager = new DomainManager(); 
    
    dManager.createDomain(mockedVManager, mockedEManager, mockedAManager); 
    IDomain expected = new Domain(mockedVManager, mockedEManager, mockedAManager); 
    assertEquals(dManager.getDomain(), expected); 
    
  }
  
}
