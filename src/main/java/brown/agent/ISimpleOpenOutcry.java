package brown.agent;

import brown.channels.library.AuctionChannel;

public interface ISimpleOpenOutcry {

  /**
   * Provides agent response to open outcry auction
   * @param channel - simple agent channel
   */
  public abstract void onSimpleOpenOutcry(AuctionChannel channel);

}