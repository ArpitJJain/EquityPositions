package com.arpit.equityposition.services;

import com.arpit.equityposition.model.Position;
import com.arpit.equityposition.model.Transaction;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;


/**
 * Service Interface for Position Operations.
 * @author : Arpit
 * @version 1.0
 * @since : 23-Jun-2020
 */
public interface PositionService {
    /**
     * Get the position for given securityCode first. if not found get for TradeId
     * @param securityCode
     * @param tradeId
     * @return
     */
    public Position getPositions(String securityCode,Long tradeId);

    /**
     * Add the position
     * @param p
     * @return
     */
    public Position addPosition(Position p);
    /**
     * Cancel the position
     * @param p
     * @return
     */
    public Position cancelPosition(Position p);
    /**
     * Update the position
     * @param p
     * @return
     */
    public Position updatePosition(Position p);

    /**
     * To Print All Positions
     * @return
     */
    public Collection<Position> getPositions();
}
