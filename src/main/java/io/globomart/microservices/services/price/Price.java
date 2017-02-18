package io.globomart.microservices.services.price;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T_PRICE")
public class Price {

	public static Long nextId = 0L;
	
	
/*	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Long id;*/

	//protected String number;

/*	@Column(name = "name")
	protected String owner;

	protected BigDecimal balance;*/
	
	protected BigDecimal price;
	@Id
	private Long productId;
	
	/**
	 * This is a very simple, and non-scalable solution to generating unique
	 * ids. Not recommended for a real application. Consider using the
	 * <tt>@GeneratedValue</tt> annotation and a sequence to generate ids.
	 * 
	 * @return The next available id.
	 */
	/*
	 * protected static Long getNextId() { synchronized (nextId) { return
	 * nextId++; } }
	 */

	/**
	 * Default constructor for JPA only.
	 */
	protected Price() {
		price = BigDecimal.ZERO;
	}

	/*public Price(String number, String owner) {
		//id = getNextId();
		this.number = number;
		this.owner = owner;
		balance = BigDecimal.ZERO;
	}*/
	
	
	/*public long getId() {
		return id;
	}*/
	
	
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

	/**
	 * Set JPA id - for testing and JPA only. Not intended for normal use.
	 * 
	 * @param id
	 *            The new id.
	 */
	/*protected void setId(long id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	protected void setNumber(String accountNumber) {
		this.number = accountNumber;
	}

	public String getOwner() {
		return owner;
	}

	protected void setOwner(String owner) {
		this.owner = owner;
	}

	public BigDecimal getBalance() {
		return balance.setScale(2, BigDecimal.ROUND_HALF_EVEN);
	}

	public void withdraw(BigDecimal amount) {
		balance.subtract(amount);
	}

	public void deposit(BigDecimal amount) {
		balance.add(amount);
	}

	@Override
	public String toString() {
		return number + " [" + owner + "]: $" + balance;
	}*/

}
