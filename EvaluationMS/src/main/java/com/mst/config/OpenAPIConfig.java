package com.mst.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenAPIConfig {

	@Value("${mstproject.openapi.dev-url}")
	private String devUrl;
	
	@Value("${email.destination}")
	private String emailDestination;
	
	
	@Bean
	public OpenAPI myOpenAPI() {
		Server devServer = new Server();
		devServer.setUrl(devUrl);
		devServer.setDescription("Server URL for development Env");
		
		Contact contact = new Contact();
		contact.setEmail(emailDestination);
		contact.setName("mstproject");
		contact.setUrl("http://www.mstproject.com");
		License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");
		
		Info info=  new Info().title("Evaluation MS API").version("1.0").contact(contact)
				.description("This API exposes endpoints to evaluate developers' performance and workload.").termsOfService("https://www.mstproject.com/terms")
				.license(mitLicense);
		return new OpenAPI().info(info).servers(List.of(devServer));
	}
	
}