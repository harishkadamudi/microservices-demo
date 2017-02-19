package io.globomart.microservices.services.composite;

import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import io.globomart.microservices.exceptions.ProductstNotFoundException;
import io.globomart.microservices.services.price.Price;

/**
 * Hide the access to the microservice inside this local service.
 * 
 * @author harishkadamudi
 */
@Service
public class ProductsPriceService {

	@Autowired
	@LoadBalanced
	protected RestTemplate restTemplate;
	
    @Autowired
    Util util;
    
	protected String productsserviceUrl;
	protected String priceserviceUrl;

	protected Logger LOGGER = Logger.getLogger(ProductsPriceService.class.getName());

	public ProductsPriceService(String productsserviceUrl, String priceserviceUrl) {
		this.productsserviceUrl = productsserviceUrl.startsWith("http") ? productsserviceUrl : "http://" + productsserviceUrl;
		this.priceserviceUrl = priceserviceUrl.startsWith("http") ? priceserviceUrl : "http://" + priceserviceUrl;
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
	public ResponseEntity<Product> findByNumber(Long productId) {
		Product product = null;
		LOGGER.info("findByNumber() invoked: for " + productId);
		try {
			product = restTemplate.getForObject(productsserviceUrl + "/products/{number}", Product.class, productId);
		//	return util.createOkResponse(product);
		} catch (HttpClientErrorException e) { // 404
			// Nothing found
			LOGGER.warning("Error - findByNumber() invoked: for " + productId);
			return util.createResponse(null, HttpStatus.BAD_GATEWAY);
		}
		if (product == null)
			throw new ProductstNotFoundException("No Products Found");
		return util.createOkResponse(product);
	}
	
	public ResponseEntity<Price> findPriceByNumber(Long productId) {
		Price price = null;
		LOGGER.info("findByNumber() invoked: for " + productId);
		try {
			price = restTemplate.getForObject(priceserviceUrl + "/getprice/{number}", Price.class, productId);
		//	return util.createOkResponse(product);
		} catch (HttpClientErrorException e) { // 404
			// Nothing found
			LOGGER.warning("Error - findByNumber() invoked: for " + productId);
			return util.createResponse(null, HttpStatus.BAD_GATEWAY);
		}
		if (price == null)
			throw new ProductstNotFoundException("No Products Found");
		return util.createOkResponse(price);
	}
}
