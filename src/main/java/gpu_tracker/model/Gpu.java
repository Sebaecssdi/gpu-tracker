package gpu_tracker.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "gpu")
public class Gpu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private Long apiId;

    private String name;
    private String picUrl;
    private String brand;
    private String chipModel;
    private String memory;

}
