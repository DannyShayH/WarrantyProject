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
public class WarrantyDTO {

    @JsonProperty()
    long id;

    @JsonProperty()
    long productId;

    @JsonProperty()
    int warrantyMonths;

    @JsonProperty()
    LocalDate startDate;

    @JsonProperty()
    LocalDate endDate;

    @JsonProperty()
    boolean notified90Days;

    @JsonProperty()
    boolean notified60Days;

    @JsonProperty()
    boolean notified30Days;

    @JsonProperty()
    boolean notifiedExpired;
}
