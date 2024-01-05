package irish.bla.webfluxdemo.config;

import irish.bla.webfluxdemo.requesthandler.CalculatorHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.*;

@Configuration
@RequiredArgsConstructor
public class CalculationRouterConfig {

    private final CalculatorHandler calculatorHandler;

    // curl -H "OP:+" http://localhost:8080/calculator/100/10
    // curl -H "OP:-" http://localhost:8080/calculator/100/10
    // curl -H "OP:/" http://localhost:8080/calculator/100/10
    private RouterFunction<ServerResponse> serverResponseRouterFunction() {
        return RouterFunctions.route()
                .GET("{a}/{b}", isOperation("+"), calculatorHandler::additionHandler)
                .GET("{a}/{b}", isOperation("-"), calculatorHandler::subtractionHandler)
                .GET("{a}/{b}", isOperation("*"), calculatorHandler::multiplyHandler)
                .GET("{a}/{b}", isOperation("/"), calculatorHandler::divHandler)
                .GET("{a}/{b}",req -> ServerResponse.badRequest().bodyValue("OP should be + - * /"))
                .build();
    }

    private RequestPredicate isOperation(String op) {
        return RequestPredicates.headers(headers -> op.equals(headers.asHttpHeaders().toSingleValueMap().get("OP")));
    }
    @Bean
    public RouterFunction<ServerResponse> highLevelCalculatorRouter() {
        return RouterFunctions.route()
                // delegating to the other router
                .path("calculator", this::serverResponseRouterFunction)
                .build();
    }
}
