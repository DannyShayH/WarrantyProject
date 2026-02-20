package app.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@Getter
@Setter
@ToString
public class LawDataDTO {

    private String title;
    private String content;

    public LawDataDTO(String title, String content){
        this.title = title;
        this.content = content;
    }
}
