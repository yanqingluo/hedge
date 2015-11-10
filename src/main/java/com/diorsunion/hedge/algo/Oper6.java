package com.diorsunion.hedge.algo;

import com.diorsunion.hedge.bo.Operation;
import com.diorsunion.hedge.common.CalendarUtils;
import com.diorsunion.hedge.dal.entity.Account;
import com.diorsunion.hedge.dal.entity.Stock;
import com.diorsunion.hedge.dal.entity.StockPrice;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author harley-dog on 2015/7/22.
 */
public class Oper6 extends Operation {

    public static final String PROFIT = "P";//止盈率
    public static final String LOSS = "L";//止损率
    /**
     * 记录周期开始第几天
     * 0 :初始化 ;
     * 1: 周期第一天,卖跌股
     * 2~N:周期第N天
     */
    int day_index = 0;//
    private Account account_period_begin = null;//周期记录日的账户情况
    private StockPrice.PriceType priceType = StockPrice.PriceType.CLOSE;//用收盘价来计算

    public Oper6(Map<String, Integer> params) {
        super(params);
    }

    @Override
    public void oper(Account account, List<Account> account_per_days) {
        int profit = params.get(PROFIT);//止盈率
        int loss = params.get(LOSS);//止损率
        day_index++;//每次增长一天
//        //第一天,均衡买入
        if (day_index == 1) {
            int buy_count = account.stockWarehouse.size() + 1;
            for (Stock stock : account.stockWarehouse.keySet()) {
                account.buy(stock, account.balance / buy_count--, priceType);
            }
            account_period_begin = account;
            return;
        }
        Date today = account.date;
        //5天后开始操作
        if (day_index > 5) {
            account.stockWarehouse.forEach(((stock, num) -> {
                int i = 0;
                StockPrice stockPrice = stock.getStockPrice(today);
                Date pre_date = today;
                StockPrice stockPrice_pre = null;
                do {
                    pre_date = CalendarUtils.addDate(pre_date, -1);
                    stockPrice_pre = stock.getStockPrice(pre_date);
                    if (stockPrice_pre != null) {
                        i++;
                    }
                } while (i < 5);

                double ma5_today = stockPrice.ma5;
                double ma5_pre = stockPrice_pre.ma5;
                double r = (stockPrice.close - ma5_today) * 100 / ma5_today;

                if (ma5_today < ma5_pre && r > profit) {
                    System.out.println("\t" + stock.name + ",当前价:" + String.format("%.2f", stockPrice.close)
                            + ",均线:" + printMoney(ma5_today) + ",5日前均线:" + printMoney(ma5_pre)
                            + ",均线下跌,当前价高过均线,预测会跌,卖出");
                    account.sell(stock, account.getTotalValue(priceType) / 10, priceType);
                }

                if (ma5_today > ma5_pre && r < loss) {
                    System.out.println("\t" + stock.name + ",当前价:" + String.format("%.2f", stockPrice.close)
                            + ",均线:" + printMoney(ma5_today) + ",5日前均线:" + printMoney(ma5_pre)
                            + ",均线上涨,当前价低于均线,预测会涨,买入");
                    account.buy(stock, account.balance, priceType);
                }
            }));


        }
    }

    @Override
    public String getDesc() {
        return " 对冲算法1.6版本\n" +
                " * 中心思想是\n" +
                " * 1.首先均等买入做多和做空两股,计算为一个周期的开始\n" +
                " * 2.每天每次在5日均线以下就买入,5日均线以上就卖出\n";
    }

}
