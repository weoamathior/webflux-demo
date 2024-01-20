package irish.bla.webfluxdemo.webtestclient;

import irish.bla.webfluxdemo.controller.ReactiveMathController;
import irish.bla.webfluxdemo.dto.MultiplyRequestDto;
import irish.bla.webfluxdemo.dto.Response;
import irish.bla.webfluxdemo.service.ReactiveMathService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@WebFluxTest(ReactiveMathController.class)
public class Lecture149PostTestTest {
    @Autowired
    WebTestClient webTestClient;

    @MockBean
    ReactiveMathService reactiveMathService;

    @Test
    void postTest() {
        Mockito.when(reactiveMathService.multiply(Mockito.any()))
                .thenReturn(Mono.just(new Response(1)));

        webTestClient
                .post()
                .uri("/reactive-math/multiply")
                .accept(MediaType.APPLICATION_JSON)
                .headers(m -> m.setBasicAuth("username","password"))
                .headers(m -> m.set("somekey","someval"))
                .bodyValue(new MultiplyRequestDto())
                .exchange()
                .expectStatus().is2xxSuccessful();
    }
}
