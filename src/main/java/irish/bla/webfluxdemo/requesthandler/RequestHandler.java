package irish.bla.webfluxdemo.requesthandler;

import irish.bla.webfluxdemo.dto.Response;
import irish.bla.webfluxdemo.service.ReactiveMathService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class RequestHandler {

    private final ReactiveMathService reactiveMathService;

    public Mono<ServerResponse> squareHandler(ServerRequest request) {
        int input = Integer.parseInt(request.pathVariable("input"));
        Mono<Response> responseMono = reactiveMathService.findSquare(input);
        return ServerResponse.ok().body(responseMono, Response.class);
    }
    public Mono<ServerResponse> tableHandler(ServerRequest request) {
        int input = Integer.parseInt(request.pathVariable("input"));
        Flux<Response> responseFlux = reactiveMathService.multTable(input);
        return ServerResponse.ok().body(responseFlux, Response.class);
    }

}
