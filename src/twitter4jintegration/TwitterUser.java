package twitter4jintegration;

/**
 * A wrapper class created for twitter4j.User that stores user information used in Twitter analysis.
 * @author Lina 
 * 
 */
public class TwitterUser {
	private String screenName;
	private String name;
	private int followerCount;
	private String location;

	public TwitterUser(String screenName, String name, int followerCount, String location) {
		this.screenName = screenName;
		this.name = name;
		this.followerCount = followerCount;
		this.location = location;
	}

	/**
	 * Overload constructor that initializes a User instance based on information in
	 * twitter4j.User object
	 * 
	 * @param user
	 *            twitter4j.User object
	 */
	public TwitterUser(twitter4j.User user) {
		this(user.getScreenName(), user.getName(), user.getFollowersCount(), user.getLocation());
	}

	public String getScreenName() {
		return screenName;
	}

	public String getName() {
		return name;
	}

	public int getFollowerCount() {
		return followerCount;
	}

	public String getLocation() {
		return location;
	}
}
