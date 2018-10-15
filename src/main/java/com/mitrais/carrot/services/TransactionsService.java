package com.mitrais.carrot.services;

import com.mitrais.carrot.models.Transactions;
import com.mitrais.carrot.repositories.TransactionsRepository;
import com.mitrais.carrot.services.interfaces.ITransactionsService;
import com.mitrais.carrot.validation.exception.ResourceNotFoundException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service Implementation Class for maintain Service for transactions
 * @author Wirdan_S773
 */
@Service
public class TransactionsService implements ITransactionsService {
    /**
     * transactions repository
     */
    private TransactionsRepository transactionsRepository;

    /**
     * the transaction service constructor
     * @param transactionsRepository the repository for transaction entity
     */
    public TransactionsService (@Autowired TransactionsRepository transactionsRepository){
        this.transactionsRepository = transactionsRepository;
    }

    /**
     * findAll will return a list of transaction

     */
    @Override
    public Iterable<Transactions> findAll() {
        return transactionsRepository.findAll();
    }


    /**
     * find transaction by id
     *
     * @param id the transaction id
     */
    @Override
    public Transactions findById(Integer id) {
        return transactionsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Data", "id", id));
    }

    /**
     * insert new transaction
     *
     * @param transactions the entity of transactions
     * @return created transactions
     */
    @Override
    public Transactions save(Transactions transactions) {
        return transactionsRepository.save(transactions);
    }

    /**
     * update transaction
     *
     * @param transactions the updated transactions
     * @return updated transactions
     */
    @Override
    public Transactions update(Transactions transactions1, Transactions transactions) {
        Transactions model = transactionsRepository.findById(transactions.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Data", "id", transactions.getId()));
        BeanUtils.copyProperties(transactions,model);

        model.setId(transactions.getId());
        return  transactionsRepository.save(model);
    }


    /**
     * updating transaction.isdeleted become true
     */
    @Override
    public void delete(Transactions transactions) {
        transactions.setDeleted(true);
        transactionsRepository.save(transactions);

    }
}
