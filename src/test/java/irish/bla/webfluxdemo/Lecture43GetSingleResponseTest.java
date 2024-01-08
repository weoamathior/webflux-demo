package irish.bla.webfluxdemo;

import irish.bla.webfluxdemo.dto.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;

public class Lecture43GetSingleResponseTest extends BaseTest{
    @Autowired
    WebClient webClient;

    @Test

    void blockTest() {
        Response response = webClient
                .get()
                .uri("reactive-math/square/{input}", 5)
                .retrieve()
                .bodyToMono(Response.class)
                .block();
        System.out.println(response);
    }
}
