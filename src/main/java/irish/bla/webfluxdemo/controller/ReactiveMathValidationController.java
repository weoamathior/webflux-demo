package irish.bla.webfluxdemo.controller;

import irish.bla.webfluxdemo.dto.Response;
import irish.bla.webfluxdemo.exception.InputValidationException;
import irish.bla.webfluxdemo.service.ReactiveMathService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("reactive-math")
@RequiredArgsConstructor
public class ReactiveMathValidationController {
    private final ReactiveMathService reactiveMathService;

    /*
    Demo of custom error advice handler
    curl localhost:8080/reactive-math/square/120/throw
    The point is that the error advice works the same within the reactive context
     */
    @GetMapping("square/{input}/throw")
    public Mono<Response> findSquare(@PathVariable int input) {
        if (input < 10 || input > 20) {
            throw new InputValidationException(input);
        }
        return this.reactiveMathService.findSquare(input);
    }

    /*
    The 'reactive' way of doing the above.  Note the behavior is the same and the controller advice still kicks in
    when the exception is thrown
     */
    @GetMapping("square/{input}/mono-error")
    public Mono<Response> monoError(@PathVariable int input) {
        return Mono.just(input)
                .handle((integer, sink) -> {
                    if (integer >=10 && integer <=20 ) {
                        sink.next(integer);
                    } else {
                        sink.error(new InputValidationException(integer));
                    }
                })
                .cast(Integer.class)
                .flatMap(this.reactiveMathService::findSquare);
    }
}
