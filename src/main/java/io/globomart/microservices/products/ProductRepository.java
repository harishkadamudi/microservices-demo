package io.globomart.microservices.products;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Repository for Product data implemented using Spring Data JPA.
 * 
 * @author harishkadamudi
 */
public interface ProductRepository extends JpaRepository<Product, Long> {
	/**
	 * Find an product with the specified product number.
	 *
	 * @param productName
	 * @return The product if found, null otherwise.
	 */
	public Product findByName(String productName);

	//public Account save(Product account);
	/**
	 * Find products whose description name contains the specified string
	 * 
	 * @param partialName
	 *            Any alphabetic string.
	 * @return The list of matching products - always non-null, but may be
	 *         empty.
	 */
	public List<Product> findByDescriptionContainingIgnoreCase(String description);

	/**
	 * Fetch the number of products known to the system.
	 * 
	 * @return The number of products.
	 */
	@Query("SELECT count(*) from Product")
	public int countAccounts();
}
