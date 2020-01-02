package brown.platform.accounting;

/**
 * An account stores agent money and items.
 * 
 * @author andrewcoggins
 *
 */
public interface IAccount {

	/**
	 * Get the agent ID of the account holder.
	 * 
	 * @return agent ID
	 */
	public int getID();

	/**
	 * Get the money in the account.
	 * 
	 * @return the money in the account
	 */
	public double getMoney();

	/**
	 * Add money to the account.
	 * 
	 * @param money the money to be added
	 */
	public void addMoney(double money);

	/**
	 * Remove money from the account.
	 * 
	 * @param newMoney the money to be removed
	 */
	public void removeMoney(double newMoney);

	/**
	 * Remove all items and all money from the account.
	 */
	public void clear();

}
