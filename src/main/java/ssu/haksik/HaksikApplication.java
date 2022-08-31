package ssu.haksik;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

@SpringBootApplication
@EnableScheduling
public class HaksikApplication {

	public static void main(String[] args) {
		SpringApplication.run(HaksikApplication.class, args);}

	// blank for test
	// blank for test
}
