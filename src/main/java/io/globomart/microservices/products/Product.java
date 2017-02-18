package io.globomart.microservices.products;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Persistent Product entity with JPA markup. Products are stored in an H2
 * relational database.
 * 
 * @author harishkadamudi
 */
@Entity
@Table(name = "T_PRODUCT")
public class Product implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * This is a very simple, and non-scalable solution to generating unique
	 * ids. Not recommended for a real application. Consider using the
	 * <tt>@GeneratedValue</tt> annotation and a sequence to generate ids.
	 * 
	 * @return The next available id.
	 */
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	//@JsonIgnore
	@NotNull
	@Column(name = "productid")
	protected Long id;
	
	@Column(name = "productname")
	@NotNull
	protected String name;

	@Column(name = "productdescription")
	@NotNull
	protected String description;
	
	//protected BigDecimal balance;


	/**
	 * Default constructor for JPA only.
	 */
	protected Product() {
	}

	public Product(String number, String description) {
		this.name = number;
		this.description = description;
	}

	public long getId() {
		return id;
	}

	/**
	 * Set JPA id - for testing and JPA only. Not intended for normal use.
	 * 
	 * @param id
	 *            The new id.
	 */
	protected void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	protected void setDescription(String description) {
		this.description = description;
	}

}
