package io.globomart.microservices.services.composite;

import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.globomart.microservices.services.price.Price;

/**
 * Client controller, fetches Product info from the microservice via
 * {@link PriceProductsService}.
 * 
 * @author harishkadamudi
 */
@RestController
public class ProductPriceController {

	/*
	 * @Autowired private PriceRepository priceRepository;
	 */
	@Autowired
    Util util;
	
	@Autowired
	private ProductsPriceService productsPriceService;
	protected final Logger LOGGER = Logger.getLogger(ProductPriceController.class.getName());

	public ProductPriceController(ProductsPriceService productsPriceService) {
		this.productsPriceService = productsPriceService;
	}

	@RequestMapping("/compositeproducts/{productNumber}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseEntity<Product> byNumber(@PathVariable("productNumber") Long productNumber) {
		
		LOGGER.info("price-service byNumber() invoked: " + productNumber);
		Product product = null;
		Price price =  null;
		
		ResponseEntity<Product> productentity = productsPriceService.findByNumber(productNumber);
		if (!productentity.getStatusCode().is2xxSuccessful()) {
            // We can't proceed, return whatever fault we got from the getProduct call
            return util.createResponse(null, productentity.getStatusCode());
        }else{
        	product = productentity.getBody();
        }
		
		ResponseEntity<Price> priceentity = productsPriceService.findPriceByNumber(productNumber);
		if (!priceentity.getStatusCode().is2xxSuccessful()) {
            // We can't proceed, return whatever fault we got from the getProduct call
            return util.createResponse(null, priceentity.getStatusCode());
        }else{
        	price = priceentity.getBody();
        	product.setPrice(price.getPrice());
        }
		LOGGER.info("price-service byNumber() found: " + product);
		return util.createResponse(product, HttpStatus.OK);
	}
}
