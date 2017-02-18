package io.globomart.microservices.services.price;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Home page controller.
 * 
 * @author harishkadamudi
 */
@RestController
public class HomeController {

	@RequestMapping("/")
	public String home() {
		return "index";
	}

}
