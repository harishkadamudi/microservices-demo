package io.globomart.microservices.products;

/**
 * A special class for search criteria, Add parameters for dynamic criteria
 * @author harishkadamudi
 */
public class SearchCriteria {

	// this field will be mapped to product description while searching for the
	// product based on the product description
	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
