package com.mitrais.carrot.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Barn Model
 * 
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "barn", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "role_name"
        })
})
@JsonIgnoreProperties(
        value = {"createdTime", "lastModifiedTime"},
        allowGetters = true
)
public class Barn extends ModelAudit {
	
	/**
	 * Generated Serial Version of Bazaar Class
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Identifier
	 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    /**
     * The name of Barn
     */
    @NotNull
    @Size(max = 50)
    @Column(name = "role_name")
    private String name;
    
    
    /**
     * The Start Period of Barn
     */
    @Column(name = "start_periode")
    private LocalDateTime startPeriode;

    /**
     * The End Period of Barn
     */
    @Column(name = "end_periode")
    private LocalDateTime endPeriode;

    /**
     * The owner of Barn
     */
    @NotNull
    private String owner;
    
    /**
     * The carrot number of Employee
     */
    @Column(name = "carrot_per_employee")
    private Integer carrotPerEmployee;
    
    /**
     * The total Carrot
     */
    @Column(name = "total_carrot")
    private Integer totalCarrot;

    /**
     * The status of Barn
     */
    private Integer status;
    
    
    /**
     * The release status of Barn
     */
    @Column(name = "is_released")
    private Boolean isReleased;

    /**
     * default constructor
     */
    public Barn() {}
}
