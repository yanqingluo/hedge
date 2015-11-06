package com.diorsunion.hedge.bo;

import com.diorsunion.hedge.algo.Oper1;
import com.diorsunion.hedge.algo.Oper2;
import com.diorsunion.hedge.algo.Oper3;
import com.diorsunion.hedge.algo.Oper4;
import com.diorsunion.hedge.bo.stockdatainit.RandomStockPriceInit;
import com.diorsunion.hedge.dal.entity.Account;
import com.diorsunion.hedge.dal.entity.Stock;
import com.diorsunion.hedge.dal.entity.StockPrice;
import com.diorsunion.hedge.util.DateUtil;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author harley-dog on 2015/7/16.
 */
public class Win {

    final static double init_money = 500000;
    static List<Operation> opers = new ArrayList<>();

    final static StockPriceInit dataPriceInit;
    static Date begin;
    static Date end;

    static {
        opers.add(new Oper1(null));
        opers.add(new Oper2(null));
        opers.add(new Oper3(ImmutableMap.of(Oper3.RATE, 1)));
        opers.add(new Oper4(ImmutableMap.of(Oper4.N, 2)));
        dataPriceInit = new RandomStockPriceInit();
        try {
            begin = DateUtil.dateFormat.parse("2015-01-01");
            end = DateUtil.dateFormat.parse("2015-01-11");
        } catch (ParseException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }


    public static void main(String[] args) {
        //初始化股票价格
        Stock stock_0 = new Stock();
        stock_0.name = "做多";
        Stock stock_1 = new Stock();
        stock_1.name = "做空";
        List<Date> dates = dataPriceInit.init(begin, end, stock_0, stock_1);
        for (Operation oper : opers) {
            System.out.println("----------------------------------------------------------------");
            System.out.println("操作方法:" + oper.getDesc());
            List<Account> account_per_days = Lists.newArrayList();//每天的账户情况
            Account account = new Account(dates.get(0), init_money, stock_0, stock_1);//初始化一个账户
            account_per_days.add(account);
            int day = 1;
            System.out.println("第" + (day++) + "天,初始化");
            oper.oper(account, account_per_days);
            System.out.println();
            for (Date date : dates) {
                Account nextAccount = account.initNextDayAccount(date);
                account_per_days.add(nextAccount);
                printStockDetail(day, date, nextAccount, "操作前");
                oper.oper(nextAccount, account_per_days);
                printStockDetail(day++, date, nextAccount, "操作后");
                System.out.println();
                account = nextAccount;
            }
        }
        System.out.println("*--------------------------------------------------------------*");
    }

    public static void printStockDetail(int day, Date date, Account account, String oper) {
        System.out.println("第" + (day) + "天," + DateUtil.dateFormat.format(date) + oper + ":\t" + account);
        account.stockWarehouse.forEach((stock, num) -> {
            StockPrice stockPrice = stock.getStockPrice(date);
            System.out.println("\t" + stock.name + ":数量" + num + ",价格:" + stockPrice + ",总价" + account.getStockValue(stock, StockPrice.PriceType.CLOSE));
        });
    }

}
