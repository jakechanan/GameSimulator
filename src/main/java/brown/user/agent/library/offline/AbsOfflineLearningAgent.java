package brown.user.agent.library.offline;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import brown.communication.action.library.GameAction;
import brown.communication.messages.IActionMessage;
import brown.communication.messages.library.ActionMessage;
import brown.platform.accounting.IAccountUpdate;

public abstract class AbsOfflineLearningAgent {
	private List<Integer> myActions;
	private List<Double> myRewards;
	private Map<Integer, Integer> opponents; // opponent ID --> idx in opponentActions
	private List<List<Integer>> opponentActions;
	
	public AbsOfflineLearningAgent() {
		this.myActions = new ArrayList<>();
		this.myRewards = new ArrayList<>();
		this.opponents = new HashMap<>();
		this.opponentActions = new ArrayList<>();
	}
	
	public abstract int nextMove();
	public abstract void afterRound();
	
	public static void train(OfflineGame simulationType, List<AbsOfflineLearningAgent> agents, int numIters) {
		// setup simulation
		Map<AbsOfflineLearningAgent, Integer> agentID = new HashMap<>();
		for (AbsOfflineLearningAgent agt : agents) {
			agentID.put(agt, agentID.size());
		}
		     
		
		// this is where the important stuff happens...
		int k = 0;
		for (int i = 0; i < numIters; i++) { // for each iteration/round:
			List<IActionMessage> messages = new ArrayList<>(agentID.size());
			Map<Integer, Integer> actions = new HashMap<>(agentID.size());
			Map<Integer, Double> rewards = new HashMap<>(agentID.size());
			
			// get agent actions
			for (AbsOfflineLearningAgent agt : agentID.keySet()) {
				int id = agentID.get(agt);
				Integer move = agt.nextMove(); // this is where nextMove() is called
				messages.add(new ActionMessage(k, id, -1, new GameAction(move)));
				actions.put(id, move);
				k++;
			}
			
			// simulate round, observe reward
			double r = 0;
			for (IAccountUpdate upd : simulationType.simulateRound(messages)) {
				int agt = upd.getTo();
				double rwd = upd.getCost();
				rewards.put(agt, rwd);
			}
			
			// update game history and allow agents to learn
			for (AbsOfflineLearningAgent agt : agentID.keySet()) {
				agt.updateHistory(actions, rewards, agentID.get(agt));	
				agt.afterRound();
			}
		}
	}
	
	public List<Integer> getMyActions() {
		return Collections.unmodifiableList(myActions);
	}
	
	public List<Double> getMyRewards() {
		return Collections.unmodifiableList(myRewards);
	}
	
	public List<Integer> getOpponentActions(int opponentNum) {
		if (opponentActions.isEmpty()) {
			return new ArrayList<>();
		}
		if (opponentNum >= opponentActions.size()) {
			opponentNum = opponentActions.size() - 1;
		}
		return Collections.unmodifiableList(opponentActions.get(opponentNum));
	}
	
	public void updateHistory(Map<Integer, Integer> actions, Map<Integer, Double> rewards, int id) {
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
		
		myRewards.add(rewards.get(id));
	}
}
