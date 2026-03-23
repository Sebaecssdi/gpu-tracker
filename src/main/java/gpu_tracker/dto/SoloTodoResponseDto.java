package gpu_tracker.dto;

import lombok.Data;

import java.util.List;

@Data
public class SoloTodoResponseDto {
    private List<EntityDto> entities;

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
    }
}
