package com.dic.bill.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
     * Строка KwtpMg
     */
    @AllArgsConstructor
    @Getter
    @Setter
    public class KwtpMgRec {
        int kwtpMgId;
        String lsk;
        String strSumma;
        String strPenya;
        String strDebt;
        String dopl;
        int nink;
        String nkom;
        String oper;
        String strDtek;
        String strDatInk;
        boolean isTest;
    }
