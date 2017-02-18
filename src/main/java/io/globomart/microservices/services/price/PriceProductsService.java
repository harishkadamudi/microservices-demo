package io.globomart.microservices.services.price;

import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import io.globomart.microservices.exceptions.ProductstNotFoundException;

/**
 * Hide the access to the microservice inside this local service.
 * 
 * @author harishkadamudi
 */
@Service
public class PriceProductsService {

	@Autowired
	@LoadBalanced
	protected RestTemplate restTemplate;

	protected String serviceUrl;

	protected Logger LOGGER = Logger.getLogger(PriceProductsService.class.getName());

	public PriceProductsService(String serviceUrl) {
		this.serviceUrl = serviceUrl.startsWith("http") ? serviceUrl : "http://" + serviceUrl;
	}

	/**
	 * The RestTemplate works because it uses a custom request-factory that uses
	 * Ribbon to look-up the service to use. This method simply exists to show
	 * this.
	 */
	@PostConstruct
	public void demoOnly() {
		// Can't do this in the constructor because the RestTemplate injection
		// happens afterwards.
		LOGGER.warning("The RestTemplate request factory is " + restTemplate.getRequestFactory().getClass());
	}

	/**
	 * This Service will interactive with Product Server in order to get product
	 * based on productid
	 * 
	 * @param accountNumber
	 * @return Product entity Based on the productid
	 * @exception ProductstNotFoundException
	 *                Return gracefully ProductstNotFoundException exception if
	 *                no Product record exists for given productid
	 */
	public Product findByNumber(Long productId) {
		Product product = null;
		LOGGER.info("findByNumber() invoked: for " + productId);
		try {
			product = restTemplate.getForObject(serviceUrl + "/products/{number}", Product.class, productId);
		} catch (HttpClientErrorException e) { // 404
			// Nothing found
			LOGGER.warning("Error - findByNumber() invoked: for " + productId);	
		}
		if (product == null)
			throw new ProductstNotFoundException("No Products Found");
		return product;
	}
}
