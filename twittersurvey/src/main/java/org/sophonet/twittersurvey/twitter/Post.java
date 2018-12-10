package org.sophonet.twittersurvey.twitter;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;


/**
 *
 * @author Dardenne
 */
public class Post {
    void post() {
        // The factory instance is re-useable and thread safe.
        Twitter twitter = TwitterFactory.getSingleton();

        //System.out.println ("Successfully updated the status to [" + status.getText() + "].");
    }
}
