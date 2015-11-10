package com.diorsunion.hedge.web.conrtoller;

import com.diorsunion.hedge.dal.entity.Stock;
import com.google.common.collect.Lists;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


/**
 * @author harley-dog on 2014/7/7.
 */
@Controller
@Scope("prototype")
@RequestMapping("/shares")
public class SharesController {

    @RequestMapping(value = "", method = {RequestMethod.GET})
    @ResponseBody
    public JsonResult<Stock> query() {
        Stock s1 = new Stock();
        s1.id = 6;
        s1.name = "YANG";
        Stock s2 = new Stock();
        s2.id = 7;
        s2.name = "YINN";
        List<Stock> stockList = Lists.newArrayList(s1, s2);
        return JsonResult.succResult(stockList);
    }
}
