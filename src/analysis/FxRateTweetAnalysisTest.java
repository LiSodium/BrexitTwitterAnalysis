package analysis;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

import org.junit.jupiter.api.Test;

import forexhistoricaldata.ForexDatapoint;
import twitter4jintegration.Tweet;

/**
 * JUnit tests for FxRateTweetAnalysis class methods
 * 
 * @author Lina
 *
 */
class FxRateTweetAnalysisTest {
	@Test
	void testGetRelevantTweets() {

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
		Calendar c6 = Calendar.getInstance();
		c6.setTimeInMillis(300000);

		ForexDatapoint d1 = new ForexDatapoint(c2, 1);
		ForexDatapoint d2 = new ForexDatapoint(c3, 1);
		ForexDatapoint d3 = new ForexDatapoint(c4, 1);

		ArrayList<ForexDatapoint> datapoints = new ArrayList<ForexDatapoint>();
		datapoints.add(d1);
		datapoints.add(d2);
		datapoints.add(d3);

		Tweet t1 = new Tweet(null, c1.getTime(), 0l, null, 0l);
		Tweet t2 = new Tweet(null, c3.getTime(), 0l, null, 0l);
		Tweet t3 = new Tweet(null, c5.getTime(), 0l, null, 0l);
		Tweet t4 = new Tweet(null, c6.getTime(), 0l, null, 0l);

		ArrayList<Tweet> tweets = new ArrayList<Tweet>();
		tweets.add(t1);
		tweets.add(t2);
		tweets.add(t3);
		tweets.add(t4);

		ArrayList<Tweet> result = FxRateTweetAnalysis.getRelevantTweets(datapoints, tweets, 1);

		assertEquals(3, result.size());
	}

	@Test
	void testGetWordFrequencyMap() {
		ArrayList<Tweet> tweets = new ArrayList<Tweet>();
		Date today = new Date();
		Tweet t1 = new Tweet("The Cat is spotted.", today, 0l, null, 0l);
		Tweet t2 = new Tweet("Garfield is my favorite cat!", today, 0l, null, 0l);
		tweets.add(t1);
		tweets.add(t2);

		HashSet<String> commonWordSet = new HashSet<String>();
		commonWordSet.add("is");

		HashMap<String, Integer> wordFrequencyMap = FxRateTweetAnalysis.getWordFrequencyMap(tweets, commonWordSet);
		assertEquals(2, wordFrequencyMap.get("cat").intValue());
		assertEquals(false, wordFrequencyMap.containsKey("is"));
	}

	@Test
	void testGetSortedWordFrequencyList() {
		HashMap<String, Integer> wordFrequencyMap = new HashMap<String, Integer>();
		wordFrequencyMap.put("word1", 1);
		wordFrequencyMap.put("word2", 20);

		ArrayList<Entry<String, Integer>> wordList = FxRateTweetAnalysis.getSortedWordFrequencyList(wordFrequencyMap);
		assertEquals(20, wordList.get(0).getValue().intValue());
		assertEquals(1, wordList.get(wordList.size() - 1).getValue().intValue());
	}

}
