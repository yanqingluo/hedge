package com.diorsunion.hedge.bo;

import com.diorsunion.hedge.dal.entity.Account;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

/**
 * 实现这个接口，进行股票的虚拟买卖操作 ，目的是提高净值
 *
 * @author harley-dog on 2015/7/22.1
 */
public abstract class Operation {
    /**
     * 可以由外界传入指定的参数,进行调优
     */
    public final Map<String, Integer> params = Maps.newHashMap();

    public Operation(Map<String, Integer> params) {
        if (params != null) {
            this.params.putAll(params);
        }
    }

    /**
     * 操作股票
     *
     * @param account          当前操作账户
     * @param account_per_days 历史每天账户
     */
    public abstract void oper(Account account, List<Account> account_per_days);

    public abstract String getDesc();

    protected String printMoney(double d) {
        return String.format("%.2f", d);
    }
}
