package irish.bla.webfluxdemo.webclient;

import irish.bla.webfluxdemo.dto.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

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

    @Test
    void stepVerifierTest() {
        Mono<Response> monoResponse = webClient
                .get()
                .uri("reactive-math/square/{input}", 5)
                .retrieve()
                .bodyToMono(Response.class);

        StepVerifier.create(monoResponse)
                .expectNextMatches(r -> r.getOutput() == 25)
                .verifyComplete();
    }
}
