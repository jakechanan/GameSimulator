package brown.auction.type.distribution.library;

import java.util.List;

import brown.auction.type.distribution.ITypeDistribution;
import brown.auction.type.generator.ITypeGenerator;
import brown.auction.type.valuation.IType;

public abstract class AbsTypeDistribution
    implements ITypeDistribution {

  protected List<ITypeGenerator> generators;

  public AbsTypeDistribution(List<ITypeGenerator> generators) {
    this.generators = generators;
  }

  public abstract IType sample();



}
