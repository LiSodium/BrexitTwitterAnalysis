package utilities;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

import forexhistoricaldata.ForexDatapoint;
import twitter4jintegration.Tweet;

class FileIOTest {

	@Test
	void testImportTweetFileReader() throws FileNotFoundException {
		String filename = "testTweetFile.txt";
		ArrayList<Tweet> tweets = FileIO.importTweetFileReader(filename);
		assertEquals(2, tweets.size());
		assertEquals(
				"RT @jpraffarin: La situation au levant vue par les dirigeants iraniens. Verbatim sur mon blog https://t.co/SSjl7TQ5KX",
				tweets.get(0).getText());
		assertEquals("Tue Dec 22 00:57:19 PST 2015", tweets.get(0).getCreatedAt().getTime().toString());
		assertEquals(10, tweets.get(0).getRetweetCount());
		assertEquals("Michel Barnier", tweets.get(0).getUser().getName());
		assertEquals(679224183038038017l, tweets.get(0).getTweetId());
	}

	@Test
	void testFailedImportTweetFileReader() {
		String filename = "not_a_real_file";
		Exception exception = null;

		try {
			FileIO.importTweetFileReader(filename);
		} catch (FileNotFoundException e) {
			exception = e;
		}
		assertEquals(true, exception instanceof FileNotFoundException);
	}

	@Test
	void testImportWordFileReader() throws FileNotFoundException {
		String filename = "testWordFile.txt";
		HashSet<String> words = FileIO.importWordFileReader(filename);
		assertEquals(true, words.contains("Hello world!"));
		assertEquals(true, words.contains("Goodbye world!"));
		assertEquals(2, words.size());
	}

	@Test
	void testFailedImportWordFileReader() {
		String filename = "not_a_real_file";
		Exception exception = null;

		try {
			FileIO.importTweetFileReader(filename);
		} catch (FileNotFoundException e) {
			exception = e;
		}
		assertEquals(true, exception instanceof FileNotFoundException);
	}

	@Test
	void testImportForexDataFileReader() throws FileNotFoundException {
		String filename = "testForexDataFile.csv";
		ArrayList<ForexDatapoint> datapoints = FileIO.importForexDataFileReader(filename);
		assertEquals(2, datapoints.size());
		assertEquals(1.473350, datapoints.get(1).getOpenBidQuote());
	}

	@Test
	void testFailedImportForexDataFileReader() {
		String filename = "not_a_real_file";
		Exception exception = null;

		try {
			FileIO.importTweetFileReader(filename);
		} catch (FileNotFoundException e) {
			exception = e;
		}
		assertEquals(true, exception instanceof FileNotFoundException);
	}
}
