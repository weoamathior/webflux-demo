package irish.bla.webfluxdemo;

import irish.bla.webfluxdemo.dto.MultiplyRequestDto;
import irish.bla.webfluxdemo.dto.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lecture49PostRequestTest extends BaseTest{
    @Autowired
    private WebClient webClient;

    @Test
    public void postTest() {
        Mono<Response> responseMono = this.webClient
                .post()
                .uri("reactive-math/multiply")
                .bodyValue(buildRequestDto(5, 2))
                .retrieve()
                .bodyToMono(Response.class)
                .doOnNext(System.out::println);

        StepVerifier.create(responseMono)
                .expectNextCount(1)
                .verifyComplete();

    }

    private MultiplyRequestDto buildRequestDto(int a, int b) {
        MultiplyRequestDto multiplyRequestDto = new MultiplyRequestDto();
        multiplyRequestDto.setFirst(a);
        multiplyRequestDto.setSecond(b);
        return multiplyRequestDto;
    }
}
