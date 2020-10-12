package utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import forexhistoricaldata.ForexDatapoint;
import twitter4jintegration.Tweet;

/**
 * Class that contains methods to import and export files.
 * 
 * @author Lina Zhu
 *
 */
public class FileIO {
	private static Gson gson = new Gson();

	public static List<Tweet> importTweetFileReader(String filename) throws FileNotFoundException {
		File importFile = new File(filename);
		Scanner importReader = new Scanner(importFile);
		String json = importReader.nextLine();
		List<Tweet> tweets = gson.fromJson(json, new TypeToken<List<Tweet>>() {
		}.getType());
		importReader.close();
		return tweets;
	}

	public static void exportFileWriter(List<Tweet> tweets, String filename) throws FileNotFoundException {
		PrintWriter exportFile;
		exportFile = new PrintWriter(filename);
		tweets.sort(null);
		String json = gson.toJson(tweets);
		exportFile.print(json);
		exportFile.close();
	}

	public static void exportResultsFileWriter(List<Entry<String, Integer>> wordList, String filename)
			throws FileNotFoundException {
		PrintWriter exportFile;
		exportFile = new PrintWriter(filename);
		String toPrint = "";
		for (Entry<String, Integer> word : wordList) {
			toPrint += word.getKey() + " : " + word.getValue() + "\n";
		}
		exportFile.print(toPrint);
		exportFile.close();
	}

	public static Set<String> importWordFileReader(String filename) throws FileNotFoundException {
		File importFile = new File(filename);
		Scanner importReader = new Scanner(importFile);
		Set<String> words = new HashSet<String>();
		while (importReader.hasNextLine()) {
			words.add(importReader.nextLine().toLowerCase());
		}
		importReader.close();
		return words;
	}

	public static List<ForexDatapoint> importForexDataFileReader(String filename) throws FileNotFoundException {
		File importFile = new File(filename);
		Scanner importReader = new Scanner(importFile);
		List<ForexDatapoint> datapoints = new ArrayList<ForexDatapoint>();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd HHmmssZ");

		while (importReader.hasNextLine()) {
			boolean isValidDatapoint = true;
			double openBidQuote = 0;
			Calendar date = Calendar.getInstance();
			String[] dataRow = importReader.nextLine().split(";");
			// Concatenate timezone to end of date
			dataRow[0] = dataRow[0] + "-0500";

			try {
				date.setTime(formatter.parse(dataRow[0]));
				openBidQuote = Double.parseDouble(dataRow[1]);
			} catch (ParseException | NumberFormatException e) {
				isValidDatapoint = false;
			}

			if (isValidDatapoint) {
				ForexDatapoint datapoint = new ForexDatapoint(date, openBidQuote);
				datapoints.add(datapoint);
			}
		}
		importReader.close();
		return datapoints;
	}
}
