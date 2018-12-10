/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sophonet.twittersurvey.data;

import org.springframework.data.annotation.Id;

import java.util.Date;


/**
 *
 * @author Dardenne
 */
public class TwitterUser {
    @Id
    public String id;
    public Date lastLoaded;

    public TwitterUser() {
    }

    public TwitterUser(String id, Date lastLoaded) {
        this.id = id;
        this.lastLoaded = lastLoaded;
    }

    @Override
    public String toString() {
        return String.format("User[id = '%s', Last load date = '%s']", id,
            lastLoaded);
    }
}
