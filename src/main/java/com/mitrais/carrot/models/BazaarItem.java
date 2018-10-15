package com.mitrais.carrot.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Entity Class of BazaarItem served also for Hibernate connection to
 * MysqlDatabase
 * 
 * @author Ryan Mario - Using Data annotation from lombok to serve as setter and
 *         getter
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "item")
@JsonIgnoreProperties(
        value = {"createdTime", "lastModifiedTime", "bazaar"},
        allowGetters = true
)
public class BazaarItem extends ModelAudit {

	/**
	 * Generated Serial Version of BazaarItem Class
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Simple Constructor of Bazaar Item
	 */
	public BazaarItem() {
	}
	
	/**
	 * Constructor for BazaarItem, initiate all compulsory param
	 * 
	 * @param id Id of Bazaar Item
	 * @param name Name of Bazaar Item
	 * @param description Description of Bazaar Item
	 * @param picture Picture of Bazaar Item
	 * @param exchangeRate Exchange Rate of Bazaar Item
	 * @param totalItem Total Item of Bazaar Item
	 * @param autoApproveTransactions Auto Approve Transactions number of Bazaar Item
	 * @param itemOnSale Number of Item On Sale of Bazaar Item
	 * @param bazaar For managing relation from Bazaar Item to Bazaar
	 */
	public BazaarItem(Integer id, @NotNull String name, String description, String picture, Float exchangeRate,
			Integer totalItem, Integer autoApproveTransactions, Integer itemOnSale, Bazaar bazaar) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.picture = picture;
		this.exchangeRate = exchangeRate;
		this.totalItem = totalItem;
		this.autoApproveTransactions = autoApproveTransactions;
		this.itemOnSale = itemOnSale;
		this.bazaar = bazaar;
	}

	/*
	 * the id for every Bazaar Item
	 */
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
    private Integer id;

	/*
	 * the name of Bazaar Item
	 */
    @NotNull
    @Column(name = "name")
    private String name;

    /*
     * the description of Bazaar Item
     */
    @Column(name = "description", columnDefinition = "text")
    private String description;

    /*
     * the picture of Bazaar Item
     */
    private String picture;

    /*
     * the exchange rate of Bazaar Item
     */
    @Column(name = "exchange_rate")
    private Float exchangeRate;

    /*
     * the total items of Bazaar Item
     */
    @Column(name = "total_item")
    private Integer totalItem;

    /*
     * the auto approve transactions number of Bazaar Item
     */
    @Column(name = "auto_approve_transactions")
    private Integer autoApproveTransactions;

    /*
     * the number of items on sale of Bazaar Item
     */
    @Column(name = "item_on_sale")
    private Integer itemOnSale;
    
    @ManyToOne	    
    @JoinColumn(name = "bazaar_id", nullable = false)
    private Bazaar bazaar;
}
