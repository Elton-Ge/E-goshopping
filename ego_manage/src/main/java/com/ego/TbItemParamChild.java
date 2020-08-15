package com.ego;

import com.ego.pojo.TbItemCat;
import com.ego.pojo.TbItemParam;

/**
 * @Auther: Elton Ge
 * @Date: 3/8/20
 * @Description: com.ego
 * @version: 1.0
 */
public class TbItemParamChild extends TbItemParam {
    private String  itemCatName;

    public TbItemParamChild() {
    }

    public TbItemParamChild(String itemCatName) {
        this.itemCatName = itemCatName;
    }

    public String getItemCatName() {
        return itemCatName;
    }

    public void setItemCatName(String itemCatName) {
        this.itemCatName = itemCatName;
    }
}
