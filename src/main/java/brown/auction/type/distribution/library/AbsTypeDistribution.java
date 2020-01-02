package brown.auction.type.distribution.library;

import java.util.List;

import brown.auction.type.distribution.ITypeDistribution;
import brown.auction.type.generator.ITypeGenerator;
import brown.auction.type.valuation.IType;

public abstract class AbsTypeDistribution implements ITypeDistribution {

  protected List<ITypeGenerator> generators;

  public AbsTypeDistribution(List<ITypeGenerator> generators) {
    this.generators = generators;
  }

  public abstract IType sample();

  @Override
  public String toString() {
    return "AbsTypeDistribution [generators=" + generators + "]";
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
    AbsTypeDistribution other = (AbsTypeDistribution) obj;
    if (generators == null) {
      if (other.generators != null)
        return false;
    } else if (!generators.equals(other.generators))
      return false;
    return true;
  }

}
