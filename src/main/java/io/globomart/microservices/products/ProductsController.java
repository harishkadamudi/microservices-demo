package io.globomart.microservices.products;

import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.globomart.microservices.exceptions.ProductstCreateException;
import io.globomart.microservices.exceptions.ProductstNotFoundException;

/**
 * A RESTFul controller for accessing Product Information.
 * 
 * @author harishakadamudi
 */
@RestController
public class ProductsController {

	protected static final Logger LOGGER = Logger.getLogger(ProductsController.class.getName());
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

		LOGGER.info("ProductRepository says system has " + productRepository.countAccounts() + " Products");
	}
	
	/**
	 * Saves the Product with the specified inputs.
	 *  
	 * @param product
	 * @return saved entity.
	 * @throws ProductstNotFoundException.
	 * @throws ProductstCreateException.
	 * 		   If any Exception Occurs while saving Product Entity/Object
	 */
	@POST
	@RequestMapping("/products/create")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Product saveProducts(@RequestBody Product product) {
		Product save = null;
		try{
			LOGGER.info("product-service saveProducts() invoked: " + product);
			save = productRepository.save(product);
			if (save == null)
				throw new ProductstCreateException(save);
			else {
				return save;
			}
		}catch(Exception ex){
			LOGGER.warning("error while saving product");
			throw new ProductstCreateException(save);
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

		LOGGER.info("products-service byNumber() invoked: " + productId);
		Product product = productRepository.findOne(productId);
		LOGGER.info("products-service byNumber() found: " + product);

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
		LOGGER.info("products-service bydescription() invoked: " + productRepository.getClass().getName() + " for "
				+ criteria.getDescription());

		List<Product> accounts = productRepository.findByDescriptionContainingIgnoreCase(criteria.getDescription());
		LOGGER.info("products-service bydescription() found: " + accounts);

		if (accounts == null || accounts.size() == 0)
			throw new ProductstNotFoundException(criteria.getDescription());
		else {
			return accounts;
		}
	}
	
	/**
	 * Deletes Products from DB, based on the ProductId.
	 * 
	 * @param productId
	 * 			Product to be deleted
	 * @return HttpStatus code 200-OK, If product is deleted successfully.
	 * @return HttpStatus code 500-Bad Request, If Exception while deleting the product.
	 * 
	 */
	@RequestMapping("/products/delete/{productid}")
	public ResponseEntity<Boolean> deletebyid(@PathVariable("productid") Long productId) {
		LOGGER.info("products-service deletebyid() invoked: " + productRepository.getClass().getName() + " for "
				+ productId);
		try{
			productRepository.delete(productId);
			LOGGER.info("products-service deletebyid() found: ");
		}catch(Exception ex){
			return new ResponseEntity<Boolean>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Boolean>(HttpStatus.OK);
	}
}
