package com.diorsunion.hedge.algo;

import com.diorsunion.hedge.bo.Operation;
import com.diorsunion.hedge.dal.entity.Account;
import com.diorsunion.hedge.dal.entity.Stock;
import com.diorsunion.hedge.dal.entity.StockPrice;

import java.util.List;
import java.util.Map;

/**
 * @author harley-dog on 2015/7/22.
 */
public class Oper4 extends Operation {

    public static final String N = "N";

    int c = 0;
    private StockPrice.PriceType priceType = StockPrice.PriceType.CLOSE;//用收盘价来计算

    public Oper4(Map<String, Integer> params) {
        super(params);
    }

    private int N() {
        return params.containsKey(N) ? params.get(N) : 0;
    }


    @Override
    public void oper(Account account, List<Account> account_per_days) {
        //第一天,均衡买入
        if (account_per_days.size() == 1) {
            int buy_count = account.stockWarehouse.size();
            for (Stock stock : account.stockWarehouse.keySet()) {
                account.buy(stock, account.balance / buy_count--, priceType);
            }
            return;
        }
        if (account_per_days.size() > 2) {
            double total_current = account.getTotalStockValue(priceType);//当天总股值
            double total_period = account_per_days.get(account_per_days.size() - 2).getTotalStockValue(priceType);//周期开始的总股值

            if ((total_current - total_period) > 0) {
                c++;
            } else {
                c = 0;
            }
            int N = N();
            if (c > N()) {
                System.out.println("单向增长" + c + "次,超过指定的" + N + "次,进行操作");
                Stock stock_high = account.getHighest(priceType);
                Stock stock_low = account.getLowest(priceType);
                double price_high = account.getStockValue(stock_high, priceType);//得到当前股价
                double price_low = account.getStockValue(stock_low, priceType);//得到周期初始的股价
                double diff = price_high - price_low;//计算差值(就是获利)
                account.sell(stock_high, diff / 2, priceType);//把获利的一半卖出
                account.buy(stock_low, diff / 2, priceType);//用获利的一半买入
                c = 0;
            }
        }
    }

    @Override
    public String getDesc() {
        return " 对冲算法1.4版本\n" +
                " * 中心思想是\n" +
                " * 1.首先均等买入做多和做空两股,计算为一个周期的开始\n" +
                " * 2.然后每经过一个周期,当涨跌总是单一反向涨跌时,总值会增长\n" +
                " * 3.当连续单向增长n次后,进行操作\n" +
                " * 4.操作方式是 将涨股 超过跌股的部分的一半卖掉,补入跌股\n" +
                " * 5.当进行一次操作后,周期重新开始计算";
    }

}
