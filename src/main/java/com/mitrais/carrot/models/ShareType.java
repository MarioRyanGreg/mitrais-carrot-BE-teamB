package com.mitrais.carrot.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * ShareType Entity
 * 
 * @author Medianto_L132
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "sharing_type", uniqueConstraints = { @UniqueConstraint(columnNames = { "sharing_type" }) })
@JsonIgnoreProperties(value = { "createdTime", "lastModifiedTime" }, allowGetters = true)
public class ShareType extends ModelAudit {

	/**
	 * Generated Serial Version of Bazaar Class
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * id ShareType Id auto increment
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * sharingType ShareType sharing type name.
	 */
	@NotNull
	@Size(max = 50)
	@Column(name = "sharing_type")
	private String sharingType;

	/**
	 * carrot Carrot the amount of carrot will be used in this sharingType
	 */
	@NotNull
	private Integer carrot;

}
