package com.ego.search.service.impl;

import com.ego.dubbo.service.TbItemCatDubboService;
import com.ego.dubbo.service.TbItemDescDubboService;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemCat;
import com.ego.pojo.TbItemDesc;
import com.ego.search.pojo.SearchPojo;
import com.ego.search.service.SearchService;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.HighlightOptions;
import org.springframework.data.solr.core.query.HighlightQuery;
import org.springframework.data.solr.core.query.SimpleHighlightQuery;
import org.springframework.data.solr.core.query.result.HighlightEntry;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Auther: Elton Ge
 * @Date: 7/8/20
 * @Description: com.ego.search.service.impl
 * @version: 1.0
 */
@Service
public class SearchServiceImpl implements SearchService {
    @Autowired
    private SolrTemplate solrTemplate;

    @Reference
    private TbItemDubboService tbItemDubboService;
    @Reference
    private TbItemCatDubboService tbItemCatDubboService;
    @Reference
    private TbItemDescDubboService tbItemDescDubboService;

    @Override
    public Map<String, Object> search(String q,int page,int size) {
        //设置查询条件
        Criteria criteria = new Criteria("item_keywords");
        criteria.is(q);
        //这个query相当于solr可视化页面中左侧全部query条件。
        HighlightQuery query = new SimpleHighlightQuery(criteria);
        //设置排序规则
        query.addSort(Sort.by(Sort.Direction.DESC,"_version_"));
        //设置分页条件
        query.setOffset((long) (size*(page-1)));
        query.setRows(size);
        //设置高亮条件
        HighlightOptions highlightOptions = new HighlightOptions();
        highlightOptions.addField("item_title","item_sell_point");
        highlightOptions.setSimplePrefix("<span style='color:red'>");
        highlightOptions.setSimplePostfix("</span>");
        query.setHighlightOptions(highlightOptions);

        //查询的页面 相当于可视化页面点击查询按钮后得出的总体值
        HighlightPage<SearchPojo> ego = solrTemplate.queryForHighlightPage("ego", query, SearchPojo.class);

        List<SearchPojo> listResult = new ArrayList<>();
//        List<SearchPojo> content = ego.getContent();//这是只取出非高亮数据 ，相当于可视化页面中的"docs"

        //取出包含高亮的数据：相当于包括docs 和 "highlighting"
        List<HighlightEntry<SearchPojo>> highlighted = ego.getHighlighted();
        for (HighlightEntry<SearchPojo> searchPojoHighlightEntry : highlighted) {
            //获取非高亮数据，相当于docs
            SearchPojo entity = searchPojoHighlightEntry.getEntity();

            String image = entity.getImage();
            entity.setImages(image!=null&&!image.equals("")?image.split(","):new String[1]);

            //获取高亮数据，相当于 highlighting
            List<HighlightEntry.Highlight> highlights = searchPojoHighlightEntry.getHighlights();
            //里面的高亮数据 ，HighlightEntry.Highlight 相当于 "1474318759":{
            //      "item_title":["奥克斯(AUX) M288 移动联通2G老人<span style=\"color:red\">手</span>机 双卡双待 土豪金"],
            //      "item_sell_point":["老年机大字体大按键大音量 超长待机 触屏<span style=\"color:red\">手</span>写 <span style=\"color:red\">手</span>电筒外放收音机语音王QQ电子书"]},
            for (HighlightEntry.Highlight highlight : highlights) {
                if (highlight.getField().getName().equals("item_title")) {
                    entity.setTitle(highlight.getSnipplets().get(0));
                }
                if (highlight.getField().getName().equals("item_sell_point")) {
                    entity.setTitle(highlight.getSnipplets().get(0));
                }
            }

            listResult.add(entity);
        }

        HashMap<String, Object> map = new HashMap<>();
        map.put("itemList",listResult);
        map.put("query",q);
        map.put("totalPages",ego.getTotalPages());

        return map;
    }

    @Override
    public int insert(long [] ids) {
        List<SearchPojo> list = new ArrayList<>();
        for (long id : ids) {
            SearchPojo searchPojo=new SearchPojo();
            TbItem tbItem = tbItemDubboService.selectById(id);
            searchPojo.setId(id);
            searchPojo.setImage(tbItem.getImage());
            searchPojo.setTitle(tbItem.getTitle());
            searchPojo.setPrice(tbItem.getPrice());
            searchPojo.setSellPoint(tbItem.getSellPoint());
            TbItemDesc tbItemDesc = tbItemDescDubboService.selectById(id);
            searchPojo.setDesc(tbItemDesc.getItemDesc());
            TbItemCat tbItemCat = tbItemCatDubboService.selectById(tbItem.getCid());
            searchPojo.setCatName(tbItemCat.getName());
            list.add(searchPojo);
        }
        UpdateResponse response = solrTemplate.saveBeans("ego", list);
        solrTemplate.commit("ego");     //这个事务必须要写
        if (response.getStatus()==0) {
            return 1;
        }
        return 0;
    }

    @Override
    public int delete(String[] ids) {
        List<String> list = Arrays.asList(ids);
        UpdateResponse response = solrTemplate.deleteByIds("ego", list);
        solrTemplate.commit("ego");
        if (response.getStatus()==0){
            return 1;
        }
        return 0;
    }
}
