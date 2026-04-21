package gpu_tracker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

import java.util.List;

@Data
public class SoloTodoResponseDto {


    private List<ResultDto> results;

    @Data
    public static class ResultDto {
        private ProductDto product;
        private List<EntityDto> entities;
    }
    @Data
    public static class ProductDto {
        private Long id;
        private String name;
        @JsonProperty("picture_url")
        private String pictureUrl;
        private JsonNode specs;
    }

    @Data
    public static class EntityDto {
        private String external_url;
        private String store;
        private ActiveRegistryDto active_registry;
    }

    @Data
    public static class ActiveRegistryDto {
        private String timestamp;
        private Double offer_price;
        @JsonProperty("is_available")
        private boolean is_available;
    }

    @Data
    public static class StoreDto {
        private String name;
    }
}
