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
import brown.communication.action.library.BoSIIAction;
import brown.communication.messages.IActionMessage;
import brown.platform.accounting.IAccountUpdate;
import brown.platform.accounting.library.AccountUpdate;

public class BoSIIUtility extends AbsRule implements IUtilityRule {

    @Override
    public void setAllocation(IMarketState state, List<IActionMessage> messages) {
        List<IAccountUpdate> acctUpdates = new LinkedList<IAccountUpdate>();
        Map<Integer, Double> utils = new HashMap<>();
        
        if (messages.size() == 2) {
	        int STUBBORN = 0, COMPROMISE = 1;
	        int GOOD_MOOD = 0, BAD_MOOD = 1;
	
	        IActionMessage a1Message = messages.get(0);
	        IActionMessage a2Message = messages.get(1);
	
	        int agent1 = a1Message.getAgentID();
	        int agent2 = a2Message.getAgentID();
	
	        int a1Action = ((IGameAction) a1Message.getBid()).getAction();
	        int a2Action = ((IGameAction) a2Message.getBid()).getAction();
	        
	        Integer a1Mood = ((BoSIIAction) a1Message.getBid()).getMood();
	        Integer a2Mood = ((BoSIIAction) a2Message.getBid()).getMood();
	
	        double a1Util;
	        double a2Util;
	
	        if (a1Mood == null) {
	        	if (a2Mood.equals(GOOD_MOOD)) {
	        		if (a1Action == a2Action) {
	        			a1Util = 0.0;
	        			a2Util = 0.0;
	        		} else if (a1Action == STUBBORN) {
	        			a1Util = 7.0;
	        			a2Util = 3.0;
	        		} else {
	        			a1Util = 3.0;
	        			a2Util = 7.0;
	        		}
	        	} else {
	        		if (a1Action == STUBBORN && a2Action == STUBBORN) {
	        			a1Util = 0.0;
	        			a2Util = 7.0;
	        		} else if (a1Action == COMPROMISE && a2Action == COMPROMISE) {
	        			a1Util = 0.0;
	        			a2Util = 3.0;
	        		} else if (a1Action == STUBBORN && a2Action == COMPROMISE) {
	        			a1Util = 7.0;
	        			a2Util = 0.0;
	        		} else {
	        			a1Util = 3.0;
	        			a2Util = 0.0;
	        		}
	        	}
	        } else {
	        	if (a1Mood.equals(GOOD_MOOD)) {
	        		if (a1Action == a2Action) {
	        			a2Util = 0.0;
	        			a1Util = 0.0;
	        		} else if (a2Action == STUBBORN) {
	        			a2Util = 7.0;
	        			a1Util = 3.0;
	        		} else {
	        			a2Util = 3.0;
	        			a1Util = 7.0;
	        		}
	        	} else {
	        		if (a1Action == STUBBORN && a2Action == STUBBORN) {
	        			a2Util = 0.0;
	        			a1Util = 7.0;
	        		} else if (a1Action == COMPROMISE && a2Action == COMPROMISE) {
	        			a2Util = 0.0;
	        			a1Util = 3.0;
	        		} else if (a2Action == STUBBORN && a1Action == COMPROMISE) {
	        			a2Util = 7.0;
	        			a1Util = 0.0;
	        		} else {
	        			a2Util = 3.0;
	        			a1Util = 0.0;
	        		}
	        	}
	        }
	        
	        utils.put(agent1, a1Util);
	        utils.put(agent2, a2Util);
    	} else {
        	IActionMessage a1Message = messages.get(0);
	
	        int agent1 = a1Message.getAgentID();
	        utils.put(agent1, 0.0);
        }

        // for each agent...
        for (Integer agentID : utils.keySet()) {
            acctUpdates.add(new AccountUpdate(agentID, -1, utils.get(agentID)));
        }

        state.setUtilities(acctUpdates);
    }

}
