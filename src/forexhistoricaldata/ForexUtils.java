package forexhistoricaldata;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class that contains methods to run forex data point queries
 * 
 * @author Lina
 *
 */
public class ForexUtils {
	public final static long millisecondsInMinute = 60000;

	/**
	 * Gets a list of forex data points that satisfy the specified percent change
	 * and time interval conditions
	 * 
	 * @param datapoints
	 *            all data points
	 * @param timeInterval
	 *            increment to next data point
	 * @param percentChange
	 *            percent difference threshold between two data points
	 * @return the data points we are interested in
	 */
	public static List<ForexDatapoint> getRelevantDatapoints(List<ForexDatapoint> datapoints,
			int timeInterval, double percentChange) {
		List<ForexDatapoint> filteredDatapoints = new ArrayList<ForexDatapoint>();

		if (datapoints.size() > 0) {
			Collections.sort(datapoints);
			ForexDatapoint beginningPoint = datapoints.get(0);

			for (ForexDatapoint datapoint : datapoints) {
				boolean meetsTimeThreshold = datapoint.getDate().getTimeInMillis()
						- beginningPoint.getDate().getTimeInMillis() >= timeInterval * millisecondsInMinute;

				if (meetsTimeThreshold) {
					boolean meetsPercentThreshold;

					if (percentChange > 0) {
						meetsPercentThreshold = (datapoint.getOpenBidQuote() - beginningPoint.getOpenBidQuote())
								/ beginningPoint.getOpenBidQuote() >= percentChange / 100;
					} else {
						meetsPercentThreshold = (datapoint.getOpenBidQuote() - beginningPoint.getOpenBidQuote())
								/ beginningPoint.getOpenBidQuote() <= percentChange / 100;
					}

					if (meetsPercentThreshold) {
						filteredDatapoints.add(datapoint);
					}

					beginningPoint = datapoint;
				}
			}
		}
		return filteredDatapoints;
	}

}
