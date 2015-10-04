package hr.vspr.dpasic.socials.rest.service.networks;

import hr.vspr.dpasic.socials.rest.dao.mongo.networks.FacebookCredentialsMongo;
import hr.vspr.dpasic.socials.rest.dao.mongo.factory.MongoDBFactory;
import hr.vspr.dpasic.socials.rest.model.facebook.FacebookCredentials;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Service;

@Service("facebookCredentialsService")
public class FacebookCredentialsService implements DisposableBean {

    private FacebookCredentialsMongo facebookCredentialsMongo;

    public FacebookCredentialsService() {
        this.facebookCredentialsMongo = new FacebookCredentialsMongo();
    }

    public FacebookCredentials getFacebookCredentialsForUsername(String username) {
        return facebookCredentialsMongo.getFacebookCredentialsForUsername(username);
    }

    public void addOrUpdateFacebookCredentials(FacebookCredentials facebookCredentials) {
        String username = facebookCredentials.getUsername();

        /* If Facebook credentials for given username does not exist */
        if (!doesExistCredentialsForUsername(username)) {
            facebookCredentialsMongo.addFacebookCredentials(facebookCredentials);
        } else {
            facebookCredentialsMongo.updateFacebookCredentialsForNewCredential(facebookCredentials);
        }
    }

    private boolean doesExistCredentialsForUsername(String username) {
        return (facebookCredentialsMongo.getFacebookCredentialsForUsername(username) != null);
    }

    public void deleteFacebookCredentialsForUsername(String username) {
        facebookCredentialsMongo.deleteFacebookCredentialsForUsername(username);
    }

    public FacebookCredentialsMongo getFacebookCredentialsMongo() {
        return facebookCredentialsMongo;
    }

    public void setFacebookCredentialsMongo(FacebookCredentialsMongo facebookCredentialsMongo) {
        this.facebookCredentialsMongo = facebookCredentialsMongo;
    }

    @Override
    public void destroy() throws Exception {
        MongoDBFactory.close();
    }
}
