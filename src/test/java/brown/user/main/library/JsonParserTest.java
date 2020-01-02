package brown.user.main.library;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.simple.parser.ParseException;
import org.junit.Test;

import brown.auction.rules.IActivityRule;
import brown.auction.rules.IInformationRevelationPolicy;
import brown.auction.rules.IInnerIRPolicy;
import brown.auction.rules.IQueryRule;
import brown.auction.rules.ITerminationCondition;
import brown.auction.rules.IUtilityRule;
import brown.platform.game.IFlexibleRules;
import brown.platform.game.library.FlexibleRules;
import brown.user.main.IEndowmentConfig;
import brown.user.main.IGameConfig;
import brown.user.main.IJsonParser;
import brown.user.main.ISimulationConfig;
import brown.user.main.IValuationConfig;

public class JsonParserTest {

  @Test
  public void testJSONParse() throws FileNotFoundException,
      ClassNotFoundException, NoSuchMethodException, SecurityException,
      InstantiationException, IllegalAccessException, IllegalArgumentException,
      InvocationTargetException, IOException, ParseException {
    IJsonParser testParser = new JsonParser();
    List<ISimulationConfig> simulationConfigs = testParser.parseJSON("input_configs/test_input.json");
    assertTrue(simulationConfigs.size() == 2); 
    
    ISimulationConfig firstSimulationConfig = simulationConfigs.get(0); 
    ISimulationConfig secondSimulationConfig = simulationConfigs.get(1); 
    
    assertEquals(firstSimulationConfig, secondSimulationConfig); 
    
    assertTrue(firstSimulationConfig.getSimulationRuns() == 1);  
    
    List<IValuationConfig> valuationConfigs = new LinkedList<IValuationConfig>(); 
    
    Class<?> distributionClass =
        Class.forName("brown.auction.value.distribution.library.AdditiveValuationDistribution");
    
    Constructor<?> distributionCons =
        distributionClass.getConstructor(List.class);
    
    List<Constructor<?>> genList = new LinkedList<Constructor<?>>(); 
    List<List<Double>> paramList = new LinkedList<List<Double>>(); 
    
    Class<?> generatorClass = Class.forName(
        "brown.auction.value.generator.library.NormalValGenerator");
    Constructor<?> generatorCons =
        generatorClass.getConstructor(List.class);
    
    List<Double> genParams = new LinkedList<Double>(); 
    genParams.add(0.0); 
    genParams.add(1.0); 
    
    genList.add(generatorCons); 
    paramList.add(genParams); 
    
    valuationConfigs.add(new ValuationConfig(distributionCons, genList, paramList)); 
    assertEquals(firstSimulationConfig.getVConfig(), valuationConfigs); 
    
    List<IEndowmentConfig> eConfigs = new LinkedList<IEndowmentConfig>(); 
    
    Class<?> endowmentDistributionClass =
        Class.forName("brown.auction.endowment.distribution.library.IndependentEndowmentDist");
    Constructor<?> endowmentDistributionCons =
        endowmentDistributionClass.getConstructor(List.class);

    List<Constructor<?>> endowmentList = new LinkedList<Constructor<?>>(); 
    List<List<Double>> endowmentParamList = new LinkedList<List<Double>>(); 
    
    Class<?> eGeneratorClass = Class.forName(
        "brown.auction.value.generator.library.NormalValGenerator");
    Constructor<?> eGeneratorCons =
        eGeneratorClass.getConstructor(List.class);
    
    List<Double> eGenParamsOne = new LinkedList<Double>(); 
    eGenParamsOne.add(0.0); 
    eGenParamsOne.add(10.0); 
    List<Double> eGenParams = new LinkedList<Double>(); 
    eGenParams.add(0.0); 
    eGenParams.add(100.0); 
    
    endowmentList.add(eGeneratorCons); 
    endowmentList.add(eGeneratorCons); 
    endowmentParamList.add(eGenParamsOne);
    endowmentParamList.add(eGenParams); 
    
    Map<String, Integer> endowmentMapping = new HashMap<String, Integer>(); 
    endowmentMapping.put("testItem", 1);
    eConfigs.add(new EndowmentConfig(endowmentDistributionCons, endowmentList, endowmentParamList)); 
    
    System.out.println(eConfigs); 
    System.out.println(firstSimulationConfig.getEConfig()); 
    
    
    assertEquals(firstSimulationConfig.getEConfig(), eConfigs); 
    
    List<List<IGameConfig>> marketConfigs = new LinkedList<List<IGameConfig>>(); 
    List<IGameConfig> simMarkets = new LinkedList<IGameConfig>(); 
    
    IUtilityRule mockAllocationRule = mock(IUtilityRule.class); 
    IQueryRule mockQueryRule = mock(IQueryRule.class);
    IActivityRule mockActivityRule = mock(IActivityRule.class); 
    IInformationRevelationPolicy mockIR = mock(IInformationRevelationPolicy.class); 
    ITerminationCondition mocktCondition = mock(ITerminationCondition.class); 
    IInnerIRPolicy innerIR = mock(IInnerIRPolicy.class); 
    
    IFlexibleRules mRules = new FlexibleRules(mockAllocationRule, mockQueryRule, mockActivityRule, mockIR, innerIR, mocktCondition);
    
    simMarkets.add(new MarketConfig(mRules)); 
    simMarkets.add(new MarketConfig(mRules)); 
    marketConfigs.add(simMarkets); 
    assertEquals(firstSimulationConfig.getMConfig(), marketConfigs); 
    
  }
}
