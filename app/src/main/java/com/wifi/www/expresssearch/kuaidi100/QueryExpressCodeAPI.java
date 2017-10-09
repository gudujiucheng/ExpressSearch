package com.wifi.www.expresssearch.kuaidi100;

import android.util.Log;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.wifi.www.expresssearch.base.MyCallBack;

/**
 * 标题:
 * 描述:
 * 作者: 张灿
 * 创建时间: 2017/9/28 10:51
 */

public class QueryExpressCodeAPI {

    public static void queryExpressName(String number ,final MyCallBack callBack) {
        OkGo.<String>post("http://www.kuaidi100.com/autonumber/autoComNum").params("text",number).execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                Log.e("Test",response.body());
                callBack.onCallBack(response.body());
            }
        });
    }
}
