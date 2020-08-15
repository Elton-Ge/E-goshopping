package com.ego.item.service.impl;

import com.ego.commons.pojo.TbItemDetails;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbItemCatDubboService;
import com.ego.dubbo.service.TbItemDescDubboService;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.dubbo.service.TbItemParamItemDubboService;
import com.ego.item.pojo.CategoryNode;
import com.ego.item.pojo.ItemCategoryNav;
import com.ego.item.pojo.Param;
import com.ego.item.pojo.ParamItem;
import com.ego.item.service.ItemService;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemCat;
import com.ego.pojo.TbItemDesc;
import com.ego.pojo.TbItemParamItem;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: Elton Ge
 * @Date: 4/8/20
 * @Description: com.ego.item.service.impl
 * @version: 1.0
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Reference
    private TbItemCatDubboService tbItemCatDubboService;
    @Reference
    private TbItemDubboService tbItemDubboService;
    @Reference
    private TbItemDescDubboService tbItemDescDubboService;
    @Reference
    private TbItemParamItemDubboService tbItemParamItemDubboService;

    @Override
    @Cacheable(cacheNames = "com.ego.item",key = "'showItemCategoryNav'")
    public ItemCategoryNav showItemCategoryNav() {
        System.out.println("执行了方法");
        ItemCategoryNav itemCategoryNav = new ItemCategoryNav();
        itemCategoryNav.setData(getCategoryNode(0L));
        return  itemCategoryNav;
    }

    private List<Object> getCategoryNode(long pid){
        List<TbItemCat> list = tbItemCatDubboService.selectByPid(pid);
        List<Object> listResult = new ArrayList<>();
        for (TbItemCat tbItemCat : list) {
            // 一个cat对应一个菜单项目。前两层类型都是CategoryNode类型，第三层是String
              if (tbItemCat.getIsParent()){   // 说明是第一层或第二层。
                  CategoryNode categoryNode = new CategoryNode();
                  categoryNode.setU("/products/"+tbItemCat.getId()+".html");
                  categoryNode.setN( "<a href='/products/"+tbItemCat.getId()+".html'>"+tbItemCat.getName()+"</a>");
                  categoryNode.setI(getCategoryNode(tbItemCat.getId()));
                  listResult.add(categoryNode);
              }else {
                  listResult.add("/products/"+tbItemCat.getId()+".html|"+tbItemCat.getName());
              }
        }
        return listResult;
    }

    /**
     * 显示商品详情
     * @param id
     * @return
     */
    @Cacheable(cacheNames = "com.ego.item",key = "'showDetails'+#id")
    @Override
    public TbItemDetails showDetails(Long id) {
        System.out.println("执行了该方法");
        TbItem tbItem = tbItemDubboService.selectById(id);
        TbItemDetails tbItemDetails = new TbItemDetails();
        tbItemDetails.setId(id);
        tbItemDetails.setPrice(tbItem.getPrice());
        tbItemDetails.setSellPoint(tbItem.getSellPoint());
        tbItemDetails.setTitle(tbItem.getTitle());
        System.out.println(tbItem.getSellPoint());
        System.out.println(tbItem.getTitle());
        String image = tbItem.getImage();
        tbItemDetails.setImages(image!=null&&!image.equals("")?image.split(","):new String[1]);
        return tbItemDetails;
    }

    @Override
    public String showItemDesc(Long itemid) {
        TbItemDesc tbItemDesc = tbItemDescDubboService.selectById(itemid);
        return tbItemDesc.getItemDesc();

    }

    /**
     * 显示规格参数
     * @param itemid
     * @return
     */
    @Override
    public String showParamItem(Long itemid) {
        TbItemParamItem tbItemParamItem = tbItemParamItemDubboService.selectByItemId(itemid);
        String json = tbItemParamItem.getParamData();
        List<ParamItem> list = JsonUtils.jsonToList(json, ParamItem.class);
//        StringBuffer sf = new StringBuffer();
        StringBuilder sb=new StringBuilder();
        for(ParamItem paramItem : list){
            // 把一个ParamItem当作一个表格看待
            sb.append("<table style='color:grey;' width='100%' cellpadding='5'>");
            for(int i = 0 ;i<paramItem.getParams().size();i++){
                sb.append("<tr>");
                if(i==0){// 说明是第一行，第一行要显示分组信息
                    sb.append("<td style='width:100px;text-align:right;'>"+paramItem.getGroup()+"</td>");
                }else{
                    // html列是空最好给个空格
                    sb.append("<td> </td>");// 除了第一行以外其他行第一列都是空。
                }
                sb.append("<td style='width:200px;text-align:right;'>"+paramItem.getParams().get(i).getK()+"</td>");
                sb.append("<td>"+paramItem.getParams().get(i).getV()+"</td>");
                sb.append("</tr>");
            }
            sb.append("</table>");
            sb.append("<hr style='color:gray;'/>");
        }
        return  sb.toString();
    }
}
