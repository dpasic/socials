package hr.vspr.dpasic.socials.rest.dao.mongo;

import hr.vspr.dpasic.socials.rest.dao.mongo.factory.MongoDBFactory;
import hr.vspr.dpasic.socials.rest.model.UserCredential;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public class UsersCredentialsMongo {

    private static final Logger LOG = LoggerFactory.getLogger(UsersCredentialsMongo.class);
    public static final String USERS_CREDENTIALS_COLLECTION = "usersCredentialsCollection";

    private MongoOperations mongoOps;

    public UsersCredentialsMongo() {
        this.mongoOps = MongoDBFactory.getMongoOperations();
    }

    public UserCredential getUserCredentialForUsername(String username) {
        LOG.debug(String.format("Retrieving user credential for username %s from mongodb.", username));
        Query query = new Query(Criteria.where("_id").is(username));
        return this.mongoOps.findOne(query, UserCredential.class,
                USERS_CREDENTIALS_COLLECTION);
    }

    public void addUserCredential(UserCredential userCredential) {
        LOG.debug("Adding user credential to mongodb.");
        mongoOps.insert(userCredential, USERS_CREDENTIALS_COLLECTION);
    }

    public void updateUserCredentialForNewCredential(UserCredential newUserCredential) {
        LOG.debug(String.format("Updating user credential for username %s in mongodb.",
                newUserCredential.getUsername()));
        Update update = new Update();
        update.set("hashedPassword", newUserCredential.getHashedPassword());

        Query query = new Query(Criteria.where("_id").is(newUserCredential.getUsername()));
        this.mongoOps.updateFirst(query, update, UserCredential.class,
                USERS_CREDENTIALS_COLLECTION);
    }

    public void deleteUserCredentialForUsername(String username) {
        LOG.debug(String.format("Deleting user credential for username %s from mongodb.", username));
        Query query = new Query(Criteria.where("_id").is(username));
        this.mongoOps.remove(query, UserCredential.class,
                USERS_CREDENTIALS_COLLECTION);
    }
}
