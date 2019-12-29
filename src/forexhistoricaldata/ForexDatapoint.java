package forexhistoricaldata;

import java.util.Date;

/**
 * A wrapper class created for storing forex currency historical data points.
 * 
 * @author Lina
 *
 */
public class ForexDatapoint {
	private Date date;
	private double openBidQuote;

	public ForexDatapoint(Date date, double openBidQuote) {
		this.date = date;
		this.openBidQuote = openBidQuote;
	}

	public Date getDate() {
		return date;
	}

	public double getOpenBidQuote() {
		return openBidQuote;
	}

}
