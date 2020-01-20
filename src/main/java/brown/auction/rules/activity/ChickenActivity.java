package brown.auction.rules.activity;

import java.util.List;

import brown.auction.marketstate.IMarketState;
import brown.auction.rules.IActivityRule;
import brown.communication.action.IGameAction;
import brown.communication.action.library.GameAction;
import brown.communication.messages.IActionMessage;

public class ChickenActivity extends AbsActivity implements IActivityRule {

    @Override
    public void isAcceptable(IMarketState state, IActionMessage aBid,
                             List<IActionMessage> currentBids) {

        IGameAction agentBid = (GameAction) aBid.getBid();
        Integer agentAction = agentBid.getAction();

        if (agentAction == 0 || agentAction == 1) {
            state.setAcceptable(true);
        } else {
            state.setAcceptable(false);
        }
    }
}
