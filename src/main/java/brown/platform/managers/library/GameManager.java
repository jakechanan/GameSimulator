package brown.platform.managers.library;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import brown.auction.marketstate.IMarketPublicState;
import brown.auction.marketstate.library.MarketPublicState;
import brown.auction.marketstate.library.MarketState;
import brown.auction.type.valuation.IType;
import brown.communication.messages.IActionMessage;
import brown.communication.messages.IActionRequestMessage;
import brown.communication.messages.IInformationMessage;
import brown.communication.messages.ISimulationReportMessage;
import brown.communication.messages.IStatusMessage;
import brown.communication.messages.library.ActionRejectionMessage;
import brown.communication.messages.library.ErrorMessage;
import brown.communication.messages.library.InformationMessage;
import brown.communication.messages.library.SimulationReportMessage;
import brown.logging.library.PlatformLogging;
import brown.platform.accounting.IAccountUpdate;
import brown.platform.game.IFlexibleRules;
import brown.platform.game.IGame;
import brown.platform.game.IGameBlock;
import brown.platform.game.library.Game;
import brown.platform.game.library.SimultaneousGame;
import brown.platform.information.IWhiteboard;
import brown.platform.information.library.Whiteboard;
import brown.platform.managers.IGameManager;

/**
 * Market manager stores and handles multiple markets
 * 
 * @author acoggins
 *
 */
public class GameManager implements IGameManager {
  private Map<Integer, IGame> activeMarkets;
  private List<IGameBlock> allMarkets;
  private IWhiteboard whiteboard;
  private boolean lock;

  /**
   * Constructor for a market manager initializes ledgers and markets. ledgers
   * tracks the ledgers for all markets at each time markets maps each market at
   * each time to a unique id information is initially a blank prevstateinfo
   * index is initially set to -1 idCount keeps track of the number of markets
   * in the MarketManager- initially, this is 0.
   */
  public GameManager() {
    this.allMarkets = new LinkedList<IGameBlock>();
    this.activeMarkets = new ConcurrentHashMap<Integer, IGame>();
    this.lock = false;
    this.whiteboard = new Whiteboard();
  }

  @Override
  public void createSimultaneousGame(List<IFlexibleRules> marketRules) {
    if (!this.lock) {
      IGameBlock marketBlock = new SimultaneousGame(marketRules);
      this.allMarkets.add(marketBlock);
    } else {
      PlatformLogging.log("ERROR: market manager locked.");
    }
  }

  @Override
  public void lock() {
    this.lock = true;
  }

  @Override
  public Integer getNumMarketBlocks() {
    return this.allMarkets.size();
  }

  @Override
  public void openMarkets(int index, Map<Integer, IType> agents, int groupIndex,
      int numGroups) {
    IGameBlock currentMarketBlock = this.allMarkets.get(index);
    List<IFlexibleRules> marketRules = currentMarketBlock.getMarkets();
    // TODO: make sure marketIDs don't overlap.
    // TODO: use the index of the previous market size, if it's available. 
    for (int i = 0; i < marketRules.size(); i++) {
      int marketID = i + (marketRules.size() * groupIndex) + (index * marketRules.size() * numGroups);
      this.activeMarkets.put(marketID, new Game(marketID, marketRules.get(i),
          new MarketState(), new MarketPublicState(), agents));
    }
  }

  @Override
  public IStatusMessage handleTradeMessage(IActionMessage message) {
    Integer marketID = message.getAuctionID();
    Integer agentID = message.getAgentID();
    if (this.activeMarkets.containsKey(marketID)) {
      IGame market = this.activeMarkets.get(marketID);
      synchronized (market) {
        boolean accepted = market.processBid(message);
        if (!accepted) {
          return new ActionRejectionMessage(0, agentID,
              "[x] REJECTED: Trade message for auction "
                  + message.getAuctionID().toString()
                  + " denied: rejected by activity rule.");
        } else {
          return new ActionRejectionMessage(-1, -1, "");
        }
      }
    } else {
      return new ErrorMessage(0, agentID,
          "[x] ERROR: Trade message for auction "
              + message.getAuctionID().toString()
              + " denied: market no longer active.");
    }
  }

  @Override
  public List<Integer> getActiveMarketIDs() {
    return new LinkedList<Integer>(this.activeMarkets.keySet());
  }

  @Override
  public IGame getActiveMarket(Integer marketID) {
    return this.activeMarkets.get(marketID);
  }

  public List<IActionRequestMessage> updateMarket(Integer marketID) {
    IGame market = this.activeMarkets.get(marketID);
    // tick the market
    market.tick();
    // update market trade history
    market.updateTradeHistory();
    // update inner information: copy changes from the market state to the
    // market public state.
    market.updateInnerInformation();

    for (Integer agentID : market.getMarketAgents()) {
      this.whiteboard.postInnerInformation(marketID, agentID,
          this.activeMarkets.get(marketID).getPublicState());
    }

    List<IActionRequestMessage> tradeRequests =
        new LinkedList<IActionRequestMessage>();
    for (Integer agentID : market.getMarketAgents()) {
      IActionRequestMessage tRequest = market.constructTradeRequest(agentID);
      IMarketPublicState agentState = whiteboard.getInnerInformation(marketID,
          agentID, market.getTimestep());
      tRequest.addInformation(agentState);
      tradeRequests.add(tRequest);
    }
    return tradeRequests;
  }

  @Override
  public Map<Integer, IInformationMessage>
      constructInformationMessages(Integer marketID, List<Integer> agentIDs) {
    Map<Integer, IInformationMessage> informationMessages =
        new HashMap<Integer, IInformationMessage>();
    IMarketPublicState publicState =
        this.whiteboard.getOuterInformation(marketID);
    for (Integer agentID : agentIDs) {
      informationMessages.put(agentID,
          new InformationMessage(0, agentID, publicState));
    }
    return informationMessages;
  }

  @Override
  public Map<Integer, ISimulationReportMessage>
      constructSimulationReportMessages(List<Integer> agentIDs) {

    Map<Integer, IMarketPublicState> simInformation =
        this.whiteboard.getSimulationReportWhiteboard();

    Map<Integer, ISimulationReportMessage> agentMessages =
        new HashMap<Integer, ISimulationReportMessage>();

    for (Integer agentID : agentIDs) {
      agentMessages.put(agentID,
          new SimulationReportMessage(0, agentID, simInformation));
    }
    return agentMessages;
  }

  @Override
  public List<IAccountUpdate> finishMarket(Integer marketID) {
    List<IAccountUpdate> accountUpdates =
        this.activeMarkets.get(marketID).constructAccountUpdates();
    IGame market = this.activeMarkets.get(marketID);
    market.updateOuterInformation();
    this.whiteboard.postOuterInformation(marketID,
        this.activeMarkets.get(marketID).getPublicState());
    this.whiteboard.postSimulationInformation(marketID,
        this.activeMarkets.get(marketID).getUnredactedPublicState());
    return accountUpdates;
  }

  @Override
  public void finalizeMarket(Integer marketID) {
    this.activeMarkets.remove(marketID);
  }

  @Override
  public boolean marketOpen(Integer marketID) {
    return this.activeMarkets.get(marketID).isOpen();
  }

  @Override
  public boolean anyMarketsOpen() {
    for (Integer marketID : this.activeMarkets.keySet()) {
      if (this.activeMarkets.get(marketID).isOpen()) {
        return true;
      }
    }
    return false;
  }

  @Override
  public void reset() {
    this.activeMarkets.clear();
    this.whiteboard.clear();
  }

}
