package com.ego.item.pojo;

import java.util.List;

/**
 * @Auther: Elton Ge
 * @Date: 7/8/20
 * @Description: com.ego.item.pojo
 * @version: 1.0
 */
public class ParamItem {
    private String group;
    private List<Param> params;

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public List<Param> getParams() {
        return params;
    }

    public void setParams(List<Param> params) {
        this.params = params;
    }
}
