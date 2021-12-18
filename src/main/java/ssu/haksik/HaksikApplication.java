package ssu.haksik;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HaksikApplication {

	public static void main(String[] args) {
		SpringApplication.run(HaksikApplication.class, args);
	}

}
