package app.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductRegistrationDTO {

    @JsonProperty()
    long id;

    @JsonProperty()
    long userId;

    @JsonProperty()
    long productId;

    @JsonProperty()
    long receiptId;

    @JsonProperty()
    LocalDate purchasedAt;

    @JsonProperty()
    LocalDate registeredAt;


}
