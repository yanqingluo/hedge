package com.diorsunion.hedge.dal.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author harley-dog on 2015/6/4.
 */
@Entity
public class StockPrice implements Serializable {
    @GeneratedValue
    public int id;
    @JoinColumns(value = {
            @JoinColumn(name = "id"),
            @JoinColumn(name = "code")
    })
    public Stock stock;
    public Date thedate;
    public Date preDate;
    public Date nextDate;
    public double open;
    public double close;
    public double low;
    public double high;
    public double percent;
    public double chg;
    public double dea;
    public double dif;
    public double macd;
    public double ma5;
    public double ma10;
    public double ma20;
    public double ma30;
    public double turnrate;
    public long volume;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StockPrice that = (StockPrice) o;

        if (id != that.id) return false;
        if (Double.compare(that.open, open) != 0) return false;
        if (Double.compare(that.close, close) != 0) return false;
        if (Double.compare(that.low, low) != 0) return false;
        if (Double.compare(that.high, high) != 0) return false;
        if (Double.compare(that.percent, percent) != 0) return false;
        if (Double.compare(that.chg, chg) != 0) return false;
        if (Double.compare(that.dea, dea) != 0) return false;
        if (Double.compare(that.dif, dif) != 0) return false;
        if (Double.compare(that.macd, macd) != 0) return false;
        if (Double.compare(that.ma5, ma5) != 0) return false;
        if (Double.compare(that.ma10, ma10) != 0) return false;
        if (Double.compare(that.ma20, ma20) != 0) return false;
        if (Double.compare(that.ma30, ma30) != 0) return false;
        if (Double.compare(that.turnrate, turnrate) != 0) return false;
        if (volume != that.volume) return false;
        if (stock != null ? !stock.equals(that.stock) : that.stock != null) return false;
        if (thedate != null ? !thedate.equals(that.thedate) : that.thedate != null) return false;
        if (preDate != null ? !preDate.equals(that.preDate) : that.preDate != null) return false;
        return !(nextDate != null ? !nextDate.equals(that.nextDate) : that.nextDate != null);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + (stock != null ? stock.hashCode() : 0);
        result = 31 * result + (thedate != null ? thedate.hashCode() : 0);
        result = 31 * result + (preDate != null ? preDate.hashCode() : 0);
        result = 31 * result + (nextDate != null ? nextDate.hashCode() : 0);
        temp = Double.doubleToLongBits(open);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(close);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(low);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(high);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(percent);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(chg);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(dea);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(dif);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(macd);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(ma5);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(ma10);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(ma20);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(ma30);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(turnrate);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (int) (volume ^ (volume >>> 32));
        return result;
    }

    //根据价格类型返回相应的价格
    public double getPriceByType(PriceType priceType) {
        switch (priceType) {
            case OPEN:
                return open;
            case CLOSE:
                return close;
            case HIGH:
                return high;
            case LOW:
                return low;
            default:
                return high;
        }
    }

    @Override
    public String toString() {
        return "[" +
                "开盘=" + new BigDecimal(open).setScale(2, BigDecimal.ROUND_HALF_UP) +
                ", 收盘=" + new BigDecimal(close).setScale(2, BigDecimal.ROUND_HALF_UP) +
                ", 最低=" + new BigDecimal(low).setScale(2, BigDecimal.ROUND_HALF_UP) +
                ", 最高=" + new BigDecimal(high).setScale(2, BigDecimal.ROUND_HALF_UP) +
                ']';
    }

    public enum PriceType {
        OPEN("开盘价"),
        CLOSE("收盘价"),
        HIGH("最高价"),
        LOW("最低价");
        public final String name;

        PriceType(String name) {
            this.name = name;
        }

        public static PriceType getDefault() {
            return PriceType.CLOSE;
        }
    }
}
