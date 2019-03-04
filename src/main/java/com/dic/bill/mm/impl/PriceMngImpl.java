package com.dic.bill.mm.impl;

import com.dic.bill.mm.PriceMng;
import com.dic.bill.model.scott.Nabor;
import com.dic.bill.model.scott.Price;
import com.ric.cmn.Utl;
import com.ric.cmn.excp.ErrorWhileChrg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Сервис методов получения цены по услуге
 */
@Service
@Slf4j
public class PriceMngImpl implements PriceMng {

    @Autowired
    private ApplicationContext ctx;

    /**
     * умножить расценку на коэффициент, округлить
     * @param nabor - строка набора
     * @param priceColumn - колонку цены, которую использовать
     * @return - итогововая цена со всеми коэффициентами
     */
    @Override
    @Cacheable(cacheNames="PriceMng.multiplyPrice", key="{#nabor.getUsl(), " +
            "#nabor.getOrg(), #nabor.getKoeff(), #nabor.getNorm()}" )
    public BigDecimal multiplyPrice(Nabor nabor, int priceColumn) throws ErrorWhileChrg {
        log.info("ИСПОЛЬЗОВАН МЕТОД, НО НЕ КЭШ #nabor.getUsl()={}, " +
                        "#nabor.getOrg()={}, #nabor.getKoeff()={}, #nabor.getNorm()={}",
                nabor.getUsl().getId(),
                nabor.getOrg().getId(), nabor.getKoeff(), nabor.getNorm());
        BigDecimal coeff = Utl.nvl(nabor.getKoeff(), BigDecimal.ZERO);
        BigDecimal norm = Utl.nvl(nabor.getNorm(), BigDecimal.ZERO);
        if (nabor.getUsl().getSptarn().equals(3) ||
                nabor.getUsl().getFkCalcTp()!=null && Utl.in(nabor.getUsl().getFkCalcTp(), 24)) {
            // если поле norm является коэффициентом к расценке или тип расчета услуги fkCalcTp=24
            // (Бред! может поменять потом sptarn на 3 для таких услуг?) FIXME
            coeff=coeff.multiply(norm);
        }
        // получить базовую расценку, зависящую от организации
        Price basePrice = getBasePrice(nabor);
        if (basePrice == null) {
            throw new ErrorWhileChrg("ОШИБКА! Не найдена цена по услуге usl.id="+nabor.getUsl().getId());
        }
        // рассчитать цену
        BigDecimal price = BigDecimal.ZERO;
        if (priceColumn == 1) {
            price = basePrice.getPrice();
        } else if (priceColumn == 2) {
            price = basePrice.getPriceAddit();
        } else if (priceColumn == 3) {
            price = basePrice.getPriceEmpt();
        }
        return Utl.nvl(price, BigDecimal.ZERO).multiply(coeff)
                .setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * Получить базовую запись расценки, зависящей от организации
     * @param nabor - строка набора услуги
     */
    private Price getBasePrice(Nabor nabor) {
        Price foundPrice = null;
        for (Price price : nabor.getUsl().getPrice()) {
            if (price.getOrg()!=null && price.getOrg().equals(nabor.getOrg())) {
                return price;
            } else {
                foundPrice=price;
            }
        }
        // не найдено по организации, вернуть без организации
        return foundPrice;
    }


}
