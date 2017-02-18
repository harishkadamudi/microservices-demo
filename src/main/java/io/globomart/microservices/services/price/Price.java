package io.globomart.microservices.services.price;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "T_PRICE")
public class Price {

	public static Long nextId = 0L;
	
	@NotNull
	protected BigDecimal price;

	/**
	 * This is a very simple, and non-scalable solution to generating unique
	 * ids. Not recommended for a real application. Consider using the
	 * <tt>@GeneratedValue</tt> annotation and a sequence to generate ids.
	 * 
	 * @return The next available id.
	 */
	@Id
	@NotNull
	private Long productId;
	
	/**
	 * Default constructor for JPA only.
	 */
	protected Price() {
		price = BigDecimal.ZERO;
	}
	
	public Price(BigDecimal price, Long productId) {
		super();
		this.price = price;
		this.productId = productId;
	}
	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

}
