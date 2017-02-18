package io.globomart.microservices.services.price;

import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.globomart.microservices.exceptions.EntityCreateException;
import io.globomart.microservices.exceptions.PriceNotFoundException;

/**
 * Client controller, fetches Product info from the microservice via
 * {@link PriceProductsService}.
 * 
 * @author harishkadamudi
 */
@RestController
public class PriceController {

	@Autowired
	protected PriceProductsService accountsService;

	@Autowired
	private PriceRepository priceRepository;

	protected final Logger LOGGER = Logger.getLogger(PriceController.class.getName());

	public PriceController(PriceProductsService accountsService) {
		this.accountsService = accountsService;
	}
	
	/**
	 * Saves the Price with Productid.
	 *  
	 * @param price
	 * 			Price Entity
	 * @return saved entity.
	 * @throws EntityCreateException.
	 * @throws EntityCreateException.
	 * 		   If any Exception Occurs while saving Price Entity/Object
	 */
	@RequestMapping("/createprice")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Price createPrice(@RequestBody Price price) {
		Price save = null;
		try{
			LOGGER.info("price-service savePrice() invoked: " + price);
			save = priceRepository.save(price);
			if (save == null)
				throw new EntityCreateException(save,"error while creating price information");
			else {
				return save;
			}
		}catch(Exception ex){
			LOGGER.warning("error while saving price");
			throw new EntityCreateException(save,ex.getLocalizedMessage());
		}
	}

	/**
	 * API to fetch all products with its price.
	 * This API does not gives all necessary product information.
	 * @param price
	 * 			Price Entity
	 * @return saved entity.
	 * @throws EntityCreateException.
	 * @throws EntityCreateException.
	 * 		   If any Exception Occurs while saving Price Entity/Object
	 */
	@RequestMapping("/getprices")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Price> getPrices() {
		List<Price> prices = null;
		try{
			LOGGER.info("price-service getPrices() invoked: ");
			prices = priceRepository.findAll();
			if(null == prices || prices.size() == 0)
				throw new PriceNotFoundException("No Product Found");
			return prices;
		}catch(Exception ex){
			LOGGER.info("price-service savePrice() error: ");
			throw new PriceNotFoundException("Error while getting Products");
		}
		
	}

	/**
	 * API to fetch products with its price based on the productid.
	 * This API does not gives all necessary product information.
	 * @param id
	 * 			productnumber
	 * @return saved entity.
	 * @throws EntityCreateException.
	 * @throws EntityCreateException.
	 * 		   If any Exception Occurs while saving Price Entity/Object
	 */
	@RequestMapping("/getprice/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Price getPrice(Model model, @PathVariable("id") Long id) {
		Price prices = null;
		try{
			LOGGER.info("price-service getPrice() invoked: ");
			prices = priceRepository.findOne(id);
			if(null == prices)
				throw new PriceNotFoundException("No Product Found");
			return prices;
		}catch(Exception ex){
			LOGGER.info("price-service getPrice() error: ");
			throw new PriceNotFoundException("Error while getting Products");
		}
	}
	
	/**
	 * EndPoint to get Product Information based on input ProductId
	 * This API does gives all necessary product information.
	 * @param id
	 * 			productnumber
	 * @return saved entity.
	 * @throws EntityCreateException.
	 * @throws EntityCreateException.
	 * 		   If any Exception Occurs while saving Price Entity/Object
	 */
	@RequestMapping("/products/{productNumber}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Product byNumber(@PathVariable("productNumber") Long productNumber) {

		LOGGER.info("price-service byNumber() invoked: " + productNumber);

		Product aggregatedproduct = accountsService.findByNumber(productNumber);
		
		// getprice of that product
		Price price = this.priceRepository.findOne(aggregatedproduct.getId());
		aggregatedproduct.setPrice(price.getPrice());

		LOGGER.info("price-service byNumber() found: " + aggregatedproduct);
		return aggregatedproduct;
	}
}
