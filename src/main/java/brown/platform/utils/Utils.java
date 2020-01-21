package brown.platform.utils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import brown.auction.marketstate.IMarketPublicState;
import brown.auction.marketstate.IMarketState;
import brown.auction.marketstate.library.MarketPublicState;
import brown.communication.messages.IActionMessage;
import brown.communication.messages.IActionRequestMessage;
import brown.communication.messages.IInformationMessage;
import brown.communication.messages.IServerToAgentMessage;
import brown.communication.messages.ISimulationReportMessage;
import brown.communication.messages.library.ActionMessage;
import brown.communication.messages.library.InformationMessage;
import brown.communication.messages.library.SimulationReportMessage;
import brown.platform.accounting.IAccountUpdate;
import brown.platform.accounting.library.AccountUpdate;

public class Utils {

  public static IServerToAgentMessage sanitize(IServerToAgentMessage message,
      Map<Integer, Integer> agentIDs) {
    // sanitize the private IDs from an information message
    if (message instanceof IInformationMessage) {
      
      System.out.println("sanitizing"); 
      
      IInformationMessage iMessage = (IInformationMessage) message;
      IMarketPublicState publicState = iMessage.getPublicState();

      IMarketPublicState newPublicState = sanitizeState(publicState, agentIDs); 
      
      IInformationMessage newMessage = new InformationMessage(iMessage.getMessageID(), iMessage.getAgentID(), newPublicState);  
      
      return newMessage; 
    } else if (message instanceof IActionRequestMessage) {
      return message;
    } else if (message instanceof ISimulationReportMessage) {
      ISimulationReportMessage oldMessage = (ISimulationReportMessage) message; 
      Map<Integer, IMarketPublicState> reports = oldMessage.getMarketResults(); 
      Map<Integer, IMarketPublicState> newReports = new HashMap<Integer, IMarketPublicState>(); 
      
      for (Integer marketID : reports.keySet()) {
        IMarketPublicState publicState = reports.get(marketID); 
        IMarketPublicState newPublicState = sanitizeState(publicState, agentIDs); 
        newReports.put(marketID, newPublicState); 
      }
      ISimulationReportMessage newMessage = new SimulationReportMessage(oldMessage.getMessageID(), oldMessage.getAgentID(), newReports); 
      return newMessage; 
    }

    return message;
  }
  
  public static IMarketPublicState toPublicState(IMarketState state) {

    IMarketPublicState newState = new MarketPublicState(); 
    
    newState.setTicks(state.getTicks());
    newState.setTime(state.getTime());
    newState.setUtilities(state.getUtilities()); 
    newState.setReserves(state.getReserves());
    
    for (List<IActionMessage> tradeStep: state.getTradeHistory())
      newState.addToTradeHistory(tradeStep);
  
    return newState; 
  }
  
  
  private static IMarketPublicState sanitizeState(IMarketPublicState publicState, Map<Integer, Integer> agentIDs) {
    // TODO: take this boy apart and put its pieces back together after
    // changing out all the IDs.

    List<List<IActionMessage>> tradeHistory = publicState.getTradeHistory();
    List<IAccountUpdate> payments = publicState.getUtilities();
    Map<String, Double> reserves = publicState.getReserves();
    int ticks = publicState.getTicks();
    long time = publicState.getTime();
    IMarketPublicState newPublicState = new MarketPublicState();

    // ticks
    newPublicState.setTicks(ticks);
    // time
    newPublicState.setTime(time);
    // reserves
    newPublicState.setReserves(reserves);
    // replace trade history
    for (List<IActionMessage> trades : tradeHistory) {
      List<IActionMessage> newTrades = new LinkedList<IActionMessage>();
      for (IActionMessage trade : trades) {
        Integer publicAgentID = agentIDs.get(trade.getAgentID());

        IActionMessage newActionMessage =
            new ActionMessage(trade.getMessageID(), publicAgentID,
                trade.getAuctionID(), trade.getBid());
        newTrades.add(newActionMessage); 
      }
      newPublicState.addToTradeHistory(newTrades);
    }

    // replace account updates
    List<IAccountUpdate> newAccountUpdates = new LinkedList<IAccountUpdate>();

    for (IAccountUpdate acctUpdate : payments) {
      Integer to = acctUpdate.getTo();
      Integer from = acctUpdate.getFrom();
      if (to > 0)
        to = agentIDs.get(to);
      if (from > 0)
        from = agentIDs.get(from);
      newAccountUpdates
          .add(new AccountUpdate(to, from, acctUpdate.getCost()));
    }
    newPublicState.setUtilities(newAccountUpdates);
    
    return newPublicState; 
  }
  

}
