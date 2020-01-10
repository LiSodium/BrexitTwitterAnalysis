package forexhistoricaldata;

import java.util.Calendar;

/**
 * A wrapper class created for storing forex currency historical data points.
 * 
 * @author Lina
 *
 */
public class ForexDatapoint implements Comparable<Object> {
	private Calendar date;
	private double openBidQuote;

	public ForexDatapoint(Calendar date, double openBidQuote) {
		this.date = date;
		this.openBidQuote = openBidQuote;
	}

	public Calendar getDate() {
		return date;
	}

	public double getOpenBidQuote() {
		return openBidQuote;
	}

	public int compareTo(Object other) {
		ForexDatapoint otherForexDatapoint = (ForexDatapoint) other;
		if (this.getDate().compareTo(otherForexDatapoint.getDate()) == 0) {
			return 0;
		} else if (this.getDate().compareTo(otherForexDatapoint.getDate()) < 0) {
			return -1;
		}
		return 1;
	}

}
