package brown.user.main.library;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import brown.auction.rules.IActivityRule;
import brown.auction.rules.IInformationRevelationPolicy;
import brown.auction.rules.IInnerIRPolicy;
import brown.auction.rules.IQueryRule;
import brown.auction.rules.ITerminationCondition;
import brown.auction.rules.IUtilityRule;
import brown.logging.library.ErrorLogging;
import brown.logging.library.PlatformLogging;
import brown.platform.game.IFlexibleRules;
import brown.platform.game.library.FlexibleRules;
import brown.user.main.IEndowmentConfig;
import brown.user.main.IJsonParser;
import brown.user.main.IGameConfig;
import brown.user.main.ISimulationConfig;
import brown.user.main.IValuationConfig;

public class JsonParser implements IJsonParser {

  @SuppressWarnings("unchecked")
  @Override
  public List<ISimulationConfig> parseJSON(String fileName)
      throws FileNotFoundException, IOException, ParseException,
      ClassNotFoundException, NoSuchMethodException, SecurityException,
      InstantiationException, IllegalAccessException, IllegalArgumentException,
      InvocationTargetException {
    Object rawInput = new JSONParser().parse(new FileReader(fileName));

    JSONObject jo = (JSONObject) rawInput;
    JSONArray ja = (JSONArray) jo.get("simulation");

    Iterator simulationIterator = ja.iterator();
    Iterator seqMarketIterator;
    Iterator simMarketIterator;
    Iterator generatorIterator;

    Iterator<Map.Entry> keyIterator;
    Iterator<Map.Entry> seqMarketKeyIterator;
    Iterator<Map.Entry> simMarketKeyIterator;
    Iterator<Map.Entry> marketRulesKeyIterator;
    Iterator<Map.Entry> generatorKeyIterator;

    // within simulation strings
    List<Integer> numRunsList = new LinkedList<Integer>();
    List<Integer> groupSizeList = new LinkedList<Integer>(); 
    List<List<List<Map<String, String>>>> marketRules =
        new LinkedList<List<List<Map<String, String>>>>();
    List<String> valuationDistributions = new LinkedList<String>();
    List<List<String>> valuationGeneratorNames = new LinkedList<List<String>>();
    List<List<List<Double>>> valuationGeneratorParameters =
        new LinkedList<List<List<Double>>>();
    List<String> endowmentDistributions = new LinkedList<String>();
    List<List<String>> endowmentGeneratorNames = new LinkedList<List<String>>();
    List<List<List<Double>>> endowmentGeneratorParameters =
        new LinkedList<List<List<Double>>>();

    // get the strings from the json
    while (simulationIterator.hasNext()) {

      List<List<Map<String, String>>> simulationMarketRules =
          new LinkedList<List<Map<String, String>>>();
      String simulationTypeDistributions = "";
      List<String> simulationValuationGeneratorNames = new LinkedList<String>();
      List<List<Double>> simulationValuationGeneratorParameters =
          new LinkedList<List<Double>>();
      String simulationEndowmentDistributions = "";
      List<String> simulationEndowmentGeneratorNames = new LinkedList<String>();
      List<List<Double>> simulationEndowmentGeneratorParameters =
          new LinkedList<List<Double>>();
      
      boolean groupSizeAdded = false; 
      keyIterator = ((Map) simulationIterator.next()).entrySet().iterator();
      while (keyIterator.hasNext()) {
        Map.Entry pair = keyIterator.next();
        if (pair.getKey().equals("numRuns")) {
          numRunsList.add(((Long) pair.getValue()).intValue());
        } else if (pair.getKey().equals("groupSize")) {
          groupSizeList.add(((Long) pair.getValue()).intValue());
          groupSizeAdded = true; 
        } else if (pair.getKey().equals("typeDistribution")) {
          String distribution = (String) pair.getValue();
          simulationTypeDistributions = distribution;
        } else if (pair.getKey().equals("typeGenerator")) {
          List<String> singleValuationGeneratorNames = new LinkedList<String>();
          List<List<Double>> singleValuationGeneratorParameters =
              new LinkedList<List<Double>>();
          generatorIterator = ((JSONArray) pair.getValue()).iterator();
          while (generatorIterator.hasNext()) {
            generatorKeyIterator =
                ((Map) generatorIterator.next()).entrySet().iterator();
            while (generatorKeyIterator.hasNext()) {
              Map.Entry generatorPair = generatorKeyIterator.next();
              if (generatorPair.getKey().equals("name")) {
                String generatorName = (String) generatorPair.getValue();
                singleValuationGeneratorNames.add(generatorName);
              } else if (generatorPair.getKey().equals("parameters")) {
                List<Double> generatorParams =
                    (List<Double>) generatorPair.getValue();
                singleValuationGeneratorParameters.add(generatorParams);
              }
            }
          }
          simulationValuationGeneratorNames = singleValuationGeneratorNames;
          simulationValuationGeneratorParameters =
              singleValuationGeneratorParameters;
        } else if (pair.getKey().equals("endowmentDistribution")) {
          String distribution = (String) pair.getValue();
          simulationEndowmentDistributions = distribution;
        } else if (pair.getKey().equals("endowmentGenerator")) {
          List<String> singleEndowmentGeneratorNames = new LinkedList<String>();
          List<List<Double>> singleEndowmentGeneratorParameters =
              new LinkedList<List<Double>>();
          generatorIterator = ((JSONArray) pair.getValue()).iterator();
          while (generatorIterator.hasNext()) {
            generatorKeyIterator =
                ((Map) generatorIterator.next()).entrySet().iterator();
            while (generatorKeyIterator.hasNext()) {
              Map.Entry generatorPair = generatorKeyIterator.next();
              if (generatorPair.getKey().equals("name")) {
                String generatorName = (String) generatorPair.getValue();
                singleEndowmentGeneratorNames.add(generatorName);
              } else if (generatorPair.getKey().equals("parameters")) {
                List<Double> generatorParams =
                    (List<Double>) generatorPair.getValue();
                singleEndowmentGeneratorParameters.add(generatorParams);
              }
            }
          }
          simulationEndowmentGeneratorNames = singleEndowmentGeneratorNames;
          simulationEndowmentGeneratorParameters =
              singleEndowmentGeneratorParameters;
        } else if (pair.getKey().equals("seqMarket")) {
          seqMarketIterator = ((JSONArray) pair.getValue()).iterator();
          while (seqMarketIterator.hasNext()) {
            seqMarketKeyIterator =
                ((Map) seqMarketIterator.next()).entrySet().iterator();
            while (seqMarketKeyIterator.hasNext()) {
              Map.Entry seqMarketPair = seqMarketKeyIterator.next();
              if (seqMarketPair.getKey().equals("simMarket")) {
                List<Map<String, String>> simMarketRules =
                    new LinkedList<Map<String, String>>();

                simMarketIterator =
                    ((JSONArray) seqMarketPair.getValue()).iterator();
                while (simMarketIterator.hasNext()) {
                  Map<String, String> singleMarketRules =
                      new HashMap<String, String>();
                  List<String> singleMarketitems = new LinkedList<String>();

                  simMarketKeyIterator =
                      ((Map) simMarketIterator.next()).entrySet().iterator();
                  while (simMarketKeyIterator.hasNext()) {
                    Map.Entry simMarketPair = simMarketKeyIterator.next();
                    if (simMarketPair.getKey().equals("marketRules")) {
                      marketRulesKeyIterator = ((Map) simMarketPair.getValue())
                          .entrySet().iterator();
                      while (marketRulesKeyIterator.hasNext()) {
                        Map.Entry marketRulesPair =
                            marketRulesKeyIterator.next();
                        if (marketRulesPair.getKey().equals("uRule")) {
                          String aRule = (String) marketRulesPair.getValue();
                          singleMarketRules
                              .put((String) marketRulesPair.getKey(), aRule);
                        } else if (marketRulesPair.getKey().equals("qRule")) {
                          String qRule = (String) marketRulesPair.getValue();
                          singleMarketRules
                              .put((String) marketRulesPair.getKey(), qRule);
                        } else if (marketRulesPair.getKey().equals("actRule")) {
                          String actRule = (String) marketRulesPair.getValue();
                          singleMarketRules
                              .put((String) marketRulesPair.getKey(), actRule);
                        } else if (marketRulesPair.getKey()
                            .equals("irPolicy")) {
                          String irPolicy = (String) marketRulesPair.getValue();
                          singleMarketRules
                              .put((String) marketRulesPair.getKey(), irPolicy);
                        } else if (marketRulesPair.getKey()
                            .equals("innerIRPolicy")) {
                          String innerIRPolicy =
                              (String) marketRulesPair.getValue();
                          singleMarketRules.put(
                              (String) marketRulesPair.getKey(), innerIRPolicy);
                        } else if (marketRulesPair.getKey()
                            .equals("tCondition")) {
                          String tCondition =
                              (String) marketRulesPair.getValue();
                          singleMarketRules.put(
                              (String) marketRulesPair.getKey(), tCondition);
                        } else {
                          ErrorLogging.log(
                              "ERROR: JSON Parse: MarketRules: unrecognized input key: "
                                  + marketRulesPair.getKey());
                        }
                      }
                    } else {
                      ErrorLogging.log(
                          "ERROR: JSON Parse: simMarket: unrecognized input key: "
                              + simMarketPair.getKey());
                    }
                  }
                  simMarketRules.add(singleMarketRules);
                } 
                simulationMarketRules.add(simMarketRules);
              } else {
                ErrorLogging.log(
                    "ERROR: JSON Parse: SeqMarket: unrecognized input key: "
                        + seqMarketPair.getKey());
              }
            }
          }
        } else {
          ErrorLogging
              .log("ERROR: JSON Parse: Simulation: unrecognized input key: "
                  + pair.getKey());
        }
      }
      marketRules.add(simulationMarketRules);
      valuationDistributions.add(simulationTypeDistributions);
      valuationGeneratorNames.add(simulationValuationGeneratorNames);
      valuationGeneratorParameters.add(simulationValuationGeneratorParameters);
      endowmentDistributions.add(simulationEndowmentDistributions);
      endowmentGeneratorNames.add(simulationEndowmentGeneratorNames);
      endowmentGeneratorParameters.add(simulationEndowmentGeneratorParameters);
      if (!groupSizeAdded) 
        groupSizeList.add(-1); 

    }

    PlatformLogging.log(marketRules);
    PlatformLogging.log(valuationDistributions);
    PlatformLogging.log(valuationGeneratorNames);
    PlatformLogging.log(valuationGeneratorParameters);
    PlatformLogging.log(endowmentDistributions);
    PlatformLogging.log(endowmentGeneratorNames);
    PlatformLogging.log(endowmentGeneratorParameters);

    // parse the strings into classes if necessary, and put into configs

    // valuation configs

    List<List<IValuationConfig>> valuationConfigs =
        new LinkedList<List<IValuationConfig>>();
    for (int i = 0; i < valuationDistributions.size(); i++) {
      List<IValuationConfig> simulationValConfigs =
          new LinkedList<IValuationConfig>();
      String simulationValuationDistributions = valuationDistributions.get(i);
      List<String> simulationValuationGeneratorNames =
          valuationGeneratorNames.get(i);
      List<List<Double>> simulationValuationGeneratorParams =
          valuationGeneratorParameters.get(i);
      String valuationDistributionString = simulationValuationDistributions;
      Class<?> distributionClass =
          Class.forName("brown.auction.type.distribution.library."
              + valuationDistributionString);
      Constructor<?> distributionCons =
          distributionClass.getConstructor(List.class);

      List<String> singleValuationGeneratorNames =
          simulationValuationGeneratorNames;

      List<List<Double>> singleValuationGeneratorParams =
          simulationValuationGeneratorParams;

      List<Constructor<?>> valuationConstructors =
          new LinkedList<Constructor<?>>();
      List<List<Double>> valuationConstructorParams =
          new LinkedList<List<Double>>();

      for (int k = 0; k < singleValuationGeneratorNames.size(); k++) {
        String generatorName = singleValuationGeneratorNames.get(k);
        Class<?> generatorClass = Class
            .forName("brown.auction.type.generator.library." + generatorName);
        Constructor<?> generatorCons =
            generatorClass.getConstructor(List.class);

        List<Double> generatorParams = new LinkedList<Double>();
        List<Double> generatorStringParams =
            singleValuationGeneratorParams.get(k);
        for (Double param : generatorStringParams) {
          generatorParams.add(param);
        }

        valuationConstructors.add(generatorCons);
        valuationConstructorParams.add(generatorParams);
      }
      IValuationConfig valConfig = new ValuationConfig(distributionCons,
          valuationConstructors, valuationConstructorParams);
      simulationValConfigs.add(valConfig);
      valuationConfigs.add(simulationValConfigs);
    }

    // endowment configs

    List<List<IEndowmentConfig>> endowmentConfigs =
        new LinkedList<List<IEndowmentConfig>>();
    for (int i = 0; i < endowmentDistributions.size(); i++) {
      List<IEndowmentConfig> simulationEndowmentConfigs =
          new LinkedList<IEndowmentConfig>();
      List<String> simulationEndowmentDistributions = endowmentDistributions;
      List<List<String>> simulationEndowmentGeneratorNames =
          endowmentGeneratorNames;
      List<List<List<Double>>> simulationEndowmentGeneratorParams =
          endowmentGeneratorParameters;
      for (int j = 0; j < simulationEndowmentDistributions.size(); j++) {
        String endowmentDistributionstring =
            simulationEndowmentDistributions.get(j);
        Class<?> distributionClass =
            Class.forName("brown.auction.endowment.distribution.library."
                + endowmentDistributionstring);
        Constructor<?> distributionCons =
            distributionClass.getConstructor(List.class);

        List<String> singleEndowmentGeneratorNames =
            simulationEndowmentGeneratorNames.get(j);
        List<List<Double>> singleEndowmentGeneratorParams =
            simulationEndowmentGeneratorParams.get(j);

        List<Constructor<?>> endowmentConstructors =
            new LinkedList<Constructor<?>>();
        List<List<Double>> endowmentConstructorParams =
            new LinkedList<List<Double>>();

        for (int k = 0; k < singleEndowmentGeneratorNames.size(); k++) {
          String generatorName = singleEndowmentGeneratorNames.get(k);
          Class<?> generatorClass = Class.forName(
              "brown.auction.type.generator.library." + generatorName);
          Constructor<?> generatorCons =
              generatorClass.getConstructor(List.class);

          List<Double> generatorParams = new LinkedList<Double>();
          List<Double> generatorStringParams =
              singleEndowmentGeneratorParams.get(k);
          for (Double param : generatorStringParams) {
            generatorParams.add(param);
          }
          endowmentConstructors.add(generatorCons);
          endowmentConstructorParams.add(generatorParams);
        }
        IEndowmentConfig endowConfig = new EndowmentConfig(distributionCons,
            endowmentConstructors, endowmentConstructorParams);
        simulationEndowmentConfigs.add(endowConfig);
      }
      endowmentConfigs.add(simulationEndowmentConfigs);
    }

    // market configs
    
    List<List<List<IGameConfig>>> marketConfigs =
        new LinkedList<List<List<IGameConfig>>>();
    for (int i = 0; i < marketRules.size(); i++) {
      List<List<IGameConfig>> simulationMarketConfigs =
          new LinkedList<List<IGameConfig>>();
      List<List<Map<String, String>>> simulationMarketRules =
          marketRules.get(i);
      for (int j = 0; j < simulationMarketRules.size(); j++) {
        List<IGameConfig> simMarketConfigs = new LinkedList<IGameConfig>();
        List<Map<String, String>> simultaneousMarketRules =
            simulationMarketRules.get(j);
        for (int k = 0; k < simultaneousMarketRules.size(); k++) {
          Map<String, String> singleMarketRules =
              simultaneousMarketRules.get(k);
          String aRuleString = singleMarketRules.get("uRule");
          String qRuleString = singleMarketRules.get("qRule");
          String actRuleString = singleMarketRules.get("actRule");
          String irPolicyString = singleMarketRules.get("irPolicy");
          String innerIRPolicyString = singleMarketRules.get("innerIRPolicy");
          String tConditionString = singleMarketRules.get("tCondition");

          Class<?> aRuleClass = Class.forName(
              "brown.auction.rules.utility." + aRuleString);
          Class<?> qRuleClass = Class
              .forName("brown.auction.rules.query." + qRuleString);
          Class<?> actRuleClass = Class.forName(
              "brown.auction.rules.activity." + actRuleString);
          Class<?> irPolicyClass = Class
              .forName("brown.auction.rules.ir." + irPolicyString);
          Class<?> innerIRPolicyClass = Class.forName(
              "brown.auction.rules.innerir." + innerIRPolicyString);
          Class<?> tConditionClass = Class.forName(
              "brown.auction.rules.termination." + tConditionString);

          Constructor<?> aRuleCons = aRuleClass.getConstructor();
          Constructor<?> qRuleCons = qRuleClass.getConstructor();
          Constructor<?> actRuleCons = actRuleClass.getConstructor();
          Constructor<?> irPolicyCons = irPolicyClass.getConstructor();
          Constructor<?> innerIRPolicyCons =
              innerIRPolicyClass.getConstructor();
          Constructor<?> tConditionCons = tConditionClass.getConstructor();

          IFlexibleRules marketRule =
              new FlexibleRules((IUtilityRule) aRuleCons.newInstance(),
                  (IQueryRule) qRuleCons.newInstance(),
                  (IActivityRule) actRuleCons.newInstance(),
                  (IInformationRevelationPolicy) irPolicyCons.newInstance(),
                  (IInnerIRPolicy) innerIRPolicyCons.newInstance(),
                  (ITerminationCondition) tConditionCons.newInstance());

          IGameConfig singleMarketConfig = new MarketConfig(marketRule);
          simMarketConfigs.add(singleMarketConfig);
        }
        simulationMarketConfigs.add(simMarketConfigs);
      }
      marketConfigs.add(simulationMarketConfigs);
    }

    // put it all together
    List<ISimulationConfig> simulationConfigs =
        new LinkedList<ISimulationConfig>();

    for (int i = 0; i < marketConfigs.size(); i++) {
      ISimulationConfig singleConfig =
          new SimulationConfig(numRunsList.get(i), groupSizeList.get(i), valuationConfigs.get(i),
              endowmentConfigs.get(i), marketConfigs.get(i));
      simulationConfigs.add(singleConfig);
    }

    return simulationConfigs;
  }

  @Override
  public Map<String, Integer> parseJSONOuterParameters(String fileName)
      throws FileNotFoundException, IOException, ParseException {
    Object rawInput = new JSONParser().parse(new FileReader(fileName));

    JSONObject jo = (JSONObject) rawInput;

    Map<String, Integer> outerParams = new HashMap<String, Integer>();
    outerParams.put("numTotalRuns", ((Long) jo.get("numTotalRuns")).intValue());
    outerParams.put("startingDelayTime",
        ((Long) jo.get("startingDelayTime")).intValue());
    if (jo.containsKey("serverPort"))
      outerParams.put("serverPort", ((Long) jo.get("serverPort")).intValue()); 
    else
      outerParams.put("serverPort", 2121);
    return outerParams;
  }
  
  @Override
  public Map<String, Double> parseJSONDoubleParameters(String fileName)
      throws FileNotFoundException, IOException, ParseException {
    Object rawInput = new JSONParser().parse(new FileReader(fileName));

    JSONObject jo = (JSONObject) rawInput;
    Map<String, Double> outerParams = new HashMap<String, Double>();
    outerParams.put("simulationDelayTime",
        ((Double) jo.get("simulationDelayTime")));

    return outerParams;

  }

}
