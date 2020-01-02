package brown.communication.action.library;

import brown.communication.action.IGameAction;

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