package hr.vspr.dpasic.socials.rest.dao.mongo.networks;

import hr.vspr.dpasic.socials.rest.dao.mongo.factory.MongoDBFactory;
import hr.vspr.dpasic.socials.rest.model.twitter.TwitterCredentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public class TwitterCredentialsMongo {

    private static final Logger LOG = LoggerFactory.getLogger(TwitterCredentialsMongo.class);
    public static final String TWITTER_CREDENTIALS_COLLECTION = "twitterCredentialsCollection";

    private MongoOperations mongoOps;

    public TwitterCredentialsMongo() {
        this.mongoOps = MongoDBFactory.getMongoOperations();
    }

    public TwitterCredentials getTwitterCredentialsForUsername(String username) {
        LOG.debug(String.format("Retrieving Twitter credentials for username %s from mongodb.", username));
        Query query = new Query(Criteria.where("_id").is(username));
        return this.mongoOps.findOne(query, TwitterCredentials.class,
                TWITTER_CREDENTIALS_COLLECTION);
    }

    public void addTwitterCredentials(TwitterCredentials twitterCredentials) {
        LOG.debug("Adding Twitter credentials to mongodb.");
        mongoOps.insert(twitterCredentials, TWITTER_CREDENTIALS_COLLECTION);
    }

    public void updateTwitterCredentialsForNewCredentials(TwitterCredentials newTwitterCredentials) {
        LOG.debug(String.format("Updating Twitter credentials for username %s in mongodb.",
                newTwitterCredentials.getUsername()));
        Update update = new Update();
        update.set("accessToken", newTwitterCredentials.getAccessToken());
        update.set("accessTokenSecret", newTwitterCredentials.getAccessTokenSecret());

        Query query = new Query(Criteria.where("_id").is(newTwitterCredentials.getUsername()));
        this.mongoOps.updateFirst(query, update, TwitterCredentials.class,
                TWITTER_CREDENTIALS_COLLECTION);
    }

    public void deleteTwitterCredentialsForUsername(String username) {
        LOG.debug(String.format("Deleting Twitter credentials for username %s from mongodb.", username));
        Query query = new Query(Criteria.where("_id").is(username));
        this.mongoOps.remove(query, TwitterCredentials.class,
                TWITTER_CREDENTIALS_COLLECTION);
    }
}
