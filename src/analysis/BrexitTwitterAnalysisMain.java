package analysis;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

import forexhistoricaldata.ForexDatapoint;
import forexhistoricaldata.ForexUtils;
import twitter4jintegration.Tweet;
import utilities.FileIO;

/**
 * Main class to execute application
 * 
 * @author Lina
 *
 */
public class BrexitTwitterAnalysisMain {

	private JFrame frame;
	private JTextField textFieldPercentChange;
	private JTextField textFieldTimeFrame;

	/**
	 * Compiles a list of words from tweets that meet the criteria of forex data
	 * points specified.
	 * 
	 * @param percentChange
	 *            percent difference between two sequential forex data points
	 * @param timeFrame
	 *            time frame since forex data points from which to query tweets
	 * @param tweetFilename
	 *            import file of tweets
	 * @param wordFilename
	 *            import file of common words to exclude from analysis
	 * @param dataFilename
	 *            import file of forex data points
	 * @return list of words meeting the criteria specified
	 * @throws FileNotFoundException
	 */
	public static ArrayList<Entry<String, Integer>> runFxRateTweetAnalysis(double percentChange, int timeFrame,
			String tweetFilename, String wordFilename, String dataFilename) throws FileNotFoundException {
		ArrayList<Tweet> tweets = FileIO.importTweetFileReader(tweetFilename);
		ArrayList<ForexDatapoint> allDatapoints = FileIO.importForexDataFileReader(dataFilename);
		ArrayList<ForexDatapoint> relevantDatapoints = ForexUtils.getRelevantDatapoints(allDatapoints, 1,
				percentChange);

		ArrayList<Tweet> tweetsOfInterest = FxRateTweetAnalysis.getRelevantTweets(relevantDatapoints, tweets,
				timeFrame);

		HashSet<String> commonWordSet = FileIO.importWordFileReader(wordFilename);
		HashMap<String, Integer> wordMap = FxRateTweetAnalysis.getWordFrequencyMap(tweetsOfInterest, commonWordSet);
		ArrayList<Entry<String, Integer>> wordList = FxRateTweetAnalysis.getSortedWordFrequencyList(wordMap);

		return wordList;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BrexitTwitterAnalysisMain window = new BrexitTwitterAnalysisMain();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public BrexitTwitterAnalysisMain() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 233, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frame.getContentPane().setLayout(springLayout);

		JLabel lblPercentChange = new JLabel("Percent Change:");
		lblPercentChange.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 11));
		lblPercentChange.setHorizontalAlignment(SwingConstants.LEFT);
		frame.getContentPane().add(lblPercentChange);

		textFieldPercentChange = new JTextField();
		springLayout.putConstraint(SpringLayout.EAST, lblPercentChange, -20, SpringLayout.WEST, textFieldPercentChange);
		springLayout.putConstraint(SpringLayout.WEST, textFieldPercentChange, 133, SpringLayout.WEST,
				frame.getContentPane());
		textFieldPercentChange.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 11));
		frame.getContentPane().add(textFieldPercentChange);
		textFieldPercentChange.setColumns(10);

		JLabel lblTimeFrame = new JLabel("Time Frame:");
		springLayout.putConstraint(SpringLayout.SOUTH, lblPercentChange, -9, SpringLayout.NORTH, lblTimeFrame);
		springLayout.putConstraint(SpringLayout.NORTH, lblTimeFrame, 63, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblTimeFrame, 0, SpringLayout.WEST, lblPercentChange);
		lblTimeFrame.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 11));
		frame.getContentPane().add(lblTimeFrame);

		textFieldTimeFrame = new JTextField();
		springLayout.putConstraint(SpringLayout.SOUTH, textFieldPercentChange, -8, SpringLayout.NORTH,
				textFieldTimeFrame);
		springLayout.putConstraint(SpringLayout.WEST, textFieldTimeFrame, 44, SpringLayout.EAST, lblTimeFrame);
		springLayout.putConstraint(SpringLayout.SOUTH, textFieldTimeFrame, 0, SpringLayout.SOUTH, lblTimeFrame);
		springLayout.putConstraint(SpringLayout.NORTH, textFieldTimeFrame, -1, SpringLayout.NORTH, lblTimeFrame);
		springLayout.putConstraint(SpringLayout.EAST, textFieldTimeFrame, -30, SpringLayout.EAST,
				frame.getContentPane());
		textFieldTimeFrame.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 11));
		frame.getContentPane().add(textFieldTimeFrame);
		textFieldTimeFrame.setColumns(10);

		JTextPane textPaneResult = new JTextPane();
		springLayout.putConstraint(SpringLayout.EAST, textFieldPercentChange, 0, SpringLayout.EAST, textPaneResult);
		springLayout.putConstraint(SpringLayout.NORTH, textPaneResult, 157, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, textPaneResult, 30, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, textPaneResult, -24, SpringLayout.SOUTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, textPaneResult, -30, SpringLayout.EAST, frame.getContentPane());
		frame.getContentPane().add(textPaneResult);

		JLabel lblSearchQuery = new JLabel("Search Query");
		springLayout.putConstraint(SpringLayout.NORTH, textFieldPercentChange, 9, SpringLayout.SOUTH, lblSearchQuery);
		springLayout.putConstraint(SpringLayout.NORTH, lblSearchQuery, 10, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, lblSearchQuery, 30, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblSearchQuery, 0, SpringLayout.WEST, lblPercentChange);
		lblSearchQuery.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 16));
		frame.getContentPane().add(lblSearchQuery);

		JLabel lblResult = new JLabel("Result:");
		springLayout.putConstraint(SpringLayout.WEST, lblResult, 0, SpringLayout.WEST, textPaneResult);
		springLayout.putConstraint(SpringLayout.SOUTH, lblResult, -6, SpringLayout.NORTH, textPaneResult);
		lblResult.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 11));
		frame.getContentPane().add(lblResult);

		JButton btnRun = new JButton("Run");
		btnRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				double percentChange = 0.0;
				int timeFrame = 0;
				int topResult = 10;
				String printToScreen = "";
				try {
					percentChange = Double.parseDouble(textFieldPercentChange.getText());
					timeFrame = Integer.parseInt(textFieldTimeFrame.getText());
					ArrayList<Entry<String, Integer>> result = runFxRateTweetAnalysis(percentChange, timeFrame,
							"tweets.txt", "words.txt", "DAT_ASCII_GBPUSD_M1_2016.csv");
					if (topResult > result.size()) {
						topResult = result.size();
					}
					for (int i = 0; i < topResult; i++) {
						if (!printToScreen.equals("")) {
							printToScreen += "\n";
						}
						printToScreen += result.get(i).getKey();
					}
					textPaneResult.setText(printToScreen);
					FileIO.exportResultsFileWriter(result,
							"results_" + Calendar.getInstance().getTimeInMillis() + ".txt");
					JOptionPane.showMessageDialog(null, "Success! Please see outputted file for further analysis.");
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "Invalid input. Please try again.");
				} catch (FileNotFoundException e) {
					JOptionPane.showMessageDialog(null,
							"Import files not found. Please review the filename(s) and try again.");
				}
			}
		});
		springLayout.putConstraint(SpringLayout.NORTH, btnRun, 16, SpringLayout.SOUTH, lblTimeFrame);
		springLayout.putConstraint(SpringLayout.WEST, btnRun, 0, SpringLayout.WEST, lblPercentChange);
		springLayout.putConstraint(SpringLayout.SOUTH, btnRun, 36, SpringLayout.SOUTH, lblTimeFrame);
		springLayout.putConstraint(SpringLayout.EAST, btnRun, 0, SpringLayout.EAST, lblTimeFrame);
		btnRun.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 10));
		frame.getContentPane().add(btnRun);
	}
}
