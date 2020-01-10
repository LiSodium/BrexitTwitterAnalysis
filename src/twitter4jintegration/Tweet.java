package twitter4jintegration;

import java.util.Calendar;
import java.util.Date;

/**
 * A wrapper class created for twitter4j.Status that stores tweet information
 * used in Twitter analysis.
 * 
 * @author Lina
 * 
 */

public class Tweet implements Comparable<Object> {
	private String text;
	private Calendar createdAt;
	private long retweetCount;
	private TwitterUser user;
	private long tweetId;

	public Tweet(String text, Date createdAt, long retweetCount, TwitterUser user, long tweetId) {
		this.text = text;
		this.createdAt = Calendar.getInstance();
		this.createdAt.setTime(createdAt);
		this.retweetCount = retweetCount;
		this.user = user;
		this.tweetId = tweetId;
	}

	/**
	 * Overload constructor that initializes a Tweet instance based on information
	 * in twitter4j.Status object
	 * 
	 * @param user
	 *            twitter4j.Status object
	 */
	public Tweet(twitter4j.Status tweet, TwitterUser user) {
		this(tweet.getText(), tweet.getCreatedAt(), tweet.getRetweetCount(), user, tweet.getId());
	}

	public String getText() {
		return text;
	}

	public TwitterUser getUser() {
		return user;
	}

	public Calendar getCreatedAt() {
		return createdAt;
	}

	public long getRetweetCount() {
		return retweetCount;
	}

	public long getTweetId() {
		return tweetId;
	}

	/**
	 * Comparison method for Tweet objects using the creation date
	 * 
	 * @param other
	 *            Tweet object to compare
	 */

	public int compareTo(Object other) {
		Tweet otherTweet = (Tweet) other;
		if (this.getCreatedAt().compareTo(otherTweet.getCreatedAt()) == 0) {
			return 0;
		} else if (this.getCreatedAt().compareTo(otherTweet.getCreatedAt()) < 0) {
			return -1;
		}
		return 1;
	}

}
