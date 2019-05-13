package com.dic.bill.dao;

import java.util.List;

import com.dic.bill.dto.SumUslOrgDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dic.bill.dto.SumRecMg;
import com.dic.bill.dto.SumUslOrgRec;
import com.dic.bill.model.scott.SaldoUsl;
import com.ric.dto.SumSaldoRec;


public interface SaldoUslDAO extends JpaRepository<SaldoUsl, Integer> {

    /**
     * Получить записи задолженностей из V_CHARGEPAY
     *
     * @param lsk    - лицевой счет
     * @param period - необходимый период
     */
    @Query(value = "select t.mg, t.summa "
            + "from SCOTT.V_CHARGEPAY t "
            + "where t.lsk=:lsk and t.period=:period "
            + "and nvl(t.summa,0) <> 0 "
            + "order by t.mg", nativeQuery = true)
    List<SumRecMg> getVchargePayByLsk(@Param("lsk") String lsk, @Param("period") Integer period);

    /**
     * Получить записи начислений из ARCH_CHARGES + A_NABOR2
     *
     * @param lsk    - лицевой счет
     * @param period - необходимый период
     */
    @Query(value = "select n.org as orgId, t.summa_it as summa, t.usl_id as uslId from SCOTT.ARCH_CHARGES t "
            + "left join SCOTT.A_NABOR2 n on t.lsk = n.lsk and t.usl_id=n.usl "
            + "and t.mg between n.mgFrom and n.mgTo "
            + "where t.mg=:period "
            + "and t.lsk=:lsk "
            + "and nvl(t.summa_it,0) > 0", // только положительные значения!
            nativeQuery = true)
    List<SumUslOrgRec> getChargeNaborByLsk(@Param("lsk") String lsk, @Param("period") Integer period);

    /**
     * Получить записи начислений из XITOG3
     *
     * @param lsk    - лицевой счет
     * @param period - необходимый период
     */
    @Query(value = "select t.org as b1, t.charges as c1, t.usl as a1 from SCOTT.XITOG3_LSK t "
            + "where t.mg=:period "
            + "and t.lsk=:lsk "
            + "and nvl(t.charges,0) > 0", // только положительные значения!
            nativeQuery = true)
    List<SumUslOrgRec> getChargeXitog3ByLsk(@Param("lsk") String lsk, @Param("period") Integer period);

    /**
     * Получить записи вх.сальдо по пене из XITOG3
     *
     * @param lsk    - лицевой счет
     * @param period - необходимый период
     */
    @Query(value = "select t.usl as uslId, t.org as orgId, t.pinsal as summa from SCOTT.XITOG3_LSK t "
            + "where t.mg=:period "
            + "and t.lsk=:lsk "
            + "and nvl(t.pinsal,0) <> 0",
            nativeQuery = true)
    List<SumUslOrgRec> getPinSalXitog3ByLsk(@Param("lsk") String lsk, @Param("period") String period);

    /**
     * Получить записи сальдо
     *
     * @param lsk - лицевой счет
     * @param mg  - необходимый период
     */
    @Query(value = "select t.usl.id as uslId, t.org.id as orgId, t.summa as summa "
            + "from SaldoUsl t "
            + "where t.kart.lsk=:lsk and t.mg=:mg "
            + "and nvl(t.summa,0) <> 0")
    List<SumUslOrgRec> getSaldoUslByLsk(@Param("lsk") String lsk, @Param("mg") String mg);

    /**
     * Получить записи дебетового сальдо
     *
     * @param mg - необходимый период
     */
    @Query(value = "select t "
            + "from SaldoUsl t "
            + "where t.mg=:mg "
            + "and nvl(t.summa,0) > 0")
    List<SaldoUsl> getDebitSaldoUslByLsk(@Param("mg") String mg);

    /**
     * Получить записи кредитового сальдо
     *
     * @param mg - необходимый период
     */
    @Query(value = "select t "
            + "from SaldoUsl t "
            + "where t.mg=:mg "
            + "and nvl(t.summa,0) < 0")
    List<SaldoUsl> getCreditSaldoUsl(@Param("mg") String mg);

    /**
     * Получить записи сальдо при наличии сальдо с другим знаком по данному лс
     *
     * @param mg - необходимый период
     */
    @Query(value = "select t " +
            "from SaldoUsl t " +
            "where exists (select d.kart.lsk from SaldoUsl d " +
            "where d.kart=t.kart and d.mg=t.mg and sign(nvl(d.summa,0)) != sign(nvl(t.summa,0))) " +
            "and t.mg=:mg ")
    List<SaldoUsl> getSaldoUslWhereCreditAndDebitExists(@Param("mg") String mg);

    /**
     * Получить записи сальдо при наличии кредита по определенной услуге, и дебета по другим услугам
     * @param usl - услуга
     * @param mg - необходимый период
     */
    @Query(value = "select t " +
            "from SaldoUsl t " +
            "where exists (select d.kart.lsk from SaldoUsl d " +
            "where d.usl.id=:usl and d.mg=t.mg and d.kart=t.kart and nvl(d.summa,0)  < 0) " +
            "and exists (select d.kart.lsk from SaldoUsl d " +
            "where d.mg=t.mg and d.kart=t.kart and nvl(d.summa,0)  > 0) " +
            "and t.mg=:mg ")
    List<SaldoUsl> getSaldoUslWhereCreditAndDebitExistsByUsl(@Param("usl") String usl, @Param("mg") String mg);

    /**
     * Получить записи всех лиц счетов, где есть долги или переплаты
     *
     * @param lskFrom - начальный лиц.счет
     * @param lskTo   - конечный лиц.счет
     */
    @Query(value = "select a.lsk  from ( " +
            "select t.lsk, t.mg, sum(t.summa) as summa from scott.v_chargepay t " +
            "where t.period=:period and t.lsk between :lskFrom and :lskTo " +
            "group by t.lsk, t.mg " +
            ") a " +
            "group by a.lsk " +
            "having sum(a.summa) <> 0 " +
            "order by a.lsk", nativeQuery = true)
    List<String> getAllWithNonZeroDeb(@Param("lskFrom") String lskFrom, @Param("lskTo") String lskTo,
                                      @Param("period") String period);


    /**
     * Получить сальдо, оплату, перерасчеты по лицевому счету
     *
     * @param lsk    лицевой счет
     * @param period - период
     */
    @Query(value = "select sum(t.indebet) as \"indebet\", sum(t.inkredit) as \"inkredit\", "
            + "sum(t.outdebet) as \"outdebet\", sum(t.outkredit) as \"outkredit\", "
            + "sum(t.payment) as \"payment\" "
            + "from SCOTT.XITOG3_LSK t "
            + "where t.lsk = :lsk and t.mg = :period",
            nativeQuery = true)
    SumSaldoRec getSaldoByLsk(@Param("lsk") String lsk, @Param("period") String period);


    /**
     * Получить список сальдо определенного знака, за период
     *
     * @param mg - необходимый период
     */
    @Query(value = "select t "
            + "from SaldoUsl t "
            + "where t.mg=:mg "
            + "and sign(t.summa) = :sign")
    List<SaldoUsl> getSaldoUslBySign(@Param("mg") String mg, @Param("sign") Integer sign);

}
