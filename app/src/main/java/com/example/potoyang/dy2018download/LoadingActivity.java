package com.example.potoyang.dy2018download;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


/**
 * Created by 71579 on 2016/5/22.
 */

public class LoadingActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int SHOW_RESPONSE = 0;

    private static final String URLSTR = "http://www.dy2018.com";//需要解析的网址
    private String resultData = "";
    private Button btn_get, btn_send;

    //子线程通知主线程更新UI
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SHOW_RESPONSE:
                    Toast.makeText(getBaseContext(), "点击Send 进入下一个页面", Toast.LENGTH_LONG).show();
                    LogUtil.d("TAG", "获取成功");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        btn_get = (Button) findViewById(R.id.btn_get);
        btn_send = (Button) findViewById(R.id.btn_send);

        btn_get.setOnClickListener(this);
        btn_send.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_get:
                String address = URLSTR;
                //调用HttpUtil解析网页，并使用HttpCallbaxkListener接口返回结果,赋予resultData
                HttpUtil.sendHttpRequest(address, new HttpUtil.HttpCallbackListener() {
                    @Override
                    public void onFinish(String response) {
                        Message message = new Message();
                        message.what = SHOW_RESPONSE;
                        handler.sendMessage(message);
                        resultData = response;
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
                break;
            case R.id.btn_send:
                //启动MainActivity,并通过"DATA"键将resultData传递过去
                if (!resultData.equals("")) {
                    Intent intent = new Intent();
                    intent.putExtra("DATA", resultData);
                    intent.setClass(LoadingActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                break;
            default:
                break;
        }
    }
}
