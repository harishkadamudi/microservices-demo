package io.globomart.microservices.exceptions;

/**
 * Exception handling, in case of creating the product 
 * @author harishkadamudi
 */
public class EntityCreateException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public <T> EntityCreateException(T t, String errorMessage) {
		super(errorMessage);
	}
}
