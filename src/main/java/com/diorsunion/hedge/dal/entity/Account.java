package com.diorsunion.hedge.dal.entity;

import com.diorsunion.hedge.common.CalendarUtils;
import com.google.common.collect.Maps;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import static com.diorsunion.hedge.dal.entity.StockPrice.PriceType;

/**
 * 一天的账户情况
 *
 * @author harley-dog on 2015/7/22.
 */
public class Account {
    public Date date;
    public double balance;//余额
    public Map<Stock, Integer> stockWarehouse = Maps.newLinkedHashMap();//股票仓库

    public Account(Date date, double balance, Stock... stocks) {
        this.balance = balance;
        this.date = date;
        for (Stock stock : stocks) {
            stockWarehouse.put(stock, 0);
        }

    }

    /**
     * 买股1
     *
     * @param stock     买什么股
     * @param money     打算花这么多钱来买股，但实际上不一定能买这么多股
     * @param priceType 用什么价格来买股票[开盘价，收盘价，最高价，最低价]
     */
    public void buy(Stock stock, double money, PriceType priceType) {
        //没钱就不买了
        if (money > balance) {
            System.out.println("余额不足");
            return;
        }
        StockPrice stockPrice = stock.getStockPrice(date);//获取当天的股票价格
        double price = stockPrice.getPriceByType(priceType);
        int num = (int) (money / price);//能买这么多股
        if (stockWarehouse.containsKey(stock)) {
            stockWarehouse.put(stock, stockWarehouse.get(stock) + num);
        } else {
            stockWarehouse.put(stock, num);
        }
        double cost = price * num;
        balance -= cost;//买股后增加这么多钱
        System.out.println(CalendarUtils.getDateFormat(date) + ":用" + priceType.name +
                "买入[" + stock.name + "],单价:" + String.format("%.2f", price) +
                ",数量:" + num + ",总共花费:" + String.format("%.2f", cost) + "元,余额:" + String.format("%.2f", balance) + "元");
    }

    /**
     * 买股2
     *
     * @param stock     买什么股
     * @param num       打算买多少股
     * @param priceType 用什么价格来买股票[开盘价，收盘价，最高价，最低价]
     */
    public void buyBycount(Stock stock, int num, PriceType priceType) {
        StockPrice stockPrice = stock.getStockPrice(date);//获取当天的股票价格
        double price = stockPrice.getPriceByType(priceType);
        //没钱就不买了
        if (num * price > balance) {
            System.out.println("余额不足");
            return;
        }
        if (stockWarehouse.containsKey(stock)) {
            stockWarehouse.put(stock, stockWarehouse.get(stock) + num);
        } else {
            stockWarehouse.put(stock, num);
        }
        double cost = price * num;
        balance -= cost;//买股后增加这么多钱
        System.out.println(CalendarUtils.getDateFormat(date) + ":用" + priceType.name +
                "买入[" + stock.name + "],单价:" + String.format("%.2f", price) +
                ",数量:" + num + ",总共花费" + String.format("%.2f", cost) + "元,余额:" + String.format("%.2f", balance) + "元");
    }

    /**
     * 卖股
     *
     * @param stock     卖什么股
     * @param money     预计卖股后得到的钱
     * @param priceType 用什么价格来买股票[开盘价，收盘价，最高价，最低价]
     */
    public void sell(Stock stock, double money, PriceType priceType) {
        StockPrice stockPrice = stock.getStockPrice(date);//获取当天的股票价格
        double price = stockPrice.getPriceByType(priceType);
        int num = (int) (money / price) + 1;//差不多需要卖这么多支股
        if (!stockWarehouse.containsKey(stock)) {
            System.out.println("没有这只股票");
            return;
        }
        if (stockWarehouse.get(stock) < num) {
            System.out.println("股票数量不够");
            return;
        }
        stockWarehouse.put(stock, stockWarehouse.get(stock) - num);
        double cost = price * num;
        balance += cost;//卖股后增加这么多钱
        System.out.println(CalendarUtils.getDateFormat(date) + ":用" + priceType.name +
                "卖出[" + stock.name + "],单价:" + String.format("%.2f", price) +
                ",数量:" + num + ",总共获得" + String.format("%.2f", cost) + "元,余额:" + String.format("%.2f", balance) + "元");
    }


    /**
     * 卖出某只股的全部->清仓
     *
     * @param stock     卖什么股
     * @param priceType 用什么价格来买股票[开盘价，收盘价，最高价，最低价]
     */
    public void sellAll(Stock stock, PriceType priceType) {
        if (!stockWarehouse.containsKey(stock)) {
            System.out.println("没有这只股票");
            return;
        }
        StockPrice stockPrice = stock.getStockPrice(date);//获取当天的股票价格
        double price = stockPrice.getPriceByType(priceType);
        int num = stockWarehouse.get(stock);
        stockWarehouse.put(stock, stockWarehouse.get(stock) - num);
        double cost = price * num;
        balance += cost;//卖股后增加这么多钱
        System.out.println(CalendarUtils.getDateFormat(date) + ":用" + priceType.name +
                "卖出[" + stock.name + "],单价:" + String.format("%.2f", price) +
                ",数量:" + num + ",总共获得" + String.format("%.2f", cost) + "元,余额:" + String.format("%.2f", balance) + "元");
    }

    /**
     * 卖股
     *
     * @param stock     卖什么股
     * @param num       打算卖多少股
     * @param priceType 用什么价格来买股票[开盘价，收盘价，最高价，最低价]
     */
    public void sellByCount(Stock stock, int num, PriceType priceType) {
        StockPrice stockPrice = stock.getStockPrice(date);//获取当天的股票价格
        double price = stockPrice.getPriceByType(priceType);
        if (!stockWarehouse.containsKey(stock)) {
            System.out.println("没有这只股票");
            return;
        }
        stockWarehouse.put(stock, stockWarehouse.get(stock) - num);
        double cost = price * num;
        balance += cost;//卖股后增加这么多钱
        System.out.println(CalendarUtils.getDateFormat(date) +
                ":用" + priceType.name + "卖出[" + stock.name + "],单价:" + String.format("%.2f", price) +
                ",数量" + num + ",总共获得" + String.format("%.2f", cost) + "元,余额:" + String.format("%.2f", balance) + "元");
    }

    //获取股票价值最高的股票
    public Stock getHighest(PriceType priceType) {
        double d = 0;
        Stock stock = null;
        for (Map.Entry<Stock, Integer> entry : stockWarehouse.entrySet()) {
            Stock s = entry.getKey();
            StockPrice stockPrice = s.getStockPrice(date);
            double price = stockPrice.getPriceByType(priceType);
            Integer num = entry.getValue();
            double total = price * num;
            if (total > d) {
                d = total;
                stock = s;
            }
        }
        return stock;
    }

    //获取股票价值最低的股票
    public Stock getLowest(PriceType priceType) {
        double d = Integer.MAX_VALUE;
        Stock stock = null;
        for (Map.Entry<Stock, Integer> entry : stockWarehouse.entrySet()) {
            Stock s = entry.getKey();
            StockPrice stockPrice = s.getStockPrice(date);
            double price = stockPrice.getPriceByType(priceType);
            Integer num = entry.getValue();
            double total = price * num;
            if (total < d) {
                d = total;
                stock = s;
            }
        }
        return stock;
    }

    public double getStockValue(Stock stock, PriceType priceType) {
        if (!stockWarehouse.containsKey(stock)) {
            return 0d;
        }
        int num = stockWarehouse.get(stock);
        StockPrice stockPrice = stock.getStockPrice(date);

        double price = stockPrice == null ? 0 : stockPrice.getPriceByType(priceType);
        return new BigDecimal(price).multiply(new BigDecimal(num)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    //获取股票总值
    public double getTotalStockValue(PriceType priceType) {
        if (priceType == null) {
            priceType = PriceType.getDefault();
        }
        double total = 0;
        for (Map.Entry<Stock, Integer> entry : stockWarehouse.entrySet()) {
            Stock s = entry.getKey();
            StockPrice stockPrice = s.getStockPrice(date);
            double price = stockPrice == null ? 0 : stockPrice.getPriceByType(priceType);
            Integer num = entry.getValue();
            double t = price * num;
            total += t;
        }
        return total;
    }

    //获取账户总值
    public double getTotalValue(PriceType priceType) {
        return getTotalStockValue(priceType) + balance;
    }

    public String getTotalValueStr(PriceType priceType) {
        return String.format("%.2f", getTotalValue(priceType));
    }

    @Override
    public String toString() {
        double share_total = getTotalStockValue(null);//获取股票总值
        double total = balance + share_total;
        return "账户余额:" + String.format("%.2f", balance) + ",\t股票总值:" + String.format("%.2f", share_total) + ",\t总资产" + String.format("%.2f", total);
    }

    public Account initNextDayAccount(Date date) {
        Account account = new Account(date, this.balance);
        account.stockWarehouse.putAll(this.stockWarehouse);
        return account;
    }
}
