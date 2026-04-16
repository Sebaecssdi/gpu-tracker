package gpu_tracker.service;

import gpu_tracker.dto.SoloTodoResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SoloTodoScrapperService {

    private final WebClient webClient;
    private final Map<String, String> storeCache = new ConcurrentHashMap<>();

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

//    public String translateStoreName(String storeUrl) {
//        if (storeUrl == null || storeUrl.isEmpty()){
//            return "Desconocida";
//        }
//
//        String storeId = storeUrl.replaceAll("[^0-9]","");
//
//        return switch (storeId){
//            case "86" -> "SP Digital";
//            case "34" -> "PC Express";
//            case "266" -> "NotebookStore";
//
//            default -> "Tienda ID: " + storeId;
//        };
//
//    }

    public String translateStoreName(String storeUrl) {
        if (storeUrl == null || storeUrl.isEmpty()){
            return "Desconocida";
        }
        String storeId = storeUrl.replaceAll("[^0-9]","");

        if (storeCache.containsKey(storeId)){
            return storeCache.get(storeId);
        }
        try {
            System.out.println("Adding new store with ID: " + storeId);
            SoloTodoResponseDto.StoreDto storeResponse = webClient.get()
                    .uri("/stores/" + storeId + "/")
                    .retrieve()
                    .bodyToMono(SoloTodoResponseDto.StoreDto.class)
                    .block();

            if (storeResponse != null && storeResponse.getName() != null) {
                String realName = storeResponse.getName();
                storeCache.put(storeId, realName);
                return realName;
            }
        } catch (Exception e) {
            System.out.println("It could not access the name of the store for the ID: " + storeId);
        }
        return "Store ID: " + storeId;
    }

}
