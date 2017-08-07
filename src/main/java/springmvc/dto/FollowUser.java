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
public class FollowUser {
    public String userName;
    public Boolean follow;
}
