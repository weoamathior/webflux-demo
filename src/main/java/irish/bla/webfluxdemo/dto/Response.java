package irish.bla.webfluxdemo.dto;

import lombok.Data;

import java.util.Date;

@Data
public class Response {
    private Date date = new Date();
    private int output;

    public Response(int o) {
        this.output = o;
    }
}
