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
 * @author Lina
 *
 */
public class RunFxRateTweetAnalysis {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		try {
			ArrayList<Tweet> tweets = FileIO.importTweetFileReader("tweets.txt");
			boolean isValidPercentInput = false;
			double percent = 0.0;
			while (!isValidPercentInput) {
				System.out.println("Enter a percent threshold to search: (e.g. .01 indicates .01%):");
				String percentInput = in.nextLine();
				try {
					percent = Double.parseDouble(percentInput);
					isValidPercentInput = true;
				}

				catch (NumberFormatException e) {
					System.out.println("Invalid input");
				}
			}
			boolean isValidTimeInput = false;
			int minutes = 0;
			while (!isValidTimeInput) {
				System.out.println("Enter a time interval to search (in minutes):");
				String minutesInput = in.nextLine();
				try {
					minutes = Integer.parseInt(minutesInput);
					isValidTimeInput = true;
				} catch (NumberFormatException e) {
					System.out.println("Invalid input");
				}
			}

			ArrayList<ForexDatapoint> allDatapoints = FileIO.importForexDataFileReader("DAT_ASCII_GBPUSD_M1_2016.csv");
			ArrayList<ForexDatapoint> relevantDatapoints = ForexUtils.getRelevantDatapoints(allDatapoints, 1, percent);

			ArrayList<Tweet> tweetsOfInterest = FxRateTweetAnalysis.getRelevantTweets(relevantDatapoints, tweets, minutes);

			HashSet<String> commonWordSet = FileIO.importWordFileReader("words.txt");
			HashMap<String, Integer> wordMap = FxRateTweetAnalysis.getWordFrequencyMap(tweetsOfInterest, commonWordSet);
			ArrayList<Entry<String, Integer>> wordList = FxRateTweetAnalysis.getSortedWordFrequencyList(wordMap);
			FileIO.exportResultsFileWriter(wordList, "results.txt");

			System.out.println("Success! Please see results.txt for the analysis.");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		in.close();

	}

}
