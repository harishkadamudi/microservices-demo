package io.globomart.microservices.services.price;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * Product DTO - used to interact with the {@link PriceProductsService}.
 * 
 * @author harishkadamudi
 */
@JsonRootName("Product")
public class Product {

	// Product ID
	protected Long id;
	// Product Name
	protected String name;
	// Product Description
	protected String description;
	//Product Price
	protected BigDecimal price;

	/**
	 * Default constructor for JPA only.
	 */
	protected Product() {
		//balance = BigDecimal.ZERO;
	}

	public long getId() {
		return id;
	}

	/**
	 * Set JPA id - for testing and JPA only. Not intended for normal use.
	 * 
	 * @param id
	 *            The new id.
	 */
	protected void setId(long id) {
		this.id = id;
	}

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	protected void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

}
