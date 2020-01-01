package forexhistoricaldata;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import utilities.FileIO;

class ForexUtilsTest {

	@Test
	void testFilterDatapoints() throws FileNotFoundException {
		String filename = "testForexDataFile.csv";
		
		ArrayList<ForexDatapoint> datapoints = FileIO.importForexDataFileReader(filename);
		ArrayList<ForexDatapoint> filteredTimeExact = ForexUtils.filterDatapoints(datapoints, 2, 0);
		ArrayList<ForexDatapoint> filteredTimeGreater = ForexUtils.filterDatapoints(datapoints, 10, 0);
		ArrayList<ForexDatapoint> filteredTimeLess = ForexUtils.filterDatapoints(datapoints, 0, 0);
		
		ArrayList<ForexDatapoint> filteredPercentExact = ForexUtils.filterDatapoints(datapoints, 2, 0);
		ArrayList<ForexDatapoint> filteredPercentGreater = ForexUtils.filterDatapoints(datapoints, 2, 0.10);
		ArrayList<ForexDatapoint> filteredPercentLess = ForexUtils.filterDatapoints(datapoints, 2, -0.10);
		
		assertEquals(1, filteredTimeExact.size());
		assertEquals(0, filteredTimeGreater.size());
		assertEquals(2, filteredTimeLess.size());
		
		assertEquals(1, filteredPercentExact.size());
		assertEquals(0, filteredPercentGreater.size());
		assertEquals(0, filteredPercentLess.size());
		
	}

}
