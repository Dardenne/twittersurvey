package org.sophonet.twittersurvey.twitter;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import twitter4j.Paging;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import org.sophonet.twittersurvey.TweetRepository;
import org.sophonet.twittersurvey.TwitterUserRepository;
import org.sophonet.twittersurvey.data.Tweet;
import org.sophonet.twittersurvey.data.TwitterUser;
import org.springframework.stereotype.Service;
import twitter4j.MediaEntity;

/**
 *
 * @author Dardenne
 */
@Service
public class Search {

    private static Search search = null;
    @Autowired
    private TweetRepository tweetRepository;
    @Autowired
    private TwitterUserRepository userRepository;
    private static final String OS = System.getProperty("os.name").toLowerCase();
    private static final String IMAGE_DIR = createImageDir();

    private Search() {
        createImageDir();
    }

    private static String createImageDir() {
        File imageDir = new File(isWindows() ? "d:/temp/twitter" : "/home/dardenne/temp/twitter");
        if (!imageDir.exists()) {
            imageDir.mkdir();
        }
        return imageDir.getAbsolutePath() + (isWindows() ? "\\" : "/");
    }

    public static Search getInstance() {
        if (null == search) {
            search = new Search();
        }
        return search;
    }

    public void searchTweets() throws TwitterException {
        Paging paging;
        Twitter twitter = Configuration.getInstance().getTwitter();
        Query q = new Query("LesPatriotes");
        q.setCount(100);
        QueryResult r = twitter.search(q);
        int totalTweets = 0;
        long maxID = -1;
        for (Status s : r.getTweets()) // Loop through all the tweets...
        {

            //	Increment our count of tweets retrieved
            totalTweets++;

            //	Keep track of the lowest tweet ID.  If you do not do this, you cannot retrieve multiple
            //	blocks of tweets...
            if (maxID == -1 || s.getId() < maxID) {
                maxID = s.getId();
            }

            //	Do something with the tweet....
            String tweet = String.format("At %s, @%-20s said:  %s\n",
                    s.getCreatedAt().toString(),
                    s.getUser().getScreenName(),
                    cleanText(s.getText()));
            System.out.println(tweet);

        }
    }

    public void searchTweetsUser(String user) throws TwitterException {
        Twitter twitter = Configuration.getInstance().getTwitter();
        Paging paging;
        List<Status> statuses;
        int i = 0;
        paging = new Paging(1, 500);
        userRepository.save(new TwitterUser(user, new Date()));
        statuses = twitter.getUserTimeline(user, paging);

        for (Status status : statuses) {
            try {

                String fmt = ++i + " " + status.getId() + " " + status.getCreatedAt() + "@" + status.getUser().getScreenName() + " - length = " + status.getText().length() + " - " + status.getText();
                Logger.getLogger(Search.class.getName()).log(Level.INFO, fmt);
                Tweet tweet = new Tweet(Long.toString(status.getId()), status.getCreatedAt(), status.getUser().getScreenName(), status.getUser().getName(), status.getText());
                tweetRepository.delete(Long.toString(status.getId()));
                tweet = tweetRepository.save(tweet);
                for (MediaEntity m : status.getMediaEntities()) {
                    try {
                        URL url = new URL(m.getMediaURL());
                        ByteArrayOutputStream out;
                        try (InputStream in = new BufferedInputStream(url.openStream())) {
                            out = new ByteArrayOutputStream();
                            byte[] buf = new byte[1024];
                            int n = 0;
                            while (-1 != (n = in.read(buf))) {
                                out.write(buf, 0, n);
                            }
                            out.close();
                        }
                        byte[] response = out.toByteArray();

                        try (FileOutputStream fos = new FileOutputStream(new File(IMAGE_DIR + tweet.getId()) + "." + getExtension(m.getType()))) {
                            fos.write(response);
                        }
                    } catch (IOException e) {
                        Logger.getLogger(Search.class.getName()).log(Level.SEVERE, user, e);
                    }
                }

            } catch (Exception e) {
                Logger.getLogger(Search.class.getName()).log(Level.SEVERE, user, e);
            }
        }

    }

    public static String cleanText(String text) {
        text = text.replace("\n", "\\n");
        text = text.replace("\t", "\\t");

        return text;
    }

    private String getExtension(String type) {
        switch (type) {
            case "photo":
                return "jpg";
            case "video":
                return "mp4";
            case "animated_gif":
                return "gif";
            default:
                return "err";
        }
    }

    private static boolean isWindows() {
        return (OS.contains("win"));
    }

    private void testGoogle() {
        var httpClient = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(URI.create("http://www.google.com")).build();
        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(System.out::println);
    }
}
