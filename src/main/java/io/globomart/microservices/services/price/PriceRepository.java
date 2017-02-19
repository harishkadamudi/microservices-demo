package io.globomart.microservices.services.price;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for Price data implemented using Spring Data JPA.
 * 
 * @author harishkadamudi
 */
public interface PriceRepository extends JpaRepository<Price, Long> {
	
}
