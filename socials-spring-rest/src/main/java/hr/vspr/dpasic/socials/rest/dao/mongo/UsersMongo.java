package hr.vspr.dpasic.socials.rest.dao.mongo;

import hr.vspr.dpasic.socials.rest.dao.mongo.factory.MongoDBFactory;
import hr.vspr.dpasic.socials.rest.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class UsersMongo {

    private static final Logger LOG = LoggerFactory.getLogger(UsersMongo.class);
    public static final String USERS_COLLECTION = "usersCollection";

    private MongoOperations mongoOps;

    public UsersMongo() {
        this.mongoOps = MongoDBFactory.getMongoOperations();
    }

    public List<User> getAllUsers() {
        LOG.debug("Retrieving all users from mongodb.");
        return this.mongoOps.findAll(User.class, USERS_COLLECTION);
    }

    public User getUserForUsername(String username) {
        LOG.debug(String.format("Retrieving user with username %s from mongodb.", username));
        Query query = new Query(Criteria.where("_id").is(username));
        return this.mongoOps.findOne(query, User.class, USERS_COLLECTION);
    }

    public void addUser(User user) {
        LOG.debug("Adding user to mongodb.");
        mongoOps.insert(user, USERS_COLLECTION);
    }
}
