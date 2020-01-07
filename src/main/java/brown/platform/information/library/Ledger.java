package brown.platform.information.library;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import brown.communication.messages.IActionMessage;
import brown.platform.accounting.ITransaction;
import brown.platform.information.ILedger;

public class Ledger implements ILedger {

  private Map<Integer, Map<Integer, List<IActionMessage>>> acceptedBids;
  private Map<Integer, Map<Integer, List<IActionMessage>>> rejectedBids;
  private Map<Integer, List<ITransaction>> transactions;

  public Ledger() {
    this.acceptedBids = new HashMap<Integer, Map<Integer, List<IActionMessage>>>();
    this.rejectedBids = new HashMap<Integer, Map<Integer, List<IActionMessage>>>();
    this.transactions = new HashMap<Integer, List<ITransaction>>();
  }

  @Override
  public void postBid(IActionMessage aBid, Integer timeStep) {
    if (this.acceptedBids.containsKey(aBid.getAuctionID())) {
      Map<Integer, List<IActionMessage>> marketBids = this.acceptedBids.get(aBid.getAuctionID()); 
      if (marketBids.containsKey(timeStep)) {
        List<IActionMessage> bids = marketBids.get(timeStep); 
        bids.add(aBid); 
        marketBids.put(timeStep, bids); 
      } else {
        List<IActionMessage> bids = new LinkedList<IActionMessage>();
        bids.add(aBid); 
        marketBids.put(timeStep, bids); 
      }
      this.acceptedBids.put(aBid.getAuctionID(), marketBids); 
    } else {
      Map<Integer, List<IActionMessage>> marketBids = new HashMap<Integer, List<IActionMessage>>(); 
      List<IActionMessage> bids = new LinkedList<IActionMessage>(); 
      bids.add(aBid); 
      marketBids.put(timeStep, bids); 
      this.acceptedBids.put(aBid.getAuctionID(), marketBids); 
    }
  }

  @Override
  public void postRejectedBid(IActionMessage aBid, Integer timeStep) {
    if (this.rejectedBids.containsKey(aBid.getAuctionID())) {
      Map<Integer, List<IActionMessage>> marketBids = this.rejectedBids.get(aBid.getAuctionID()); 
      if (marketBids.containsKey(timeStep)) {
        List<IActionMessage> bids = marketBids.get(timeStep); 
        bids.add(aBid); 
        marketBids.put(timeStep, bids); 
      } else {
        List<IActionMessage> bids = new LinkedList<IActionMessage>();
        bids.add(aBid); 
        marketBids.put(timeStep, bids); 
      }
      this.rejectedBids.put(aBid.getAuctionID(), marketBids); 
    } else {
      Map<Integer, List<IActionMessage>> marketBids = new HashMap<Integer, List<IActionMessage>>(); 
      List<IActionMessage> bids = new LinkedList<IActionMessage>(); 
      bids.add(aBid); 
      marketBids.put(timeStep, bids); 
      this.rejectedBids.put(aBid.getAuctionID(), marketBids); 
    }
  }

  @Override
  public void postTransaction(Integer marketID, ITransaction transaction) {
    if (this.transactions.containsKey(marketID)) {
      List<ITransaction> marketTransactions = this.transactions.get(marketID);
      marketTransactions.add(transaction); 
      this.transactions.put(marketID, marketTransactions);
    } else {
      List<ITransaction> marketTransactions = new LinkedList<ITransaction>(); 
      marketTransactions.add(transaction); 
      this.transactions.put(marketID, marketTransactions);
    }
  }

  @Override
  public Map<Integer, List<IActionMessage>> getAcceptedBids(Integer marketID) {
    if (this.acceptedBids.containsKey(marketID)) {
      return this.acceptedBids.get(marketID);     
    } else {
      return new HashMap<Integer, List<IActionMessage>>(); 
    }
  }

  @Override
  public Map<Integer, List<IActionMessage>> getRejectedBids(Integer marketID) {
    if (this.rejectedBids.containsKey(marketID)) {
      return this.rejectedBids.get(marketID);     
    } else {
      return new HashMap<Integer, List<IActionMessage>>(); 
    }
  }

  @Override
  public List<ITransaction> getMarketTransactions(Integer marketID) {
    if (this.transactions.containsKey(marketID)) {
      return this.transactions.get(marketID); 
    } else {
      return new LinkedList<ITransaction>(); 
    }
  }

}
