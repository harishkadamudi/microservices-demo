package io.globomart.microservices.services.price;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for Price data implemented using Spring Data JPA.
 * 
 * @author harishkadamudi
 */
public interface PriceRepository extends JpaRepository<Price, Long> {
	/**
	 * Find an account with the specified account number.
	 *
	 * @param accountNumber
	 * @return The account if found, null otherwise.
	 */
//	public Product findByNumber(String accountNumber);

	//public Account save(Account account);
	/**
	 * Find accounts whose owner name contains the specified string
	 * 
	 * @param partialName
	 *            Any alphabetic string.
	 * @return The list of matching accounts - always non-null, but may be
	 *         empty.
	 */
//	public List<Product> findByDescriptionContainingIgnoreCase(String description);

	/**
	 * Fetch the number of accounts known to the system.
	 * 
	 * @return The number of accounts.
	 */
	/*@Query("SELECT count(*) from Product")
	public int countAccounts();*/
}
