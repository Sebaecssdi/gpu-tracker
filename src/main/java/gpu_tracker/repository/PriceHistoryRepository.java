package gpu_tracker.repository;

import gpu_tracker.model.PriceHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PriceHistoryRepository extends JpaRepository<PriceHistory, Long> {
    List<PriceHistory> findByGpuIdOrderByFetchDateAsc(Long gpuId);

}
