package irish.bla.webfluxdemo.config;

import irish.bla.webfluxdemo.dto.InputFailedValidationResponse;
import irish.bla.webfluxdemo.exception.InputValidationException;
import irish.bla.webfluxdemo.requesthandler.RequestHandler;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@Configuration
@AllArgsConstructor
public class RouterConfig {

    private final RequestHandler requestHandler;
    private RouterFunction<ServerResponse> serverResponseRouterFunction() {
        return RouterFunctions.route()
                .GET("square/{input}", requestHandler::squareHandler)
                .GET("table/{input}", requestHandler::tableHandler)
                .GET("table/{input}/stream", requestHandler::tableStreamHandler)
                // curl -XPOST -H 'Content-Type: application/json'  -d '{"first":5,"second":9}' http://localhost:8080/router/multiply
                .POST("multiply", requestHandler::multiplyHandler)
                .GET("square/{input}/validation", requestHandler::squareHandlerWithValidation)
                .onError(InputValidationException.class, exceptionHandler())
                .build();
    }
    // Can have multiple router beans
    @Bean
    public RouterFunction<ServerResponse> highLevelRouter() {
        return RouterFunctions.route()
                // delegating to the other router
                .path("router", this::serverResponseRouterFunction)
                .build();
    }

    private BiFunction<Throwable, ServerRequest, Mono<ServerResponse>> exceptionHandler() {
        return (err, req) -> {
            InputValidationException ex = (InputValidationException) err;
            InputFailedValidationResponse response = new InputFailedValidationResponse();
            response.setInput(ex.getInput());
            response.setMessage(ex.getMessage());
            response.setErrorCode(ex.getErrorCode());
            return ServerResponse.badRequest().bodyValue(response);
        };
    }
}
