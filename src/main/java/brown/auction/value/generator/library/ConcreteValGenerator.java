package brown.auction.value.generator.library;

import java.util.List;

import brown.auction.value.generator.ITypeGenerator;

public class ConcreteValGenerator implements ITypeGenerator {
  
  private Double value; 
  
  public ConcreteValGenerator() {
    this.value = ((Double) null); 
  }
  
  public ConcreteValGenerator(List<Double> values) {
    this.value = values.get(0); 
  }
  
  @Override
  public Double makeValuation() {
    return value;
  }
  
}
