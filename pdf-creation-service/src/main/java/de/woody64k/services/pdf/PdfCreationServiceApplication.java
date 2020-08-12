package de.woody64k.services.pdf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class PdfCreationServiceApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(PdfCreationServiceApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(PdfCreationServiceApplication.class);
	}
}
