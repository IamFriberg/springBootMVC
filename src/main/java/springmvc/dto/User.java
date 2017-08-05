package springmvc.dto;

import groovy.transform.builder.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

@Builder
@Data
public class User {

    @NotNull
    @NotEmpty
    public String userName;
    @NotNull
    @NotEmpty
    public String password;
}
