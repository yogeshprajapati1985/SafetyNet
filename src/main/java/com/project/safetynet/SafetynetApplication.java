package com.project.safetynet;

import com.project.safetynet.utils.ExcludeFromJacocoGeneratedReport;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SafetynetApplication {

	@ExcludeFromJacocoGeneratedReport
	public static void main(String[] args) {
		SpringApplication.run(SafetynetApplication.class, args);
	}

}
