package brown.auction.type.valuation.library;

import brown.auction.type.valuation.IType;

public class HLType implements IType {

  private Integer highLow; 
  
  public HLType(Integer highLow) {
    this.highLow = highLow; 
  }

  @Override
  public Double getType() {
    return Double.valueOf(this.highLow);
  }
}
