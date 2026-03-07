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
public class ReceiptDTO {

    @JsonProperty()
    long id;

    @JsonProperty()
    long productRegistrationId;

    @JsonProperty()
    LocalDate purchasedAt;

    @JsonProperty()
    String description;

    @JsonProperty()
    double price;

}
