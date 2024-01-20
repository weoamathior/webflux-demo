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
import reactor.core.publisher.Mono;

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
    void fluentAssertionTest() {
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
}
