package com.diorsunion.hedge.dal.entity;

import com.google.common.collect.Maps;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * @author harley-dog on 2015/6/4.
 */
@Entity
public class Stock implements Serializable {
    @Id
    public Integer id;
    public String code;
    public String name;
    public String organization;
    public String url;
    public Date syncEndDate;//同步截至日期
    public Map<Date, StockPrice> stockPriceMap = Maps.newLinkedHashMap();//股票每天的价格

    public StockPrice getStockPrice(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return stockPriceMap.get(calendar.getTime());
    }

    public void setStockPrice(Date date, StockPrice stockPrice) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        stockPriceMap.put(calendar.getTime(), stockPrice);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stock stock = (Stock) o;
        if (code != null ? !code.equals(stock.code) : stock.code != null) return false;
        if (id != null ? !id.equals(stock.id) : stock.id != null) return false;
        if (name != null ? !name.equals(stock.name) : stock.name != null) return false;
        if (organization != null ? !organization.equals(stock.organization) : stock.organization != null)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (organization != null ? organization.hashCode() : 0);
        return result;
    }
}
