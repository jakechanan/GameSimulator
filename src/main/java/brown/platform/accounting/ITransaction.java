package brown.platform.accounting;

import brown.platform.accounting.library.Transaction;

/**
 * ITransaction describes a change made to an account.
 * 
 * @author andrewcoggins
 *
 */
public interface ITransaction {

	/**
	 * Remove private details from the transaction (e.g., the agent's private ID).
	 * 
	 * @param agentID the agent ID to be sanitized
	 * @return a sanitized transaction
	 */
	public Transaction sanitize(Integer agentID);

	/**
	 * Get the recipient of the transaction.
	 * 
	 * @return
	 */
	public Integer getTo();

	/**
	 * Get the sender of the transaction, if applicable.
	 * 
	 * @return
	 */
	public Integer getFrom();

	/**
	 * Get the amount of money exchanged in the transaction.
	 * 
	 * @return
	 */
	public Double getCost();

}
