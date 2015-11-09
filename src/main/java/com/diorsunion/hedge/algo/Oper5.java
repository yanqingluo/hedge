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
public class Oper5 extends Operation {

    public static final String PROFIT = "P";//止盈率
    public static final String LOSS = "L";//止损率

    private Account account_period_begin = null;//周期记录日的账户情况
    /**
     * 记录周期开始第几天
     * 0 :初始化 ;
     * 1: 周期第一天,卖跌股
     * 2~N:周期第N天
     */
    int day_index = -1;//

    private StockPrice.PriceType priceType = StockPrice.PriceType.CLOSE;//用收盘价来计算

    private Stock empty_Stock = null;

    public Oper5(Map<String, Integer> params) {
        super(params);
    }

    @Override
    public void oper(Account account, List<Account> account_per_days) {
        day_index++;//每次增长一天
        //第一天,均衡买入
        if (day_index == 0) {
            int buy_count = account.stockWarehouse.size();
            for (Stock stock : account.stockWarehouse.keySet()) {
                account.buy(stock, account.balance / buy_count--, priceType);
            }
            account_period_begin = account;
            return;
        }
        if (day_index == 1) {
            //周期开始第二天,把跌的全卖了,涨的别动
            Stock stock_low = account.getLowest(priceType);
            account.sellAll(stock_low, priceType);//把跌的股全抛了
            empty_Stock = stock_low;
            day_index = 1;
            return;
        }

        double total_current = account.getTotalValue(priceType);//当天总股值
        double total_period = account_period_begin.getTotalValue(priceType);//周期开始的总股值
        //r是当天对比周期开始的增长率
        BigDecimal r = new BigDecimal((total_current - total_period) * 100 / total_period).setScale(2, BigDecimal.ROUND_HALF_UP);
        int profit = params.get(PROFIT);//止盈率
        int loss = params.get(LOSS);//止损率

        if (r.intValue() >= profit) {
            System.out.println("周期开始:" +
                    CalendarUtils.dateFormat.format(account_period_begin.date) +
                    ",资产净值:" + account_period_begin.getTotalValueStr(priceType) +
                    ",当前资产净值" + account.getTotalValueStr(priceType) +
                    ",收益率达到" + String.format("%2.2f", r.doubleValue()) +
                    "%,超过止盈率" + profit +
                    "%,开始操作");
            Stock stock_high = account.getHighest(priceType);
            double value_high = account.getStockValue(stock_high, priceType);//得到当前持有的那支股票净值
            double total_value = account.getTotalValue(priceType);//得到资产总净值
            double diff = value_high - total_value / 2;//计算差值(就是获利)
            account.sell(stock_high, diff / 2, priceType);//把获利的一半卖出
            account.buy(empty_Stock, account.balance, priceType);//用所有钱买空仓的那支股
            account_period_begin = account;
            empty_Stock = null;
            day_index = 0;
            return;
        }
        if (r.intValue() <= loss) {
            System.out.println("周期开始:" +
                    CalendarUtils.dateFormat.format(account_period_begin.date) +
                    ",资产净值:" + account_period_begin.getTotalValueStr(priceType) +
                    ",当前资产净值" + account.getTotalValueStr(priceType) +
                    ",损失率达到" + String.format("%2.2f", r.doubleValue()) +
                    "%,超过止损率" + loss + "%,开始操作");
            Stock stock_high = account.getHighest(priceType);
            double value_high = account.getStockValue(stock_high, priceType);//得到当前持有的那支股票净值
            double diff = account.balance - value_high;
            if (diff > 0) {
                account.buy(stock_high, diff / 2, priceType);
                account.buy(empty_Stock, account.balance, priceType);
            } else {
                account.sell(stock_high, -diff / 2, priceType);
                account.buy(empty_Stock, account.balance, priceType);
            }
            account_period_begin = account;
            empty_Stock = null;
            day_index = 0;
            return;
        }
    }

    @Override
    public String getDesc() {
        return " 对冲算法1.5版本\n" +
                " * 中心思想是\n" +
                " * 1.首先均等买入做多和做空两股,计算为一个周期的开始\n" +
                " * 2.然后第二天,两只股有涨有跌,按照会持续涨跌的思路继续买入卖出股票\n" +
                " * 3.把跌的股票卖出,涨的不动\n" +
                " * 4.此后保持只持有涨的股票,到总资产盈利P%为止,或者资产损失L%为止\n" +
                " * 5.均衡两只股票,完成一个周期,继续下一个周期";
    }

}
