package brown.platform.accounting.library;

import brown.platform.accounting.IAccountUpdate;
import brown.platform.accounting.ITransaction;

public class AccountUpdate implements IAccountUpdate {

	private Integer TO;
	private Integer FROM;
	private double PRICE;

	/**
	 * For Kryo DO NOT USE
	 */
	public AccountUpdate() {
		this.TO = null;
		this.FROM = null;
		this.PRICE = -1;
	}

	public AccountUpdate(Integer to, double price) {
		this.TO = to;
		this.FROM = -1;
		this.PRICE = price;
	}

	/**
	 * Constructor
	 * 
	 * @param to    agent whose account is to be updated
	 * @param from  agent who the update is from (not relevant in oneSided)
	 * @param price money involved in the exchange
	 * @param cart  added or removed
	 */
	public AccountUpdate(Integer to, Integer from, double price) {
		this.TO = to;
		this.FROM = from;
		this.PRICE = price;
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
	public ITransaction toTransaction() {
		return new Transaction(TO, FROM, PRICE);
	}

	@Override
	public String toString() {
		return "AccountUpdate [TO=" + TO + ", FROM=" + FROM + ", PRICE=" + PRICE + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((FROM == null) ? 0 : FROM.hashCode());
		long temp;
		temp = Double.doubleToLongBits(PRICE);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		AccountUpdate other = (AccountUpdate) obj;
		if (FROM == null) {
			if (other.FROM != null)
				return false;
		} else if (!FROM.equals(other.FROM))
			return false;
		if (Double.doubleToLongBits(PRICE) != Double.doubleToLongBits(other.PRICE))
			return false;
		if (TO == null) {
			if (other.TO != null)
				return false;
		} else if (!TO.equals(other.TO))
			return false;
		return true;
	}

}
