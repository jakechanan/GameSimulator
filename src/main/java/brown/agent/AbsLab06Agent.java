package brown.agent;

import java.util.Arrays;
import java.util.List;

import brown.accounting.library.Transaction;
import brown.bid.library.BidDirection;
import brown.bid.library.CancelBid;
import brown.bid.library.TwoSidedBid;
import brown.bidbundle.library.CancelBundle;
import brown.bidbundle.library.TwoSidedBidBundle;
import brown.channels.library.CallMarketChannel;
import brown.exceptions.AgentCreationException;
import brown.market.marketstate.library.OrderBook;
import brown.messages.library.BankUpdateMessage;
import brown.messages.library.CallMarketReportMessage;
import brown.messages.library.GameReportMessage;
import brown.messages.library.PredictionMarketReport;
import brown.messages.library.PrivateInformationMessage;
import brown.setup.library.CallMarketSetup;

public abstract class AbsLab06Agent extends AbsCallMarketAgent {
	private List<Transaction> ledger = Arrays.asList(new Transaction[0]);
	private OrderBook orderbook = null;

	public AbsLab06Agent(String host, int port) throws AgentCreationException {
		super(host, port, new CallMarketSetup());
	}
	
	public AbsLab06Agent(String host, int port, String name) throws AgentCreationException {
		super(host, port, new CallMarketSetup(), name);
	}
	
	@Override
	public void onCallMarket(CallMarketChannel channel) {
		this.orderbook = channel.getOrderBook();
		this.onMarketRequest(channel);
	}
	
	@Override
	public void onBankUpdate(BankUpdateMessage update) {
		double price = update.moniesChanged / ((double) update.quantity);
		int quantity = update.quantity == null ? 0 : (int) update.quantity.doubleValue();
		onTransaction(quantity, price);
	}
	
	@Override
	public void onGameReport(GameReportMessage gmReport) {
		if (gmReport instanceof PredictionMarketReport) {
			PredictionMarketReport pmReport = (PredictionMarketReport) gmReport;
			System.out.println("Real coin result: " + pmReport.getCoin());
		} else if (gmReport instanceof CallMarketReportMessage) {
			CallMarketReportMessage cmReport = (CallMarketReportMessage) gmReport;
			ledger.addAll(cmReport.getTransactions());
		}
	}
	
	@Override
	public void onPrivateInformation(PrivateInformationMessage privateInfo) {
		super.onPrivateInformation(privateInfo);
		onMarketStart();
	}
	
	public void buy(double price, int quantity, CallMarketChannel channel) {
		TwoSidedBid bid = new TwoSidedBid(BidDirection.BUY, price, quantity);
		channel.bid(this, new TwoSidedBidBundle(bid));
	}
	
	public void sell(double price, int quantity, CallMarketChannel channel) {
		TwoSidedBid bid = new TwoSidedBid(BidDirection.SELL, price, quantity);
		channel.bid(this, new TwoSidedBidBundle(bid));
	}
	
	public void cancel(double price, boolean buy, CallMarketChannel channel) {
		BidDirection dir = buy ? BidDirection.BUY : BidDirection.SELL;
		channel.bid(this, new CancelBundle(new CancelBid(dir, price)));
	}
	
	public boolean getCoin() {
		return this.coin;
	}
	
	public int getNumDecoys() {
		return this.numDecoys;
	}
	
	public List<Transaction> getLedger() {
		return this.ledger;
	}
	
	public OrderBook getOrderBook() {
		return this.orderbook;
	}
	
	public abstract void onMarketStart();
	public abstract void onMarketRequest(CallMarketChannel channel);
	public abstract void onTransaction(int quantity, double price);
}
