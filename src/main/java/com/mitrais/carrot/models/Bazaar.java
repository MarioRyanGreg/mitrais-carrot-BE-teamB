package com.mitrais.carrot.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;
import java.util.HashSet;		
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Entity Class of Bazaar served also for Hibernate connection to
 * MysqlDatabase
 * 
 * @author Ryan Mario - Using Data annotation from lombok to serve as setter and
 *         getter
 */

@Entity
@Table(name = "bazaar")
@JsonIgnoreProperties(
        value = {"createdTime", "lastModifiedTime"},
        allowGetters = true
)
@Data
@EqualsAndHashCode(callSuper=false)
public class Bazaar extends ModelAudit {
	
	/**
	 * Generated Serial Version of Bazaar Class
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Simple Constructor of Bazaar Item
	 */
	public Bazaar() {
	}
	
	/**
	 * Constructor for BazaarItem, initiate all compulsory param
	 * 
	 * @param id Id of Bazaar
	 * @param bazarName Name of Bazaar
	 * @param startPeriode Start Period of Bazaar
	 * @param endPeriode End Period of Bazaar
	 * @param description Description of Bazaar
	 * @param status Status of Bazaar
	 * @param bazaarItems Items available on Bazaar
	 */
	public Bazaar(Integer id, @NotNull @Size(max = 50) String bazarName, @NotNull LocalDateTime startPeriode,
			@NotNull LocalDateTime endPeriode, String description, Integer status, Set<BazaarItem> bazaarItems) {
		super();
		this.id = id;
		this.bazarName = bazarName;
		this.startPeriode = startPeriode;
		this.endPeriode = endPeriode;
		this.description = description;
		this.status = status;
		this.bazaarItems = bazaarItems;
	}
	
	/*
	 * the id for every Bazaar
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

	/*
	 * the name of Bazaar
	 */
    @NotNull
    @Size(max = 50)
    @Column(name = "bazar_name")
    private String bazarName;

    /*
	 * the start period of Bazaar
	 */
    @NotNull
    @Column(name = "start_period")
    private LocalDateTime startPeriode;

    /*
	 * the end period of Bazaar
	 */
    @NotNull
    @Column(name = "end_period")
    private LocalDateTime endPeriode;

    /*
   	 * the description of Bazaar
   	 */
    @Column(name = "description", columnDefinition = "text")
    private String description;

    /*
   	 * the status of Bazaar
   	 */
    private Integer status;

    /*
	 * the object connector to bazarItem object
	 */
    @OneToMany(mappedBy = "bazaar")
    private Set<BazaarItem> bazaarItems = new HashSet<>();

}
