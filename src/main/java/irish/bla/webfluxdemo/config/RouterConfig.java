package irish.bla.webfluxdemo.config;

import irish.bla.webfluxdemo.requesthandler.RequestHandler;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@AllArgsConstructor
public class RouterConfig {

    private final RequestHandler requestHandler;
    @Bean
    public RouterFunction<ServerResponse> serverResponseRouterFunction() {
        return RouterFunctions.route()
                .GET("router/square/{input}", requestHandler::squareHandler)
                .GET("router/table/{input}", requestHandler::tableHandler)
                .GET("router/table/{input}/stream", requestHandler::tableStreamHandler)
                // curl -XPOST -H 'Content-Type: application/json'  -d '{"first":5,"second":9}' http://localhost:8080/router/multiply
                .POST("router/multiply", requestHandler::multiplyHandler)
                .build();
    }
}
