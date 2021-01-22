package brown.user.agent.library;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import brown.communication.messages.IActionMessage;
import brown.platform.accounting.IAccountUpdate;
import brown.user.agent.library.AbsOfflineAgent;

public class MysteryLemonadeQOpponent1 extends AbsOfflineAgent {
	private int round;
	
	public MysteryLemonadeQOpponent1() {
		super();
		this.round = 0;
	}

	@Override
	public int nextMove() {
		int res;
		if (this.round % 100 < 50) {
			res = ((this.round * 4) + (this.round % 3)) % 12;
		} else {
			res = this.round % 12; 
		}
		this.round++;
		return res;
	}

}
