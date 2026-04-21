package gpu_tracker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SoloTodoProductDetailsDto {
    private Long id;
    private String name;
    private String brand;
    @JsonProperty("chip_model")
    private String chipModel;
    private String memory;
    @JsonProperty("pic_url")
    private String picUrl;
}