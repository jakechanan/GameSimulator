package brown.platform.managers.library;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import brown.auction.endowment.IEndowment;
import brown.auction.endowment.library.Endowment;
import brown.platform.accounting.IAccount;
import brown.platform.accounting.library.Account;
import brown.platform.managers.IAccountManager;

public class AccountManagerTest {
  

  @Test 
  public void testAccountCreation() {
    
    IAccountManager manager = new AccountManager(); 
    
    IEndowment e = new Endowment(100.0); 
    
    manager.createAccount(0, e);
    manager.createAccount(1, e);
    
    IAccount accountZero = new Account(0, 100.0); 
    IAccount accountOne = new Account(1, 100.0); 
    
    assertTrue(manager.containsAccount(0)); 
    assertTrue(manager.containsAccount(1)); 
    assertEquals(manager.getAccount(0), accountZero); 
    assertEquals(manager.getAccount(1), accountOne); 
    
    List<IAccount> accountList = new LinkedList<IAccount>(); 
    accountList.add(accountZero); 
    accountList.add(accountOne); 
    
    assertEquals(manager.getAccounts(), accountList); 
    
    manager.lock();
    
    manager.createAccount(2, e);
    
    assertTrue(!manager.containsAccount(2)); 
    
  }
  
  @Test 
  public void testAccountManager() {
    
    IAccountManager manager = new AccountManager(); 
    
    IEndowment e = new Endowment(100.0); 
    
    manager.createAccount(0, e);
    manager.createAccount(1, e);
    
    IAccount act = manager.getAccount(0); 
    
    act.addMoney(1000.0);

    manager.setAccount(0, act);
    
    assertEquals(manager.getAccount(0), act); 
    
    IAccount acctOne = new Account(0, 60.0); 
    IAccount acctTwo = new Account(1, 70.0); 
    
    manager.reendow(0, new Endowment(60.0));
    manager.reendow(1, new Endowment(70.0));
    
    assertEquals(manager.getAccount(0), acctOne); 
    assertEquals(manager.getAccount(1), acctTwo); 
        
  }
  
}