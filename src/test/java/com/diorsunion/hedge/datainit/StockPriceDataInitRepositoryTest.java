package com.diorsunion.hedge.datainit;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.diorsunion.dbtest.spring.DBTestClassRunner;
import com.diorsunion.hedge.common.CalendarUtils;
import com.diorsunion.hedge.dal.entity.Stock;
import com.diorsunion.hedge.dal.entity.StockPrice;
import com.diorsunion.hedge.dal.repository.StockPriceRepository;
import com.diorsunion.hedge.dal.repository.StockRepository;
import com.google.common.collect.Lists;
import org.apache.http.HttpEntity;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.diorsunion.hedge.util.Util.dateFormat_US;

/**
 * 初始化股票数据
 *
 * @author harley-dog on 2015/6/4.
 */
@RunWith(DBTestClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:springbeans-ds-mysql-test.xml",
        "classpath:springbeans-mybatis.xml"
})
public class StockPriceDataInitRepositoryTest {

    private static final String cookie = "s=1xnr187ssj; xq_a_token=138a2f04de6b9d0bafb7cf63b0e45cf14e9c28e4; xq_r_token=e3e63e30d7452591c0237b3f2cdb5e89c597aca2; __utmt=1; __utma=1.1846729013.1445954480.1446648970.1446734495.9; __utmb=1.2.10.1446734495; __utmc=1; __utmz=1.1445954480.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); Hm_lvt_1db88642e346389874251b5a1eded6e3=1446138112,1446562163,1446648970,1446734495; Hm_lpvt_1db88642e346389874251b5a1eded6e3=14467345310";

    @Resource
    StockPriceRepository stockPriceRepository;
    static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    @Resource
    StockRepository stockRepository;

    static URIBuilder uriBuilder;

    static {
        try {
            uriBuilder = new URIBuilder()
                    .setScheme("http")
                    .setHost("xueqiu.com")
                    .setPath("/stock/forchartk/stocklist.json")
                    .setParameter("type", "normal")
                    .setParameter("end", String.valueOf(new Date().getTime()))
                    .setParameter("_", String.valueOf(System.currentTimeMillis()));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    final static List<Integer> stockIds = Lists.newArrayList(8, 9);//要初始化哪些股票的数据
    final static List<Stock> STOCK_LIST = Lists.newArrayList();

    @Test
    public void initStockData() throws Exception {
        stockIds.forEach(id -> {
            STOCK_LIST.add(stockRepository.get(id));
        });
        STOCK_LIST.forEach(stock -> {
            Date lastDate = null;

            for (StockPriceRepository.Period period : StockPriceRepository.Period.values()) {
                CloseableHttpResponse response1 = null;
                try {
                    uriBuilder.setParameter("period", period.value).setParameter("symbol", stock.code).build();
                    Date begin = new Date(date20000101.getTime());
                    if (stock.syncEndDate != null) {
                        long endtime = stock.syncEndDate.getTime();
                        long yestime = CalendarUtils.getYestoday().getTime();
                        if (endtime >= yestime) {
                            continue;
                        }
                        begin.setTime(endtime + 86400000);
                    }
                    uriBuilder.setParameter("begin", String.valueOf(begin.getTime()));
                    List<StockPrice> delete_stockPriceList = stockPriceRepository.findSharePrice(stock, begin, null, period);
                    delete_stockPriceList.forEach(x -> {
                        stockPriceRepository.delete(x.id, period);
                    });
                    HttpGet httpget = new HttpGet(uriBuilder.build());
                    System.out.println(httpget.getURI());
                    System.out.println("Executing request " + httpget.getRequestLine());
                    httpget.addHeader("Cookie", cookie);
                    httpget.addHeader("Host", "xueqiu.com");
                    httpget.addHeader("RA-Sid", "3CB01D6F-20140709-112055-4c09cf-c1239f");
                    CookieStore cookieStore = new BasicCookieStore();
                    BasicClientCookie cookie = new BasicClientCookie("Host", "xueqiu.com");
                    cookie.setDomain("xueqiu.com");
                    cookie.setPath("/");
                    cookieStore.addCookie(cookie);
                    CloseableHttpClient httpclient = HttpClients.custom()
                            .setDefaultCookieStore(cookieStore)
                            .build();
                    response1 = httpclient.execute(httpget);
                    HttpEntity entity = response1.getEntity();
                    String s = EntityUtils.toString(entity);
                    JSONObject obj = JSON.parseObject(s);
                    JSONArray chartlist = (JSONArray) obj.get("chartlist");
                    StockPrice stockPrice = new StockPrice();
                    for (int i = 0; i < chartlist.size(); i++) {
                        JSONObject jsonObject = chartlist.getJSONObject(i);
                        JSONObject preObject = null;
                        JSONObject nextObject = null;
                        Date thedate = dateFormat_US.parse(jsonObject.get("time").toString());
                        if (i == chartlist.size() - 1 && period == StockPriceRepository.Period.day) {
                            lastDate = thedate;
                        }
                        if (i > 0) {
                            preObject = chartlist.getJSONObject(i - 1);
                        }
                        if (i < chartlist.size() - 1) {
                            nextObject = chartlist.getJSONObject(i + 1);
                        }
                        stockPrice.chg = Double.parseDouble(jsonObject.get("chg").toString());
                        stockPrice.close = Double.parseDouble(jsonObject.get("close").toString());
                        stockPrice.dea = Double.parseDouble(jsonObject.get("dea").toString());
                        stockPrice.dif = Double.parseDouble(jsonObject.get("dif").toString());
                        stockPrice.high = Double.parseDouble(jsonObject.get("high").toString());
                        stockPrice.low = Double.parseDouble(jsonObject.get("low").toString());
                        stockPrice.ma10 = Double.parseDouble(jsonObject.get("ma10").toString());
                        stockPrice.ma20 = Double.parseDouble(jsonObject.get("ma20").toString());
                        stockPrice.ma30 = Double.parseDouble(jsonObject.get("ma30").toString());
                        stockPrice.ma5 = Double.parseDouble(jsonObject.get("ma5").toString());
                        stockPrice.macd = Double.parseDouble(jsonObject.get("macd").toString());
                        stockPrice.open = Double.parseDouble(jsonObject.get("open").toString());
                        stockPrice.percent = Double.parseDouble(jsonObject.get("chg").toString());
                        stockPrice.volume = new BigDecimal(jsonObject.get("volume").toString()).longValue();
                        stockPrice.stock = stock;
                        stockPrice.turnrate = Double.parseDouble(jsonObject.get("turnrate").toString());
                        stockPrice.thedate = dateFormat_US.parse(jsonObject.get("time").toString());
                        stockPrice.preDate = (i == 0 && begin.getTime() != date20000101.getTime()) ? begin : preObject != null ? dateFormat_US.parse(preObject.get("time").toString()) : null;
                        stockPrice.nextDate = nextObject != null ? dateFormat_US.parse(nextObject.get("time").toString()) : null;
                        stockPriceRepository.insert(stockPrice, period);
                    }
                    EntityUtils.consume(entity);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (response1 != null) {
                        try {
                            response1.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            stock.syncEndDate = lastDate;
            stockRepository.update(stock);
        });
    }

    public final static Date date20000101 = new Date(946656000000L);//2000-01-01 00:00:00 000


}
