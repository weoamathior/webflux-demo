package irish.bla.webfluxdemo;

import irish.bla.webfluxdemo.dto.InputFailedValidationResponse;
import irish.bla.webfluxdemo.dto.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lecture52ExchangeTest extends BaseTest{

    @Autowired
    WebClient webClient;

    // exchange == retrieve + additional information - e.g. http status code
    @Test
    void badRequestTest() {
        Mono<Object> monoResponse = webClient
                .get()
                .uri("reactive-math/square/{input}/throw", 5)
                .exchangeToMono(this::exchange)
                .doOnNext(System.out::println)
                .doOnError(err -> System.out.println(err.getMessage()));

        StepVerifier.create(monoResponse)
                .expectNextCount(1)
                .verifyComplete();

    }

    public void setWebClient(WebClient webClient) {
        this.webClient = webClient;
    }

    private Mono<Object> exchange(ClientResponse cr) {

        if (cr.statusCode().value() == 400) {
            return cr.bodyToMono(InputFailedValidationResponse.class);
        }
        return cr.bodyToMono(Response.class);
    }
}
