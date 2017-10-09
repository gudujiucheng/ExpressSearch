package com.wifi.www.expresssearch;

import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wifi.www.expresssearch.base.MyCallBack;
import com.wifi.www.expresssearch.bean.ItemBean;
import com.wifi.www.expresssearch.bean.MyModel;
import com.wifi.www.expresssearch.bean.kuaidi100.QueryExpressMsgResp;
import com.wifi.www.expresssearch.bean.kuaidi100.QueryExpressNameCodeResp;
import com.wifi.www.expresssearch.kuaidi100.QueryExpressCodeAPI;
import com.wifi.www.expresssearch.kuaidi100.QueryExpressMsgAPI;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText mEtInput;

    private ExecutorService mThreadPool;//线程池
    private Map<String, Future<?>> mTaskTags = new LinkedHashMap<String, Future<?>>();
    private List<MyModel> mList = new ArrayList<MyModel>();
    private RecyclerView mRecyclerView;
    private BaseQuickAdapter mAdapter;

    private String CAN_NOT_GET_CODE_ERROR = "CAN_NOT_GET_CODE_ERROR";
    private String NO_EXPRESS_ERROR = "NO_EXPRESS_ERROR";
    private int mType;//0 查询快递  1 查询重复


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        setAdapter();
        EventBus.getDefault().register(this);
    }

    private void initView() {
        mEtInput = (EditText) findViewById(R.id.editText);
        findViewById(R.id.rtv_search).setOnClickListener(this);
        findViewById(R.id.rtv_clear).setOnClickListener(this);
        findViewById(R.id.rtv_search_02).setOnClickListener(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
    }

    private void setAdapter() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter = new BaseQuickAdapter<MyModel, BaseViewHolder>(R.layout.item_list, mList) {
            @Override
            protected void convert(BaseViewHolder helper, final MyModel item) {
                helper.setOnClickListener(R.id.tv_copy, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                        // 将文本内容放到系统剪贴板里。
                        cm.setText(item.getExNumber());
                        Toast.makeText(MainActivity.this, "复制成功！", Toast.LENGTH_SHORT).show();
                    }
                });
                ((TextView) helper.getView(R.id.tv_msg)).setTextColor(Color.BLUE);
                ItemBean kuaidi100 = item.getKuaidi100();
                ItemBean kuaidiniao = item.getKuaidiniao();
                if (kuaidiniao != null) {
                    helper.getView(R.id.rl_kdn).setVisibility(View.VISIBLE);
                    if (CAN_NOT_GET_CODE_ERROR.equals(kuaidiniao.getExCode())) {//无法识别物流名称
                        helper.setText(R.id.tv_name, "物流名称：" + "无法识别此单号归属什么快递，请手动复制查询！！！");
                        helper.setText(R.id.tv_msg, "物流跟进信息：" + "请手动查询");
                        ((TextView) helper.getView(R.id.tv_msg)).setTextColor(Color.RED);
                    } else {
                        helper.setText(R.id.tv_name, "物流名称：" + kuaidiniao.getExName());
                        if (NO_EXPRESS_ERROR.equals(kuaidiniao.getMsg())) {//无物流跟进
                            ((TextView) helper.getView(R.id.tv_msg)).setTextColor(Color.RED);
                            helper.setText(R.id.tv_msg, "物流跟进信息：" + "暂无物流跟进");
                        } else {
                            helper.setText(R.id.tv_msg, "物流跟进信息：" + kuaidiniao.getMsg());
                        }
                        if (kuaidiniao.getMsg() != null && (kuaidiniao.getMsg().contains("已揽收") || kuaidiniao.getMsg().contains("已收件"))) {
                            ((TextView) helper.getView(R.id.tv_msg)).setTextColor(Color.RED);
                        }
                    }
                } else {
                    helper.getView(R.id.rl_kdn).setVisibility(View.GONE);
                }

                if (kuaidi100 != null) {
                    helper.getView(R.id.rl_kd100).setVisibility(View.VISIBLE);
                    if (CAN_NOT_GET_CODE_ERROR.equals(kuaidi100.getExCode())) {//无法识别物流名称
                        helper.setText(R.id.tv_name_100, "物流名称：" + "无法识别此单号归属什么快递，请手动复制查询！！！");
                        helper.setText(R.id.tv_msg_100, "物流跟进信息：" + "请手动查询");
                        ((TextView) helper.getView(R.id.tv_msg_100)).setTextColor(Color.RED);
                    } else {
                        helper.setText(R.id.tv_name_100, "物流名称：" + kuaidi100.getExName());
                        if (NO_EXPRESS_ERROR.equals(kuaidi100.getMsg())) {//无物流跟进
                            ((TextView) helper.getView(R.id.tv_msg_100)).setTextColor(Color.RED);
                            helper.setText(R.id.tv_msg_100, "物流跟进信息：" + "暂无物流跟进");
                        } else {
                            helper.setText(R.id.tv_msg_100, "物流跟进信息：" + kuaidi100.getMsg());
                        }
                        if (kuaidi100.getMsg() != null && (kuaidi100.getMsg().contains("已揽收") || kuaidi100.getMsg().contains("已收件"))) {
                            ((TextView) helper.getView(R.id.tv_msg_100)).setTextColor(Color.RED);
                        }
                    }
                } else {
                    helper.getView(R.id.rl_kd100).setVisibility(View.GONE);
                }

                if(mType == 0){
                    helper.setText(R.id.tv_num, "物流单号：" + item.getExNumber());
                }else{
                    helper.setText(R.id.tv_num, item.getExNumber()+ "  共出现"+item.getRepeatCount()+"次  ");
                }
            }

        });

    }

    private void initData() {
        if (mThreadPool == null) {
            // 最多同时允许的线程数为3个
            mThreadPool = Executors.newFixedThreadPool(8);
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rtv_search://查询快递跟进
                mType = 0;
                String s = mEtInput.getText().toString();
                if (TextUtils.isEmpty(s)) {
                    Toast.makeText(this, "请输入单号", Toast.LENGTH_SHORT).show();
                    return;
                }
                mList.clear();
                mTaskTags.clear();
                if (mAdapter != null) {
                    mAdapter.notifyDataSetChanged();
                }
                getExCode(s);
                break;
            case R.id.rtv_clear://清空数据
                mType = 0;
                mList.clear();
                mTaskTags.clear();
                mEtInput.setText("");
                if (mAdapter != null) {
                    mAdapter.notifyDataSetChanged();
                }
                break;
            case R.id.rtv_search_02://查询重复
                mType = 1;
                mList.clear();
                String input = mEtInput.getText().toString();
                if (TextUtils.isEmpty(input)) {
                    Toast.makeText(this, "请输入数据", Toast.LENGTH_SHORT).show();
                    return;
                }
                getRepeat(input);
                break;
        }
    }

    /**
     * 获取快递名称标示
     *
     * @param s
     */
    private void getExCode(String s) {
        String[] express = s.split("\n");
        for (int i = 0; i < express.length; i++) {
            String expressItem = express[i].trim();
            Log.e("Test", expressItem);
            if (!TextUtils.isEmpty(expressItem)) {
                MyModel model = new MyModel();
                model.setExNumber(expressItem);
                mList.add(model);
                mThreadPool.submit(new MyTask(expressItem, mList.size()-1));
                mThreadPool.submit(new My100Task(expressItem, mList.size()-1));
            }
        }
    }


    private void getRepeat(String s) {
        List<String> list = new ArrayList<>();
        String[] msg = s.split("\n");
        for (int i = 0; i < msg.length; i++) {
            String expressItem = msg[i].trim();
            if(!TextUtils.isEmpty(expressItem)){
                if(!list.contains(expressItem)){
                    list.add(expressItem);
                }else{
                    MyModel model = new MyModel();
                    model.setExNumber(expressItem);
                    if(mList.contains(model)){
                        for (int j = 0; j <mList.size() ; j++) {
                            String temp = mList.get(j).getExNumber();
                            if(expressItem.equals(temp)){
                                mList.get(j).setRepeatCount(mList.get(j).getRepeatCount()+1);
                            }
                        }

                    }else{
                        model.setRepeatCount(1);
                        mList.add(model);
                    }

                }
            }

        }
        mAdapter.notifyDataSetChanged();
    }

    class MyTask implements Runnable {

        private String expressItem;
        private int position;

        public MyTask(String expressItem, int position) {
            this.expressItem = expressItem;
            this.position = position;
        }

        @Override
        public void run() {
            KdApiOrderDistinguish api = new KdApiOrderDistinguish();
            try {
                String result = api.getOrderTracesByJson(expressItem);
                Log.e("Test", result);
                NameBean resp = JSON.parseObject(result, NameBean.class);
                List<NameBean.ShippersBean> list = resp.getShippers();
                MyModel myModel = mList.get(position);
                myModel.setKuaidiniao(new ItemBean());
                if (list != null && list.size() > 0) {
                    String name = list.get(0).getShipperName();
                    String code = list.get(0).getShipperCode();
                    myModel.getKuaidiniao().setExName(name);
                    myModel.getKuaidiniao().setExCode(code);
                } else {
                    myModel.getKuaidiniao().setExCode(CAN_NOT_GET_CODE_ERROR);
                }

                if (CAN_NOT_GET_CODE_ERROR.equals(myModel.getKuaidiniao().getExCode())) {
                    Log.e("Test", "无法识别单号");
                    EventBus.getDefault().post(new MyEvent(position));
                    return;
                }
                EventBus.getDefault().post(new MyEvent(position));
                //                Future<?> futrue = mTaskTags.get(myModel.getKuaidiniao().getExCode() + myModel.getExNumber());
                //                if (futrue != null && !futrue.isCancelled() && !futrue.isDone()) {//线程不为空，没有取消，没有完成
                //                    System.out.println("取消 任务");
                //                    // 线程正在执行
                //                    futrue.cancel(true);//取消线程，防止多个线程同时开启，造成复用图片错乱的问题
                //                    futrue = null;
                //                }
                //                futrue = mThreadPool.submit(new MyTask02(myModel.getKuaidiniao().getExCode(), expressItem, position));
                //                mTaskTags.put(myModel.getKuaidiniao().getExCode() + myModel.getExNumber(), futrue);

                TrackQueryAPI api02 = new TrackQueryAPI();
                try {
                    String result02 = api02.getOrderTracesByJson(myModel.getKuaidiniao().getExCode(), expressItem);
                    ExpressMsgResp resp02 = JSON.parseObject(result02, ExpressMsgResp.class);
                    List<ExpressMsgResp.TracesBean> list02 = resp02.getTraces();
                    String msg;
                    if (list02 != null && list02.size() > 0) {
                        msg = list02.get(list02.size() - 1).getAcceptTime() + "\n" + list02.get(list02.size() - 1).getAcceptStation();
                    } else {
                        msg = NO_EXPRESS_ERROR;
                    }
                    mList.get(position).getKuaidiniao().setMsg(msg);
                    Log.e("Test", result);
                    EventBus.getDefault().post(new MyEvent(position));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    class My100Task implements Runnable {

        private String expressItem;
        private int position;

        public My100Task(String expressItem, int position) {
            this.expressItem = expressItem;
            this.position = position;
        }

        @Override
        public void run() {
            QueryExpressCodeAPI.queryExpressName(expressItem, new MyCallBack() {
                @Override
                public void onCallBack(String response) {
                    final MyModel myModel = mList.get(position);
                    myModel.setKuaidi100(new ItemBean());
                    QueryExpressNameCodeResp resp = JSON.parseObject(response, QueryExpressNameCodeResp.class);
                    if (resp != null & resp.getAuto() != null && resp.getAuto().size() > 0) {
                        List<QueryExpressNameCodeResp.AutoBean> expressList = resp.getAuto();
                        String expressCode = expressList.get(0).getComCode();
                        myModel.getKuaidi100().setExName(expressCode);

                        //请求具体信息
                        QueryExpressMsgAPI.queryExpressName(expressCode, expressItem, new MyCallBack() {
                            @Override
                            public void onCallBack(String response) {
                                QueryExpressMsgResp resp = JSON.parseObject(response, QueryExpressMsgResp.class);
                                List<QueryExpressMsgResp.DataBean> msgList = resp.getData();
                                String msg;
                                if (msgList != null && msgList.size() > 0) {
                                    msg = msgList.get(0).getTime() + "\n" + msgList.get(0).getContext();
                                } else {
                                    msg = NO_EXPRESS_ERROR;
                                }
                                mList.get(position).getKuaidi100().setMsg(msg);
                                EventBus.getDefault().post(new MyEvent(position));
                            }
                        });
                    } else {
                        mList.get(position).getKuaidi100().setExCode(CAN_NOT_GET_CODE_ERROR);
                        Log.e("Test", "无法识别单号");
                    }
                    EventBus.getDefault().post(new MyEvent(position));

                }
            });


        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    class MyEvent {
        public MyEvent(int position) {
            this.position = position;
        }

        int position;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MyEvent event) {
        if (mAdapter != null && event != null) {
            mAdapter.notifyItemChanged(event.position);
        }

    }


}
