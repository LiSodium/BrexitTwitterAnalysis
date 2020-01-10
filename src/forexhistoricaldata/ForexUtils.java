package forexhistoricaldata;

import java.util.ArrayList;
import java.util.Collections;

public class ForexUtils {
	public final static long millisecondsInMinute = 60000;

	public static ArrayList<ForexDatapoint> getRelevantDatapoints(ArrayList<ForexDatapoint> datapoints,
			int timeInterval, double percentChange) {
		ArrayList<ForexDatapoint> filteredDatapoints = new ArrayList<ForexDatapoint>();

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
