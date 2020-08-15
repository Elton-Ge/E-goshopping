package com.ego.commons.pojo;

/**
 * @Auther: Elton Ge
 * @Date: 2/8/20
 * @Description: com.ego.commons.pojo
 * @version: 1.0
 * EazyUItree
 */
public class EazyUITree {
    private Long id;             //eazyuiTree的Id
    private String text;          //eazyuiTree的name
    private String state;         //eazyuiTree的状态， 默认为open，还有closed

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
