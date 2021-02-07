package brown.user.agent.library.offline;

import java.util.List;

import brown.user.agent.library.offline.AbsOfflineLearningAgent;

public class MysteryStagHuntOpponent extends AbsOfflineLearningAgent {
	private static final int STAG = 0, HARE = 1;
	private int round;

	public MysteryStagHuntOpponent() {
		super();
		this.round = 0;
	}

	@Override
	public int nextMove() {
		if (this.round == 0) {
			return HARE;
		}
		
		if (this.round % 6 == 0) {
			return STAG;
		}
		
		if (this.round % 6 == 2) {
			return HARE;
		}
		
		List<Integer> opponentMoves = this.getOpponentActions(0);
		
		if (opponentMoves.size() < 10) {
			return (int)(Math.random() * 2);
		}
		
		int j = 0;
		for (int i = 1; i <= 2; i++) {
			if (opponentMoves.get(opponentMoves.size() - i).intValue() == HARE) {
				j++;
			}
		}
		if (j % 2 == 0) {
			return STAG;
		} else {
			return HARE;
		}
	}

	@Override
	public void afterRound() {
		// TODO Auto-generated method stub
		this.round++;
	}

}
