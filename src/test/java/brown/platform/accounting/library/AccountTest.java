package brown.platform.accounting.library;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import brown.platform.accounting.IAccount;

/**
 * Test for account class.
 * 
 * @author andrew
 *
 */
public class AccountTest {

  @Test
  public void testAccountInit() {

    IAccount a = new Account(100, 90.0);

    assertTrue(a.getID() == 100);

    assertTrue(a.getMoney() == 90.0);
  }

  @Test
  public void testAccount() {

    IAccount a = new Account(5, 100.0);

    // test money

    a.addMoney(50.0);

    assertTrue(a.getMoney() == 150.0);

    a.removeMoney(90.0);

    assertTrue(a.getMoney() == 60.0);

    a.clear();

    assertTrue(a.getMoney() == 0.0);

  }

}
