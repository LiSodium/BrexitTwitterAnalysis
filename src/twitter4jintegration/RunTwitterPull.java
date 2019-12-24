package twitter4jintegration;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map.Entry;

import twitter4j.TwitterException;

/**
 * Class that contains main method to run Twitter query. Pulls tweet objects and stores them into a JSON file.
 * @author Lina
 *
 */
public class RunTwitterPull {

	public static void main(String[] args) {
		try {
			ReadConfigMain config = new ReadConfigMain();
			ArrayList<TwitterUser> users = TwitterUtils.getUsers(config.getScreennames());
			ArrayList<Tweet> tweets = TwitterUtils.getTweets(users, config.getSinceId(), config.getMaxId());
			FileIO.exportFileWriter(tweets, "tweets.txt");
		} catch (TwitterException | FileNotFoundException e) {
			e.printStackTrace();
		}

	}
}
