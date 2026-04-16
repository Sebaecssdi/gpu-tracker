package gpu_tracker.scheduler;

import gpu_tracker.dto.SoloTodoResponseDto;
import gpu_tracker.model.Gpu;
import gpu_tracker.model.PriceHistory;
import gpu_tracker.repository.GpuRepository;
import gpu_tracker.repository.PriceHistoryRepository;
import gpu_tracker.service.GpuService;
import gpu_tracker.service.SoloTodoScrapperService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GpuPriceTrackerJob {
    private final GpuRepository gpuRepository;
    private final GpuService gpuService;
    private final SoloTodoScrapperService soloTodoScrapperService;
    private final PriceHistoryRepository priceHistoryRepository;

    @Scheduled(cron = "0 55 20 * * *", zone = "America/Santiago")
    public void trackGpuPricesAuto(){
        System.out.println("Starting Gpu Price Tracker Job: " + LocalDateTime.now());

        List<Gpu> gpusToTrack = gpuRepository.findAll();

        if (gpusToTrack.isEmpty()){
            System.out.println("No gpus to track");
            return;
        }

        for  (Gpu gpu : gpusToTrack){
            try {
                System.out.println("Buscando precio para: " + gpu.getName());

                SoloTodoResponseDto response = soloTodoScrapperService.fetchGpuData(gpu.getApiId());
                SoloTodoResponseDto.EntityDto bestOffer = soloTodoScrapperService.findBestOffer(response);

                if (bestOffer != null){
                    PriceHistory history = new PriceHistory();
                    history.setGpu(gpu);
                    history.setPrice(bestOffer.getActive_registry().getOffer_price());
                    history.setFetchDate(LocalDateTime.now());
                    history.setStore(soloTodoScrapperService.translateStoreName(bestOffer.getStore()));
                    priceHistoryRepository.save(history);
                } else  {
                    System.out.println("No hay stock hoy para: " + gpu.getName());
                }

                Thread.sleep(2000);

            } catch (Exception e) {
                System.out.println("Error al trackear la gpu: " + gpu.getName());
            }
        }
        System.out.println("Finished Gpu Price Tracker Job: " + LocalDateTime.now());

    }
}
