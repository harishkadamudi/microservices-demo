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
	protected Logger logger = Logger.getLogger(PriceController.class.getName());

	public PriceController(PriceProductsService accountsService) {
		this.accountsService = accountsService;
	}

	/*@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setAllowedFields("accountNumber", "searchText");
	}*/
/*
	@RequestMapping("/accounts")
	public String goHome() {
		return "index";
	}
*/
	@RequestMapping("/createprice")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Price createPrice(@RequestBody Price price) {
		return priceRepository.save(price);
		// return "index";
	}

	@RequestMapping("/getprices")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Price> getPrices() {
		return priceRepository.findAll();
		// return "index";
	}

	@RequestMapping("/getprice/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Price getPrice(Model model, @PathVariable("id") Long id) {
		return priceRepository.findOne(id);
		// return "index";
	}

	@RequestMapping("/products/{productNumber}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Product byNumber(Model model, @PathVariable("productNumber") String productNumber) {

		logger.info("web-service byNumber() invoked: " + productNumber);

		Product account = accountsService.findByNumber(productNumber);
		
		// getprice of that product
		Price price = this.priceRepository.findOne(account.getId());
		account.setPrice(price.getPrice());
		logger.info("web-service byNumber() found: " + account);
		// model.addAttribute("account", account);
		return account;
	}

/*	@RequestMapping("/accounts/owner/{text}")
	public String ownerSearch(Model model, @PathVariable("text") String name) {
		logger.info("web-service byOwner() invoked: " + name);

		List<Account> accounts = accountsService.byOwnerContains(name);
		logger.info("web-service byOwner() found: " + accounts);
		model.addAttribute("search", name);
		if (accounts != null)
			model.addAttribute("accounts", accounts);
		return "accounts";
	}*/

/*	@RequestMapping(value = "/accounts/search", method = RequestMethod.GET)
	public String searchForm(Model model) {
		model.addAttribute("searchCriteria", new SearchCriteria());
		return "accountSearch";
	}*/

	/*@RequestMapping(value = "/accounts/dosearch")
	public Product doSearch(Model model, SearchCriteria criteria, BindingResult result) {
		logger.info("web-service search() invoked: " + criteria);

		criteria.validate(result);

		if (result.hasErrors())
			return "accountSearch";

		String accountNumber = criteria.getAccountNumber();
		if (StringUtils.hasText(accountNumber)) {
			return byNumber(model, accountNumber);
		} else {
			String searchText = criteria.getSearchText();
			return ownerSearch(model, searchText);
		}
	}*/
}
