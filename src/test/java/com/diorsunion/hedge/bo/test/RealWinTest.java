package com.diorsunion.hedge.bo.test;

import com.diorsunion.dbtest.spring.DBTestClassRunner;
import com.diorsunion.hedge.algo.Oper3;
import com.diorsunion.hedge.bo.Operation;
import com.diorsunion.hedge.bo.StockPriceInit;
import com.diorsunion.hedge.bo.Win;
import com.diorsunion.hedge.dal.entity.Account;
import com.diorsunion.hedge.dal.entity.Stock;
import com.diorsunion.hedge.util.DateUtil;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 初始化股票数据
 *
 * @author harley-dog on 2015/6/4.
 */
@RunWith(DBTestClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:springbeans-ds-mysql-test.xml",
        "classpath:springbeans-mybatis.xml",
        "classpath:springbeans-bo.xml"
}) //还是用配置文件方便些
public class RealWinTest {

    final static double init_money = 100000;
    static List<Operation> opers = new ArrayList<>();
    static Date begin;
    static Date end;

    static {
//        opers.add(new Oper1(null));
//        opers.add(new Oper2(null));
        opers.add(new Oper3(ImmutableMap.of(Oper3.RATE, 1)));
//        opers.add(new Oper4(ImmutableMap.of(Oper4.N, 2)));
        try {
            begin = DateUtil.dateFormat.parse("2015-09-21");
            end = DateUtil.dateFormat.parse("2015-11-05");
        } catch (ParseException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    @Resource(name = "RealStockPriceInit")
    StockPriceInit stockPriceInit;

    @Test
    public void initStockData() throws Exception {

        //初始化股票价格
        Stock stock_0 = new Stock();
        stock_0.id = 8;
        stock_0.name = "SQQQ";
        Stock stock_1 = new Stock();
        stock_1.id = 9;
        stock_1.name = "QQQ";
        List<Date> dates = stockPriceInit.init(begin, end, stock_0, stock_1);
        for (Operation oper : opers) {
            System.out.println("----------------------------------------------------------------");
            System.out.println("操作方法:" + oper.getDesc());
            List<Account> account_per_days = Lists.newArrayList();//每天的账户情况
            Account account = new Account(dates.get(0), init_money, stock_0, stock_1);//初始化一个账户
            account_per_days.add(account);
            int day = 1;
            System.out.println("第" + (day++) + "天,初始化,启动资金" + init_money);
            oper.oper(account, account_per_days);
            System.out.println();
            for (Date date : dates) {
                Account nextAccount = account.initNextDayAccount(date);
                account_per_days.add(nextAccount);
                Win.printStockDetail(day, date, nextAccount, "操作前");
                oper.oper(nextAccount, account_per_days);
                Win.printStockDetail(day++, date, nextAccount, "操作后");
                System.out.println();
                account = nextAccount;
            }
        }
        System.out.println("*--------------------------------------------------------------*");
    }


}
