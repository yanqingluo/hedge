//package com.diorsunion.hedge.bo.test;
//
//import com.diorsunion.dbtest.annotation.DataSet;
//import com.diorsunion.dbtest.annotation.DataSets;
//import com.diorsunion.hedge.bo.SharesBO;
//import com.diorsunion.hedge.common.CalendarUtils;
//import com.diorsunion.hedge.dal.entity.Stock;
//import com.diorsunion.hedge.dal.entity.SharesBalance;
//import com.diorsunion.hedge.dal.entity.StockPrice;
//import com.diorsunion.hedge.dal.entity.SharesStock;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.math.BigDecimal;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//
///**
// * @author harley-dog on 2015/6/4.
// */
//public class ShareBOTest extends BOBaseTest {
//
//    @Autowired
//    SharesBO sharesBO;
//
//    final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//
//    @Test
//    @DataSets({
//            @DataSet(entityClass = Stock.class,number=2,custom = "id={1,2};thedate={2014-02-02 00:00:00,2014-02-02 00:00:00};"),
//            @DataSet(entityClass = SharesStock.class,number=2,custom = "share_id={1,2};num={1000,2200};thedate={2014-02-02 00:00:00,2014-02-02 00:00:00}"),
//            @DataSet(entityClass = StockPrice.class,number=2,custom = "share_id={1,2};open={20,30};close={30,40};thedate={2014-02-02 00:00:00,2014-02-02 00:00:00}"),
//            @DataSet(entityClass = SharesBalance.class,number=1,custom = "cash=123456;thedate={2014-02-02 00:00:00};")
//    })
//    public void testQuery1() {
//        Date date = new Date();
//        List<SharesBO.SharesResult> list =  sharesBO.query(7);
//        assert (list!=null);
//        assert (list.size() == 7);
//        SharesBO.SharesResult sharesResult = list.get(0);
//        assert (sharesResult!=null);
//        assert (sharesResult.thedate.getTime()==CalendarUtils.getBeginDate(date, -1).getTime());
//        assert (sharesResult.cash==1234.56d);
//        assert (sharesResult.assets==2414.56d);
//    }
//
//    @Test
//    @DataSets({
//            @DataSet(entityClass = Stock.class,number=1,custom = "id=1;"),
//            @DataSet(entityClass = SharesStock.class,number=4,custom = "share_id={1,1,1,1};num={10,20,30,40};thedate={2014-02-02 00:00:00,2014-02-05 00:00:00,2014-02-06 00:00:00,2014-02-07 00:00:00}"),
//            @DataSet(entityClass = StockPrice.class,number=2,custom = "share_id={1,1};open={20,30};close={20,30};thedate={2014-02-02 00:00:00,2014-02-05 00:00:00}"),
//            @DataSet(entityClass = SharesBalance.class,number=2,custom = "cash={1000,2000};thedate={2014-02-02 00:00:00,2014-02-04 00:00:00};")
//    })
//    public void testQuery2() throws ParseException {
//        Date date = new SimpleDateFormat("yyyy-MM-dd").parse("2014-02-08");
//        List<SharesBO.SharesResult> list =  sharesBO.query(date,7);
//        assert (list!=null);
//        assert (list.size() == 7);
//        assert (list.get(0)!=null);
//        assert (list.get(0).thedate.getTime()==CalendarUtils.getBeginDate(date, -1).getTime());
//        assert (list.get(0).cash==2000d);
//        assert (list.get(0).assets==3200d);
//        assert (new BigDecimal(list.get(0).toYestoday).setScale(4,BigDecimal.ROUND_HALF_UP).doubleValue()==0.1034d);
//        assert (list.get(1).cash==2000d);
//        assert (list.get(1).assets==2900d);
//        assert (new BigDecimal(list.get(1).toYestoday).setScale(4,BigDecimal.ROUND_HALF_UP).doubleValue()==0.1154d);
//        assert (list.get(2).cash==2000d);
//        assert (list.get(2).assets==2600d);
//        assert (new BigDecimal(list.get(2).toYestoday).setScale(4,BigDecimal.ROUND_HALF_UP).doubleValue()==0.1818d);
//        assert (list.get(3).cash==2000d);
//        assert (list.get(3).assets==2200d);
//        assert (new BigDecimal(list.get(3).toYestoday).setScale(4,BigDecimal.ROUND_HALF_UP).doubleValue()==0.8333d);
//        assert (list.get(4).cash==1000d);
//        assert (list.get(4).assets==1200d);
//        assert (list.get(4).toYestoday==0d);
//        assert (list.get(5).cash==1000d);
//        assert (list.get(5).assets==1200d);
//        assert (list.get(5).toYestoday==0d);
//        assert (list.get(6).cash==0d);
//        assert (list.get(6).assets==0d);
//        assert (list.get(6).toYestoday==0d);
//    }
//}
