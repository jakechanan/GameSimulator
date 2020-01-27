package brown.communication.action.library;

import brown.communication.action.IGameAction;

/**
 * A bid that is used in games like the lemonade game.
 * @author acoggins
 *
 */
public class BoSIIAction  extends GameAction implements IGameAction {
	
	private Integer mood;
	
  // for kryo do not use
  public BoSIIAction() {
    super(null);
    this.mood = null;
  }
  
	public BoSIIAction(Integer action, Integer mood) {
		super(action);
		this.mood = mood;
	}
	
	public Integer getMood() {
		return this.mood;
	}
	
	

}