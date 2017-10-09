package com.wifi.www.expresssearch.bean;

/**
 * 标题:
 * 描述:
 * 作者: 张灿
 * 创建时间: 2017/9/22 11:57
 */

public class MyModel {
    private int repeatCount;
    private String exNumber;//编号
    /**
     * 快递鸟
     */
    private ItemBean kuaidiniao;

    /**
     * 快递100
     */
    private ItemBean kuaidi100;

    public int getRepeatCount() {
        return repeatCount;
    }

    public void setRepeatCount(int repeatCount) {
        this.repeatCount = repeatCount;
    }

    public String getExNumber() {
        return exNumber;
    }

    public void setExNumber(String exNumber) {
        this.exNumber = exNumber;
    }

    public ItemBean getKuaidiniao() {
        return kuaidiniao;
    }

    public void setKuaidiniao(ItemBean kuaidiniao) {
        this.kuaidiniao = kuaidiniao;
    }

    public ItemBean getKuaidi100() {
        return kuaidi100;
    }

    public void setKuaidi100(ItemBean kuaidi100) {
        this.kuaidi100 = kuaidi100;
    }


    @Override
    public boolean equals(Object o) {
        if (o instanceof MyModel) {
            return ((MyModel) o).getExNumber().equals(exNumber);
        }
        return super.equals(o);
    }
}
