package de.woody64k.services.email;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class EMailServiceApplication extends SpringBootServletInitializer {

    /**
     * To run service as SpringBoot application.
     * 
     * @param args
     */
    public static void main(String[] args) {
	SpringApplication.run(EMailServiceApplication.class, args);
    }

    /**
     * To host the service on an application server.
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
	return application.sources(EMailServiceApplication.class);
    }
}
