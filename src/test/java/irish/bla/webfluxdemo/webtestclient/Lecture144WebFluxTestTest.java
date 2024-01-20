package irish.bla.webfluxdemo.webtestclient;

import irish.bla.webfluxdemo.controller.ReactiveMathController;
import irish.bla.webfluxdemo.dto.Response;
import irish.bla.webfluxdemo.service.ReactiveMathService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

/*
WebFluxTest doesn't create a lot of beans.
Need to create mocks
 */
@WebFluxTest(ReactiveMathController.class)
public class Lecture144WebFluxTestTest {
    @Autowired
    WebTestClient webTestClient;
    @MockBean
    ReactiveMathService reactiveMathService;

    @Test
    void singleResponseTest() {
        Mockito.when(reactiveMathService.findSquare(Mockito.anyInt()))
                .thenReturn(Mono.just(new Response(25)));
        webTestClient
                .get()
                .uri("/reactive-math/square/{input}", 5)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(Response.class)
                .value(b -> Assertions.assertThat(b.getOutput()).isEqualTo(25));

    }

    @Test
    void manyResponseTest() {
        Flux<Response> fluxRespones = Flux.range(1, 3)
                .map(Response::new);
        Mockito.when(reactiveMathService.multTable(Mockito.anyInt()))
                .thenReturn(fluxRespones);

        webTestClient
                .get()
                .uri("/reactive-math/table/{input}", 5)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Response.class)
                .hasSize(3);

    }
    @Test
    void streamingResponseTest() {
        Flux<Response> fluxRespones = Flux.range(1, 3)
                .map(Response::new)
                .delayElements(Duration.ofMillis(100));
        Mockito.when(reactiveMathService.multTable(Mockito.anyInt()))
                .thenReturn(fluxRespones);

        webTestClient
                .get()
                .uri("/reactive-math/table/{input}/stream", 5)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader().contentTypeCompatibleWith(MediaType.TEXT_EVENT_STREAM_VALUE)
                .expectBodyList(Response.class)
                .hasSize(3);

    }
}