package com.diorsunion.hedge.dal.repository;

import com.diorsunion.hedge.common.BaseRepository;
import com.diorsunion.hedge.dal.entity.Stock;

import javax.annotation.Resource;

/**
 * Created by harley-dog on 2015/6/4.
 */
@Resource
public interface StockRepository extends BaseRepository<Stock, Integer> {
}
