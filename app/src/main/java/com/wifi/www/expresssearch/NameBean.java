package com.wifi.www.expresssearch;

import java.util.List;

/**
 * 标题:
 * 描述:
 * 作者: 张灿
 * 创建时间: 2017/9/22 11:45
 */

public class NameBean {

    /**
     * EBusinessID : 1257021
     * Success : true
     * LogisticCode : 3967950525457
     * Shippers : [{"ShipperCode":"YD","ShipperName":"韵达快递"}]
     */

    private String EBusinessID;
    private boolean Success;//是否成功
    private String LogisticCode;//物流单号
    /**
     * ShipperCode : YD
     * ShipperName : 韵达快递
     */

    private List<ShippersBean> Shippers;//可能会匹配到多种物流

    public String getEBusinessID() {
        return EBusinessID;
    }

    public void setEBusinessID(String EBusinessID) {
        this.EBusinessID = EBusinessID;
    }

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean Success) {
        this.Success = Success;
    }

    public String getLogisticCode() {
        return LogisticCode;
    }

    public void setLogisticCode(String LogisticCode) {
        this.LogisticCode = LogisticCode;
    }

    public List<ShippersBean> getShippers() {
        return Shippers;
    }

    public void setShippers(List<ShippersBean> Shippers) {
        this.Shippers = Shippers;
    }

    public static class ShippersBean {
        private String ShipperCode;//快递公司编码
        private String ShipperName;//快递名称

        public String getShipperCode() {
            return ShipperCode;
        }

        public void setShipperCode(String ShipperCode) {
            this.ShipperCode = ShipperCode;
        }

        public String getShipperName() {
            return ShipperName;
        }

        public void setShipperName(String ShipperName) {
            this.ShipperName = ShipperName;
        }
    }
}
