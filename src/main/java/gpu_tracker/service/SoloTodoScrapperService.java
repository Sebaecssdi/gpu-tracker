package gpu_tracker.service;

import gpu_tracker.dto.SoloTodoResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Comparator;
import java.util.List;

@Service
public class SoloTodoScrapperService {

    private final WebClient webClient;

    public SoloTodoScrapperService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("https://publicapi.solotodo.com")
                .build();
    }

    public SoloTodoResponseDto fetchGpuData(Long apiId){
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/products/available_entities/")
                        .queryParam("ids", apiId)
                        .queryParam("exclude_refurbished", "true")
                        .build())
                .retrieve()
                .bodyToMono(SoloTodoResponseDto.class)
                .block();
    }

    public SoloTodoResponseDto.EntityDto findBestOffer(SoloTodoResponseDto responseDto){
        if (responseDto == null|| responseDto.getResults() == null || responseDto.getResults().isEmpty()) {
            return null;
        }

        List<SoloTodoResponseDto.EntityDto> entities = responseDto.getResults().get(0).getEntities();

        return entities.stream()
                .filter(entity -> entity.getActive_registry() != null)
                .filter(entity -> entity.getActive_registry().is_available())
                .min(Comparator.comparing(entity -> entity.getActive_registry().getOffer_price()))
                .orElse(null);

    }

}
