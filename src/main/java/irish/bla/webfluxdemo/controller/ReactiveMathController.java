package irish.bla.webfluxdemo.controller;

import irish.bla.webfluxdemo.dto.MultiplyRequestDto;
import irish.bla.webfluxdemo.dto.Response;
import irish.bla.webfluxdemo.service.ReactiveMathService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@Slf4j
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

    /*
    The underlying service does not use the reactive pipeline and therefore will not exhibit the same
    behavior as the method above.
     */
    @GetMapping(value = "table/{input}/stream/no",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Response> multTableStreamNo(@PathVariable int input) {
        return this.reactiveMathService.multTableNoPipeline(input);
    }


    /*
    curl -XPOST -H 'Content-Type: application/json'  -d '{"first":5,"second":9}' http://localhost:8080/reactive-math/multiply
     */
    @PostMapping("multiply")
    public Mono<Response> multiply(@RequestBody Mono<MultiplyRequestDto> requestDtoMono, @RequestHeader Map<String,String> headers) {
        log.info("headers = {}", headers);
        return this.reactiveMathService.multiply(requestDtoMono);
    }
}
