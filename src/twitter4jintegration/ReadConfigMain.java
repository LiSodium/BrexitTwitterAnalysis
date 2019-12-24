package twitter4jintegration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Class that contains methods to read values from properties file
 * @author Lina
 *
 */
public class ReadConfigMain {
	private String screennames;
	private Long sinceId;
	private Long maxId;
	private InputStream inputStream;

	public ReadConfigMain() {

		Properties properties = new Properties();
		String propertiesFilename = "config.properties";

		try {
			inputStream = getClass().getClassLoader().getResourceAsStream(propertiesFilename);
			properties.load(inputStream);
			screennames = properties.getProperty("screennames");
			sinceId = Long.parseLong(properties.getProperty("sinceId"));
			maxId = Long.parseLong(properties.getProperty("maxId"));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public Long getSinceId() {
		return sinceId;
	}

	public Long getMaxId() {
		return maxId;
	}

	public String getScreennames() {
		return screennames;
	}
}
