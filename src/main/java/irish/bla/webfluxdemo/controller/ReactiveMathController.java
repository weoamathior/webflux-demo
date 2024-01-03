package irish.bla.webfluxdemo.controller;

import irish.bla.webfluxdemo.dto.Response;
import irish.bla.webfluxdemo.service.ReactiveMathService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("reactive-math")
@RequiredArgsConstructor
public class ReactiveMathController {
    private final ReactiveMathService reactiveMathService;

    @GetMapping("square/{input}")
    public Mono<Response> findSquare(@PathVariable int input) {
        return reactiveMathService.findSquare(input);
    }

    @GetMapping("table/{input}")
    public Flux<Response> multTable(@PathVariable int input) {
        return this.reactiveMathService.multTable(input);
    }

    @GetMapping(value = "table/{input}/stream",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Response> multTableStream(@PathVariable int input) {
        return this.reactiveMathService.multTable(input);
    }

}
