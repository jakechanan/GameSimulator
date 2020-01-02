package brown.user.main.library;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import brown.auction.rules.IActivityRule;
import brown.auction.rules.IInformationRevelationPolicy;
import brown.auction.rules.IInnerIRPolicy;
import brown.auction.rules.IQueryRule;
import brown.auction.rules.ITerminationCondition;
import brown.auction.rules.IUtilityRule;
import brown.auction.type.distribution.library.HLTypeDistribution;
import brown.auction.type.generator.library.NormalValGenerator;
import brown.platform.game.IFlexibleRules;
import brown.platform.game.library.FlexibleRules;
import brown.user.main.IEndowmentConfig;
import brown.user.main.IGameConfig;
import brown.user.main.ISimulationConfig;
import brown.user.main.IValuationConfig;

public class SimulationConfigTest {

  @Test
  public void testSimulationConfigOne()
      throws NoSuchMethodException, SecurityException, ClassNotFoundException {

    List<IValuationConfig> vConfigs = new LinkedList<IValuationConfig>();
    List<IEndowmentConfig> eConfigs = new LinkedList<IEndowmentConfig>();
    List<List<IGameConfig>> mConfigSquared =
        new LinkedList<List<IGameConfig>>();

    List<IGameConfig> mConfigs = new LinkedList<IGameConfig>();

    IUtilityRule mockAllocationRule = mock(IUtilityRule.class);
    IQueryRule mockQueryRule = mock(IQueryRule.class);
    IActivityRule mockActivityRule = mock(IActivityRule.class);
    IInformationRevelationPolicy mockIR =
        mock(IInformationRevelationPolicy.class);
    ITerminationCondition mocktCondition = mock(ITerminationCondition.class);
    IInnerIRPolicy innerIR = mock(IInnerIRPolicy.class);

    IFlexibleRules mRules = new FlexibleRules(mockAllocationRule, mockQueryRule,
        mockActivityRule, mockIR, innerIR, mocktCondition);

    IGameConfig mConfig = new MarketConfig(mRules);
    mConfigs.add(mConfig);
    mConfigSquared.add(mConfigs);

    Constructor<?> distCons =
        HLTypeDistribution.class.getConstructor(List.class);
    Constructor<?> gCons = NormalValGenerator.class.getConstructor(List.class);
    List<Double> params = new LinkedList<Double>();
    params.add(0.0);
    params.add(1.0);

    List<Constructor<?>> endowmentList = new LinkedList<Constructor<?>>();
    List<List<Double>> endowmentParamList = new LinkedList<List<Double>>();

    endowmentList.add(gCons);
    endowmentParamList.add(params);

    List<String> tNameList = new LinkedList<String>();
    tNameList.add("trade");
    IValuationConfig vConfig =
        new ValuationConfig(distCons, endowmentList, endowmentParamList);
    vConfigs.add(vConfig);

    Class<?> endowmentDistributionClass = Class.forName(
        "brown.auction.endowment.distribution.library.IndependentEndowmentDist");
    Constructor<?> endowmentDistributionCons =
        endowmentDistributionClass.getConstructor(List.class);

    List<Constructor<?>> genList = new LinkedList<Constructor<?>>();
    List<List<Double>> paramList = new LinkedList<List<Double>>();

    Class genClass = Class
        .forName("brown.auction.type.generator.library.NormalValGenerator");
    List<Double> genParams = new LinkedList<Double>();
    genParams.add(0.0);
    genParams.add(100.0);

    Constructor<?> genCons = genClass.getConstructor(List.class);
    genList.add(genCons);
    paramList.add(genParams);

    Map<String, Integer> endowmentMapping = new HashMap<String, Integer>();
    endowmentMapping.put("trade", 1);

    EndowmentConfig eConfig =
        new EndowmentConfig(endowmentDistributionCons, genList, paramList);
    eConfigs.add(eConfig);

    ISimulationConfig sConfig =
        new SimulationConfig(1, vConfigs, eConfigs, mConfigSquared);

    assertTrue(sConfig.getSimulationRuns() == 1);
    assertEquals(sConfig.getEConfig(), eConfigs);
    assertEquals(sConfig.getVConfig(), vConfigs);
    assertEquals(sConfig.getMConfig(), mConfigSquared);
  }

}
