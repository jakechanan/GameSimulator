package brown.auction.marketstate.library;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import brown.auction.marketstate.IMarketPublicState;
import brown.communication.messages.IActionMessage;
import brown.platform.accounting.IAccountUpdate;

/**
 * Standard MarketState stores the internal information of a market.
 * 
 * @author acoggins
 */
public class MarketPublicState implements IMarketPublicState {

  private int ticks;
  private long time;

  // history
  private List<List<IActionMessage>> tradeHistory;

  // Utility rule
  private List<IAccountUpdate> payments;

  // activity rule also deals with reserve prices. 
  private Map<String, Double> reserves;


  public MarketPublicState() {
    this.payments = new LinkedList<IAccountUpdate>();
    this.time = System.currentTimeMillis();
    this.tradeHistory = new LinkedList<List<IActionMessage>>();
  }

  @Override
  public void tick() {
    this.ticks++;
  }

  @Override
  public int getTicks() {
    return this.ticks;
  }

  @Override
  public long getTime() {
    return this.time;
  }

  @Override
  public List<IAccountUpdate> getUtilities() {
    return this.payments;
  }

  @Override
  public void setUtilities(List<IAccountUpdate> payments) {
    this.payments = payments;
  }

  @Override
  public List<List<IActionMessage>> getTradeHistory() {
    return this.tradeHistory;
  }

  @Override
  public void addToTradeHistory(List<IActionMessage> tradeMessages) {
    this.tradeHistory.add(tradeMessages);
  }

  @Override
  public Map<String, Double> getReserves() {
    return this.reserves; 
  }

  @Override
  public void setReserves(Map<String, Double> reserves) {
    this.reserves = reserves; 
  }

  public void setTime(long time) {
    this.time = time; 
  }
  
  public void setTicks(int ticks) {
    this.ticks = ticks;
  }

  
  @Override
  public String toString() {
    return "MarketPublicState [ticks=" + ticks + ", time=" + time
        + ", tradeHistory=" + tradeHistory + ", payments=" + payments
        + ", reserves=" + reserves + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((payments == null) ? 0 : payments.hashCode());
    result = prime * result + ((reserves == null) ? 0 : reserves.hashCode());
    result = prime * result + ticks;
    result = prime * result + (int) (time ^ (time >>> 32));
    result =
        prime * result + ((tradeHistory == null) ? 0 : tradeHistory.hashCode());
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
    MarketPublicState other = (MarketPublicState) obj;
    if (payments == null) {
      if (other.payments != null)
        return false;
    } else if (!payments.equals(other.payments))
      return false;
    if (reserves == null) {
      if (other.reserves != null)
        return false;
    } else if (!reserves.equals(other.reserves))
      return false;
    if (ticks != other.ticks)
      return false;
    if (time != other.time)
      return false;
    if (tradeHistory == null) {
      if (other.tradeHistory != null)
        return false;
    } else if (!tradeHistory.equals(other.tradeHistory))
      return false;
    return true;
  }
  

  
}
