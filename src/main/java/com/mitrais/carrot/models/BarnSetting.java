package com.mitrais.carrot.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * BarnSetting Model
 * @author Deta_M557
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "barn_setting", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "role_name"
        })
})
@JsonIgnoreProperties(
        value = {"createdTime", "lastModifiedTime"},
        allowGetters = true
)
public class BarnSetting extends ModelAudit {

	/**
	 * Generated Serial Version of Bazaar Class
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The BarnSetting identifier
	 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    /**
     * The role name of Barn Setting
     */
    @NotNull
    @Size(max = 50)
    @Column(name = "role_name")
    private String name;
    
    /**
     * The description of Barn Setting
     */
    @Column(name = "description", columnDefinition = "text")
    private String description;

    /**
     * The carrot of Barn Setting
     */
    @NotNull
    private Integer carrot;
    
    
    /**
     * The Localdate of BarnSetting
     */
    @Column(name = "date")
    private LocalDateTime date;
    
    /**
     * The release status of BarnSetting
     */
    @Column(name = "is_released")
    private Integer isReleased;

    /**
     * The Barn object of BarnSetting
     */
    @OneToOne
    private Barn barn;
    
    /**
     * The reward entities of BarnSetting
     */
    @OneToOne
    private Rewards rewards;

}
