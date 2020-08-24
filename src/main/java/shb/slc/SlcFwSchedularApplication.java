package shb.slc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SlcFwSchedularApplication {
    public static void main(String[] args) {
        SpringApplication.run(SlcFwSchedularApplication.class, args);
    }
}
