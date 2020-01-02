package brown.platform.accounting;

/**
 * Object to specify an update to an account resulting from an auction.
 * 
 * @author andrewcoggins
 *
 */
public interface IAccountUpdate {

	/**
	 * Converts the IAccountUpdate to an ITransaction.
	 * 
	 * @return an ITransaction reflecting the IAccountUpdate
	 */
	public ITransaction toTransaction();

	/**
	 * Gets the recipient of the IAccountUpdate.
	 *
	 * @return
	 */
	public Integer getTo();

	/**
	 * Gets the sender of the IAccountUpdate, if applicable.
	 * 
	 * @return
	 */
	public Integer getFrom();

	/**
	 * Gets the money to be exchanged in the IAccountUpdate.
	 * 
	 * @return
	 */
	public Double getCost();

}
