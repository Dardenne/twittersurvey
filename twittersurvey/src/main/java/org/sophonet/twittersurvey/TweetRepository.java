package org.sophonet.twittersurvey;

import org.sophonet.twittersurvey.data.Tweet;

import org.springframework.data.mongodb.repository.MongoRepository;


public interface TweetRepository extends MongoRepository<Tweet, String> {
    public TweetRepository findById(String id);
}
