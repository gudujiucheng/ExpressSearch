package com.wifi.www.expresssearch.bean;

/**
 * 标题:
 * 描述:
 * 作者: 张灿
 * 创建时间: 2017/10/9 11:15
 */

public class ItemBean {
    private String exCode;//快递对应code
    private String exName ;//快递名称
    private String msg ;

    public String getExCode() {
        return exCode;
    }

    public void setExCode(String exCode) {
        this.exCode = exCode;
    }

    public String getExName() {
        return exName;
    }

    public void setExName(String exName) {
        this.exName = exName;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
