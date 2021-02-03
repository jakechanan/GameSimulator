package brown.user.agent.library.offline;

import java.util.List;

import brown.user.agent.library.offline.AbsOfflineLearningAgent;

public class MysteryChickenQOpponent extends AbsOfflineLearningAgent {
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

	@Override
	public void afterRound() {
		// TODO Auto-generated method stub
		
	}

}
