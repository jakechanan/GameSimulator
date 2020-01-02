package brown.platform.accounting.library;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Test for Transaction.
 * 
 * @author andrew
 *
 */
public class TransactionTest {

  @Test
  public void testTransactionr() {
    Transaction anOrder = new Transaction(0, 100.0);
    assertTrue(anOrder.getTo() == 0);
    assertTrue(anOrder.getFrom() == -1);
    assertTrue(anOrder.getCost() == 100.0);
  }

  @Test
  public void testTransactionTwo() {
    Transaction anOrder = new Transaction(10, 11, 10.0);
    assertTrue(anOrder.getTo() == 10);
    assertTrue(anOrder.getFrom() == 11);
    assertTrue(anOrder.getCost() == 10.0);
  }

}
