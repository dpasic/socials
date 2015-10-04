package hr.vspr.dpasic.socials.rest.oauth.factory;

import hr.vspr.dpasic.socials.rest.config.EnvironmentUtils;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterAppFactory {

    private TwitterAppFactory() {
    }

    public static Twitter getTwitterInstance() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setOAuthConsumerKey(EnvironmentUtils.getProperty("twitter.oauth.consumerKey"))
                .setOAuthConsumerSecret(EnvironmentUtils.getProperty("twitter.oauth.consumerSecret"));

        TwitterFactory twitterFactory = new TwitterFactory(cb.build());
        return twitterFactory.getInstance();
    }
}
