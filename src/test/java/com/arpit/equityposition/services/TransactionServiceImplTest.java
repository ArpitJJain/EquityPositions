package com.arpit.equityposition.services;

import com.arpit.equityposition.model.Position;
import com.arpit.equityposition.model.Transaction;
import com.arpit.equityposition.utilities.ValidActions;
import com.arpit.equityposition.utilities.ValidSides;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class TransactionServiceImplTest {

    @org.junit.jupiter.api.Test
    void gievnStatementTest() {
        PositionService pos = new PositionServiceImpl();
        TransactionService txs = new TransactionServiceImpl(pos);
        Transaction t1 = new Transaction(1,1,1,"REL",50, ValidActions.INSERT, ValidSides.BUY);
        Transaction t2 = new Transaction(2,2,1,"ITC",40, ValidActions.INSERT, ValidSides.SELL);
        Transaction t3 = new Transaction(3,3,1,"INF",70, ValidActions.INSERT, ValidSides.BUY);
        Transaction t4 = new Transaction(4,1,2,"REL",60, ValidActions.UPDATE, ValidSides.BUY);
        Transaction t5 = new Transaction(5,2,2,"ITC",30, ValidActions.CANCEL, ValidSides.BUY);
        Transaction t6 = new Transaction(6,4,1,"INF",20, ValidActions.INSERT, ValidSides.SELL);
        txs.processTransaction(t1);
        txs.processTransaction(t2);
        txs.processTransaction(t3);
        txs.processTransaction(t4);
        txs.processTransaction(t5);
        txs.processTransaction(t6);
        Position p = pos.getPositions("REL",1l);
        assertEquals(60,p.getQuantity());
        p = pos.getPositions("INF",4l);
        assertEquals(50, p.getQuantity());
        p = pos.getPositions("ITC",2l);
        assertEquals(0,p.getQuantity());

    }

    @org.junit.jupiter.api.Test
    public void insertOnSameVersionOnSameTradeID(){
        PositionService pos = new PositionServiceImpl();
        TransactionService txs = new TransactionServiceImpl(pos);
        Transaction t1 = new Transaction(1,1,1,"REL",50, ValidActions.INSERT, ValidSides.BUY);
        Transaction t2 = new Transaction(2,1,1,"REL",40, ValidActions.INSERT, ValidSides.BUY);
        txs.processTransaction(t1);
        txs.processTransaction(t2);
        Position p = pos.getPositions("REL",1l);
        assertEquals(50,p.getQuantity());
    }
    @org.junit.jupiter.api.Test
    public void updateOnSameVersionOnSameTradeID(){
        PositionService pos = new PositionServiceImpl();
        TransactionService txs = new TransactionServiceImpl(pos);
        Transaction t1 = new Transaction(1,1,1,"REL",50, ValidActions.INSERT, ValidSides.BUY);
        Transaction t2 = new Transaction(2,1,1,"REL",40, ValidActions.UPDATE, ValidSides.BUY);
        txs.processTransaction(t1);
        txs.processTransaction(t2);
        Position p = pos.getPositions("REL",1l);
        assertEquals(50,p.getQuantity());
    }

    @org.junit.jupiter.api.Test
    public void updateSecurity(){
        PositionService pos = new PositionServiceImpl();
        TransactionService txs = new TransactionServiceImpl(pos);
        Transaction t1 = new Transaction(1,1,1,"REL",50, ValidActions.INSERT, ValidSides.BUY);
        Transaction t2 = new Transaction(2,1,2,"ITC",40, ValidActions.UPDATE, ValidSides.SELL);
        txs.processTransaction(t1);
        txs.processTransaction(t2);
        Position p = pos.getPositions("REL",1l);
        assertEquals(-40,p.getQuantity());
        assertEquals("ITC",p.getSecurityCode());
    }

    @org.junit.jupiter.api.Test
    public void cancleSecurityWithTradeId(){
        PositionService pos = new PositionServiceImpl();
        TransactionService txs = new TransactionServiceImpl(pos);
        Transaction t1 = new Transaction(1,1,1,"REL",50, ValidActions.INSERT, ValidSides.BUY);
        Transaction t2 = new Transaction(2,1,2,"ITC",40, ValidActions.CANCEL, ValidSides.SELL);
        txs.processTransaction(t1);
        txs.processTransaction(t2);
        Position p = pos.getPositions("REL",1l);
        assertEquals(0,p.getQuantity());
        assertEquals("REL",p.getSecurityCode());
    }

    @org.junit.jupiter.api.Test
    public void outOfSequenseTransaction(){
        PositionService pos = new PositionServiceImpl();
        TransactionService txs = new TransactionServiceImpl(pos);
        Transaction t1 = new Transaction(1,1,2,"REL",50, ValidActions.UPDATE, ValidSides.BUY);
        Transaction t2 = new Transaction(2,1,1,"ITC",40, ValidActions.INSERT, ValidSides.SELL);
        txs.processTransaction(t1);
        txs.processTransaction(t2);
        Position p = pos.getPositions("REL",1l);
        assertEquals(50,p.getQuantity());
        assertEquals("REL",p.getSecurityCode());
    }

}