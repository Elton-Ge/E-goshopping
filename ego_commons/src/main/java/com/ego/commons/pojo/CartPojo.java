package com.ego.commons.pojo;

/**
 * @Auther: Elton Ge
 * @Date: 9/8/20
 * @Description: com.ego.commons.pojo
 * @version: 1.0
 * 购物车实体类
 */
public class CartPojo {
    private Long id;       //商品id
    private String title;
    private Long price;
    private String [] images;

    private Integer num;     //购物车中商品数量

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

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
