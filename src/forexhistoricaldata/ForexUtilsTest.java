package forexhistoricaldata;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;

import org.junit.jupiter.api.Test;

import utilities.FileIO;

class ForexUtilsTest {

	@Test
	void testGetRelevantDatapointsExactTime() {

		Calendar c1 = Calendar.getInstance();
		c1.setTimeInMillis(0);
		Calendar c2 = Calendar.getInstance();
		c2.setTimeInMillis(60000);

		ForexDatapoint d1 = new ForexDatapoint(c1, 1);
		ForexDatapoint d2 = new ForexDatapoint(c2, 1);

		ArrayList<ForexDatapoint> datapoints = new ArrayList<ForexDatapoint>();
		datapoints.add(d1);
		datapoints.add(d2);

		ArrayList<ForexDatapoint> datapointsWithinTimeCriteria = ForexUtils.getRelevantDatapoints(datapoints, 1, 0);

		assertEquals(1, datapointsWithinTimeCriteria.size());
	}
	
	@Test
	void testGetRelevantDatapointsGreaterTime() {

		Calendar c1 = Calendar.getInstance();
		c1.setTimeInMillis(0);
		Calendar c2 = Calendar.getInstance();
		c2.setTimeInMillis(60000);

		ForexDatapoint d1 = new ForexDatapoint(c1, 1);
		ForexDatapoint d2 = new ForexDatapoint(c2, 1);
		ArrayList<ForexDatapoint> datapoints = new ArrayList<ForexDatapoint>();
		datapoints.add(d1);
		datapoints.add(d2);

		ArrayList<ForexDatapoint> datapointsWithinTimeCriteria = ForexUtils.getRelevantDatapoints(datapoints, 10, 0);

		assertEquals(0, datapointsWithinTimeCriteria.size());
	}
	
	@Test
	void testGetRelevantDatapointsExactPercentChange() {

		Calendar c1 = Calendar.getInstance();
		c1.setTimeInMillis(0);
		Calendar c2 = Calendar.getInstance();
		c2.setTimeInMillis(60000);

		ForexDatapoint d1 = new ForexDatapoint(c1, 1);
		ForexDatapoint d2 = new ForexDatapoint(c2, 1.1);

		ArrayList<ForexDatapoint> datapoints = new ArrayList<ForexDatapoint>();
		datapoints.add(d1);
		datapoints.add(d2);

		ArrayList<ForexDatapoint> datapointsWithinPercentCriteria = ForexUtils.getRelevantDatapoints(datapoints, 1, 0.10);

		assertEquals(1, datapointsWithinPercentCriteria.size());
	}
	
	@Test
	void testGetRelevantDatapointsGreaterPercentChange() {

		Calendar c1 = Calendar.getInstance();
		c1.setTimeInMillis(0);
		Calendar c2 = Calendar.getInstance();
		c2.setTimeInMillis(60000);

		ForexDatapoint d1 = new ForexDatapoint(c1, 1);
		ForexDatapoint d2 = new ForexDatapoint(c2, 1.1);
		ArrayList<ForexDatapoint> datapoints = new ArrayList<ForexDatapoint>();
		datapoints.add(d1);
		datapoints.add(d2);

		ArrayList<ForexDatapoint> datapointsWithinPercentCriteria = ForexUtils.getRelevantDatapoints(datapoints, 1, 0.25);

		assertEquals(0, datapointsWithinPercentCriteria.size());
	}

}
