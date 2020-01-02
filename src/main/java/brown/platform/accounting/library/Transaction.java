package brown.platform.accounting.library;

import brown.platform.accounting.ITransaction;

/**
 * A transaction is a trade that transpired. Each one is recorded in the ledger.
 */
public class Transaction implements ITransaction {

  private Integer TO;
  private Integer FROM;
  private double PRICE;
  private long TIMESTAMP;

  /**
   * For Kryo DO NOT USE
   */
  public Transaction() {
    this.TO = null;
    this.FROM = null;
    this.PRICE = -1;
    this.TIMESTAMP = 0;
  }

  public Transaction(Integer to, double price) {
    this.TO = to;
    this.FROM = -1;
    this.PRICE = price;
    this.TIMESTAMP = System.currentTimeMillis();
  }

  /**
   * Constructor
   * 
   * @param to agent whose account is to be updated
   * @param from agent who the update is from (not relevant in oneSided)
   * @param price money involved in the exchange
   * @param cart added or removed
   */
  public Transaction(Integer to, Integer from, double price) {
    this.TO = to;
    this.FROM = from;
    this.PRICE = price;
    this.TIMESTAMP = System.currentTimeMillis();
  }

  public Transaction sanitize(Integer ID) {
    return new Transaction(ID != null && ID.equals(TO) ? TO : null,
        ID != null && ID.equals(FROM) ? FROM : null, PRICE);
  }

  @Override
  public Integer getTo() {
    return this.TO;
  }

  @Override
  public Integer getFrom() {
    return this.FROM;
  }

  @Override
  public Double getCost() {
    return this.PRICE;
  }

}
