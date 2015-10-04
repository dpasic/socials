package hr.vspr.dpasic.socials.rest.dao.mongo.factory;

import java.net.UnknownHostException;

import hr.vspr.dpasic.socials.rest.config.EnvironmentUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;

public class MongoDBFactory {

	private static final Logger LOG = LoggerFactory.getLogger(MongoDBFactory.class);

	private static Mongo mongo;
	public static String dbName;

	private MongoDBFactory() {
	}

	public static Mongo getMongo() {
		LOG.debug("Retrieving MongoDB");
		if (mongo == null) {
			try {
				mongo = new MongoClient(EnvironmentUtils.getProperty("mongo.host"),
						Integer.parseInt(EnvironmentUtils.getProperty("mongo.port")));
				dbName = EnvironmentUtils.getProperty("mongo.dbname");

			} catch (UnknownHostException e) {
				LOG.error(e.toString());
			} catch (MongoException e) {
				LOG.error(e.toString());
			}
		}

		return mongo;
	}

	public static DB getDB(String dbname) {
		LOG.debug("Retrieving db: " + dbname);
		return getMongo().getDB(dbname);
	}

	public static DBCollection getCollection(String dbname, String collection) {
		LOG.debug("Retrieving collection: " + collection);
		return getDB(dbname).getCollection(collection);
	}

	public static MongoOperations getMongoOperations() {
		LOG.debug("Retrieving MongoOperations for db: " + dbName);
		return new MongoTemplate(getMongo(), dbName);

	}

	public static void close() throws Exception {
		if (mongo != null) {
			mongo.close();
		}
	}
}
