package brown.auction.type.distribution.library;

import java.util.List;

import brown.auction.type.distribution.ITypeDistribution;
import brown.auction.type.generator.ITypeGenerator;
import brown.auction.type.valuation.IType;
import brown.auction.type.valuation.library.HLType;

public class HLTypeDistribution extends AbsTypeDistribution implements ITypeDistribution {

  public HLTypeDistribution(List<ITypeGenerator> generators) {
    super(generators);
  }

  @Override
  public IType sample() {
    return new HLType(0);
  }

}
