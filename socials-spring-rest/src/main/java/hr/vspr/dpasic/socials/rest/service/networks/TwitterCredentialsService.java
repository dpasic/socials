package hr.vspr.dpasic.socials.rest.service.networks;

import hr.vspr.dpasic.socials.rest.dao.mongo.networks.TwitterCredentialsMongo;
import hr.vspr.dpasic.socials.rest.dao.mongo.factory.MongoDBFactory;
import hr.vspr.dpasic.socials.rest.model.twitter.TwitterCredentials;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Service;

@Service("twitterCredentialsService")
public class TwitterCredentialsService implements DisposableBean {

    private TwitterCredentialsMongo twitterCredentialsMongo;

    public TwitterCredentialsService() {
        this.twitterCredentialsMongo = new TwitterCredentialsMongo();
    }

    public TwitterCredentials getTwitterCredentialsForUsername(String username) {
        return twitterCredentialsMongo.getTwitterCredentialsForUsername(username);
    }

    public void addOrUpdateTwitterCredentials(TwitterCredentials twitterCredentials) {
        String username = twitterCredentials.getUsername();

        /* If Twitter credentials for given username does not exist */
        if (!doExistCredentialsForUsername(username)) {
            twitterCredentialsMongo.addTwitterCredentials(twitterCredentials);
        } else {
            twitterCredentialsMongo.updateTwitterCredentialsForNewCredentials(twitterCredentials);
        }
    }

    private boolean doExistCredentialsForUsername(String username) {
        return (twitterCredentialsMongo.getTwitterCredentialsForUsername(username) != null);
    }

    public void deleteTwitterCredentialsForUsername(String username) {
        twitterCredentialsMongo.deleteTwitterCredentialsForUsername(username);
    }

    public TwitterCredentialsMongo getTwitterCredentialsMongo() {
        return twitterCredentialsMongo;
    }

    public void setTwitterCredentialsMongo(TwitterCredentialsMongo twitterCredentialsMongo) {
        this.twitterCredentialsMongo = twitterCredentialsMongo;
    }

    @Override
    public void destroy() throws Exception {
        MongoDBFactory.close();
    }
}
