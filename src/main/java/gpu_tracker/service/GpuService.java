package gpu_tracker.service;

import gpu_tracker.dto.SoloTodoProductDetailsDto;
import gpu_tracker.exception.ResourceNotFoundException;
import gpu_tracker.model.Gpu;
import gpu_tracker.model.PriceHistory;
import gpu_tracker.repository.GpuRepository;
import gpu_tracker.repository.PriceHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GpuService {

    private final GpuRepository gpuRepository;
    private final PriceHistoryRepository priceHistoryRepository;
    private final SoloTodoScrapperService scrapperService;

    public Gpu save(Gpu gpu) {
        return gpuRepository.save(gpu);
    }


    public List<Gpu> findAll(){
        return gpuRepository.findAll();
    }

    public List<Gpu> searchByName(String query) {
        if (query == null || query.isBlank()) {
            return List.of();
        }
        return gpuRepository.findByNameContainingIgnoreCase(query.trim());
    }

    public Optional<Gpu> findByApiId(Long apiId) {
        if (apiId == null) {
            return Optional.empty();
        }
        return gpuRepository.findByApiId(apiId);
    }

    public List<PriceHistory> getHistoryByGpuId(Long gpuId) {
        if (!gpuRepository.existsById(gpuId)) {
            throw new ResourceNotFoundException("No se encontró la GPU con el ID: " + gpuId);
        }
        return priceHistoryRepository.findByGpuIdOrderByFetchDateAsc(gpuId);
    }

    public Gpu addGpuFromUrl(String soloTodoUrl) {

        Long apiId = scrapperService.extractApiIdFromUrl(soloTodoUrl);
        if (apiId == null) {
            throw new IllegalArgumentException("No se pudo extraer el ID de la URL proporcionada.");
        }

        if (gpuRepository.findByApiId(apiId).isPresent()) {
            throw new IllegalArgumentException("Esta GPU ya se encuentra registrada en el sistema.");
        }
        SoloTodoProductDetailsDto details = scrapperService.fetchGpuDetails(apiId);
        if (details == null) {
            throw new RuntimeException("No se encontraron detalles para el producto en SoloTodo.");
        }
        Gpu newGpu = new Gpu();
        newGpu.setApiId(details.getId());
        newGpu.setName(details.getName());
        newGpu.setBrand(details.getBrand());
        newGpu.setChipModel(details.getChipModel());
        newGpu.setMemory(details.getMemory());
        newGpu.setPicUrl(details.getPicUrl());

        return gpuRepository.save(newGpu);
    }
}
