package io.globomart.microservices.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;

import io.globomart.microservices.products.Product;

import org.springframework.http.HttpStatus;

/**
 * Allow the controller to return a 404 if an product is not found by simply
 * throwing this exception. The @ResponseStatus causes Spring MVC to return a
 * 404 instead of the usual 500.
 * 
 * @author harishkadamudi
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class PriceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public PriceNotFoundException(Long productId) {
		super("No such Product: " + productId);
	}
	
	public PriceNotFoundException(String productDescription) {
		super("No such Product with description: " + productDescription);
	}
	
	public PriceNotFoundException(Product product) {
		super("Could not create Product: " + product);
	}
}
