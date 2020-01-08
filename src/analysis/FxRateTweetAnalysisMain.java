package analysis;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;

import forexhistoricaldata.ForexDatapoint;
import forexhistoricaldata.ForexUtils;
import utilities.FileIO;
import twitter4jintegration.Tweet;

/**
 * Main class to run the program
 * 
 * @author Lina
 *
 */
public class FxRateTweetAnalysisMain {

	public static ArrayList<Entry<String, Integer>> runFxRateTweetAnalysis(double percentChange, int timeFrame,
			String tweetFilename, String wordFilename, String dataFilename) throws FileNotFoundException {
		ArrayList<Tweet> tweets = FileIO.importTweetFileReader(tweetFilename);
		ArrayList<ForexDatapoint> allDatapoints = FileIO.importForexDataFileReader(dataFilename);
		ArrayList<ForexDatapoint> relevantDatapoints = ForexUtils.getRelevantDatapoints(allDatapoints, 1,
				percentChange);

		ArrayList<Tweet> tweetsOfInterest = FxRateTweetAnalysis.getRelevantTweets(relevantDatapoints, tweets,
				timeFrame);

		HashSet<String> commonWordSet = FileIO.importWordFileReader(wordFilename);
		HashMap<String, Integer> wordMap = FxRateTweetAnalysis.getWordFrequencyMap(tweetsOfInterest, commonWordSet);
		ArrayList<Entry<String, Integer>> wordList = FxRateTweetAnalysis.getSortedWordFrequencyList(wordMap);

		return wordList;
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		try {
			boolean isValidPercentInput = false;
			double percentChange = 0.0;
			while (!isValidPercentInput) {
				System.out.println("Enter the percent change to search (e.g. .01 to indicate .01%):");
				String percentInput = in.nextLine();
				try {
					percentChange = Double.parseDouble(percentInput);
					isValidPercentInput = true;
				}

				catch (NumberFormatException e) {
					System.out.println("Invalid input");
				}
			}
			boolean isValidTimeInput = false;
			int timeFrame = 0;
			while (!isValidTimeInput) {
				System.out.println("Enter the time frame to search (e.g. 1 to indicate one minute):");
				String minutesInput = in.nextLine();
				try {
					timeFrame = Integer.parseInt(minutesInput);
					isValidTimeInput = true;
				} catch (NumberFormatException e) {
					System.out.println("Invalid input");
				}
			}

			ArrayList<Entry<String, Integer>> result = FxRateTweetAnalysisMain.runFxRateTweetAnalysis(percentChange,
					timeFrame, "tweets.txt", "words.txt", "DAT_ASCII_GBPUSD_M1_2016.csv");
			FileIO.exportResultsFileWriter(result, "results.txt");

			System.out.println("Success! Please see results.txt for the analysis.");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		in.close();

	}

}
