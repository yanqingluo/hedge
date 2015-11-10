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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

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

    public final static Date date20100101 = new Date(1262275200000L);//2010-01-01 00:00:00 000
    private static final String cookie = "s=vdm12ee3d3; a2404_times=3; xq_a_token=138a2f04de6b9d0bafb7cf63b0e45cf14e9c28e4; xq_r_token=e3e63e30d7452591c0237b3f2cdb5e89c597aca2; __utmt=1; __utma=1.778834837.1433400627.1446032974.1447118526.7; __utmb=1.4.10.1447118526; __utmc=1; __utmz=1.1433400627.1.1.utmcsr=baidu|utmccn=(organic)|utmcmd=organic; Hm_lvt_1db88642e346389874251b5a1eded6e3=1445854088; Hm_lpvt_1db88642e346389874251b5a1eded6e3=1447118589";
    static URIBuilder uriBuilder;

    static {
        try {
            uriBuilder = new URIBuilder()
                    .setScheme("http")
                    .setHost("xueqiu.com")
                    .setPath("/stock/forchartk/stocklist.json")
                    .setParameter("type", "normal")
                    .setParameter("_", String.valueOf(System.currentTimeMillis()));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    @Resource
    StockPriceRepository stockPriceRepository;
    @Resource
    StockRepository stockRepository;

    @Test
    public void initStockData() throws Exception {
        Set<Stock> stockSet = stockRepository.findAll(null, null);
        stockSet.forEach(stock -> {
            Date lastDate = null;

            for (StockPriceRepository.Period period : StockPriceRepository.Period.values()) {
                CloseableHttpResponse response1 = null;
                try {
                    uriBuilder.setParameter("period", period.value).setParameter("symbol", stock.code).build();
                    Date begin = new Date(date20100101.getTime());
                    Date end = new Date();
                    Calendar calendar = Calendar.getInstance();
                    Calendar today = Calendar.getInstance();//
                    if (stock.syncEndDate != null) {
                        switch (period) {
                            case day:
                                //如果是天数据,就从上次同步时间的下一天开始
                                long endtime = stock.syncEndDate.getTime();
                                long yestime = CalendarUtils.getYestoday().getTime();
                                if (endtime >= yestime) {
                                    continue;
                                }
                                begin.setTime(endtime + 86400000);
                                break;
                            case week:
                                //如果是周数据,就从上一次同步数据的下一周开始,但如果今天不是下下周一(即中间没跨两周),就不用同步了
                                calendar.setTime(stock.syncEndDate);
                                calendar.add(7, Calendar.DATE);//加七天
                                begin.setTime(calendar.getTimeInMillis());
                                today.set(Calendar.DAY_OF_WEEK, 1);
                                today.add(Calendar.DATE, -1);
                                end = today.getTime();
                                break;
                            case month:
                                //如果是周数据,就从上一次同步数据的下一周开始,但如果今天不是下下周一(即中间没跨两周),就不用同步了
                                calendar.setTime(stock.syncEndDate);
                                calendar.add(Calendar.MONTH, 1);
                                calendar.set(Calendar.DAY_OF_MONTH, 1);
                                begin.setTime(calendar.getTimeInMillis());
                                today.set(Calendar.DAY_OF_MONTH, 1);
                                today.add(Calendar.DATE, -1);
                                end = today.getTime();
                                break;
                        }
                    }
                    uriBuilder.setParameter("begin", String.valueOf(begin.getTime()))
                            .setParameter("end", String.valueOf(end.getTime()));
                    List<StockPrice> delete_stockPriceList = stockPriceRepository.findSharePrice(stock, begin, end, period);
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
                    for (int i = 0; chartlist != null && i < chartlist.size(); i++) {
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
                        stockPrice.preDate = (i == 0 && begin.getTime() != date20100101.getTime()) ? begin : preObject != null ? dateFormat_US.parse(preObject.get("time").toString()) : null;
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
            if (lastDate != null) {
                stock.syncEndDate = lastDate;
                stockRepository.update(stock);
            }
        });
    }


}
