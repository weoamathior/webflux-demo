package irish.bla.webfluxdemo;

import irish.bla.webfluxdemo.dto.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class Lecture51BadRequestTest extends BaseTest{

    @Autowired
    WebClient webClient;

    @Test
    void fluxStreamTest() {
        Flux<Response> monoResponse = webClient
                .get()
                .uri("reactive-math/square/{input}/throw", 5)
                .retrieve()
                .bodyToFlux(Response.class)
                .doOnNext(System.out::println)
                .doOnError(err -> System.out.println(err.getMessage()));

        StepVerifier.create(monoResponse)
                .verifyError(WebClientResponseException.BadRequest.class);

    }
}
