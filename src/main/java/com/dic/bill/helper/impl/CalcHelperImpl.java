package com.dic.bill.helper.impl;

import com.dic.bill.dao.AddrTpDAO;
import com.dic.bill.dao.KoDAO;
import com.dic.bill.dao.LstDAO;
import com.dic.bill.helper.CalcHelper;
import com.dic.bill.mm.LstMng;
import com.dic.bill.model.bs.AddrTp;
import com.dic.bill.model.bs.Lst;
import com.dic.bill.model.scott.Ko;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Вспомогательные методы обработки данных
 */
@Service
public class CalcHelperImpl implements CalcHelper  {



}