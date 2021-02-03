package brown.user.agent.library.offline;

import java.util.ArrayList;
import java.util.List;

import brown.auction.marketstate.IMarketState;
import brown.auction.marketstate.library.MarketState;
import brown.auction.rules.IUtilityRule;
import brown.auction.rules.utility.ChickenUtility;
import brown.auction.rules.utility.StagHuntUtility;
import brown.communication.messages.IActionMessage;
import brown.platform.accounting.IAccountUpdate;
import brown.system.setup.ISetup;

public class OfflineStagHunt extends OfflineGame {
	private final IUtilityRule util;

	public OfflineStagHunt() {
		this.util = new StagHuntUtility();
	}
	

	@Override
	public List<IAccountUpdate> simulateRound(List<IActionMessage> actions) {
		IMarketState mkt = new MarketState();
		util.setAllocation(mkt, actions, null);
		return new ArrayList<>(mkt.getUtilities());
	}
}
