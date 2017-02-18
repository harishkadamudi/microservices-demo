package io.globomart.microservices.exceptions;

import io.globomart.microservices.products.Product;

/**
 * Exception handling, in case of creating the product 
 * @author harishkadamudi
 */
public class ProductstCreateException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ProductstCreateException(Product product) {
		super("Could not create Product: " + product);
	}
}
