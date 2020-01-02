package brown.platform.game.library;

import java.util.List;

import brown.platform.game.IFlexibleRules;
import brown.platform.game.IGameBlock;

/**
 * A Simultaneous market contains one or multiple markets, to be run at the same time
 * in the Simulation. 
 * @author andrewcoggins
 *
 */
public class SimultaneousGame implements IGameBlock {

    private List<IFlexibleRules> markets;

    /**
     * Constructor includes a list of IIFlexibleRules corresponding to the markets 
     * to be opened, and a list of ICart corresponding to the items each market will 
     * be open for. 
     * @param markets
     * @param marketCarts
     */
    public SimultaneousGame(List<IFlexibleRules> markets) {
        this.markets = markets;
    }
    
    public List<IFlexibleRules> getMarkets() {
      return this.markets; 
    }

}
