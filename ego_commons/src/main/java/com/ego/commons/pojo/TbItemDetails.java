package com.ego.commons.pojo;

/**
 * @Auther: Elton Ge
 * @Date: 7/8/20
 * @Description: com.ego.commons.pojo
 * @version: 1.0
 * 商品详情页面对应的pojo
 */
public class TbItemDetails {
    private Long id;
    private String title;
    private String sellPoint;
    private Long price;
    private String [] images;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }
}
