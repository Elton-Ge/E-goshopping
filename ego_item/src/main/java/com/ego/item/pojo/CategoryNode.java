package com.ego.item.pojo;

import java.util.List;

/**
 * @Auther: Elton Ge
 * @Date: 4/8/20
 * @Description: com.ego.item.pojo
 * @version: 1.0
 * 全部导航数据，包括子分类
 */
public class CategoryNode {
    private String u;
    private  String n;
    private List<Object> i;

    public String getU() {
        return u;
    }

    public void setU(String u) {
        this.u = u;
    }

    public String getN() {
        return n;
    }

    public void setN(String n) {
        this.n = n;
    }

    public List<Object> getI() {
        return i;
    }

    public void setI(List<Object> i) {
        this.i = i;
    }
}
