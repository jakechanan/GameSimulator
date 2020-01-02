package brown.platform.managers.library;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import brown.auction.type.distribution.ITypeDistribution;
import brown.auction.type.distribution.library.HLTypeDistribution;
import brown.auction.type.generator.ITypeGenerator;
import brown.auction.type.generator.library.NormalValGenerator;
import brown.auction.type.valuation.IType;
import brown.auction.type.valuation.library.HLType;
import brown.platform.accounting.IAccount;
import brown.platform.accounting.library.Account;
import brown.platform.managers.IUtilityManager;

public class UtilityManagerTest {

  @Test
  public void testUtilityManager() {

    IUtilityManager utilManager = new UtilityManager();

    utilManager.addAgentRecord(0);
    utilManager.addAgentRecord(1);

    assertTrue(utilManager.getAgentRecords().keySet().size() == 2);

    Map<Integer, IAccount> accounts = new HashMap<Integer, IAccount>();

    Map<Integer, IType> valuations =
        new HashMap<Integer, IType>();


    IAccount acctOne = new Account(0, 100.0);
    IAccount acctTwo = new Account(0, 100.0);

    accounts.put(0, acctOne);
    accounts.put(1, acctTwo);

    IType valOne = new HLType(0); 
    IType valTwo = new HLType(1); 

    valuations.put(0, valOne);
    valuations.put(0, valTwo);

    List<ITypeGenerator> gen = new LinkedList<ITypeGenerator>();
    List<Double> valParams = new LinkedList<Double>();
    valParams.add(2.0);
    valParams.add(0.1);

    gen.add(new NormalValGenerator(valParams));
    ITypeDistribution valDist =
        new HLTypeDistribution(gen);
    
    IType type = valDist.sample();

    
    IType genOne = new HLType(0);
    IType genTwo = new HLType(0); 

    valuations.put(0, genOne);
    valuations.put(1, genTwo);

    utilManager.updateUtility(accounts, valuations);

    Map<Integer, List<Double>> agentRecords = utilManager.getAgentRecords();

    for (Integer id : agentRecords.keySet()) {
      List<Double> utils = agentRecords.get(id);
      assertTrue(utils.size() == 1);
      System.out.println(utils.get(0)); 
      assertTrue(utils.get(0) == 100.0);
    }
  }

}
