package irish.bla.webfluxdemo.service;

import irish.bla.webfluxdemo.dto.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class ReactiveMathService {

    public Mono<Response> findSquare(int input) {
        return Mono.fromSupplier(() -> input * input)
                .map(Response::new);
    }

    public Flux<Response> multTable(int input) {
        return Flux.range(1,10)
                .doOnNext(i -> SleepUtil.sleepSeconds(1))
                .doOnNext(i -> log.info("reactive math service processing " + i))
                .map( i -> new Response(i * input));
    }
}
