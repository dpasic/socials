package hr.vspr.dpasic.socials.rest.dao.mongo.networks;

import hr.vspr.dpasic.socials.rest.dao.mongo.factory.MongoDBFactory;
import hr.vspr.dpasic.socials.rest.model.facebook.FacebookCredentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public class FacebookCredentialsMongo {

    private static final Logger LOG = LoggerFactory.getLogger(FacebookCredentialsMongo.class);
    public static final String FACEBOOK_CREDENTIALS_COLLECTION = "facebookCredentialsCollection";

    private MongoOperations mongoOps;

    public FacebookCredentialsMongo() {
        this.mongoOps = MongoDBFactory.getMongoOperations();
    }

    public FacebookCredentials getFacebookCredentialsForUsername(String username) {
        LOG.debug(String.format("Retrieving Facebook credentials for username %s from mongodb.", username));
        Query query = new Query(Criteria.where("_id").is(username));
        return this.mongoOps.findOne(query, FacebookCredentials.class,
                FACEBOOK_CREDENTIALS_COLLECTION);
    }

    public void addFacebookCredentials(FacebookCredentials facebookCredentials) {
        LOG.debug("Adding Facebook credentials to mongodb.");
        mongoOps.insert(facebookCredentials, FACEBOOK_CREDENTIALS_COLLECTION);
    }

    public void updateFacebookCredentialsForNewCredential(FacebookCredentials newFacebookCredentials) {
        LOG.debug(String.format("Updating Facebook credentials for username %s in mongodb.",
                newFacebookCredentials.getUsername()));
        Update update = new Update();
        update.set("accessToken", newFacebookCredentials.getAccessToken());

        Query query = new Query(Criteria.where("_id").is(newFacebookCredentials.getUsername()));
        this.mongoOps.updateFirst(query, update, FacebookCredentials.class,
                FACEBOOK_CREDENTIALS_COLLECTION);
    }

    public void deleteFacebookCredentialsForUsername(String username) {
        LOG.debug(String.format("Deleting Facebook credentials for username %s from mongodb.", username));
        Query query = new Query(Criteria.where("_id").is(username));
        this.mongoOps.remove(query, FacebookCredentials.class,
                FACEBOOK_CREDENTIALS_COLLECTION);
    }
}
