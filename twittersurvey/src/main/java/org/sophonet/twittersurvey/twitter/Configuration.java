package org.sophonet.twittersurvey.twitter;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

import twitter4j.conf.ConfigurationBuilder;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Dardenne
 */
public class Configuration {
    private static Configuration configuration = null;
    private Twitter twitter;

    private Configuration() {
        init();
    }

    public Twitter getTwitter() {
        return twitter;
    }

    public static Configuration getInstance() {
        if (null == configuration) {
            configuration = new Configuration();
        }

        return configuration;
    }

    /* Keys and tokens

    Keys, secret keys and access tokens management.
    Consumer API keys

    Hf4vYiqtR41wWau8XSgZ9LV0M (API key)

    1sPCb7bGwL9f3K3mH1PLksDtFWpQ37sNipr21g0lPLN3PcO0or (API secret key)
    Access token & access token secret

    1062392432610996224-umoITMZ1aGbO565r4i0qJFLX1qK8NU (Access token)

    0ir5krbJOdq7QHsKPDtMfu9hdnKtREVRGAjTwmmDG88FN (Access token secret)

    Read and write (Access level)
     */
    private void init() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        /* ddardenne */
        /*
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("DVe8uago19kQcTVWmQNOnu6Eh")
                .setOAuthConsumerSecret("mdFRLwFoXpYvfrCtC9M5hOznttBsLhczNZc8Qod0ZM0UnpyYk6")
                .setOAuthAccessToken("2225304290-bwEoIT1iZDVgyHyBoLHBvennS7rkhh2UtR39xIB")
                .setOAuthAccessTokenSecret("01DxemoaLhCRDq2Thut9m9lcoyei5crLkChNDGZNmM4tx")
                .setTweetModeExtended(true);
        */

        /* rumainjean */
        cb.setDebugEnabled(true).setOAuthConsumerKey("Hf4vYiqtR41wWau8XSgZ9LV0M")
          .setOAuthConsumerSecret("1sPCb7bGwL9f3K3mH1PLksDtFWpQ37sNipr21g0lPLN3PcO0or")
          .setOAuthAccessToken("1062392432610996224-umoITMZ1aGbO565r4i0qJFLX1qK8NU")
          .setOAuthAccessTokenSecret("0ir5krbJOdq7QHsKPDtMfu9hdnKtREVRGAjTwmmDG88FN")
          .setTweetModeExtended(true);

        TwitterFactory tf = new TwitterFactory(cb.build());
        twitter = tf.getInstance();

        //showHomeTimeline(twitter);
    }

    @SuppressWarnings("unused")
    private static void showHomeTimeline(Twitter twitter) {
        List<Status> statuses = null;

        try {
            statuses = twitter.getHomeTimeline();

            System.out.println("Showing home timeline.");

            for (Status status : statuses) {
                System.out.println(status.getUser().getName() + ":" +
                    status.getText());

                String url = "https://twitter.com/" +
                    status.getUser().getScreenName() + "/status/" +
                    status.getId();
                System.out.println("Above tweet URL : " + url);
            }
        } catch (TwitterException e) {
            Logger.getLogger(Search.class.getName())
                  .log(Level.SEVERE, "showHomeTimeline", e);
        }
    }
}
