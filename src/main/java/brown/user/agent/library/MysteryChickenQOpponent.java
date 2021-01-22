package brown.user.agent.library;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import brown.communication.messages.IActionMessage;
import brown.platform.accounting.IAccountUpdate;
import brown.user.agent.library.AbsOfflineAgent;

public class MysteryChickenQOpponent extends AbsOfflineAgent {
  public MysteryChickenQOpponent() {
	  super();
  }

	@Override
	public int nextMove() {
		List<Integer> opponentMoves = this.getOpponentActions(0);
		if (opponentMoves.size() < 3) {
			return (int)(Math.random() * 2);
		} else {
			int j = 0;
			for (int i = 1; i <= 3; i++) {
				if (opponentMoves.get(opponentMoves.size() - i).intValue() == 1) {
					j++;
				}
			}
			if (j > 1) {
				return 1;
			} else {
				return 0;
			}
		}
	}

}
