package com.diorsunion.hedge.bo;

import com.diorsunion.hedge.dal.entity.Stock;

import java.util.Date;
import java.util.List;

/**
 * @author harley-dog on 2015/7/23.
 *         股票价格初始化
 */
public interface StockPriceInit {
    /**
     * 初始化股票价格
     *
     * @param begin  起始时间
     * @param end    结束书剑
     * @param shares 要初始化的股票
     * @return 有价格的日期
     */
    List<Date> init(Date begin, Date end, Stock... shares);
}
