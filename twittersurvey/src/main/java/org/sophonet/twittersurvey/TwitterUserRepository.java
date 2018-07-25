/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sophonet.twittersurvey;

import org.sophonet.twittersurvey.data.TwitterUser;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author Dardenne
 */
public interface TwitterUserRepository extends MongoRepository<TwitterUser, String> {

    public TwitterUser findById(String id);
}
