package brown.communication.bid.library;

import brown.communication.bid.IGameAction;

/**
 * A bid that is used in games like the lemonade game.
 * @author acoggins
 *
 */
public class GameAction  extends AbsGameAction implements IGameAction {
	
	public GameAction(Integer action) {
		super(action);
	}

}