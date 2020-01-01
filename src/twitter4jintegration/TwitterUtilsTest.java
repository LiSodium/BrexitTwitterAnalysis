package twitter4jintegration;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;

import twitter4j.TwitterException;
/**
 * JUnit tests for TwitterUtil class
 * @author Lina
 *
 */
class TwitterUtilsTest {

	@Test
	void testGetUser() {
		String screennames = "not_a_real_user_CIS591";

		Exception exception = null;

		try {
			TwitterUtils.getUsers(screennames);
		} catch (TwitterException e) {
			exception = e;
		}

		assertEquals(true, exception instanceof TwitterException);
	}

	@Test
	void testGetUsers() throws TwitterException {
		String screennames = "theresa_may,BorisJohnson,jeremycorbyn";

		ArrayList<TwitterUser> users = TwitterUtils.getUsers(screennames);

		assertEquals("Theresa May", users.get(0).getName());
		assertEquals("", users.get(0).getLocation());

		assertEquals("Boris Johnson", users.get(1).getName());
		assertEquals("United Kingdom", users.get(1).getLocation());

		assertEquals("Jeremy Corbyn", users.get(2).getName());
		assertEquals("UK", users.get(2).getLocation());
	}

	@Test
	void testGetTweetsSinceId() throws TwitterException {
		String screennames = "BorisJohnson";

		ArrayList<TwitterUser> users = TwitterUtils.getUsers(screennames);

		ArrayList<Tweet> tweets = TwitterUtils.getTweets(users, 678912676806946816l, 688912676806946816l);

		assertNotNull(tweets.get(0).getText());
		assertNotNull(tweets.get(tweets.size() - 1).getText());
	}

	@Test
	void testGetTweetSinceId() throws TwitterException {
		String screennames = "BorisJohnson";

		ArrayList<TwitterUser> users = TwitterUtils.getUsers(screennames);

		ArrayList<Tweet> tweets = TwitterUtils.getTweets(users, 678912676806946815l, 678912676806946817l);

		boolean textFound = tweets.get(0).getText().contains("What if winter is over – for ever?");
		assertEquals(true, textFound);
	}

	@Test
	void testGetEmptyTweetsSinceId() throws TwitterException {
		String screennames = "BorisJohnson";

		ArrayList<TwitterUser> users = TwitterUtils.getUsers(screennames);
		ArrayList<Tweet> tweets = TwitterUtils.getTweets(users, 1l, 1l);

		assertEquals(0, tweets.size());
	}

}
