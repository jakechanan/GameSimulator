package brown.platform.managers.library;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import brown.auction.value.distribution.ITypeDistribution;
import brown.auction.value.distribution.library.AbsTypeDistribution;
import brown.auction.value.generator.ITypeGenerator;
import brown.auction.value.valuation.IType;
import brown.communication.messages.ITypeMessage;
import brown.communication.messages.library.ValuationMessage;
import brown.logging.library.AuctionLogging;
import brown.logging.library.PlatformLogging;
import brown.platform.managers.ITypeManager;

/**
 * ValuationManager stores and creates IValuation and IValuationDistribution
 * 
 * @author andrewcoggins
 *
 */
public class TypeManager implements ITypeManager {

  private ITypeDistribution distributions;
  private Map<Integer, IType> agentValuations;
  private boolean lock;

  /**
   * Constructor for IValuationManager begins unlocked, creates initial map for
   * agent valuation and list for IValuationDistribution
   */
  public TypeManager() {

    this.lock = false;
    this.agentValuations = new HashMap<Integer, IType>();
    this.distributions = null; 
  }

  public void createValuation(Constructor<?> distCons,
      List<Constructor<?>> generatorCons, List<List<Double>> generatorParams)
      throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    if (!this.lock) {
      List<ITypeGenerator> generatorList = new LinkedList<ITypeGenerator>();
      for (int i = 0; i < generatorCons.size(); i++) {
        ITypeGenerator newGen = (ITypeGenerator) generatorCons.get(i)
            .newInstance(generatorParams.get(i));
        generatorList.add(newGen);
      }
      this.distributions = (ITypeDistribution) distCons.newInstance(generatorList);
    } else {
      PlatformLogging.log("Creation denied: valuation manager locked.");
    }
  }

  public void addAgentValuation(Integer agentID, IType valuation) {
    AuctionLogging.log(
        "Adding Valuation for Agent" + agentID.toString() + ": " + valuation);
    this.agentValuations.put(agentID, valuation);
  }

  public IType getAgentValuation(Integer agentID) {
    return this.agentValuations.get(agentID);
  }

  public ITypeDistribution getDistribution() {
    return this.distributions;
  }

  public void lock() {
    this.lock = true;
  }

  @Override
  public Map<Integer, ITypeMessage> constructValuationMessages() {
    Map<Integer, ITypeMessage> agentValuationMessages =
        new HashMap<Integer, ITypeMessage>();
    for (Integer agentValuation : this.agentValuations.keySet()) {
      agentValuationMessages.put(agentValuation, new ValuationMessage(0,
          agentValuation, this.agentValuations.get(agentValuation)));
    }
    return agentValuationMessages;
  }

  @Override
  public void reset() {
    this.agentValuations.clear();
  }

}
