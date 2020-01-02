package brown.platform.managers.library;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import brown.auction.endowment.IEndowment;
import brown.communication.messages.IUtilityUpdateMessage;
import brown.communication.messages.library.AccountInitializationMessage;
import brown.communication.messages.library.BankUpdateMessage;
import brown.logging.library.ErrorLogging;
import brown.logging.library.PlatformLogging;
import brown.platform.accounting.IAccount;
import brown.platform.accounting.IAccountUpdate;
import brown.platform.accounting.library.Account;
import brown.platform.managers.IAccountManager;

/**
 * Account manager stores and manages accounts for the server.
 * 
 * @author acoggins, modified by kerry
 */
public class AccountManager implements IAccountManager {

  private Map<Integer, IAccount> accounts;
  private boolean lock;

  /**
   * AccountManager constructor stores Hashmap, and is initially unlocked.
   */
  public AccountManager() {
    this.accounts = new ConcurrentHashMap<Integer, IAccount>();
    this.lock = false;
  }

  public void createAccount(Integer agentID, IEndowment endowment) {
    if (!this.lock) {
      synchronized (agentID) {
        this.accounts.put(agentID,
            new Account(agentID, endowment.getMoney()));
      }
    } else {
      PlatformLogging.log("Creation denied: account manager locked.");
    }
  }

  public IAccount getAccount(Integer ID) {
    return accounts.get(ID);
  }

  public List<IAccount> getAccounts() {
    return new ArrayList<>(accounts.values());
  }

  public void setAccount(Integer ID, IAccount account) {
    synchronized (ID) {
      if (this.accounts.containsKey(ID)) {
        accounts.put(ID, account);
      }
    }
  }

  public void reset() {
    for (Integer agentID : this.accounts.keySet()) {
      IAccount agentAccount = this.accounts.get(agentID);
      agentAccount.clear();
      this.accounts.put(agentID, agentAccount);
    }
  }

  public Boolean containsAccount(Integer ID) {
    return accounts.containsKey(ID);
  }

  public void reendow(Integer agentID, IEndowment initialEndowment) {
    try {
      IAccount endowAccount = this.accounts.get(agentID);
      endowAccount.clear();
      endowAccount.addMoney(initialEndowment.getMoney());
      this.accounts.put(agentID, endowAccount);
    } catch (NullPointerException n) {
      ErrorLogging.log("ERROR: AccountManager: ID not found.");
      throw n;
    }
  }

  public void lock() {
    this.lock = true;
  }

  @Override
  public Map<Integer, IUtilityUpdateMessage> constructInitializationMessages() {
    Map<Integer, IUtilityUpdateMessage> bankUpdates =
        new HashMap<Integer, IUtilityUpdateMessage>();
    for (Integer agentID : this.accounts.keySet()) {
      IAccount agentAccount = this.accounts.get(agentID);
      IUtilityUpdateMessage agentBankUpdate =
          new AccountInitializationMessage(0, agentID, agentAccount.getMoney());
      bankUpdates.put(agentID, agentBankUpdate);
    }
    return bankUpdates;
  }

  @Override
  public Map<Integer, IUtilityUpdateMessage>
      constructBankUpdateMessages(List<IAccountUpdate> accountUpdates) {
    Map<Integer, IUtilityUpdateMessage> bankUpdates =
        new HashMap<Integer, IUtilityUpdateMessage>();
    accountUpdates.forEach(update -> bankUpdates.put(update.getTo(),
        new BankUpdateMessage(0, update.getTo(), update.getCost())));
    return bankUpdates;

  }

  @Override
  public void updateAccounts(List<IAccountUpdate> accountUpdates) {
    for (IAccountUpdate update : accountUpdates) {
      if (this.accounts.containsKey(update.getTo())) {
        IAccount account = this.accounts.get(update.getTo());
        account.addMoney(update.getCost());
      }
    }

  }

}
