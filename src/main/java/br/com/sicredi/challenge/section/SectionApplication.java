package br.com.sicredi.challenge.section;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableScheduling
@EnableSwagger2
public class SectionApplication {

	public static void main(String[] args) {
		SpringApplication.run(SectionApplication.class, args);
	}

}
