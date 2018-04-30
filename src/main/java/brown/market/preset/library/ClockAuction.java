package brown.market.preset.library;

import brown.market.preset.AbsMarketPreset;
import brown.rules.library.ClockActivity;
import brown.rules.library.ClockAllocation;
import brown.rules.library.ClockInformation;
import brown.rules.library.ClockOuterTC;
import brown.rules.library.ClockPayment;
import brown.rules.library.ClockQuery;
import brown.rules.library.NoRecordKeeping;
import brown.rules.library.OneGrouping;
import brown.rules.library.OneShotTermination;

public class ClockAuction extends AbsMarketPreset {
  double increment;
  
  /**
   * some of these are guesses.
   * need to pass in the market internal state, 
   * or otherwise delete it from this constructor.
   */
  public ClockAuction(double increment) {
    super(new ClockAllocation(),
        new ClockPayment(),
        new ClockQuery(), 
        new OneGrouping(),
        new ClockActivity(increment),
        new ClockInformation(),
        new OneShotTermination(), 
        new ClockOuterTC(),
        new NoRecordKeeping());
    this.increment = increment;
  }

  @Override
  public AbsMarketPreset copy() {
    return new ClockAuction(this.increment);
  }

}