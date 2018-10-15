package com.mitrais.carrot.services.interfaces;

import com.mitrais.carrot.models.Transactions;


/**
 * Transactions Service as Interface for accessing JPA Repository of SharingType
 *
 * @author Wirdan_s773
 *
 */

public interface ITransactionsService {
    public Iterable<Transactions> findAll();
    public Transactions findById(Integer id);
    public Transactions save(Transactions transactions);
    public Transactions update(Transactions transactions1, Transactions transactions);
    public void delete(Transactions transactions);
}
