package brown.communication.bid.library;

import brown.communication.bid.IGameAction;

public abstract class AbsGameAction implements IGameAction {
  
  private Integer action; 
  
  public AbsGameAction(Integer action) {
    this.action = action; 
  }
  
  public Integer getAction() {
    return this.action; 
  }
  
  @Override
  public String toString() {
    return "GameBid [action=" + action + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((action == null) ? 0 : action.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    AbsGameAction other = (AbsGameAction) obj;
    if (action == null) {
      if (other.action != null)
        return false;
    } else if (!action.equals(other.action))
      return false;
    return true;
  }

}