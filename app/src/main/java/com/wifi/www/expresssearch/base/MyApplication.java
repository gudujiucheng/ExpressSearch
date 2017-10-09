package com.wifi.www.expresssearch.base;

import android.app.Application;

import com.lzy.okgo.OkGo;

/**
 * 标题:
 * 描述:
 * 作者: 张灿
 * 创建时间: 2017/9/28 10:49
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        OkGo.getInstance().init(this);
    }
}
