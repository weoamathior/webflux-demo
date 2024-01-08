package irish.bla.webfluxdemo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class Response {
    private Date date = new Date();
    private int output;

    public Response(int o) {
        this.output = o;
    }
}
