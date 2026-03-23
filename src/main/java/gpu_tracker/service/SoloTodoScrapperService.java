package gpu_tracker.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class SoloTodoScrapperService {

    private final WebClient webClient;

    public SoloTodoScrapperService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("https://publicapi.solotodo.com")
                .build();
    }


}
