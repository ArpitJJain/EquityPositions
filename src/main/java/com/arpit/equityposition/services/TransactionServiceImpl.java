package com.arpit.equityposition.services;

import com.arpit.equityposition.model.Position;
import com.arpit.equityposition.model.Transaction;
import com.arpit.equityposition.utilities.ValidActions;
import com.arpit.equityposition.utilities.ValidSides;

/**
 * Service Interface for Transaction Operations.
 * @author : Arpit
 * @version 1.0
 * @since : 23-Jun-2020
 */
public class TransactionServiceImpl implements TransactionService {
    private PositionService posSer;

    public TransactionServiceImpl(PositionService posSer) {
        this.posSer = posSer;
    }

    /**
     * To Process a transaction.
     * Will add/update/cancel based on action
     *
     * @param txn
     */
    public void processTransaction(Transaction txn) {
        if(ValidActions.INSERT.equals(txn.getAction())){
            insertTransaction(txn);
        }else if(ValidActions.UPDATE.equals(txn.getAction())){
            updateTransaction(txn);
        }else if(ValidActions.CANCEL.equals(txn.getAction())){
            cancelTransaction(txn);
        }
    }

    /**
     * To add a transaction to position.
     *
     * @param txn
     * @return
     */
    public boolean insertTransaction(Transaction txn) {
        Position p = posSer.getPositions(txn.getSecurityCode(),txn.getTradeID());
        if(p == null){

            posSer.addPosition(new Position(txn.getSecurityCode(),txn.getVersion(),getActualQuanity(txn.getQuantity(),txn.getSide()),txn.getTradeID()));
        }else{
            if ( p.getTradeId() >= txn.getTradeID() && p.getVersion() >= txn.getVersion()){
                System.out.println("Position is already on an advanced version.");
                return false;
            }else{
                long quantity = getActualQuanity(txn.getQuantity(),txn.getSide());
                posSer.updatePosition(new Position(txn.getSecurityCode(), txn.getVersion(), p.getQuantity() + quantity,txn.getTradeID()));
            }
        }
        return false;
    }

    /**
     * This Method will cancel the given transaction
     *
     * @param txn
     * @return true if transactions is cancelled.
     */
    public boolean cancelTransaction(Transaction txn) {
        Position p = posSer.getPositions(txn.getSecurityCode(),txn.getTradeID());
        if(null == p){
            System.out.println("Not A Valid Operation. No Existing Position Found to cancel.");
            return false;
        }
        posSer.updatePosition(new Position(p.getSecurityCode(),txn.getVersion(),0l,txn.getTradeID()));
        return true;
    }

    /**
     * To update the position.
     * SecurityCode, Quantity, Buy/Sell can change
     *
     * @param txn
     * @return
     */
    public boolean updateTransaction(Transaction txn) {
        Position p = posSer.getPositions(txn.getSecurityCode(),txn.getTradeID());
        if(p == null) {
            posSer.addPosition(new Position(txn.getSecurityCode(), txn.getVersion(), getActualQuanity(txn.getQuantity(),txn.getSide()), txn.getTradeID()));
        }else if ( p.getTradeId() >= txn.getTradeID() && p.getVersion() >= txn.getVersion()){
            System.out.println("This is not a valid operation. ");
        }else{
            posSer.updatePosition(new Position(txn.getSecurityCode(),txn.getVersion(),getActualQuanity(txn.getQuantity(),txn.getSide()),txn.getTradeID()));
        }
        return false;
    }

    /**
     * This method will utilize the side to get actual quatity.
     * @param quantity
     * @param side
     * @return
     */
    private long getActualQuanity(long quantity, ValidSides side){
        if (ValidSides.SELL.equals(side)){
            return quantity*-1l;
        }
        else return quantity;
    }
}
