package brown.platform.managers.library;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import brown.auction.endowment.IEndowment;
import brown.auction.endowment.distribution.IEndowmentDistribution;
import brown.auction.endowment.library.Endowment;
import brown.auction.value.generator.ITypeGenerator;
import brown.logging.library.PlatformLogging;
import brown.platform.managers.IEndowmentManager;

/**
 * EndowmentManager creates and stores IEndowment
 * 
 * @author andrewcoggins
 *
 */
public class EndowmentManager implements IEndowmentManager {

  private List<IEndowmentDistribution> distributions;
  private Map<Integer, IEndowment> agentEndowments;
  private boolean lock;

  /**
   * Constructor takes no arguments. starts unlocked and creates initial map for
   * agent endowments and list of distributions.
   */
  public EndowmentManager() {

    this.lock = false;
    this.agentEndowments = new HashMap<Integer, IEndowment>();
    this.distributions = new LinkedList<IEndowmentDistribution>();
  }

  public void createEndowment(Constructor<?> distCons,
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
      this.distributions
          .add((IEndowmentDistribution) distCons.newInstance(generatorList));
    } else {
      PlatformLogging.log("Creation denied: Endowment manager locked.");
    }
  }

  public IEndowment makeAgentEndowment(Integer agentID) {
    List<IEndowment> endowments = new LinkedList<IEndowment>();
    for (IEndowmentDistribution dist : this.distributions) {
      IEndowment anEndowment = dist.sample();
      endowments.add(anEndowment);
    }
    this.agentEndowments.put(agentID, combine(endowments));
    return this.agentEndowments.get(agentID);
  }

  public List<IEndowmentDistribution> getDistribution() {
    return this.distributions;
  }

  public void lock() {
    this.lock = true;
  }

  @Override
  public void reset() {
    this.agentEndowments.clear();
  }

  private IEndowment combine(List<IEndowment> multipleEndowments) {
    if (multipleEndowments.size() > 0) {
      IEndowment first = multipleEndowments.get(0);

      double firstMoney = first.getMoney();

      multipleEndowments.remove(0);
      for (IEndowment anEndowment : multipleEndowments) {
        firstMoney += anEndowment.getMoney();
      }
      return new Endowment(firstMoney);
    } else {
      return new Endowment(0.0);
    }
  }

}
