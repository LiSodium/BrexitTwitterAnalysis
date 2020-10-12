package forexhistoricaldata;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * JUnit tests for ForexUtils class methods
 * 
 * @author Lina
 *
 */
class ForexUtilsTest {

	@Test
	void testGetRelevantDatapointsTime() {

		Calendar c1 = Calendar.getInstance();
		c1.setTimeInMillis(0);
		Calendar c2 = Calendar.getInstance();
		c2.setTimeInMillis(60000);
		Calendar c3 = Calendar.getInstance();
		c3.setTimeInMillis(70000);
		Calendar c4 = Calendar.getInstance();
		c4.setTimeInMillis(120000);
		Calendar c5 = Calendar.getInstance();
		c5.setTimeInMillis(200000);

		ForexDatapoint d1 = new ForexDatapoint(c1, 1);
		ForexDatapoint d2 = new ForexDatapoint(c2, 1);
		ForexDatapoint d3 = new ForexDatapoint(c3, 1);
		ForexDatapoint d4 = new ForexDatapoint(c4, 1);
		ForexDatapoint d5 = new ForexDatapoint(c5, 1);

		List<ForexDatapoint> datapoints = new ArrayList<ForexDatapoint>();
		datapoints.add(d1);
		datapoints.add(d2);
		datapoints.add(d3);
		datapoints.add(d4);
		datapoints.add(d5);

		List<ForexDatapoint> datapointsWithinTimeCriteria = ForexUtils.getRelevantDatapoints(datapoints, 1, 0);

		assertEquals(3, datapointsWithinTimeCriteria.size());
	}

	@Test
	void testGetRelevantDatapointsPercentChange() {

		Calendar c1 = Calendar.getInstance();
		c1.setTimeInMillis(0);
		Calendar c2 = Calendar.getInstance();
		c2.setTimeInMillis(60000);
		Calendar c3 = Calendar.getInstance();
		c3.setTimeInMillis(120000);
		Calendar c4 = Calendar.getInstance();
		c4.setTimeInMillis(180000);
		Calendar c5 = Calendar.getInstance();
		c5.setTimeInMillis(240000);

		ForexDatapoint d1 = new ForexDatapoint(c1, 1);
		ForexDatapoint d2 = new ForexDatapoint(c2, 1.1);
		ForexDatapoint d3 = new ForexDatapoint(c3, 1);
		ForexDatapoint d4 = new ForexDatapoint(c4, 1.25);
		ForexDatapoint d5 = new ForexDatapoint(c5, 2);

		List<ForexDatapoint> datapoints = new ArrayList<ForexDatapoint>();
		datapoints.add(d1);
		datapoints.add(d2);
		datapoints.add(d3);
		datapoints.add(d4);
		datapoints.add(d5);

		List<ForexDatapoint> datapointsWithinPercentCriteria = ForexUtils.getRelevantDatapoints(datapoints, 1,
				0.10);

		assertEquals(3, datapointsWithinPercentCriteria.size());
	}
}
