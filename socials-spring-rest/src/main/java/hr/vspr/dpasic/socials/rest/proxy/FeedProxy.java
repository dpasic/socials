package hr.vspr.dpasic.socials.rest.proxy;

import com.sola.instagram.InstagramSession;
import com.sola.instagram.model.Media;
import com.sola.instagram.util.PaginatedCollection;
import facebook4j.Facebook;
import facebook4j.Post;
import facebook4j.ResponseList;
import facebook4j.auth.AccessToken;
import hr.vspr.dpasic.socials.rest.model.SocialStatus;
import hr.vspr.dpasic.socials.rest.model.facebook.FacebookCredentials;
import hr.vspr.dpasic.socials.rest.model.instagram.InstagramCredentials;
import hr.vspr.dpasic.socials.rest.model.twitter.TwitterCredentials;
import hr.vspr.dpasic.socials.rest.oauth.factory.FacebookAppFactory;
import hr.vspr.dpasic.socials.rest.oauth.factory.TwitterAppFactory;
import hr.vspr.dpasic.socials.rest.parser.FacebookFeedParser;
import hr.vspr.dpasic.socials.rest.parser.InstagramFeedParser;
import hr.vspr.dpasic.socials.rest.parser.TwitterFeedParser;
import hr.vspr.dpasic.socials.rest.service.networks.FacebookCredentialsService;
import hr.vspr.dpasic.socials.rest.service.networks.InstagramCredentialsService;
import hr.vspr.dpasic.socials.rest.service.networks.TwitterCredentialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import twitter4j.Status;
import twitter4j.Twitter;

import java.util.List;

@Component("feedProxy")
public class FeedProxy {

    @Autowired
    private FacebookCredentialsService facebookCredentialsService;

    @Autowired
    private TwitterCredentialsService twitterCredentialsService;

    @Autowired
    private InstagramCredentialsService instagramCredentialsService;

    public List<SocialStatus> getFacebookFeedForUsername(String username) throws Exception {
        List<SocialStatus> facebookFeed;

        FacebookCredentials facebookCredentials = facebookCredentialsService.getFacebookCredentialsForUsername(username);
        Facebook facebook = FacebookAppFactory.getFacebookInstance();
        AccessToken accessTokenObj = new AccessToken(facebookCredentials.getAccessToken());

        facebook.setOAuthAccessToken(accessTokenObj);
        ResponseList<Post> facebookPosts = facebook.getFeed();
        facebookFeed = FacebookFeedParser.parseFacebookFeedFromPosts(facebookPosts);

        return facebookFeed;
    }

    public List<SocialStatus> getTwitterFeedForUsername(String username) throws Exception {
        List<SocialStatus> twitterFeed;

        TwitterCredentials twitterCredentials = twitterCredentialsService.getTwitterCredentialsForUsername(username);
        Twitter twitter = TwitterAppFactory.getTwitterInstance();
        twitter4j.auth.AccessToken accessTokenObj = new twitter4j.auth.AccessToken(twitterCredentials.getAccessToken(), twitterCredentials.getAccessTokenSecret());

        twitter.setOAuthAccessToken(accessTokenObj);
        twitter4j.ResponseList<Status> twitterStatuses = twitter.getUserTimeline();
        twitterFeed = TwitterFeedParser.parseTwitterFeedFromStatuses(twitterStatuses);

        return twitterFeed;
    }

    public List<SocialStatus> getInstagramFeedForUsername(String username) throws Exception {
        List<SocialStatus> instagramFeed;

        InstagramCredentials instagramCredentials = instagramCredentialsService.getInstagramCredentialsForUsername(username);
        com.sola.instagram.auth.AccessToken accessToken = new com.sola.instagram.auth.AccessToken(instagramCredentials.getAccessToken());

        InstagramSession instagramSession = new InstagramSession(accessToken);
        PaginatedCollection<Media> instagramMedias = instagramSession.getFeed();
        instagramFeed = InstagramFeedParser.parseInstagramFeedFromMedias(instagramMedias);

        return instagramFeed;
    }
}
