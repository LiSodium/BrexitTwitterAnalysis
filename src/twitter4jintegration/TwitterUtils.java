package twitter4jintegration;

import java.util.ArrayList;
import java.util.List;

import twitter4j.Paging;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;

/**
 * Class that contains methods to run Twitter queries
 * @author Lina
 *
 */
public class TwitterUtils {

	private static Twitter twitter = new TwitterFactory().getInstance();

	/**
	 * Gets a list of users from the specified screennames
	 * 
	 * @param screennames
	 *            - the accounts we are interested in
	 * @return list of users with given screennames
	 * @throws TwitterException
	 */
	public static ArrayList<TwitterUser> getUsers(String screennames) throws TwitterException {
		ArrayList<TwitterUser> users = new ArrayList<TwitterUser>();

		for (User user : twitter.lookupUsers(screennames)) {
			TwitterUser twitterUser = new TwitterUser(user);
			users.add(twitterUser);
		}
		return users;
	}

	/**
	 * Gets a list of tweets for users between the specified dates
	 * 
	 * @param users
	 *            - the accounts of the tweets that we are interested in
	 * @param startDate
	 *            - only get tweets after this date (in YYYY-MM-DD format)
	 * @param endDate
	 *            - only get tweets before this date (in YYYY-MM-DD format)
	 * @return list of tweets by the given users between the given dates
	 * @throws TwitterException
	 */
	public static ArrayList<Tweet> getTweets(ArrayList<TwitterUser> users, String startDate, String endDate)
			throws TwitterException {
		ArrayList<Tweet> tweets = new ArrayList<Tweet>();

		for (TwitterUser user : users) {
			Query query = new Query("from:" + user.getScreenName());
			query.setSince(startDate);
			query.setUntil(endDate);

			QueryResult result = twitter.search(query);

			for (Status status : result.getTweets()) {
				Tweet tweet = new Tweet(status, user);
				tweets.add(tweet);
			}
		}
		return tweets;
	}
	
	
	/**
	 * Gets a list of tweets for users between the specified tweet ids
	 * 
	 * @param users
	 *            - the accounts of the tweets that we are interested in
	 * @param sinceId
	 *            - only get tweets after this tweet id
	 * @param maxId
	 *            - only get tweets before this tweet id
	 * @return list of tweets by the given users between the given tweet ids
	 * @throws TwitterException
	 */
	public static ArrayList<Tweet> getTweets(ArrayList<TwitterUser> users, long sinceId, long maxId)
			throws TwitterException {
		ArrayList<Tweet> tweets = new ArrayList<Tweet>();
		Paging page = new Paging();
		page.setCount(200);
		page.setSinceId(sinceId);

		for (TwitterUser user : users) {
			page.setMaxId(maxId);
			long nextTweetId = maxId;
			boolean hasMoreTweets = true;

			while (nextTweetId >= sinceId && hasMoreTweets) {
				List<Status> statuses = twitter.getUserTimeline(user.getScreenName(), page);
				if (statuses.size() < 200) {
					hasMoreTweets = false;
				}

				for (Status status : statuses) {
					Tweet tweet = new Tweet(status, user);
					tweets.add(tweet);
				}
				if (tweets.size() > 0) {
					nextTweetId = tweets.get(tweets.size() - 1).getTweetId() - 1;
				}
				page.setMaxId(nextTweetId);
			}
		}

		return tweets;
	}
}
