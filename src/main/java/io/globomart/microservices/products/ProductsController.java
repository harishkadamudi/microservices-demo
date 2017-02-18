package io.globomart.microservices.products;

import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.globomart.microservices.exceptions.ProductstNotFoundException;

/**
 * A RESTFul controller for accessing Product Information.
 * 
 * @author harishakadamudi
 */
@RestController
public class ProductsController {

	protected Logger logger = Logger.getLogger(ProductsController.class.getName());
	@Autowired
	protected ProductRepository productRepository;

	/**
	 * Create an instance plugging in the repository of Product.
	 * 
	 * @param productRepository
	 *            An account repository implementation.
	 */
	@Autowired
	public ProductsController(ProductRepository productRepository) {
		this.productRepository = productRepository;

		logger.info("ProductRepository says system has " + productRepository.countAccounts() + " Products");
	}

	@POST
	@RequestMapping("/products/create")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Product saveProducts(@RequestBody Product product) {

		logger.info("product-service saveProducts() invoked: " + product);
		Product save = productRepository.save(product);
		if (save == null)
			throw new ProductstNotFoundException(save);
		else {
			return save;
		}
	}

	/**
	 * Fetch an Product with the specified product number/id.
	 * 
	 * @param productId
	 *            A numeric, not fixed digit product number.
	 * @return The product if found.
	 * @throws ProductstNotFoundException
	 *             If the number is not recognised.
	 */
	@RequestMapping("/products/{productId}")
	public Product byNumber(@PathVariable("productId") Long productId) {

		logger.info("products-service byNumber() invoked: " + productId);
		Product product = productRepository.findOne(productId);
		logger.info("products-service byNumber() found: " + product);

		if (product == null)
			throw new ProductstNotFoundException(productId);
		else {
			return product;
		}
	}

	/**
	 * Fetch Product with the specified name. A partial case-insensitive match
	 * is supported.
	 * 
	 * @param SearchCriteria
	 * @return A non-null, non-empty set of accounts.
	 * @throws ProductstNotFoundException
	 *             If there are no matches at all.
	 */
	@RequestMapping("/products/description")
	public List<Product> bydescription(@RequestBody SearchCriteria criteria) {
		logger.info("products-service bydescription() invoked: " + productRepository.getClass().getName() + " for "
				+ criteria.getDescription());

		List<Product> accounts = productRepository.findByDescriptionContainingIgnoreCase(criteria.getDescription());
		logger.info("products-service bydescription() found: " + accounts);

		if (accounts == null || accounts.size() == 0)
			throw new ProductstNotFoundException(criteria.getDescription());
		else {
			return accounts;
		}
	}

	@RequestMapping("/products/delete/{productid}")
	public String deletebyid(@PathVariable("productid") Long productId) {
		logger.info("products-service deletebyid() invoked: " + productRepository.getClass().getName() + " for "
				+ productId);

		productRepository.delete(productId);
		logger.info("products-service deletebyid() found: ");
		return "If record exists, it will be deleted";
		/*
		 * if (accounts == null || accounts.size() == 0) throw new
		 * AccountNotFoundException(productId); else { return accounts; }
		 */
	}
}
