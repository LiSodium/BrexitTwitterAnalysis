package twitter4jintegration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Read import files and write spell-checked export files
 * @author Lina Zhu 
 *
 */
public class FileIO {
	private static Gson gson = new Gson();

	public static ArrayList<Tweet> importFileReader(String filename) throws FileNotFoundException {
		File importFile = new File(filename);
		Scanner importReader = new Scanner(importFile);
		String json = importReader.nextLine();
		ArrayList<Tweet> tweets = gson.fromJson(json, new TypeToken<ArrayList<Tweet>>(){}.getType());
		importReader.close();
		return tweets;
	}

	public static void exportFileWriter(ArrayList<Tweet> tweets, String filename) throws FileNotFoundException {
		PrintWriter exportFile;
		exportFile = new PrintWriter(filename);
		tweets.sort(null);
		String json = gson.toJson(tweets);
		exportFile.print(json);
		exportFile.close();
	}

	public static void exportResultsFileWriter(ArrayList<Entry<String, Integer>> wordList, String filename) throws FileNotFoundException {
		PrintWriter exportFile;
		exportFile = new PrintWriter(filename);
		String toPrint = "";
		for(Entry<String, Integer> word : wordList) {
			toPrint += word.getKey() + " : " + word.getValue() + "\n";
		}
		exportFile.print(toPrint);
		exportFile.close();
	}
}
