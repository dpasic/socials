package rest.call.users.get;

import hr.vspr.dpasic.socials.rest.model.User;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class GetUsers {

    public static void main(String[] args) {

        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<List<User>> usersResponse = restTemplate
                    .exchange("http://localhost:8080/socials-rest/users/",
                            HttpMethod.GET, null,
                            new ParameterizedTypeReference<List<User>>() {
                            });
            List<User> users = usersResponse.getBody();

            for (User user : users) {
                System.out.println(user.toString());
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
