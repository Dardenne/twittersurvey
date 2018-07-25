/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sophonet.twittersurvey;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.sophonet.twittersurvey.twitter.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import twitter4j.TwitterException;

@Component
public class JobTwitterLoad implements org.quartz.Job {

    @Autowired
    public static Search search;

    public JobTwitterLoad() {
    }

    public void execute(JobExecutionContext context) throws JobExecutionException {
        List<String> list = List.of("_LesPatriotes", "f_philippot", "laurentwauquiez", "d_philippot59", "AymeuGaulliste", "alexiscorbiere");
        list.forEach((String user) -> {
            try {
                search.searchTweetsUser(user);
            } catch (TwitterException ex) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
}
