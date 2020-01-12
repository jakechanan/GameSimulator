package brown.auction.marketstate;

import java.util.List;
import java.util.Map;

import brown.communication.messages.IActionMessage;
import brown.platform.accounting.IAccountUpdate;
/**
 * Stores the internal state of a market as it runs. Consists of a series of
 * getters and setters for the various fields of the market state.
 * 
 * @author acoggins
 */
public interface IMarketPublicState {

  // Utility rule
  public void setUtilities(List<IAccountUpdate> payment);

  public List<IAccountUpdate> getUtilities();

  
  public Map<String, Double> getReserves(); 
  
  public void setReserves(Map<String, Double> reserves); 

  // Termination condition
  public long getTime();

  public void tick();

  public int getTicks();
  
  public List<List<IActionMessage>> getTradeHistory(); 
  
  public void addToTradeHistory(List<IActionMessage> tradeMessages); 
  
  public void setTicks(int ticks); 
  
  public void setTime(long time);

}