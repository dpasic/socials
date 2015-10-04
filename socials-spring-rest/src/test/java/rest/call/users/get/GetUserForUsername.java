package rest.call.users.get;

import hr.vspr.dpasic.socials.rest.model.User;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class GetUserForUsername {

    public static void main(String[] args) {

        String username = "user48045";

        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<User> userResponse = restTemplate
                    .exchange(String.format("http://localhost:8080/socials-rest/users/%s", username),
                            HttpMethod.GET, null,
                            new ParameterizedTypeReference<User>() {
                            });
            User user = userResponse.getBody();

            System.out.println(user.toString());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
