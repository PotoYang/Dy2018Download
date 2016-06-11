package com.example.potoyang.dy2018download;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by 71579 on 2016/5/27.
 */

public class HttpUtil {

    public static void sendHttpRequest(final String address, final HttpCallbackListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection conn = null; //连接对象
                InputStream is = null;
                try {
                    URL url = new URL(address); //URL对象
                    conn = (HttpURLConnection) url.openConnection(); //使用URL打开一个链接
                    conn.setRequestMethod("GET"); //使用get请求
                    conn.setConnectTimeout(8000);
                    conn.setReadTimeout(8000);
                    is = conn.getInputStream();   //获取输入流，此时才真正建立链接

                    //在输入流的时候读取的编码即为源网页的编码，这样中文显示就不会乱码
                    InputStreamReader isr = new InputStreamReader(is, "gb2312");
                    BufferedReader bufferReader = new BufferedReader(isr);
                    String inputLine = "";
                    String response = "";
                    while ((inputLine = bufferReader.readLine()) != null) {
                        response += inputLine + "\n";
                    }
                    if (listener != null) {
                        listener.onFinish(response);
                    }
                } catch (Exception e) {
                    if (listener != null) {
                        listener.onError(e);
                    }
                } finally {
                    if (conn != null) {
                        conn.disconnect();
                    }
                }
            }
        }).start();
    }

    public interface HttpCallbackListener {
        void onFinish(String response);

        void onError(Exception e);
    }
}
