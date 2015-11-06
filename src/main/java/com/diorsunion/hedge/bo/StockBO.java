package com.diorsunion.hedge.bo;

import com.alibaba.fastjson.JSON;
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

import java.net.URI;

/**
 * @author harley-dog on 2015/7/12.
 */
public class StockBO {

    //        http://xueqiu.com/stock/forchartk/stocklist.json?symbol=BABA&period=1day&type=normal&begin=1405166656784&end=1436702656784&_=1436702656784
    public static void main(String[] args) throws Exception {
        URI uri = new URIBuilder()
                .setScheme("http")
                .setHost("xueqiu.com")
                .setPath("/stock/forchartk/stocklist.json")
                .setParameter("symbol", "BABA")
                .setParameter("period", "1day")
                .setParameter("type", "normal")
                .setParameter("begin", "1405166656784")
                .setParameter("end", "")
                .setParameter("_", String.valueOf(System.currentTimeMillis()))
                .build();
        HttpGet httpget = new HttpGet(uri);
        System.out.println(httpget.getURI());
        System.out.println("Executing request " + httpget.getRequestLine());
        httpget.addHeader("Cookie", "s=vdm12ee3d3; xq_a_token=ec225cc39134e9d7f331b34986471ab8a38b97c4; xq_r_token=72976e9c8f5ac083b294f89b658188b16f43bdb9; __utmt=1; a2404_pages=1; a2404_times=2; Hm_lvt_1db88642e346389874251b5a1eded6e3=1445854088; Hm_lpvt_1db88642e346389874251b5a1eded6e3=1445854088; __utma=1.778834837.1433400627.1433400627.1445854088.2; __utmb=1.2.10.1445854088; __utmc=1; __utmz=1.1433400627.1.1.utmcsr=baidu|utmccn=(organic)|utmcmd=organic");
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
        CloseableHttpResponse response1 = httpclient.execute(httpget);
        try {
            System.out.println(response1.getStatusLine());
            HttpEntity entity = response1.getEntity();
            String s = EntityUtils.toString(entity);
            System.out.println(s);
            JSON.parse(s);
            EntityUtils.consume(entity);
        } finally {
            response1.close();
        }
    }
}
