package brown.auction.rules.activity;

import brown.auction.marketstate.IMarketState;
import brown.auction.rules.IActivityRule;
import brown.communication.action.IGameAction;
import brown.communication.action.library.GameAction;
import brown.communication.messages.IActionMessage;

import java.util.List;

public class RPSActivity extends AbsActivity implements IActivityRule {

    @Override
    public void isAcceptable(IMarketState state, IActionMessage aBid,
                             List<IActionMessage> currentBids) {

        IGameAction agentBid = (GameAction) aBid.getBid();
        Integer agentAction = agentBid.getAction();

        if (agentAction == 0 || agentAction == 1 || agentAction == 2) {
            state.setAcceptable(true);
        } else {
            state.setAcceptable(false);
        }
    }
}
