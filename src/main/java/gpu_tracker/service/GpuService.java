package gpu_tracker.service;

import gpu_tracker.model.Gpu;
import gpu_tracker.repository.GpuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GpuService {

    private final GpuRepository gpuRepository;

    public Gpu save(Gpu gpu) {
        return gpuRepository.save(gpu);
    }


    public List<Gpu> findAll(){
        return gpuRepository.findAll();
    }
}
