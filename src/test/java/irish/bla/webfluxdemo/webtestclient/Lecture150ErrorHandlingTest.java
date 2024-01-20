package irish.bla.webfluxdemo.webtestclient;

import irish.bla.webfluxdemo.controller.ReactiveMathController;
import irish.bla.webfluxdemo.controller.ReactiveMathValidationController;
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

@WebFluxTest(ReactiveMathValidationController.class)
public class Lecture150ErrorHandlingTest {
    @Autowired
    WebTestClient webTestClient;

    @MockBean
    ReactiveMathService reactiveMathService;

    @Test
    void postTest() {
        Mockito.when(reactiveMathService.findSquare(Mockito.anyInt()))
                .thenReturn(Mono.just(new Response(1)));

        final String expectedMessage = "allowed range is 10 - 20";
        webTestClient
                .get().uri("/reactive-math/square/{input}/throw", 5)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.message").isEqualTo(expectedMessage);
    }
}
