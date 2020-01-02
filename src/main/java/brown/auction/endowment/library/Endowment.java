package brown.auction.endowment.library;

import brown.auction.endowment.IEndowment;

public class Endowment implements IEndowment {

  private Double money;

  public Endowment(Double money) {
    this.money = money;
  }

  public Double getMoney() {
    return this.money;
  }

  @Override
  public String toString() {
    return "Endowment [money=" + money + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((money == null) ? 0 : money.hashCode());
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
    Endowment other = (Endowment) obj;
    if (money == null) {
      if (other.money != null)
        return false;
    } else if (!money.equals(other.money))
      return false;
    return true;
  }

}
