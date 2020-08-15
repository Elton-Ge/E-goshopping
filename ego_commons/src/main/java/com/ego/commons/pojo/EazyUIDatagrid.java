package com.ego.commons.pojo;

import java.util.List;

/**
 * @Auther: Elton Ge
 * @Date: 1/8/20
 * @Description: com.ego.commons
 * @version: 1.0
 * 公共类
 */
public class EazyUIDatagrid {
    private  List<?> rows;         //返回给页面显示的行数
    private  long total;       //返回给页面总数

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public EazyUIDatagrid() {
    }

    public EazyUIDatagrid(List<?> rows, long total) {
        this.rows = rows;
        this.total = total;
    }
}
