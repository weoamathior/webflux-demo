package irish.bla.webfluxdemo;

import irish.bla.webfluxdemo.dto.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class Lecture47GetMultiResponseTest extends BaseTest{

    @Autowired
    private WebClient webClient;


    @Test
    void stepVerifierTest() {
        Flux<Response> monoResponse = webClient
                .get()
                .uri("reactive-math/table/{input}", 5)
                .retrieve()
                .bodyToFlux(Response.class)
                .doOnNext(System.out::println);

        StepVerifier.create(monoResponse)
                .expectNextCount(10)
                .verifyComplete();
    }

    @Test
    void fluxStreamTest() {
        Flux<Response> monoResponse = webClient
                .get()
                .uri("reactive-math/table/{input}/stream", 5)
                .retrieve()
                .bodyToFlux(Response.class)
                .doOnNext(System.out::println);

        StepVerifier.create(monoResponse)
                .expectNextCount(10)
                .verifyComplete();
    }
}
