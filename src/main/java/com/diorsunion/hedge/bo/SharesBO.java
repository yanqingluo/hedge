//package com.diorsunion.hedge.bo;
//
//import com.diorsunion.hedge.common.CalendarUtils;
//import com.diorsunion.hedge.dal.entity.SharesBalance;
//import com.diorsunion.hedge.dal.entity.StockPrice;
//import com.diorsunion.hedge.dal.entity.SharesStock;
//import com.diorsunion.hedge.dal.repository.SharesBalanceRepository;
//import com.diorsunion.hedge.dal.repository.StockPriceRepository;
//import com.diorsunion.hedge.dal.repository.StockRepository;
//import com.diorsunion.hedge.dal.repository.SharesStockRepository;
//import com.google.common.collect.Lists;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import javax.annotation.Resource;
//import java.io.Serializable;
//import java.util.Date;
//import java.util.List;
//
///**
// * @author harley-dog on 2015/6/4.
// */
//@Resource
//public class SharesBO {
//
//    @Autowired
//    StockRepository sharesRepository;
//
//    @Autowired
//    SharesBalanceRepository sharesBalanceRepository;
//
//    @Autowired
//    StockPriceRepository sharesPriceRepository;
//
//    @Autowired
//    SharesStockRepository sharesStockRepository;
//
//    public List<SharesResult> query(Date date,int days){
//        List<SharesResult> list = Lists.newArrayList();
//        for(int i=1;i<=days;i++){
//            SharesResult result = new SharesResult();
//            list.add(result);
//            Date thedate = CalendarUtils.getBeginDate(date,-i);
//            result.thedate = thedate;
//            Date maxSharesBalancesDate = sharesBalanceRepository.getMaxDate(thedate);//求得最大的余额日期
//            if(maxSharesBalancesDate!=null){
//                SharesBalance sharesBalanceQuery = new SharesBalance();
//                sharesBalanceQuery.thedate = maxSharesBalancesDate;
//                List<SharesBalance> sharesBalanceList =  sharesBalanceRepository.find(sharesBalanceQuery);
//                SharesBalance sharesBalance = sharesBalanceList.get(0);
//                result.cash = sharesBalance.cash;
//            }else {
//                result.cash = 0;
//            }
//
//            Date maxSharesStockDate = sharesStockRepository.getMaxDate(thedate);
//            if(maxSharesStockDate!=null){
//                SharesStock sharesStockQuery = new SharesStock();
//                sharesStockQuery.thedate = maxSharesStockDate;
//                List<SharesStock> sharesStockList =  sharesStockRepository.find(sharesStockQuery);
//                for(SharesStock sharesStock:sharesStockList){
//                    StockPrice stockPriceQuery = new StockPrice();
//                    stockPriceQuery.thedate = thedate;
//                    stockPriceQuery.stock = sharesStock.stock;
//                    Date maxSharesPriceDate =  sharesPriceRepository.getMaxDate(stockPriceQuery);
//                    stockPriceQuery.thedate = maxSharesPriceDate;
//                    List<StockPrice> stockPriceList = sharesPriceRepository.find(stockPriceQuery);
//                    StockPrice stockPrice = stockPriceList.get(0);
//                    result.addSharesItem(sharesStock, stockPrice);
//                }
//            }
//            result.countAssets();
//            if(i>1){
//                SharesResult result_lastday = list.get(i-2);
//                result_lastday.toYestoday =  result.assets==0?0:(new Double(result_lastday.assets)-new Double(result.assets))/new Double(result.assets);
//            }else {
//                result.toYestoday = 0;
//            }
//        }
//        return list;
//    }
//
//    /**
//     *
//     * @param days 查询几天的内容
//     */
//    public List<SharesResult> query(int days){
//        return query(new Date(),days);
//    }
//
//    //返回结果
//    public final static class SharesResult implements Serializable{
//        public Date thedate;
//        public int cash;//现金余额
//        public int assets;//资产总额
//        public double toYestoday;//对比昨天
//        public List<SharesItem> sharesItems = Lists.newArrayList();
//        public void addSharesItem(SharesStock sharesStock,StockPrice stockPrice){
//            sharesItems.add(new SharesItem(sharesStock, stockPrice));
//        }
//        public void countAssets(){
//            assets = cash;
//            for(SharesItem sharesItem:sharesItems){
//                assets += sharesItem.sharesStock.num * sharesItem.stockPrice.close;
//            }
//        }
//    }
//
//    //股票单位
//    public final static class SharesItem implements Serializable{
//        public SharesStock sharesStock;
//        public StockPrice stockPrice;
//        public SharesItem(){
//        }
//
//        public SharesItem(SharesStock sharesStock,StockPrice stockPrice) {
//            this.sharesStock = sharesStock;
//            this.stockPrice = stockPrice;
//        }
//    }
//}
