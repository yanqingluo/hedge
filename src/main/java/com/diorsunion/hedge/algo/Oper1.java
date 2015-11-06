package com.diorsunion.hedge.algo;

import com.diorsunion.hedge.bo.Operation;
import com.diorsunion.hedge.dal.entity.Account;
import com.diorsunion.hedge.dal.entity.Stock;
import com.diorsunion.hedge.dal.entity.StockPrice;

import java.util.List;
import java.util.Map;

/**
 * 啥也不干算法
 *
 * @author harley-dog on 2015/7/22.
 */
public class Oper1 extends Operation {

    public Oper1(Map<String, Integer> params) {
        super(params);
    }

    @Override
    public void oper(Account account, List<Account> account_per_days) {
        if (account_per_days.size() == 1) {
            int buy_count = account.stockWarehouse.size();
            for (Stock stock : account.stockWarehouse.keySet()) {
                account.buy(stock, account.balance / buy_count--, StockPrice.PriceType.OPEN);
            }
        }
    }

    @Override
    public String getDesc() {
        return "如果是第一次操作,则用开盘价,均匀购买股票,比如做多做空各买一半,后续无操作,计算最后总价值";
    }

}
