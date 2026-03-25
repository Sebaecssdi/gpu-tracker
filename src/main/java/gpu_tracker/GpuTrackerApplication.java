package gpu_tracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GpuTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(GpuTrackerApplication.class, args);
	}

}
