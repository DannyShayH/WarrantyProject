package app.dto;

import app.entity.ProductRegistration;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDTO {

    @JsonProperty()
    long id;

    @JsonProperty()
    String email;

    @JsonProperty()
    String password;

    @JsonProperty()
    LocalDateTime createdAt;

    @JsonProperty()
    List<ProductRegistrationDTO> registrationList;

}
