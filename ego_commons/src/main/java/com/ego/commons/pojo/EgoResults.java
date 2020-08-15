package com.ego.commons.pojo;

/**
 * @Auther: Elton Ge
 * @Date: 31/7/20
 * @Description: com.ego.commons
 * @version: 1.0
 */
public class EgoResults {
    //status 200 成功， 400、500失败
    private int status;
    private Object data;
    private String msg;

    public static EgoResults ok(){
        EgoResults egoResults = new EgoResults();
        egoResults.setStatus(200);
        egoResults.setMsg("ok");
        return egoResults;
    }

    public static EgoResults ok(Object data){
        EgoResults egoResults = new EgoResults();
        egoResults.setData(data);
        egoResults.setStatus(200);
        egoResults.setMsg("ok");
        return egoResults;
    }
    public static EgoResults ok(String msg){
        EgoResults egoResults = new EgoResults();
        egoResults.setStatus(200);
        egoResults.setMsg(msg);
        return egoResults;
    }

    public static EgoResults error(String msg){
        EgoResults egoResults = new EgoResults();
        egoResults.setStatus(400);
        egoResults.setMsg(msg);
        return egoResults;
    }
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
