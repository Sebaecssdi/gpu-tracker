package gpu_tracker.service;

import gpu_tracker.model.Gpu;
import gpu_tracker.model.PriceHistory;
import gpu_tracker.repository.GpuRepository;
import gpu_tracker.repository.PriceHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GpuService {

    private final GpuRepository gpuRepository;
    private final PriceHistoryRepository priceHistoryRepository;

    public Gpu save(Gpu gpu) {
        return gpuRepository.save(gpu);
    }


    public List<Gpu> findAll(){
        return gpuRepository.findAll();
    }

    public List<PriceHistory> getHistoryByGpuId(Long gpuId) {
        return priceHistoryRepository.findByGpuIdOrderByFetchDateAsc(gpuId);
    }
}
