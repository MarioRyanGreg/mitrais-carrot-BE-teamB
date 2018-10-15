package com.mitrais.carrot.controllers;

import com.mitrais.carrot.models.Transactions;
import com.mitrais.carrot.payload.ApiResponse;

import com.mitrais.carrot.services.TransactionsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;


@CrossOrigin
@RestController
@RequestMapping("${spring.data.rest.basePath}")
@Api(value = "transactions", description = "Crud service for transactions")
public class TransactionsController {

    public TransactionsService transactionsService;

    public TransactionsController(@Autowired TransactionsService transactionsService) {
        this.transactionsService = transactionsService;
    }

    /**
     * get all transactions, method Get
     *
     * @return collection of transactions with response status
     */
    @GetMapping("/transactions")
    @ResponseBody
    @ApiOperation(value = "Get all transactions", notes = "Returns list of all transaction in the database.")
    public Iterable<Transactions> all() {
        return transactionsService.findAll();
    }

    /**
     * create new transactions, method POST
     *
     * @param body model of Transactions
     * @return ResponseEntity it Contain Collection of transactions with response status
     */
    @PostMapping("/transactions")
    @ResponseBody
    @ApiOperation(value = "Create new transaction", notes = "Create new transaction.")
    public ResponseEntity<ApiResponse> save(@RequestBody @Valid Transactions body) {
        body = transactionsService.save(body);
        return new ResponseEntity<>(new ApiResponse( true, ""), HttpStatus.CREATED);
    }


    /**
     * get transaction by id
     *
     * @param id the transaction id
     * @return ResponseEntity, it contains collection of transaction
     */
    @GetMapping("/transactions/{id}")
    @ResponseBody
    @ApiOperation(value = "Get one transaction by id", notes = "Get specific transaction by transaction id.")
    public Transactions detail(@PathVariable Integer id ){return  transactionsService.findById(id);}


    /**
     * update transaction by id, method PUT
     *
     * @param id           transaction id
     * @param body model of Transactions
     * @return The model with HTTP status OK
     */
    @PutMapping("/transactions/{id}")
    @ResponseBody
    @ApiOperation(value = "Update transaction by id", notes = "Update transaction data by transaction id.")
    public ResponseEntity<Transactions> update(@PathVariable Integer id , @Valid @RequestBody Transactions body){
        Transactions model = transactionsService.findById(id);
        BeanUtils.copyProperties(model, body);
        model.setId(id);
        transactionsService.save(model);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    /**
     * delete a transaction by id, method DELETE
     *
     * @param id transaction id
     * @return ResponseEntity it contain Collection of transaction with response status
     */
    @DeleteMapping("/transactions/{id}")
    @ResponseBody
    @ApiOperation(value = "Delete transaction by id", notes = "Delete transaction by transaction id, this system only mark deleted as true.")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        Transactions trans = transactionsService.findById(id);
    trans.setDeleted(true);
    transactionsService.save(trans);
    return new ResponseEntity<>(
            new ApiResponse(true, "Data id : " + id + "deleted successfully"),
            HttpStatus.OK
    );
    }


}

