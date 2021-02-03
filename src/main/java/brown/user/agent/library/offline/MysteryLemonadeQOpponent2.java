package brown.user.agent.library.offline;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import brown.communication.messages.IActionMessage;
import brown.platform.accounting.IAccountUpdate;
import brown.user.agent.library.offline.AbsOfflineLearningAgent;

public class MysteryLemonadeQOpponent2 extends AbsOfflineLearningAgent {
	private int round;
	
	public MysteryLemonadeQOpponent2() {
		super();
	}

	@Override
	public int nextMove() {
		List<Integer> o1A = this.getOpponentActions(0);
		List<Integer> o2A = this.getOpponentActions(1);
		
		if (o1A.isEmpty() || o2A.isEmpty()) {
			return 1;
		}
		
		Integer a1 = o1A.get(o1A.size() - 1);
		Integer a2 = o2A.get(o2A.size() - 1);
		
		if (a1 > a2) {
			Integer temp = a1;
			a1 = a2;
			a2 = temp;
		}
		
		int nearDist = a2 - a1;
		int farDist = 11 + a1 - a2;
		
		if (nearDist < farDist) {
			return (((a1 + a2) / 2) + 6) % 12;
		} else {
			return (((11 + a1 + a2) / 2) + 6) % 12;
		}
	}

	@Override
	public void afterRound() {
		// TODO Auto-generated method stub
		this.round++;
	}

}
