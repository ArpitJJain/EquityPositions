package com.arpit.equityposition.services;

import com.arpit.equityposition.model.Position;
import com.arpit.equityposition.model.Transaction;
/**
 * Service Implementation for Transaction Operations.
 * @author : Arpit
 * @version 1.0
 * @since : 23-Jun-2020
 */
public interface TransactionService {
    /**
     * To Process a transaction.
     * 1. This will validate the transaction.
     * 2. Will add/update/cancel based on action
     * @param txn
     */
    public void processTransaction(Transaction txn);

    /**
     * To add a transaction to position.
     * @param txn
     * @return
     */
    public boolean insertTransaction(Transaction txn);

    /**
     * To update the position.
     * SecurityCode, Quantity, Buy/Sell can change
     * @param txn
     * @return
     */
    public boolean updateTransaction(Transaction txn);

    /**
     * This Method will cancel the given transaction
     * @param txn
     * @return true if transactions is cancelled.
     */
    public boolean cancelTransaction(Transaction txn);
}
