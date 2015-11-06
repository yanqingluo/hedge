package com.diorsunion.hedge.dal.repository.test;

import com.diorsunion.dbtest.annotation.DataSet;
import com.diorsunion.dbtest.annotation.DataSets;
import com.diorsunion.hedge.dal.entity.Stock;
import com.diorsunion.hedge.dal.repository.StockRepository;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @author harley-dog on 2015/6/4.
 */
public class StockRepositoryTest extends RepositoryBaseTest {

    @Resource
    StockRepository repository;

    @Test
    @DataSets({
            @DataSet(entityClass = Stock.class, number = 1)
    })
    public void testGet() {
        Stock entity = repository.get(1);
        assert (entity != null);
    }
}
