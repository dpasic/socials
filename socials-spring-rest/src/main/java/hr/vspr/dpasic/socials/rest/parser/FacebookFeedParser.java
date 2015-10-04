package hr.vspr.dpasic.socials.rest.parser;

import facebook4j.Post;
import facebook4j.ResponseList;
import hr.vspr.dpasic.socials.rest.model.SocialNetwork;
import hr.vspr.dpasic.socials.rest.model.SocialStatus;

import java.util.ArrayList;
import java.util.List;

public class FacebookFeedParser {

    public static List<SocialStatus> parseFacebookFeedFromPosts(ResponseList<Post> facebookPosts) {
        List<SocialStatus> facebookFeed = new ArrayList<SocialStatus>();

        int counter = 1;
        for (Post facebookPost : facebookPosts) {
            if (counter > 20) {
                break;
            }
            facebookFeed.add(parseSocialStatusFromPost(facebookPost));
            counter++;
        }

        return facebookFeed;
    }

    private static SocialStatus parseSocialStatusFromPost(Post facebookPost) {
        SocialStatus socialStatus = new SocialStatus();

        socialStatus.setText(facebookPost.getMessage());
        socialStatus.setCreatedAt(facebookPost.getCreatedTime());
        socialStatus.setCreatorName(facebookPost.getFrom().getName());
        if (facebookPost.getPicture() != null) {
            socialStatus.setImageUrl(String.format("%s://%s%s", facebookPost.getPicture().getProtocol(),
                    facebookPost.getPicture().getAuthority(), facebookPost.getPicture().getFile()));
        }
        socialStatus.setSocialNetwork(SocialNetwork.FACEBOOK);

        return socialStatus;
    }
}
