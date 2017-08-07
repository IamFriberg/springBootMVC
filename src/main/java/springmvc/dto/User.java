package springmvc.dto;

import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;


/**
 * Data class that represent a User
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @NonNull
    @NotEmpty
    public String userName;
    @NonNull
    @NotEmpty
    public String password;
}
