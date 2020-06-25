package com.arpit.equityposition.model;

import com.arpit.equityposition.utilities.ValidActions;
import com.arpit.equityposition.utilities.ValidSides;

/**
 * This class will represent one single transaction.
 * @author : Arpit
 * @version 1.0
 * @since : 23-Jun-2020
 */
public final class Transaction {
    private long transactionID;
    private long tradeID;
    private long version;
    private String securityCode;
    private long quantity;
    private ValidActions action;//Insert/Update/Cancel
    private ValidSides side;//Buy/Sell

    public Transaction(long transactionID, long tradeID, long version, String securityCode, long quantity, ValidActions action, ValidSides side) {
        this.transactionID = transactionID;
        this.tradeID = tradeID;
        this.version = version;
        this.securityCode = securityCode;
        this.quantity = quantity;
        this.action = action;
        this.side = side;
    }



    public long getTransactionID() {
        return transactionID;
    }

    public long getTradeID() {
        return tradeID;
    }

    public long getVersion() {
        return version;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public long getQuantity() {
        return quantity;
    }

    public ValidActions getAction() {
        return action;
    }

    public ValidSides getSide() {
        return side;
    }
}