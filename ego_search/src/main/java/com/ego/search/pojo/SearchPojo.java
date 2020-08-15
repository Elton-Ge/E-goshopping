package com.ego.search.pojo;

import org.apache.solr.client.solrj.beans.Field;

import java.io.Serializable;

/**
 * @Auther: Elton Ge
 * @Date: 6/8/20
 * @Description: com.ego.search.pojo
 * @version: 1.0
 */
public class SearchPojo {     //此实体类不仅与search.jsp中${itemList}作用域对应，还要对应与solr中field name对应
    @Field("id")      //千万不要忘记@field注解，否则无法与solr属性映射
    private  long id;
    private String [] images;

    @Field("item_image")
    private  String image;
    @Field("item_title")
    private String title;
    @Field("item_sell_point")
    private String sellPoint;
    @Field("item_price")
    private long price ;
    @Field("item_desc")
    private String desc;
    @Field("item_category_name")
    private String catName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSellPoint() {
        return sellPoint;
    }

    public void setSellPoint(String sellPoint) {
        this.sellPoint = sellPoint;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }
}
