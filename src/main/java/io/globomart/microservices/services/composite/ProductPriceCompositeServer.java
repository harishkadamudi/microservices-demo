package io.globomart.microservices.services.composite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

/**
 * productPriceComposite-server. Works as a microservice client, fetching data from the
 * price-Service & product-service. Uses the Discovery Server (Eureka) to find the microservice.
 * 
 * @author harishkadamudi
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(useDefaultFilters = false) // Disable component scanner
public class ProductPriceCompositeServer {

	/**
	 * URL uses the logical name of price-service - upper or lower case,
	 * doesn't matter.
	 */
	public static final String PRODUCTS_SERVICE_URL = "http://PRODUCTS-SERVICE";

	/**
	 * URL uses the logical name of price-service - upper or lower case,
	 * doesn't matter.
	 */
	public static final String PRICE_SERVICE_URL = "http://PRICE-SERVICE";
	/**
	 * Run the application using Spring Boot and an embedded servlet engine.
	 * 
	 * @param args
	 *            Program arguments - ignored.
	 */
	public static void main(String[] args) {
		// Tell server to look for web-server.properties or web-server.yml
		System.setProperty("spring.config.name", "composite-server");
		SpringApplication.run(ProductPriceCompositeServer.class, args);
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

	/**
	 * The PriceProductsService encapsulates the interaction with the micro-service.
	 * {@link ProductPriceController}
	 * @return A new service instance.
	 */
	@Bean
	public ProductsPriceService productsPriceService() {
		return new ProductsPriceService(PRODUCTS_SERVICE_URL,PRICE_SERVICE_URL);
	}
	

	/**
	 * Create the controller, passing it the {@link ProductPriceController} to use.
	 * 
	 * @return new ProductPriceController
	 */
	@Bean
	public ProductPriceController productPriceController() {
		return new ProductPriceController(productsPriceService());
	}
	
	@Bean
	public Util util() {
		return new Util();
	}
	
}
