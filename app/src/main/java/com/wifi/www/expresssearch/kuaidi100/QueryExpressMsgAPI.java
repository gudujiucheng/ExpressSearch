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

public class QueryExpressMsgAPI {

    /**
     *
     * @param type 快递类型
     * @param postid 单号
     */
    public static void queryExpressName(String type, String postid,final MyCallBack callBack) {
        //        type=huitongkuaidi&postid=71028900523318
        OkGo.<String>get("http://www.kuaidi100.com/query").params("type", type).params("postid", postid).execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                Log.e("Test", response.body());
                callBack.onCallBack(response.body());
            }
        });
    }
}
