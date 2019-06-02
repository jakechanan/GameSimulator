package brown.platform.accounting.library;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import brown.platform.item.IItem;
import brown.platform.item.library.Item;

public class AccountUpdateTest {

  @Test
  public void testOrder() {

    IItem anItem = new Item("a");
    AccountUpdate anOrder = new AccountUpdate(0, 100.0, anItem);
    assertTrue(anOrder.TO == 0);
    assertTrue(anOrder.FROM == -1);
    assertTrue(anOrder.PRICE == 100.0);
    assertEquals(anOrder.ITEM, anItem);

  }

  @Test
  public void testOrderTwo() {

    IItem anItem = new Item("c", 3);
    AccountUpdate anOrder = new AccountUpdate(10, 11, 10.0, anItem);
    assertTrue(anOrder.TO == 10);
    assertTrue(anOrder.FROM == 11);
    assertTrue(anOrder.PRICE == 10.0);
    assertEquals(anOrder.ITEM, anItem);
  }

  @Test
  public void testOrderThree() {
    IItem anItem = new Item("d", 3);
    AccountUpdate anOrder = new AccountUpdate(0, 1, 100.0, anItem);
    Transaction aTransaction = new Transaction(0, 1, 100.0, anItem);
    assertEquals(anOrder.toTransaction(), aTransaction);
  }

}
