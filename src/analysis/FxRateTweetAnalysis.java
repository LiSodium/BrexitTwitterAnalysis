package analysis;

import java.util.ArrayList;


import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;

import forexhistoricaldata.ForexDatapoint;
import forexhistoricaldata.ForexUtils;
import twitter4jintegration.Tweet;

/**
 * Class that integrates the FXRate and Twitter classes. Performs analysis of tweets
 * @author Lina
 *
 */
public class FxRateTweetAnalysis {

	/**
	 * Compiles a list of tweets that were created within a specified time interval
	 * around specified times of interest.
	 * 
	 * @param timesOfInterest
	 *            ordered list of times when the FX rate had a significant change
	 * @param allTweets
	 *            ordered list of all tweets from relevant politicians
	 * @param timeIntervalInMinutes
	 *            interval of time from the times of interest where tweets should be
	 *            collected
	 * @return list of tweets created around the times when the FX rate changed
	 *         significantly
	 */
	public static ArrayList<Tweet> getRelevantTweets(ArrayList<ForexDatapoint> timesOfInterest, ArrayList<Tweet> allTweets,
			int timeIntervalInMinutes) {
		ArrayList<Tweet> relevantTweets = new ArrayList<Tweet>();
		int timeIndex = 0;
		int tweetIndex = 0;
		/*
		 * 1. Check datapoint against tweet
		 * 2. If the tweet is within the given time interval, add it to list, check the next tweet
		 * 3. If the tweet is in a greater time interval (meaning the datapoint is too small), go to the next datapoint
		 * 4. If the tweet is in a less time interval (meaning the datapoint is too big), go to the next tweet
		 * 5. when there are no more tweets & dates to compare, exit loop
		 * */
		
		while(timeIndex < timesOfInterest.size() && tweetIndex < allTweets.size()) {
			long timeDifference = allTweets.get(tweetIndex).getCreatedAt().getTimeInMillis() - timesOfInterest.get(timeIndex).getDate().getTimeInMillis(); 
			long timeIntervalInMillis = timeIntervalInMinutes * ForexUtils.millisecondsInMinute;
			
			if(Math.abs(timeDifference) <= timeIntervalInMillis) {
				relevantTweets.add(allTweets.get(tweetIndex));
				tweetIndex++;
			}
			else if(timeDifference < 0){
				tweetIndex++;
			}
			else {
				timeIndex++;
			}
		}
		return relevantTweets;
	}

	/**
	 * Compiles a map of words from tweets in a specified time interval excluded from a common word set
	 * 
	 * @param tweets
	 *            list of tweet objects
	 * @return map of words and their frequencies
	 */
	public static HashMap<String, Integer> getWordFrequencyMap(ArrayList<Tweet> tweets, HashSet<String> commonWordSet) {
		HashMap<String, Integer> wordFrequencyMap = new HashMap<String, Integer>();
		for (Tweet tweet : tweets) {
			String tweetText = tweet.getText();
			String[] words = tweetText.split("[^A-Za-z0-9']+"); //alphanumeric characters only
			for (String word : words) {
				if (!word.equals("") && !commonWordSet.contains(word)) {
					word = word.toLowerCase();
					if (wordFrequencyMap.containsKey(word)) {
						wordFrequencyMap.put(word, wordFrequencyMap.get(word) + 1);
					} else {
						wordFrequencyMap.put(word, 1);
					}
				}
			}
		}
		return wordFrequencyMap;
	}

	/**
	 * Compiles a list of words from tweets in a specified time interval, and sorts
	 * the entries in descending order.
	 * 
	 * @param wordFrequencyMap
	 *            map of words and their frequencies
	 * @return list of words and their frequencies in descending order
	 */
	public static ArrayList<Entry<String, Integer>> getSortedWordFrequencyList(
			HashMap<String, Integer> wordFrequencyMap) {
		ArrayList<Entry<String, Integer>> entries = new ArrayList<Entry<String, Integer>>();
		for (Entry<String, Integer> entry : wordFrequencyMap.entrySet()) {
			entries.add(entry);
		}
		entries.sort(new Comparator<Entry<String, Integer>>() {
			public int compare(Entry<String, Integer> entry1, Entry<String, Integer> entry2) {
				return entry2.getValue() - entry1.getValue();
			}
		});
		return entries;
	}

}
