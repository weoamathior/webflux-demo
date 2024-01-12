package irish.bla.webfluxdemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.net.URI;
import java.util.Map;

public class Lecture53QueryParamsTest extends BaseTest{

    @Autowired
    WebClient webClient;

    @Test
    public void queryParamsTest() {
        URI uri = UriComponentsBuilder.fromUriString("http://localhost:8080/jobs/search?count={count}&page={page}")
                .build(10, 20);
        Flux<Integer> integerFlux = this.webClient
                .get()
                .uri(uri)
                .retrieve()
                .bodyToFlux(Integer.class)
                .doOnNext(System.out::println);

        StepVerifier.create(integerFlux)
                .expectNextCount(2)
                .verifyComplete();
    }

    // another way to do it
    @Test
    public void queryParamsTest2() {
//        URI uri = UriComponentsBuilder.fromUriString()
//                .build(10, 20);
        Flux<Integer> integerFlux = this.webClient
                .get()
                .uri(b -> b.path("jobs/search").query("count={count}&page={page}").build(10,20))
                .retrieve()
                .bodyToFlux(Integer.class)
                .doOnNext(System.out::println);

        StepVerifier.create(integerFlux)
                .expectNextCount(2)
                .verifyComplete();
    }


    @Test
    public void queryParamsTest3() {
//        URI uri = UriComponentsBuilder.fromUriString("http://localhost:8080/jobs/search?count={count}&page={page}")
//                .build(10, 20);

        Map<String, Integer> map = Map.of("count", 10, "page", 20);
        Flux<Integer> integerFlux = this.webClient
                .get()
                .uri(b -> b.path("jobs/search").query("count={count}&page={page}").build(map))
                .retrieve()
                .bodyToFlux(Integer.class)
                .doOnNext(System.out::println);

        StepVerifier.create(integerFlux)
                .expectNextCount(2)
                .verifyComplete();
    }
}
