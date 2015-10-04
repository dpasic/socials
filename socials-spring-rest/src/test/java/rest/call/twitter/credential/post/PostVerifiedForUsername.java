package rest.call.twitter.credential.post;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class PostVerifiedForUsername {

    public static void main(String[] args) {

        String username = "user75096";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Access-Token", "");
        headers.set("Access-Token-Secret", "");

        HttpEntity<Void> entity = new HttpEntity<Void>(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    String.format("http://localhost:8080/socials-rest/twitter/auth/%s/verified", username),
                    HttpMethod.POST, entity, String.class);

            System.out.println(response.toString());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
