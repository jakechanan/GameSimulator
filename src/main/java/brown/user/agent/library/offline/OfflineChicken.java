package brown.user.agent.library.offline;

import java.util.ArrayList;
import java.util.List;

import brown.auction.marketstate.IMarketState;
import brown.auction.marketstate.library.MarketState;
import brown.auction.rules.IUtilityRule;
import brown.auction.rules.utility.ChickenUtility;
import brown.communication.messages.IActionMessage;
import brown.platform.accounting.IAccountUpdate;
import brown.system.setup.ISetup;

public class OfflineChicken extends OfflineGame {
	private final IUtilityRule util;

	public OfflineChicken() {
		this.util = new ChickenUtility();
	}
	

	@Override
	public List<IAccountUpdate> simulateRound(List<IActionMessage> actions) {
		IMarketState mkt = new MarketState();
		util.setAllocation(mkt, actions, null);
		return new ArrayList<>(mkt.getUtilities());
	}
}
