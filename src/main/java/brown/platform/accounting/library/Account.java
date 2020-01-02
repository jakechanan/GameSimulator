package brown.platform.accounting.library;

import brown.platform.accounting.IAccount;

public class Account implements IAccount {

  private Integer ID;
  private double money;

  /**
   * For Kryo DO NOT USE
   */
  public Account() {
    this.ID = null;
    this.money = 0.0;
  }

  /**
   * An account is constructed with an agent's private ID, starting money, and
   * starting items
   * 
   * @param ID agent's private ID
   * @param money agent's starting money
   * @param goods agent's starting goods.
   */
  public Account(Integer ID, double money) {
    this.ID = ID;
    this.money = money;
  }

  public int getID() {
    return this.ID;
  }

  public double getMoney() {
    return this.money;
  }

  public void addMoney(double money) {
    this.money += money;
  }

  public void removeMoney(double newMoney) {
    this.money -= newMoney;
  }

  public void clear() {
    this.money = 0.0;
  }

  @Override
  public String toString() {
    return "Account [ID=" + ID + ", money=" + money + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((ID == null) ? 0 : ID.hashCode());
    long temp;
    temp = Double.doubleToLongBits(money);
    result = prime * result + (int) (temp ^ (temp >>> 32));
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
    Account other = (Account) obj;
    if (ID == null) {
      if (other.ID != null)
        return false;
    } else if (!ID.equals(other.ID))
      return false;
    if (Double.doubleToLongBits(money) != Double.doubleToLongBits(other.money))
      return false;
    return true;
  }

}
