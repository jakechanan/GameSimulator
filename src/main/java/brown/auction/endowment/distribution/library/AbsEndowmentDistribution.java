package brown.auction.endowment.distribution.library;

import java.util.List;

import brown.auction.endowment.IEndowment;
import brown.auction.endowment.distribution.IEndowmentDistribution;
import brown.auction.type.generator.ITypeGenerator;

public abstract class AbsEndowmentDistribution
    implements IEndowmentDistribution {

  protected List<ITypeGenerator> generators;

  public AbsEndowmentDistribution(List<ITypeGenerator> generators) {
    this.generators = generators;
  }

  public abstract IEndowment sample();

  @Override
  public String toString() {
    return "AbsEndowmentDistribution [generators=" + generators + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result =
        prime * result + ((generators == null) ? 0 : generators.hashCode());
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
    AbsEndowmentDistribution other = (AbsEndowmentDistribution) obj;
    if (generators == null) {
      if (other.generators != null)
        return false;
    } else if (!generators.equals(other.generators))
      return false;
    return true;
  }

}
