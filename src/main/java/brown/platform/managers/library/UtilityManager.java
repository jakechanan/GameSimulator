package brown.platform.managers.library;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import brown.auction.type.valuation.IType;
import brown.logging.library.ErrorLogging;
import brown.platform.accounting.IAccount;
import brown.platform.managers.IUtilityManager;

/**
 * Keeps track of utility throughout the platform's run. 
 * Stores a list of agent records keeping track of their total and average 
 * utility throughout the platform run. 
 * @author andrewcoggins
 *
 */
public class UtilityManager implements IUtilityManager {
  
  private Map<Integer,List<Double>> agentRecords; 
  private boolean lock; 
  
  public UtilityManager() {
    this.agentRecords = new HashMap<Integer, List<Double>>(); 
    this.lock = false; 
  }
  
  @Override
  public void addAgentRecord(Integer agentID) { 
    if (!this.lock) {
      this.agentRecords.put(agentID, new LinkedList<Double>()); 
    } else {
      ErrorLogging.log("ERROR: Utility Manager: Cannot add agent record, Manager locked"); 
    }
  }
  

  @Override
  public void updateUtility(Map<Integer, IAccount> agentAccounts, Map<Integer, IType> agentValuations) { 
    for (Map.Entry<Integer, IAccount> entry : agentAccounts.entrySet()) {
      Integer agentID = entry.getKey(); 
      Double money  = entry.getValue().getMoney(); 
      IType agentValuation = agentValuations.get(agentID); 
      Double goodValues = agentValuation.getType(); 
      if (this.agentRecords.containsKey(agentID)) {
        List<Double> agentUtilities = this.agentRecords.get(agentID); 
        agentUtilities.add(money + goodValues); 
      } else {
        ErrorLogging.log("ERROR: UtilityManager: encountered unknown agent ID: " + agentID.toString());
      }
    }
  }

  @Override
  public void logUtility(String inFile, Map<Integer, Integer> privateToPublic, Map<Integer, String> idToName) {
    // TODO Auto-generated method stub

  }

  @Override
  public void logFinalUtility(String inFile, Map<Integer, Integer> privateToPublic, Map<Integer, String> idToName) {
	  Map<Integer, List<Double>> totals = new HashMap<Integer, List<Double>>(); 
	    Map<Integer, Double> addedTotals = new HashMap<Integer, Double>();
	    Map<Integer, List<Integer>> rank = new HashMap<Integer, List<Integer>>(); 
	    for (Integer agentID : this.agentRecords.keySet()) {
	      List<Double> money = this.agentRecords.get(agentID); 
	      double totalMoney = 0.0; 
	      for (double round : money) {
	        totalMoney += round; 
	      }
	      totals.put(agentID, money); 
	      addedTotals.put(agentID, totalMoney); 
	    }
	    
	    List<Double> totalsList = new LinkedList<Double>(addedTotals.values()); 
	    Collections.sort(totalsList);
	    Collections.reverse(totalsList);
	    
	    for (Integer agentID : this.agentRecords.keySet()) {
	      Double addedTotal = addedTotals.get(agentID); 
	      Integer place = totalsList.indexOf(addedTotal) + 1; 
	      if (rank.containsKey(place)) {
	        List<Integer> agents = rank.get(place); 
	        agents.add(agentID); 
	        rank.put(place, agents); 
	      } else {
	        List<Integer> anAgent = new LinkedList<Integer>();
	        anAgent.add(agentID); 
	        rank.put(place, anAgent); 
	      }
	    }
	    
	    
	    List<Integer> allPlaces = new LinkedList<Integer>(rank.keySet()); 
	    Collections.sort(allPlaces); 
	    NumberFormat formatter = new DecimalFormat("#0.00");
	    String ret = "\n\t################# RESULT ##################";
	    ret += "\n\t####      Agent \t#  Profit \t###";
	    ret += "\n\t###########################################";
	    for (Integer place : allPlaces) {
	      List<Integer> placeAgents = rank.get(place); 
	      for (Integer agentID : placeAgents) {
	    	ret += "\n\t### " + String.format("%12s", idToName.get(agentID)) + " \t# " + String.format("%8s", formatter.format(addedTotals.get(agentID) / totals.get(agentID).size())) + " \t###";
	      }
	    }
	    ret += "\n\t###########################################";
	    System.out.println(ret);
    
  }

  @Override
  public void lock() {
    this.lock = true; 
  }

  @Override
  public Map<Integer, List<Double>> getAgentRecords() {
    return this.agentRecords;
  }

}
