package io.globomart.microservices.services.price;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

/**
 * price-server. Works as a microservice client, fetching data from the
 * price-Service. Uses the Discovery Server (Eureka) to find the microservice.
 * 
 * @author harishkadamudi
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(useDefaultFilters = false) // Disable component scanner
public class PriceServer {

	/**
	 * URL uses the logical name of account-service - upper or lower case,
	 * doesn't matter.
	 */
	public static final String PRODUCTS_SERVICE_URL = "http://PRODUCTS-SERVICE";

	/**
	 * Run the application using Spring Boot and an embedded servlet engine.
	 * 
	 * @param args
	 *            Program arguments - ignored.
	 */
	public static void main(String[] args) {
		// Tell server to look for web-server.properties or web-server.yml
		System.setProperty("spring.config.name", "price-server");
		SpringApplication.run(PriceServer.class, args);
	}

	/**
	 * A customized RestTemplate that has the ribbon load balancer build in.
	 * Note that prior to the "Brixton" 
	 * 
	 * @return
	 */
	@LoadBalanced
	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	public PriceController priceController() {
		return new PriceController();
	}
	
	@Bean
	public HomeController homeController() {
		return new HomeController();
	}
}
