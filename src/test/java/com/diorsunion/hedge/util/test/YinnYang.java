package com.diorsunion.hedge.util.test;

import com.diorsunion.dbtest.spring.DBTestClassRunner;
import com.diorsunion.hedge.dal.entity.Stock;
import com.diorsunion.hedge.dal.repository.StockPriceRepository;
import com.diorsunion.hedge.dal.repository.StockRepository;
import com.google.common.collect.Lists;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author harley-dog on 2015/7/17.
 */
@RunWith(DBTestClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:springbeans-ds-real-test.xml",
        "classpath:springbeans-mybatis.xml"
}) //还是用配置文件方便些
public class YinnYang {

    @Resource
    StockPriceRepository stockPriceRepository;

    @Resource
    StockRepository stockRepository;

    final static List<Integer> stockIds = Lists.newArrayList(6, 7);
    public final static SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
    public static Date begin;
    public static Date end;

    static {
        try {
            begin = s.parse("2015-01-02");
            end = s.parse("2015-01-02");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @org.junit.Test
    public void test() {
        Stock s1 = stockRepository.get(6);
        Stock s2 = stockRepository.get(6);
//        List<> stockPriceRepository.findSharePrice(s1,begin,end,"1day");
    }
}
