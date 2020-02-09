package brown.user.agent.library;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import brown.communication.action.library.GameAction;
import brown.communication.messages.IActionMessage;
import brown.communication.messages.library.ActionMessage;
import brown.platform.accounting.IAccountUpdate;

public abstract class AbsOfflineAgent {
	private List<Integer> myActions;
	private Map<Integer, Integer> opponents; // opponent ID --> idx in opponentActions
	private List<List<Integer>> opponentActions;
	private List<Double> rewards;
	
	public AbsOfflineAgent() {
		this.myActions = new ArrayList<>();
		this.opponents = new HashMap<>();
		this.opponentActions = new ArrayList<>();
		this.rewards = new ArrayList<>();
	}
	
	public abstract int nextMove();
	
	public List<Integer> getMyActions() {
		return myActions;
	}
	
	public List<Integer> getOpponentActions(int opponentNum) {
		if (opponentActions.isEmpty()) {
			return new ArrayList<>();
		}
		if (opponentNum >= opponentActions.size()) {
			opponentNum = opponentActions.size() - 1;
		}
		return opponentActions.get(opponentNum);
	}
	
	public List<Double> getRewards() {
		return rewards;
	}
	
	public void updateHistory(Map<Integer, Integer> actions, int id, double reward) {
		for (Integer agt : actions.keySet()) {
			if (agt.intValue() == id) {
				myActions.add(actions.get(agt));
			} else {
				if (!opponents.containsKey(agt)) {
					opponents.put(agt, opponents.size());
					opponentActions.add(new ArrayList<>());
				}
				opponentActions.get(opponents.get(agt)).add(actions.get(agt));
			}
		}
		
		rewards.add(reward);
	}
}
