package brown.platform.managers.library;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import brown.auction.endowment.distribution.IEndowmentDistribution;
import brown.auction.endowment.distribution.library.IndependentEndowmentDist;
import brown.auction.type.generator.ITypeGenerator;
import brown.platform.managers.IEndowmentManager;

public class EndowmentManagerTest {
  
  @Test
  public void testCreateEndowment() throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException,
  IllegalAccessException, IllegalArgumentException, InvocationTargetException {
   
    
    Class distClass = Class.forName("brown.auction.endowment.distribution.library.IndependentEndowmentDist");
    Constructor<?> distCons = distClass.getConstructor( List.class); 
    
    List<Constructor<?>> genList = new LinkedList<Constructor<?>>(); 
    List<List<Double>> paramList = new LinkedList<List<Double>>(); 

    Class genClass = Class.forName("brown.auction.value.generator.library.NormalValGenerator"); 
    List<Double> genParams = new LinkedList<Double>(); 
    genParams.add(0.0); 
    genParams.add(1.0); 
    
    Constructor<?> genCons = genClass.getConstructor(List.class); 
    genList.add(genCons); 
    paramList.add(genParams); 
    
    IEndowmentManager valManager = new EndowmentManager();
    valManager.createEndowment(distCons, genList, paramList);
    
    List<ITypeGenerator> expectedGenList = new LinkedList<ITypeGenerator>(); 
    ITypeGenerator expectedGen = (ITypeGenerator) genCons.newInstance(genParams); 
    expectedGenList.add(expectedGen); 
    IEndowmentDistribution expected = new IndependentEndowmentDist(expectedGenList); 
    
    assertEquals(valManager.getDistribution().get(0), expected); 
  }
  
  
}