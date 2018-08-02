package org.sophonet.twittersurvey.twitter;

import java.util.List;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 *
 * @author Dardenne
 */
public class Configuration {

    private static Configuration configuration = null;
    private Twitter twitter;

    public Twitter getTwitter() {
        return twitter;
    }

    public static Configuration getInstance() {

        if (null == configuration) {
            configuration = new Configuration();
        }
        return configuration;
    }

    private Configuration() {
        init();
    }

    private void init() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("DVe8uago19kQcTVWmQNOnu6Eh")
                .setOAuthConsumerSecret("mdFRLwFoXpYvfrCtC9M5hOznttBsLhczNZc8Qod0ZM0UnpyYk6")
                .setOAuthAccessToken("2225304290-bwEoIT1iZDVgyHyBoLHBvennS7rkhh2UtR39xIB")
                .setOAuthAccessTokenSecret("01DxemoaLhCRDq2Thut9m9lcoyei5crLkChNDGZNmM4tx")
                .setTweetModeExtended(true);
        TwitterFactory tf = new TwitterFactory(cb.build());
        twitter = tf.getInstance();
        //showHomeTimeline(twitter);
    }

    private static void showHomeTimeline(Twitter twitter) {

        List<Status> statuses = null;
        try {
            statuses = twitter.getHomeTimeline();

            System.out.println("Showing home timeline.");

            for (Status status : statuses) {
                System.out.println(status.getUser().getName() + ":" + status.getText());
                String url = "https://twitter.com/" + status.getUser().getScreenName() + "/status/"
                        + status.getId();
                System.out.println("Above tweet URL : " + url);
            }
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }

}
