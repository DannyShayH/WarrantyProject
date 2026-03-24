package app.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDTO {

    @JsonProperty()
    long id;

    @JsonProperty()
    String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    String password;

    @JsonProperty()
    LocalDateTime createdAt;

    @JsonProperty()
    List<ProductRegistrationDTO> registrationList;

    @JsonProperty()
    Set<String> rolesAsStrings;

    public UserDTO(String email, Set<String> rolesAsStrings) {
        this.email = email;
        this.rolesAsStrings = rolesAsStrings;
    }
}
