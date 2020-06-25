package com.arpit.equityposition.services;

import com.arpit.equityposition.model.Position;

import java.util.*;
/**
 * Service Implemenation for Position Operations.
 * @author : Arpit
 * @version 1.0
 * @since : 23-Jun-2020
 */

public class PositionServiceImpl implements PositionService{
    private Map<String,Position> secCodePositionMap = new HashMap<String, Position>();
    private Map<Long,Position> tradeIdPositionMap = new HashMap<Long, Position>();
    /**
     * Get the position for given securityCode first. if not found get for TradeId
     * @param securityCode
     * @param tradeId
     * @return
     */
    public Position getPositions(String securityCode,Long tradeId) {
        Position p =  secCodePositionMap.get(securityCode);
        if (p == null){
            p = tradeIdPositionMap.get(tradeId);
        }
        return p;
    }

    /**
     * Add the position
     * @param p
     * @return
     */
    public Position addPosition(Position p) {
        tradeIdPositionMap.put(p.getTradeId(),p);
        return secCodePositionMap.put(p.getSecurityCode(),p);
    }

    /**
     * Cancel the position
     * @param p
     * @return
     */
    public Position cancelPosition(Position p) {
        tradeIdPositionMap.remove(p.getTradeId());
        return secCodePositionMap.remove(p.getSecurityCode());
    }

    /**
     * Update the position
     * @param p
     * @return
     */
    @Override
    public Position updatePosition(Position p) {
        Position cachedPosition = getPositions(p.getSecurityCode(),p.getTradeId());
        cancelPosition(cachedPosition);
        return addPosition(p);
     }

    /**
     * To Print All Positions
     * @return
     */
    public Collection<Position> getPositions() {
        return secCodePositionMap.values();
    }

}