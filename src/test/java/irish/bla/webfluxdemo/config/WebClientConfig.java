package irish.bla.webfluxdemo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
@Slf4j
@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8080")
//                .defaultHeaders(h -> h.setBasicAuth("username", "password"))
                .filter(this::sessionToken)

                .build();
    }

    private Mono<ClientResponse> sessionToken(ClientRequest request, ExchangeFunction ex) {
        // Authorization=Bearer some-long-json-jwt,
        log.info("generating session token");
        ClientRequest clientRequest = ClientRequest.from(request).headers(h -> h.setBearerAuth("some-long-json-jwt"))
                .build();

        return ex.exchange(clientRequest);
    }
}
