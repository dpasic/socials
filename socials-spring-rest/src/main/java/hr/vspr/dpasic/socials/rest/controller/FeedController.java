package hr.vspr.dpasic.socials.rest.controller;

import hr.vspr.dpasic.socials.rest.model.SocialNetworksSelection;
import hr.vspr.dpasic.socials.rest.model.SocialStatus;
import hr.vspr.dpasic.socials.rest.model.SocialsPost;
import hr.vspr.dpasic.socials.rest.proxy.FeedProxy;
import hr.vspr.dpasic.socials.rest.proxy.StatusProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/feed")
public class FeedController {

    private static final Logger LOG = LoggerFactory.getLogger(FeedController.class);

    @Autowired
    private FeedProxy feedProxy;

    @Autowired
    private StatusProxy statusProxy;

    /* example mapping /feed/user/socialNetworks?list=twitter+facebook */
    @RequestMapping(value = "/{username}/socialNetworks", method = RequestMethod.GET)
    public ResponseEntity<List<SocialStatus>> getFeedForUsernameAndSelectedNetworks(@PathVariable String username,
                                               @RequestParam("list") String socialNetworksList) {

        List<SocialStatus> socialStatuses = new ArrayList<SocialStatus>();
        String[] socialNetworksNames = socialNetworksList.split("\\+");

        try {
            for (String socialNetworkName : socialNetworksNames) {
                socialNetworkName = socialNetworkName.trim().toLowerCase();
                /* Every social network will retrieve max 20 newest statuses in feed */
                switch (socialNetworkName) {
                    case "facebook":
                        socialStatuses.addAll(feedProxy.getFacebookFeedForUsername(username));
                        break;
                    case "twitter":
                        socialStatuses.addAll(feedProxy.getTwitterFeedForUsername(username));
                        break;
                    case "instagram":
                        socialStatuses.addAll(feedProxy.getInstagramFeedForUsername(username));
                        break;
                }
            }
            Collections.sort(socialStatuses);

        } catch (Exception e) {
            LOG.warn(e.getMessage());
            return new ResponseEntity<List<SocialStatus>>(HttpStatus.BAD_REQUEST);
        }

        LOG.info(String.format("Retrieved feed for username %s from social networks APIs.", username));
        return new ResponseEntity<List<SocialStatus>>(socialStatuses, HttpStatus.OK);
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.POST)
    public ResponseEntity<String> postStatusesForUsernameAndSelectedNetworks(@PathVariable String username,
                                          @RequestBody SocialsPost socialsPost) {

        String statusText = socialsPost.getPostText();
        SocialNetworksSelection socialNetworksSelection = socialsPost.getSocialNetworksSelection();

        try {
            if (socialNetworksSelection.isFacebook()) {
                statusProxy.postStatusToFacebookForUsername(statusText, username);
            }
            if (socialNetworksSelection.isTwitter()) {
                statusProxy.postStatusToTwitterForUsername(statusText, username);
            }

        } catch (Exception e) {
            LOG.warn(e.getMessage());
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }

        LOG.info(String.format("Posted statuses for username %s to selected social networks.", username));
        return new ResponseEntity<String>(HttpStatus.OK);
    }
}
