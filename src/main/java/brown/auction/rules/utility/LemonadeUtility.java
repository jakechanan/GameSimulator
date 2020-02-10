package brown.auction.rules.utility;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import brown.auction.marketstate.IMarketState;
import brown.auction.rules.AbsRule;
import brown.auction.rules.IUtilityRule;
import brown.communication.action.IGameAction;
import brown.communication.messages.IActionMessage;
import brown.platform.accounting.IAccountUpdate;
import brown.platform.accounting.library.AccountUpdate;

public class LemonadeUtility extends AbsRule implements IUtilityRule {

  @Override
  public void setAllocation(IMarketState state, List<IActionMessage> messages) {
	if (messages.isEmpty()) {
		return;
	}
	  
    int LEMONADESLOTS = 12;

    Map<Integer, Integer> agentActions = new HashMap<Integer, Integer>();
    // keep track of how many agents do each action.
    Map<Integer, Integer> actionCount = new HashMap<Integer, Integer>(); 
    
    for (int i = 0; i < LEMONADESLOTS; i++) {
      actionCount.put(i, 0); 
    }

    List<IAccountUpdate> acctUpdates = new LinkedList<IAccountUpdate>();

    for (IActionMessage message : messages) {
      Integer gameAction = ((IGameAction) message.getBid()).getAction();
      agentActions.put(message.getAgentID(), gameAction);
      int ac = actionCount.get(gameAction); 
      ac += 1; 
      actionCount.put(gameAction, ac);
    }

    // {1: [3 7], 2: [4, 6], ...}
    List<List<Integer>> lemonadeAllocations = new LinkedList<List<Integer>>();

    // make sure no duplicates in list.
    Set<Integer> lemonadeLocationsSet =
        new LinkedHashSet<Integer>(agentActions.values());
    List<Integer> lemonadeLocations =
        new LinkedList<Integer>(lemonadeLocationsSet);

    Collections.sort(lemonadeLocations);

    for (int i = 0; i < LEMONADESLOTS; i++) {
      List<Integer> closestStands = new LinkedList<Integer>();

      if (lemonadeLocations.contains(i)) {
        // there is a lemonade stand on slot i
        closestStands.add(i);
        closestStands.add(i);
      } else {
        // i is greater than the highest lemonade slot or less than the smallest
        // lemonade slot
        if (i < lemonadeLocations.get(0)
            || i > lemonadeLocations.get(lemonadeLocations.size() - 1)) {
          closestStands
              .add(lemonadeLocations.get(lemonadeLocations.size() - 1));
          closestStands.add(lemonadeLocations.get(0));
        } else {
          // there is at least one slot smaller and one slot larger
          for (int j = 0; j < lemonadeLocations.size(); j++) {
            if (lemonadeLocations.get(j) > i) {
              closestStands.add(lemonadeLocations.get(j - 1));
              closestStands.add(lemonadeLocations.get(j));
            }
          }
        }
      }
      lemonadeAllocations.add(closestStands);
    }

    // for each agent...
    for (Integer agentID : agentActions.keySet()) {
      Integer action = agentActions.get(agentID); 
      double numStands = (double) actionCount.get(action); 
      double utilityCount = 0.0; 
      for (List<Integer> allocation : lemonadeAllocations) {
        if (allocation.get(0) == action) {
       
          utilityCount += 1.0 / numStands; 
        } 
        if (allocation.get(1) == action) {
          utilityCount += 1.0 / numStands; 
        }
      }
      acctUpdates.add(new AccountUpdate(agentID, -1, utilityCount)); 
    }
    
    state.setUtilities(acctUpdates);
  }

}
