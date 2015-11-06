package com.diorsunion.hedge.bo.stockdatainit;

import com.diorsunion.hedge.bo.StockPriceInit;
import com.diorsunion.hedge.dal.entity.Stock;
import com.diorsunion.hedge.dal.entity.StockPrice;
import com.diorsunion.hedge.dal.repository.StockPriceRepository;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 这个随机算法只适用于 正反指数股
 *
 * @author harley-dog on 2015/7/23.
 */
@Service(value = "RealStockPriceInit")
public class RealStockPriceInit implements StockPriceInit {

    @Resource
    StockPriceRepository repository;

    @Override
    public List<Date> init(Date begin, Date end, Stock... stocks) {
        List<Date> dates = Lists.newArrayList();
        Set<Date> datesSets = Sets.newHashSet();
        for (Stock stock : stocks) {
            List<StockPrice> stockPrices = repository.findSharePrice(stock, begin, end, StockPriceRepository.Period.day);
            stockPrices.forEach(stockPrice -> {

                stock.setStockPrice(stockPrice.thedate, stockPrice);
                datesSets.add(stockPrice.thedate);
            });
        }
        dates.addAll(datesSets);
        dates.sort((a, b) -> {
            return a.compareTo(b);
        });
        return dates;
    }
}
