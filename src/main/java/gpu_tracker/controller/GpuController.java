package gpu_tracker.controller;

import gpu_tracker.dto.SoloTodoResponseDto;
import gpu_tracker.model.Gpu;
import gpu_tracker.model.PriceHistory;
import gpu_tracker.repository.GpuRepository;
import gpu_tracker.service.GpuService;
import gpu_tracker.service.SoloTodoScrapperService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gpus")
@RequiredArgsConstructor
public class GpuController {

    private final GpuService gpuService;
    private final SoloTodoScrapperService soloTodoScrapperService;


    @PostMapping
    public ResponseEntity<Gpu> createGpu(@RequestBody Gpu gpu){
        Gpu savedGpu = gpuService.save(gpu);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedGpu);
    }

    @GetMapping
    public ResponseEntity<List<Gpu>> findAllGpu(){
        List<Gpu> gpuList = gpuService.findAll();
        return ResponseEntity.ok(gpuList);
    }

    @GetMapping("/test-scraping/{apiId}")
    public ResponseEntity<SoloTodoResponseDto> testScraping(@PathVariable Long apiId) {
       SoloTodoResponseDto response = soloTodoScrapperService.fetchGpuData(apiId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/history")
    public ResponseEntity<List<PriceHistory>> getGpuHistory(@PathVariable Long id) {
        List<PriceHistory> history = gpuService.getHistoryByGpuId(id);
        return ResponseEntity.ok(history);
    }
}
