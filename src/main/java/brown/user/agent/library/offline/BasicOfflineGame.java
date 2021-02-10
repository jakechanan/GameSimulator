package brown.user.agent.library.offline;

import java.util.ArrayList;
import java.util.List;

import brown.auction.marketstate.IMarketState;
import brown.auction.marketstate.library.MarketState;
import brown.auction.rules.IUtilityRule;
import brown.communication.messages.IActionMessage;
import brown.platform.accounting.IAccountUpdate;

public class BasicOfflineGame extends OfflineGame {
	private final IUtilityRule utilityRule;
	
	public BasicOfflineGame(IUtilityRule utilityRule) {
		super();
		this.utilityRule = utilityRule;
	}

	@Override
	public List<IAccountUpdate> simulateRound(List<IActionMessage> actions) {
		IMarketState mkt = new MarketState();
		this.utilityRule.setAllocation(mkt, actions, null);
		return new ArrayList<>(mkt.getUtilities());
	}

}
