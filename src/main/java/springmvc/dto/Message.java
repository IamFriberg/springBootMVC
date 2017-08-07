package springmvc.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Builder
@Data
public class Message {

    @NotNull
    @Size(min=1, max=180)
    private String message;

    @NotNull
    private String userName;

    private Date timeStamp;
}
