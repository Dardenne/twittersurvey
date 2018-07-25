/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sophonet.twittersurvey.data;

import java.util.Date;
import org.springframework.data.annotation.Id;

/**
 *
 * @author Dardenne
 */
public class Tweet {

    @Id
    public String _id;
    private Date createdAt;
    private String screenName;
    private String name;
    private String text;

    public Tweet() {

    }

    public Tweet(String _id, Date createdAt, String screenName, String name, String text) {
        this._id = _id;
        this.createdAt = createdAt;
        this.screenName = screenName;
        this.name = name;
        this.text = text;
    }

    @Override
    public String toString() {
        return "Tweet{" + "id=" + _id + ", createdAt=" + createdAt + ", screenName=" + screenName + ", name=" + name + ", text=" + text + '}';
    }

    public String getId() {
        return _id;
    }

    public void setId(String id) {
        this._id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
