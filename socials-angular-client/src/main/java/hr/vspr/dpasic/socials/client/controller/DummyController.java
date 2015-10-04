package hr.vspr.dpasic.socials.client.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/oauth")
public class DummyController {

    @RequestMapping(value = "/prepare", method = RequestMethod.GET)
    public String preparingForAuthorizing() {
        return "Opening authorizing page...";
    }

    @RequestMapping(value = "/callback", method = RequestMethod.GET)
    public String callbackDummyCall() {
        return "Redirecting to application...";
    }
}
