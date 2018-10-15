package com.mitrais.carrot.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "transactions")
@JsonIgnoreProperties(
        value = {"createdTime", "lastModifiedTime"},
        allowGetters = true
)
public class Transactions extends ModelAudit {

    public Transactions() {
    }


    /**
     * @param id              id of transactions' entity
     * @param type            type of transactions type entity
     * @param toFrom          transactions' source
     * @param description     transactions' description
     * @param carrot          ammount of carrot in transactions
     * @param transactionDate transactions' date
     * @param status          transactions' status
     * @param user            user of the transactions
     * @param bazaar          bazaar of the transactions
     * @param rewards         rewards entity of the transactions
     */
    public Transactions(Integer id, @NotNull String type, @NotNull String toFrom, String description, @NotNull Integer carrot, LocalDateTime transactionDate, Integer status, User user, Bazaar bazaar, Rewards rewards) {
        this.id = id;
        this.type = type;
        this.toFrom = toFrom;
        this.description = description;
        this.carrot = carrot;
        this.transactionDate = transactionDate;
        this.status = status;
        this.user = user;
        this.bazaar = bazaar;
        this.rewards = rewards;
    }

    /**
	 * Generated Serial Version of Bazaar Class
	 */
	private static final long serialVersionUID = 1L;

    /**
     * id of transactions' entity
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
    type of transactions type entity
     */
    @NotNull
    @Column(name = "type", columnDefinition = "enum('reward','shared','bazaar')")
    private String type;

    /**
     * transactions' source
     */
    @NotNull
    @Column(name = "to_from")
    private String toFrom;

    /**
     * transactions' description
     */
    @Column(name = "description", columnDefinition = "text")
    private String description;

    /**
     * ammount of carrot in transactions
     */
    @NotNull
    private Integer carrot;

    /**
     * transactions' date
     */
    @Column(name = "transaction_date")
    private LocalDateTime transactionDate;

    /**
     * transactions' status
     */
    private Integer status;

    /**
     * user of the transactions
     */
    @OneToOne
    private User user;

    /**
     * bazaar of the transactions
     */
    @OneToOne
    private Bazaar bazaar;

    /**
     * rewards of the transactions
     */
    @OneToOne
    private Rewards rewards;

}
