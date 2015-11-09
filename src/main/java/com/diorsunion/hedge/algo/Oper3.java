package com.diorsunion.hedge.algo;

import com.diorsunion.hedge.bo.Operation;
import com.diorsunion.hedge.common.CalendarUtils;
import com.diorsunion.hedge.dal.entity.Account;
import com.diorsunion.hedge.dal.entity.Stock;
import com.diorsunion.hedge.dal.entity.StockPrice;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author harley-dog on 2015/7/22.
 */
public class Oper3 extends Operation {

    public static final String PROFIT = "P";//止盈率
    public static final String LOSS = "L";//止损率

    private Account account_period_begin = null;

    private StockPrice.PriceType priceType = StockPrice.PriceType.CLOSE;//用收盘价来计算

    public Oper3(Map<String, Integer> params) {
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
            account_period_begin = account;
            return;
        }
        if (account_per_days.size() > 2) {
            double total_current = account.getTotalStockValue(priceType);//当天总股值
            double total_period = account_period_begin.getTotalStockValue(priceType);//周期开始的总股值
            BigDecimal r = new BigDecimal((total_current - total_period) * 100 / total_period).setScale(2, BigDecimal.ROUND_HALF_UP);
            Integer profit = params.get(PROFIT);
            Integer loss = params.get(LOSS);
            if (r.intValue() >= profit) {
                System.out.println("上一个周期开始:" + CalendarUtils.dateFormat.format(account_period_begin.date) +
                        ",资产净值:" + account_period_begin.getTotalValueStr(priceType) +
                        ",当前资产净值" + account.getTotalValueStr(priceType) +
                        ",收益率达到" + String.format("%2.2f", r.doubleValue()) +
                        "%,超过预期的" + profit +
                        "%,开始进行止盈操作");
                Stock stock_high = account.getHighest(priceType);
                Stock stock_low = account.getLowest(priceType);
                double value_high = account.getStockValue(stock_high, priceType);//得到股票净值
                double value_low = account.getStockValue(stock_low, priceType);//得到周期开始的股票净值
                double diff = value_high - value_low;//计算获利
                account.sell(stock_high, diff / 2, priceType);//把获利的一半卖出
                account.buy(stock_low, diff / 2, priceType);//用获利的一半买入
                account_period_begin = account;
            } else if (r.intValue() <= loss) {
                System.out.println("上一个周期开始:" + CalendarUtils.dateFormat.format(account_period_begin.date) +
                        ",资产净值:" + account_period_begin.getTotalValueStr(priceType) +
                        ",当前资产净值" + account.getTotalValueStr(priceType) +
                        ",损失率达到" + String.format("%2.2f", r.doubleValue()) +
                        "%,超过预期的" + profit +
                        "%,开始进行止损操作");
                Stock stock_high = account.getHighest(priceType);
                Stock stock_low = account.getLowest(priceType);
                double value_high = account.getStockValue(stock_high, priceType);//得到股票净值
                double value_low = account.getStockValue(stock_low, priceType);//得到周期开始的股票净值
                double diff = value_high - value_low;//计算损失
                account.sell(stock_high, diff / 2, priceType);//把损失的一半卖出
                account.buy(stock_low, diff / 2, priceType);//用损失的一半买入
                account_period_begin = account;

            }
        }
    }

    @Override
    public String getDesc() {
        return " 对冲算法1.3版本\n" +
                " * 中心思想是\n" +
                " * 1.首先均等买入做多和做空两股,计算为一个周期的开始\n" +
                " * 2.然后每经过一个周期,当涨跌总是单一反向涨跌时,总值会增长\n" +
                " * 3.当总值增长比率 超过指定的 百分比后,进行操作\n" +
                " * 4.操作方式是 将涨股 超过跌股的部分的一半卖掉,补入跌股\n" +
                " * 5.当进行一次操作后,周期重新开始计算";
    }

}
