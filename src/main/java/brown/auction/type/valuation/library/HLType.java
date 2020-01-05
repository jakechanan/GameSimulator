package brown.auction.type.valuation.library;

import brown.auction.type.valuation.IType;

public class HLType implements IType {

  private Integer highLow; 
  
  // for kryo do not use
  public HLType() {
    this.highLow = null;
  }
  
  public HLType(Integer highLow) {
    this.highLow = highLow; 
  }

  @Override
  public Double getType() {
    return Double.valueOf(this.highLow);
  }

  @Override
  public String toString() {
    return "HLType [highLow=" + highLow + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((highLow == null) ? 0 : highLow.hashCode());
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
    HLType other = (HLType) obj;
    if (highLow == null) {
      if (other.highLow != null)
        return false;
    } else if (!highLow.equals(other.highLow))
      return false;
    return true;
  }
  
  
}
