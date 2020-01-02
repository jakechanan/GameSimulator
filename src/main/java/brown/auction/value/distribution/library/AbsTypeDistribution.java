package brown.auction.value.distribution.library;

import java.util.List;

import brown.auction.value.distribution.ITypeDistribution;
import brown.auction.value.generator.ITypeGenerator;
import brown.auction.value.valuation.IType;

public abstract class AbsTypeDistribution
    implements ITypeDistribution {

  protected List<ITypeGenerator> generators;

  public AbsTypeDistribution(List<ITypeGenerator> generators) {
    this.generators = generators;
  }

  public abstract IType sample();



}
