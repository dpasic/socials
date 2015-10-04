package hr.vspr.dpasic.socials.rest.parser;

import hr.vspr.dpasic.socials.rest.model.SocialNetwork;
import hr.vspr.dpasic.socials.rest.model.SocialStatus;
import twitter4j.ResponseList;
import twitter4j.Status;

import java.util.ArrayList;
import java.util.List;

public class TwitterFeedParser {

    public static List<SocialStatus> parseTwitterFeedFromStatuses(ResponseList<Status> twitterStatuses) {
        List<SocialStatus> twitterFeed = new ArrayList<SocialStatus>();

        for (Status twitterStatus : twitterStatuses) {
            twitterFeed.add(parseSocialStatusFromStatus(twitterStatus));
        }

        return twitterFeed;
    }

    private static SocialStatus parseSocialStatusFromStatus(Status twitterStatus) {
        SocialStatus socialStatus = new SocialStatus();

        socialStatus.setText(twitterStatus.getText());
        socialStatus.setCreatedAt(twitterStatus.getCreatedAt());
        socialStatus.setCreatorName(twitterStatus.getUser().getScreenName());
        socialStatus.setImageUrl(twitterStatus.getMediaEntities().equals(null) || twitterStatus.getMediaEntities().length == 0
                ? null : twitterStatus.getMediaEntities()[0].getMediaURL());
        socialStatus.setSocialNetwork(SocialNetwork.TWITTER);

        return socialStatus;
    }
}
