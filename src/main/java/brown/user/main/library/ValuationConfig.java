package brown.user.main.library;

import java.lang.reflect.Constructor;
import java.util.List;

import brown.user.main.IValuationConfig;

public class ValuationConfig implements IValuationConfig {

  private Constructor<?> valDistribution;
  private List<Constructor<?>> valuationCons;
  private List<List<Double>> valuationParams;

  public ValuationConfig(Constructor<?> distCons,
      List<Constructor<?>> valuationCons, List<List<Double>> valuationParams) {
    this.valuationCons = valuationCons;
    this.valuationParams = valuationParams;
    this.valDistribution = distCons;
  }

  public Constructor<?> getValDistribution() {
    return this.valDistribution;
  }

  public List<Constructor<?>> getGeneratorConstructors() {
    return this.valuationCons;
  }

  public List<List<Double>> getGeneratorParams() {
    return this.valuationParams;
  }

  @Override
  public String toString() {
    return "ValuationConfig [valDistribution=" + valDistribution
        + ", valuationCons=" + valuationCons + ", valuationParams="
        + valuationParams + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result
        + ((valDistribution == null) ? 0 : valDistribution.hashCode());
    result = prime * result
        + ((valuationCons == null) ? 0 : valuationCons.hashCode());
    result = prime * result
        + ((valuationParams == null) ? 0 : valuationParams.hashCode());
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
    ValuationConfig other = (ValuationConfig) obj;
    if (valDistribution == null) {
      if (other.valDistribution != null)
        return false;
    } else if (!valDistribution.equals(other.valDistribution))
      return false;
    if (valuationCons == null) {
      if (other.valuationCons != null)
        return false;
    } else if (!valuationCons.equals(other.valuationCons))
      return false;
    if (valuationParams == null) {
      if (other.valuationParams != null)
        return false;
    } else if (!valuationParams.equals(other.valuationParams))
      return false;
    return true;
  }

}
