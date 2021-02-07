package brown.user.agent.library.offline;

import java.util.List;

import brown.user.agent.library.offline.AbsOfflineLearningAgent;

public class MysteryStagHuntOpponent extends AbsOfflineLearningAgent {
	private static final int STAG = 0, HARE = 1;

	public MysteryStagHuntOpponent() {
		super();
	}

	@Override
	public int nextMove() {
		List<Integer> opponentMoves = this.getOpponentActions(0);
		if (opponentMoves.size() < 5 || opponentMoves.size() % 4 == 0) {
			return HARE;
		} else {
			if (opponentMoves.get(opponentMoves.size() - 1) == HARE) {
				return HARE;
			}
			
			int j = 0;
			for (int i = 1; i <= 5; i++) {
				if (opponentMoves.get(opponentMoves.size() - i).intValue() == HARE) {
					j++;
				}
			}
			if (j > 2) {
				return HARE;
			} else {
				return STAG;
			}
		}
	}

	@Override
	public void afterRound() {
		// TODO Auto-generated method stub

	}

}
