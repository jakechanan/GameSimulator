package brown.platform.accounting.library;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class AccountUpdateTest {

  @Test
  public void testOrder() {
    AccountUpdate anOrder = new AccountUpdate(0, 100.0);
    assertTrue(anOrder.getTo() == 0);
    assertTrue(anOrder.getFrom() == -1);
    assertTrue(anOrder.getCost() == 100.0);
  }

  @Test
  public void testOrderTwo() {
    AccountUpdate anOrder = new AccountUpdate(10, 11, 10.0);
    assertTrue(anOrder.getTo() == 10);
    assertTrue(anOrder.getFrom() == 11);
    assertTrue(anOrder.getCost() == 10.0);
  }

  @Test
  public void testOrderThree() {
    AccountUpdate anOrder = new AccountUpdate(0, 1, 100.0);
    Transaction aTransaction = new Transaction(0, 1, 100.0);
    assertEquals(anOrder.toTransaction(), aTransaction);
  }

}
