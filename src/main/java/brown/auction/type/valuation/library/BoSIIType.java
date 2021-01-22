package brown.auction.type.valuation.library;

import brown.auction.type.valuation.IType;

public class BoSIIType implements IType {

  private Integer mood; 
  
  // for kryo do not use
  public BoSIIType() {
    this.mood = null;
  }
  
  public BoSIIType(Integer mood) {
    this.mood = mood; 
  }

  @Override
  public Double getType() {
    return 0.0;
  }
  
  public Integer getMood() {
	  return this.mood;
  }

  @Override
  public String toString() {
    return "HLType [mood=" + mood + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((mood == null) ? 0 : mood.hashCode());
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
    BoSIIType other = (BoSIIType) obj;
    if (mood == null) {
      if (other.mood != null)
        return false;
    } else if (!mood.equals(other.mood))
      return false;
    return true;
  }
  
  
}
