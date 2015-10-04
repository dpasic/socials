package hr.vspr.dpasic.socials.rest.dao.mongo.networks;

import hr.vspr.dpasic.socials.rest.dao.mongo.factory.MongoDBFactory;
import hr.vspr.dpasic.socials.rest.model.instagram.InstagramCredentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public class InstagramCredentialsMongo {

    private static final Logger LOG = LoggerFactory.getLogger(InstagramCredentialsMongo.class);
    public static final String INSTAGRAM_CREDENTIALS_COLLECTION = "instagramCredentialsCollection";

    private MongoOperations mongoOps;

    public InstagramCredentialsMongo() {
        this.mongoOps = MongoDBFactory.getMongoOperations();
    }

    public InstagramCredentials getInstagramCredentialsForUsername(String username) {
        LOG.debug(String.format("Retrieving Instagram credentials for username %s from mongodb.", username));
        Query query = new Query(Criteria.where("_id").is(username));
        return this.mongoOps.findOne(query, InstagramCredentials.class,
                INSTAGRAM_CREDENTIALS_COLLECTION);
    }

    public void addInstagramCredentials(InstagramCredentials instagramCredentials) {
        LOG.debug("Adding Instagram credentials to mongodb.");
        mongoOps.insert(instagramCredentials, INSTAGRAM_CREDENTIALS_COLLECTION);
    }

    public void updateInstagramCredentialsForNewCredential(InstagramCredentials newInstagramCredentials) {
        LOG.debug(String.format("Updating Instagram credentials for username %s in mongodb.",
                newInstagramCredentials.getUsername()));
        Update update = new Update();
        update.set("accessToken", newInstagramCredentials.getAccessToken());

        Query query = new Query(Criteria.where("_id").is(newInstagramCredentials.getUsername()));
        this.mongoOps.updateFirst(query, update, InstagramCredentials.class,
                INSTAGRAM_CREDENTIALS_COLLECTION);
    }

    public void deleteInstagramCredentialsForUsername(String username) {
        LOG.debug(String.format("Deleting Instagram credentials for username %s from mongodb.", username));
        Query query = new Query(Criteria.where("_id").is(username));
        this.mongoOps.remove(query, InstagramCredentials.class,
                INSTAGRAM_CREDENTIALS_COLLECTION);
    }
}
