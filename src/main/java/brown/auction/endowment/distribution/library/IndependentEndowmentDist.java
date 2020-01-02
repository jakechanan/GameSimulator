package brown.auction.endowment.distribution.library;

import java.util.List;

import brown.auction.endowment.IEndowment;
import brown.auction.endowment.distribution.IEndowmentDistribution;
import brown.auction.endowment.library.Endowment;
import brown.auction.type.generator.ITypeGenerator;

public class IndependentEndowmentDist extends AbsEndowmentDistribution
    implements IEndowmentDistribution {

  public IndependentEndowmentDist(List<ITypeGenerator> generators) {
    super(generators);
  }

  @Override
  public IEndowment sample() {
    ITypeGenerator moneyGenerator = this.generators.get(1);

    return new Endowment(moneyGenerator.makeValuation());
  }

}
