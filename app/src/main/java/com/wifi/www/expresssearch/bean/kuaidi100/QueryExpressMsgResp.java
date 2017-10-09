package com.wifi.www.expresssearch.bean.kuaidi100;

import java.util.List;

/**
 * 标题:
 * 描述:
 * 作者: 张灿
 * 创建时间: 2017/9/28 11:33
 */

public class QueryExpressMsgResp {

    /**
     * message : ok
     * nu : 71028900523318
     * ischeck : 0
     * condition : B00
     * com : huitongkuaidi
     * status : 200
     * state : 1
     * data : [{"time":"2017-09-27 01:33:30","ftime":"2017-09-27 01:33:30","context":"天津市|发件|天津市【天津转运中心】，正发往【广州转运中心】","location":""},{"time":"2017-09-27 00:11:51","ftime":"2017-09-27 00:11:51","context":"天津市|到件|到天津市【天津转运中心】","location":""},{"time":"2017-09-26 20:57:03","ftime":"2017-09-26 20:57:03","context":"天津市|发件|天津市【BEX天津北辰分部】，正发往【天津转运中心】","location":""},{"time":"2017-09-26 16:25:51","ftime":"2017-09-26 16:25:51","context":"天津市|收件|天津市【BEX天津北辰分部】，【李向东/18902099927】已揽收","location":""}]
     */

    private String message;
    private String nu;
    private String ischeck;
    private String condition;
    private String com;
    private String status;
    private String state;
    /**
     * time : 2017-09-27 01:33:30
     * ftime : 2017-09-27 01:33:30
     * context : 天津市|发件|天津市【天津转运中心】，正发往【广州转运中心】
     * location :
     */

    private List<DataBean> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNu() {
        return nu;
    }

    public void setNu(String nu) {
        this.nu = nu;
    }

    public String getIscheck() {
        return ischeck;
    }

    public void setIscheck(String ischeck) {
        this.ischeck = ischeck;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getCom() {
        return com;
    }

    public void setCom(String com) {
        this.com = com;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String time;
        private String ftime;
        private String context;
        private String location;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getFtime() {
            return ftime;
        }

        public void setFtime(String ftime) {
            this.ftime = ftime;
        }

        public String getContext() {
            return context;
        }

        public void setContext(String context) {
            this.context = context;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }
    }
}
