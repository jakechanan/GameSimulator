package brown.platform.managers.library;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import brown.auction.type.distribution.ITypeDistribution;
import brown.auction.type.distribution.library.HLTypeDistribution;
import brown.auction.type.generator.ITypeGenerator;
import brown.auction.type.valuation.IType;
import brown.auction.type.valuation.library.HLType;
import brown.platform.managers.ITypeManager;

public class ValuationManagerTest {
  
  @Test
  public void testCreateValuation() throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException,
  IllegalAccessException, IllegalArgumentException, InvocationTargetException {
    
    Class distClass = Class.forName("brown.auction.type.distribution.library.HLTypeDistribution");
    Constructor<?> distCons = distClass.getConstructor(List.class); 
    
    List<Constructor<?>> genList = new LinkedList<Constructor<?>>(); 
    List<List<Double>> paramList = new LinkedList<List<Double>>(); 
    
    Class genClass = Class.forName("brown.auction.type.generator.library.NormalValGenerator"); 
    List<Double> genParams = new LinkedList<Double>(); 
    genParams.add(0.0); 
    genParams.add(1.0); 
    
    Constructor<?> genCons = genClass.getConstructor(List.class); 
    genList.add(genCons); 
    paramList.add(genParams); 
    
    ITypeManager valManager = new TypeManager();
    valManager.createValuation(distCons, genList, paramList);
    
    List<ITypeGenerator> expectedGenList = new LinkedList<ITypeGenerator>(); 
    ITypeGenerator expectedGen = (ITypeGenerator) genCons.newInstance(genParams); 
    expectedGenList.add(expectedGen); 
    ITypeDistribution expected = new HLTypeDistribution(expectedGenList); 
    
    assertEquals(valManager.getDistribution(), expected); 
  }
  
  @Test
  public void testAgentValuation() {
    
    ITypeManager vManager = new TypeManager(); 
    List<String> tradeableNames = new LinkedList<String>(); 
    tradeableNames.add("default"); 
    IType aType = new HLType(0); 
    vManager.addAgentValuation(1, aType);
    
    assertEquals(vManager.getAgentValuation(1), aType); 
      
  }
  
}
