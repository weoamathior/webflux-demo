package irish.bla.webfluxdemo.webtestclient;

import irish.bla.webfluxdemo.config.RouterConfig;
import irish.bla.webfluxdemo.dto.Response;
import irish.bla.webfluxdemo.requesthandler.RequestHandler;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.ServerResponse;

@WebFluxTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(classes = RouterConfig.class)
public class Lecture151RouterFunctionTest {
    private WebTestClient client;

    @Autowired
//    private RouterConfig routerConfig;
    private ApplicationContext ctx;

    @MockBean
    RequestHandler handler;

    @BeforeAll
    public void setClient() {
//        client = WebTestClient.bindToRouterFunction(routerConfig.highLevelRouter()).build();
        client = WebTestClient.bindToApplicationContext(ctx).build();
    }

    @Test
    void test() {

        Mockito.when(handler.squareHandler(Mockito.any())).thenReturn(ServerResponse.ok().bodyValue(new Response(225)));

        client.get().uri("/router/square/{input}", 15)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(Response.class)
                .value(r -> Assertions.assertThat(r.getOutput()).isEqualTo(225));
    }
}
