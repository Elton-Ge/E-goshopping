package com.ego.commons.pojo;

/**
 * @Auther: Elton Ge
 * @Date: 5/8/20
 * @Description: com.ego.commons.pojo
 * @version: 1.0
 */
//var data = [{"srcB":"http://image.ego.com/images/2015/03/03/2015030304360302109345.jpg","height":240,"alt":"","width":670,
// "src":"http://image.ego.com/images/2015/03/03/2015030304360302109345.jpg","widthB":550,"href":"http://sale.jd.com/act/e0FMkuDhJz35CNt.html?cpdad=1DLSUE",
// "heightB":240},{"srcB":"http://image.ego.com/images/2015/03/03/2015030304353109508500.jpg","height":240,"alt":"","width":670,"src":"http://image.ego.com/images/2015/03/03/2015030304353109508500.jpg","widthB":550,"href":"http://sale.jd.com/act/UMJaAPD2VIXkZn.html?cpdad=1DLSUE","heightB":240},{"srcB":"http://image.ego.com/images/2015/03/03/2015030304345761102862.jpg","height":240,"alt":"","width":670,"src":"http://image.ego.com/images/2015/03/03/2015030304345761102862.jpg","widthB":550,"href":"http://sale.jd.com/act/UMJaAPD2VIXkZn.html?cpdad=1DLSUE","heightB":240},{"srcB":"http://image.ego.com/images/2015/03/03/201503030434200950530.jpg","height":240,"alt":"","width":670,"src":"http://image.ego.com/images/2015/03/03/201503030434200950530.jpg","widthB":550,"href":"http://sale.jd.com/act/kj2pmwMuYCrGsK3g.html?cpdad=1DLSUE","heightB":240},{"srcB":"http://image.ego.com/images/2015/03/03/2015030304333327002286.jpg","height":240,"alt":"","width":670,"src":"http://image.ego.com/images/2015/03/03/2015030304333327002286.jpg","widthB":550,"href":"http://sale.jd.com/act/xcDvNbzAqK0CoG7I.html?cpdad=1DLSUE","heightB":240},{"srcB":"http://image.ego.com/images/2015/03/03/2015030304324649807137.jpg","height":240,"alt":"","width":670,"src":"http://image.ego.com/images/2015/03/03/2015030304324649807137.jpg","widthB":550,"href":"http://sale.jd.com/act/eDpBF1s8KcTOYM.html?cpdad=1DLSUE","heightB":240}];
public class BigAd {
    //备份图片
    private String srcB;
    //大广告高度
    private int height;
    //图片宽度
    private int width;
    //悬浮图片信息
    private String alt;
    //显示图片url
    private  String src;
    //备份图片宽度
    private int widthB;
    //点击图片跳转路径
    private String  href;
    //备份图片高度
    private int heightB;

    public String getSrcB() {
        return srcB;
    }

    public void setSrcB(String srcB) {
        this.srcB = srcB;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public int getWidthB() {
        return widthB;
    }

    public void setWidthB(int widthB) {
        this.widthB = widthB;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public int getHeightB() {
        return heightB;
    }

    public void setHeightB(int heightB) {
        this.heightB = heightB;
    }
}
