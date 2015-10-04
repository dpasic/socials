package rest.call.credentials.post;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class PostUserCredential {

    public static void main(String[] args) {

        String username = String.format("user%d", (int)(Math.random() * 99999));
        username = "user66461";
        String rawPassword = "pa$$w0rd";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<String>(rawPassword, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    String.format("http://localhost:8080/socials-rest/users/%s/credential", username),
                    HttpMethod.POST, entity, String.class);

            System.out.println(response.toString());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
