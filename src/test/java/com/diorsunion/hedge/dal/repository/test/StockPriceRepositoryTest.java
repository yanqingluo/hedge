package com.diorsunion.hedge.dal.repository.test;

import com.diorsunion.dbtest.annotation.DataSet;
import com.diorsunion.dbtest.annotation.DataSets;
import com.diorsunion.hedge.dal.entity.Stock;
import com.diorsunion.hedge.dal.entity.StockPrice;
import com.diorsunion.hedge.dal.repository.StockPriceRepository;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author harley-dog on 2015/6/4.
 */
public class StockPriceRepositoryTest extends RepositoryBaseTest {

    @Resource
    StockPriceRepository repository;

    @Test
    @DataSets({
            @DataSet(entityClass = StockPrice.class, number = 1, tableName = "stock_price_day")
    })
    public void testGet() {
        StockPrice entity = repository.get(1, StockPriceRepository.Period.day);
        assert (entity != null);
    }

    @Test
    @DataSets({
            @DataSet(entityClass = StockPrice.class, number = 0, tableName = "stock_price_day"),
            @DataSet(entityClass = StockPrice.class, number = 0, tableName = "stock_price_week"),
            @DataSet(entityClass = StockPrice.class, number = 0, tableName = "stock_price_month")
    })
    public void testInsert() {
        for (StockPriceRepository.Period period : StockPriceRepository.Period.values()) {
            StockPrice entity = getStockPrice();
            StockPrice entity1 = repository.get(1, period);
            assert (entity1 == null);
            repository.insert(entity, period);
            StockPrice stockPrice = repository.get(entity.id, period);
            assert (entity.id == stockPrice.id);
            assert (entity.chg == stockPrice.chg);
            assert (entity.open == stockPrice.open);
            assert (entity.close == stockPrice.close);
            assert (entity.dea == stockPrice.dea);
            assert (entity.dif == stockPrice.dif);
            assert (entity.high == stockPrice.high);
            assert (entity.low == stockPrice.low);
            assert (entity.macd == stockPrice.macd);
            assert (entity.ma5 == stockPrice.ma5);
            assert (entity.ma10 == stockPrice.ma10);
            assert (entity.ma20 == stockPrice.ma20);
            assert (entity.ma30 == stockPrice.ma30);
            assert (entity.percent == stockPrice.percent);
            assert (entity.volume == stockPrice.volume);
        }
    }

    public static StockPrice getStockPrice() {
        StockPrice entity = new StockPrice();
        Stock stock = new Stock();
        stock.id = 1;
        stock.code = "BABA";
        entity.chg = 0.1;
        entity.close = 0.2;
        entity.dea = 0.3;
        entity.dif = 0.4;
        entity.high = 0.5;
        entity.low = 0.6;
        entity.ma10 = 0.7;
        entity.ma20 = 0.8;
        entity.ma30 = 0.9;
        entity.ma5 = 0.10;
        entity.macd = 0.11;
        entity.open = 0.32;
        entity.percent = 0.13;
        entity.volume = 10;
        entity.stock = stock;
        entity.turnrate = 0.26;
        entity.thedate = new Date();
        return entity;
    }

}
