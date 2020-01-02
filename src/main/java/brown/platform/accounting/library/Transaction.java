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

  @Override
  public String toString() {
    return "Transaction [TO=" + TO + ", FROM=" + FROM + ", PRICE=" + PRICE
        + ", TIMESTAMP=" + TIMESTAMP + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((FROM == null) ? 0 : FROM.hashCode());
    long temp;
    temp = Double.doubleToLongBits(PRICE);
    result = prime * result + (int) (temp ^ (temp >>> 32));
    result = prime * result + (int) (TIMESTAMP ^ (TIMESTAMP >>> 32));
    result = prime * result + ((TO == null) ? 0 : TO.hashCode());
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
    Transaction other = (Transaction) obj;
    if (FROM == null) {
      if (other.FROM != null)
        return false;
    } else if (!FROM.equals(other.FROM))
      return false;
    if (Double.doubleToLongBits(PRICE) != Double.doubleToLongBits(other.PRICE))
      return false;
    if (TIMESTAMP != other.TIMESTAMP)
      return false;
    if (TO == null) {
      if (other.TO != null)
        return false;
    } else if (!TO.equals(other.TO))
      return false;
    return true;
  }

}
