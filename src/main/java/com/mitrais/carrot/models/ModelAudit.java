package com.mitrais.carrot.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Data
@EqualsAndHashCode(callSuper = false)
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(
        value = {"createdTime", "lastModifiedTime"},
        allowGetters = true
)
public abstract class ModelAudit implements Serializable {

	/**
	 * Generated Serial Version of Bazaar Class
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "is_deleted", nullable = true, columnDefinition = "boolean default false")
    private Boolean deleted;

    @CreatedDate
    @Column(name = "created_time", nullable = false, updatable = false)
    private LocalDateTime  createdTime = LocalDateTime.now();

    @Column(name = "created_by")
    private Integer createdBy;

    @LastModifiedDate
    @Column(name = "last_modified_time", nullable = false)
    private LocalDateTime lastModifiedTime;

    @Column(name = "last_modified_by")
    private Integer lastModifiedBy;

}
