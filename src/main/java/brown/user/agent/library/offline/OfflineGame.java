package brown.user.agent.library.offline;

import java.util.List;

import brown.communication.messages.IActionMessage;
import brown.platform.accounting.IAccountUpdate;

public abstract class OfflineGame {
	public abstract List<IAccountUpdate> simulateRound(List<IActionMessage> actions);
}
