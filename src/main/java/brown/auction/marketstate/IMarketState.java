package brown.auction.marketstate;

import java.util.List;
import java.util.Map;

import brown.communication.messages.IActionMessage;
import brown.communication.messages.IActionRequestMessage;
import brown.platform.accounting.IAccountUpdate;
/**
 * Stores the internal state of a market as it runs. Consists of a series of
 * getters and setters for the various fields of the market state.
 * 
 * @author acoggins
 */
public interface IMarketState {

  // Utility rule
  public void setUtilities(List<IAccountUpdate> payment);

  public List<IAccountUpdate> getUtilities();

  // orders (this is from the payment rule)
  // delete this !!! just use set Payments
  public void clearOrders();

  // Query rule
  public IActionRequestMessage getTRequest();

  public void setTRequest(IActionRequestMessage t);

  // Activity rule
  public boolean getAcceptable();

  public void setAcceptable(boolean b);
  
  public Map<String, Double> getReserves(); 
  
  public void setReserves(Map<String, Double> reserves); 

  // Termination condition
  public long getTime();

  public void tick();

  public int getTicks();

  public boolean isOpen();

  public void close();
  
  public List<List<IActionMessage>> getTradeHistory(); 
  
  public void addToTradeHistory(List<IActionMessage> tradeMessages); 

}
