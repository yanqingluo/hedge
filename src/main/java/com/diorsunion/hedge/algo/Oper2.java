package com.diorsunion.hedge.algo;

import com.diorsunion.hedge.bo.Operation;
import com.diorsunion.hedge.dal.entity.Account;
import com.diorsunion.hedge.dal.entity.Stock;
import com.diorsunion.hedge.dal.entity.StockPrice;

import java.util.List;
import java.util.Map;

/**
 * 对冲算法1.2版本
 * 中心思想是
 * 1.首先均等买入做多和做空两股,计算为一个周期的开始
 * 2.然后每经过一个周期,当涨跌总是单一反向涨跌时,总值会增长
 * 3.当总值一直在增长时,则不动
 * 4.总值从最高开始下跌(右侧交易)时,则将涨股 超过跌股的部分的一半卖掉,补入跌股
 * 5.当进行一次操作后,周期重新开始计算
 *
 * @author harley-dog on 2015/7/22.
 */
public class Oper2 extends Operation {

    private Account account_period = null;

    private StockPrice.PriceType priceType = StockPrice.PriceType.CLOSE;//用收盘价来计算

    public Oper2(Map<String, Integer> params) {
        super(params);
    }

    @Override
    public void oper(Account account, List<Account> account_per_days) {
        //第一天,均衡买入
        if (account_per_days.size() == 1) {
            int buy_count = account.stockWarehouse.size();
            for (Stock stock : account.stockWarehouse.keySet()) {
                account.buy(stock, account.balance / buy_count--, priceType);
            }
            account_period = account;
        } else if (account_per_days.size() > 2) {
            double total_current = account.getTotalStockValue(priceType);//当天总股值
            double total_period = account_period.getTotalStockValue(priceType);//周期开始的总股值
            double total_pre_day = account_per_days.get(account_per_days.size() - 2).getTotalStockValue(priceType);//昨天的总股值
            if (total_current > total_period &&
                    total_current <= total_pre_day) {
                Stock stock_high = account.getHighest(priceType);
                Stock stock_low = account.getLowest(priceType);
                double price_high = account.getStockValue(stock_high, priceType);//得到当前股价
                double price_low = account.getStockValue(stock_low, priceType);//得到周期初始的股价
                double diff = price_high - price_low;//计算差值(就是获利)
                account.sell(stock_high, diff / 2, priceType);//把获利的一半卖出
                account.buy(stock_low, diff / 2, priceType);//用获利的一半买入
                account_period = account;
            }
        }
    }

    @Override
    public String getDesc() {
        return " * 对冲算法1.2版本\n" +
                " * 中心思想是\n" +
                " * 1.首先均等买入做多和做空两股,计算为一个周期的开始\n" +
                " * 2.然后每经过一个周期,当涨跌总是单一反向涨跌时,总值会增长\n" +
                " * 3.当总值一直在增长时,则不动\n" +
                " * 4.总值从最高开始下跌(右侧交易)时,则将涨股 超过跌股的部分的一半卖掉,补入跌股\n" +
                " * 5.当进行一次操作后,周期重新开始计算\n";
    }

}
