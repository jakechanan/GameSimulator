package brown.user.main.library;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

import brown.platform.game.IFlexibleRules;
import brown.platform.managers.IAccountManager;
import brown.platform.managers.IDomainManager;
import brown.platform.managers.IEndowmentManager;
import brown.platform.managers.IGameManager;
import brown.platform.managers.ISimulationManager;
import brown.platform.managers.ITypeManager;
import brown.platform.managers.IWorldManager;
import brown.platform.managers.library.AccountManager;
import brown.platform.managers.library.DomainManager;
import brown.platform.managers.library.EndowmentManager;
import brown.platform.managers.library.GameManager;
import brown.platform.managers.library.SimulationManager;
import brown.platform.managers.library.TypeManager;
import brown.platform.managers.library.WorldManager;
import brown.user.main.IEndowmentConfig;
import brown.user.main.IGameConfig;
import brown.user.main.ISimulationConfig;
import brown.user.main.IValuationConfig;

public class ConfigRun {

  private List<ISimulationConfig> config;

  public ConfigRun(List<ISimulationConfig> config) {
    this.config = config;
  }

  public void run(Integer startingDelayTime, Double simulationDelayTime,
      Integer numSimulations) throws InstantiationException,
      IllegalAccessException, IllegalArgumentException,
      InvocationTargetException, InterruptedException {
    ISimulationManager simulationManager = new SimulationManager();
    for (ISimulationConfig aConfig : this.config) {
      IWorldManager worldManager = new WorldManager();
      IDomainManager domainManager = new DomainManager();
      IEndowmentManager endowmentManager = new EndowmentManager();
      IAccountManager accountManager = new AccountManager();
      ITypeManager valuationManager = new TypeManager();
      IGameManager marketManager = new GameManager();


      // valuation manager.
      List<IValuationConfig> vConfigs = aConfig.getVConfig();
      for (IValuationConfig vConfig : vConfigs) {
        valuationManager.createValuation(vConfig.getValDistribution(),
            vConfig.getGeneratorConstructors(), vConfig.getGeneratorParams());
      }

      // endowment manager.
      List<IEndowmentConfig> eConfigs = aConfig.getEConfig();
      for (IEndowmentConfig eConfig : eConfigs) {
        endowmentManager.createEndowment(eConfig.getDistribution(),
            eConfig.getGeneratorConstructors(), eConfig.getGeneratorParams());
      } 
      
      // market manager
      for (List<IGameConfig> mConfigList : aConfig.getMConfig()) {
        List<IFlexibleRules> marketRules = new LinkedList<IFlexibleRules>();
        for (IGameConfig mConfig : mConfigList) {
          marketRules.add(mConfig.getRules());
        }
        marketManager.createSimultaneousGame(marketRules);
      }
      
      domainManager.createDomain(valuationManager,
          endowmentManager, accountManager);
      worldManager.createWorld(domainManager, marketManager);
      simulationManager.createSimulation(aConfig.getSimulationRuns(), aConfig.getGroupSize(),
          worldManager);
      valuationManager.lock();
      simulationManager.lock();
      marketManager.lock();
    }

    simulationManager.runSimulation(startingDelayTime, simulationDelayTime,
        numSimulations);
    
    
  }
}
