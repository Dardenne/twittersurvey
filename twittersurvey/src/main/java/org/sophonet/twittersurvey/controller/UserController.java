/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sophonet.twittersurvey.controller;

import org.sophonet.twittersurvey.data.TwitterUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.sophonet.twittersurvey.TwitterUserRepository;

/**
 *
 * @author Dardenne
 */
public class UserController {

    @Autowired
    private TwitterUserRepository userRepository;

    public void save(TwitterUser user) {
        userRepository.save(user);
    }

}
