package com.ego.search.service;

import java.util.List;
import java.util.Map;

/**
 * @Auther: Elton Ge
 * @Date: 7/8/20
 * @Description: com.ego.search.service
 * @version: 1.0
 */
public interface SearchService {
    /**
     * 实现商品solr搜索功能
     * @param q
     * @param page
     * @param size
     * @return
     */
    Map<String ,Object> search(String  q,int page,int size);

    /**
     * 对solr中数据新增
     * @param ids  商品id的集合
     * @return   返回新增结果
     */
    int insert (long [] ids );

    int delete(String [] ids) ;
}
