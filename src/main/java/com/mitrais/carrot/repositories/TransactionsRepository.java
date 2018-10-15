package com.mitrais.carrot.repositories;

import com.mitrais.carrot.models.Transactions;
import org.springframework.data.repository.CrudRepository;

/**
 * Transactions repository
 */
public interface TransactionsRepository extends CrudRepository<Transactions, Integer> {

    /**
     * find all data by is deleted value condition
     *
     * @param isdeleted | deleted value
     * @return all transactions data with criteria isDelete
     */
    public Iterable<Transactions> findBydeletedIn(Integer isdeleted);

    /**
     * find all data by is deleted condition
     *
     * @return all data without deleted
     */
    public Iterable<Transactions> findAllBydeletedIsNull();
}
