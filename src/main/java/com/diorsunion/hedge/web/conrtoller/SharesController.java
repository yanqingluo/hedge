//package com.diorsunion.hedge.web.conrtoller;
//
//import com.diorsunion.hedge.bo.SharesBO;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Scope;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import java.util.List;
//
//
///**
// * @author harley-dog on 2014/7/7.
// */
//@Controller
//@Scope("prototype")
//@RequestMapping("/shares")
//public class SharesController {
//
//    @Autowired
//    SharesBO sharesBO;
//
//    @RequestMapping(value = "/{days}", method = { RequestMethod.GET })
//    @ResponseBody
//    public JsonResult<SharesBO.SharesResult> query(@PathVariable Integer days) {
//        List<SharesBO.SharesResult> sharesItems = sharesBO.query(days);
//    	JsonResult<SharesBO.SharesResult>  jsonResult = JsonResult.succResult(sharesItems);
//        return jsonResult;
//    }
//}
