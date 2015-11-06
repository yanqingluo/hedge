package com.diorsunion.hedge.bo.stockdatainit;

import com.diorsunion.hedge.bo.StockPriceInit;
import com.diorsunion.hedge.dal.entity.Stock;
import com.diorsunion.hedge.dal.entity.StockPrice;
import com.diorsunion.hedge.util.DateUtil;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * 这个随机算法只适用于 正反指数股
 *
 * @author harley-dog on 2015/7/23.
 */
@Service(value = "RandomStockPriceInit")
public class RandomStockPriceInit implements StockPriceInit {

    final static Random r = new Random(System.currentTimeMillis());

    //获取随机的涨跌
    public static boolean getRandomUD() {
        return r.nextInt() % 2 == 0;
    }

    private static final BigDecimal b100 = new BigDecimal(100);

    //获取随机的涨跌比率
    public static double getRandomRate() {
        return new BigDecimal(r.nextInt(9) + 1).divide(b100, 2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    @Override
    public List<Date> init(Date begin, Date end, Stock... stocks) {
        List<Date> dates = Lists.newArrayList();
        StockPrice stockPrice_0_begin = new StockPrice();
        StockPrice stockPrice_1_begin = new StockPrice();
        //初始化股价
        double b_0 = r.nextInt(70) + 30;
        double b_1 = r.nextInt(70) + 30;
        stockPrice_0_begin.open = b_0;
        stockPrice_0_begin.close = b_0;
        stockPrice_0_begin.high = b_0;
        stockPrice_0_begin.low = b_0;
        stockPrice_1_begin.open = b_1;
        stockPrice_1_begin.close = b_1;
        stockPrice_1_begin.high = b_1;
        stockPrice_1_begin.low = b_1;

        stocks[0].setStockPrice(begin, stockPrice_0_begin);
        stocks[1].setStockPrice(begin, stockPrice_1_begin);
        int i = 0;
        System.out.print("第" + (++i) + "天,");
        System.out.print(stocks[0].name + ":\t" + DateUtil.dateFormat.format(begin) + ":\t" + String.format("%.2f", stocks[0].getStockPrice(begin).close) + ":\t+ 0%");
        System.out.println("\t" + stocks[1].name + ":\t" + DateUtil.dateFormat.format(begin) + ":\t" + String.format("%.2f", stocks[1].getStockPrice(begin).close) + ":\t- 0%");
        for (Date date = DateUtil.addDate(begin, 1); date.getTime() <= end.getTime(); date = DateUtil.addDate(date, 1)) {
            dates.add(date);
            boolean b = getRandomUD();//获取随机涨跌
            double r = getRandomRate();//获取随机涨跌比率
            StockPrice stockPrice_0 = new StockPrice();
            StockPrice stockPrice_1 = new StockPrice();
            StockPrice stockPrice_yestoday_0 = stocks[0].getStockPrice(DateUtil.addDate(date, -1));//获取昨天的股票价格
            StockPrice stockPrice_yestoday_1 = stocks[1].getStockPrice(DateUtil.addDate(date, -1));//获取昨天的股票价格
            double close_yestoday_0 = stockPrice_yestoday_0.close;//昨天的收盘价格
            double close_yestoday_1 = stockPrice_yestoday_1.close;//昨天的收盘价格
            stockPrice_0.open = b ? close_yestoday_0 * (1 + r) : close_yestoday_0 * (1 - r);
            stockPrice_0.high = stockPrice_0.open;
            stockPrice_0.low = stockPrice_0.open;
            stockPrice_0.close = stockPrice_0.open;
            stockPrice_1.open = b ? close_yestoday_1 * (1 - r) : close_yestoday_1 * (1 + r);
            stockPrice_1.high = stockPrice_1.open;
            stockPrice_1.low = stockPrice_1.open;
            stockPrice_1.close = stockPrice_1.open;
            stocks[0].setStockPrice(date, stockPrice_0);
            stocks[1].setStockPrice(date, stockPrice_1);
            System.out.print("第" + (++i) + "天,");
            System.out.print(stocks[0].name + ":\t" + DateUtil.dateFormat.format(date) + ":\t" + String.format("%.2f", stockPrice_0.close) + ":\t" + (b ? "+" : "-") + String.format("%2.0f", r * 100d) + "%");
            System.out.println("\t" + stocks[1].name + ":\t" + DateUtil.dateFormat.format(date) + ":\t" + String.format("%.2f", stockPrice_1.close) + ":\t" + (!b ? "+" : "-") + String.format("%2.0f", r * 100d) + "%");
        }
        return dates;
    }
}
