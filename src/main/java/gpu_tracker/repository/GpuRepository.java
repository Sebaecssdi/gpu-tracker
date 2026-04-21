package gpu_tracker.repository;

import gpu_tracker.model.Gpu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GpuRepository extends JpaRepository<Gpu, Long> {

    List<Gpu> findByNameContainingIgnoreCase(String name);

    Optional<Gpu> findByApiId(Long apiId);
}
