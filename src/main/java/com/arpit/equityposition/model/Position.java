package com.arpit.equityposition.model;

import com.arpit.equityposition.utilities.ValidSides;

/**
 * This class will represent position of given security.
 * @author : Arpit
 * @version 1.0
 * @since : 23-Jun-2020
 */
public final class Position {
    private String securityCode;
    private Long version;
    private Long quantity;
    private Long tradeId;

    public Position(String securityCode, Long version, Long quantity, Long tradeId) {
        this.securityCode = securityCode;
        this.version = version;
        this.quantity = quantity;
        this.tradeId = tradeId;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public Long getVersion() {
        return version;
    }

    public Long getQuantity() {
        return quantity;
    }

    public Long getTradeId() {
        return tradeId;
    }
}