package com.example.potoyang.dy2018download;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static com.example.potoyang.dy2018download.Regex.getRegexURL;

/**
 * Created by 71579 on 2016/5/21.
 */

public class ShowActivity extends AppCompatActivity {

    private static final int GET_RESPONSE = 1;
    private String data = null;
    private String URLString = null;
    private ListView lv_url;

    private ListViewAdapter adapter;
    private List<String> listURL = new ArrayList<>();

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GET_RESPONSE:
                    Toast.makeText(getBaseContext(), "点击任何一个选项就可以进行下载啦!", Toast.LENGTH_SHORT).show();
                    show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        lv_url = (ListView) findViewById(R.id.lv_url);

        Intent intent = getIntent();
        URLString = "http://www.dy2018.com".concat(intent.getStringExtra("urlStr"));

        //调用HttpUtil解析网页，并使用HttpCallbaxkListener接口返回结果,赋予resultData
        HttpUtil.sendHttpRequest(URLString, new HttpUtil.HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Message message = new Message();
                message.what = GET_RESPONSE;
                handler.sendMessage(message);
                data = response;
            }

            @Override
            public void onError(Exception e) {
            }
        });
    }

    private void show() {
        if (!data.equals("")) {
            String[] temp = getRegexURL(data).split("\n");

            for (int i = 0; i < temp.length; i++) {
                listURL.add(temp[i]);
            }

            adapter = new ListViewAdapter(getBaseContext(), listURL);
            lv_url.setAdapter(adapter);   //加载适配器

            lv_url.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String link = "ftp://".concat(listURL.get(position));
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                    intent.addCategory("android.intent.category.DEFAULT");
                    startActivity(intent);
                }
            });
        }
    }
}