package com.diorsunion.hedge.dal.repository;

import com.diorsunion.hedge.common.BaseRepository;
import com.diorsunion.hedge.dal.entity.Stock;
import com.diorsunion.hedge.dal.entity.StockPrice;
import org.apache.ibatis.annotations.Param;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author harley-dog on 2015/6/4.
 */
@Resource
public interface StockPriceRepository extends BaseRepository<StockPrice, Integer> {
    Date getMaxDate(StockPrice query);

    void insert(@Param("entity") StockPrice entity, @Param("period") Period period);

    void delete(@Param("id") int id, @Param("period") Period period);

    List<StockPrice> findSharePrice(@Param("stock") Stock stock, @Param("begin") Date begin, @Param("end") Date end, @Param("period") Period period);

    StockPrice get(@Param("id") int id, @Param("period") Period period);

    enum Period {
        week("1week"),
        month("1month"),
        day("1day");

        public final String value;

        Period(String value) {
            this.value = value;
        }
    }
}
