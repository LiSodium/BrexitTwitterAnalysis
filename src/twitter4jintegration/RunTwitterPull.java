package twitter4jintegration;

import java.io.FileNotFoundException;
import java.util.List;

import twitter4j.TwitterException;
import utilities.FileIO;

/**
 * Class that contains main method to run Twitter query. Pulls tweet objects and
 * stores them into a JSON file.
 * 
 * @author Lina
 *
 */
public class RunTwitterPull {

	public static void main(String[] args) {
		try {
			ReadConfigMain config = new ReadConfigMain();
			List<TwitterUser> users = TwitterUtils.getUsers(config.getScreennames());
			List<Tweet> tweets = TwitterUtils.getTweets(users, config.getSinceId(), config.getMaxId());
			FileIO.exportFileWriter(tweets, "tweets.txt");
		} catch (TwitterException | FileNotFoundException e) {
			e.printStackTrace();
		}

	}
}
