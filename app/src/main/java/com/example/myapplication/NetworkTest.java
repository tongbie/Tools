package com.example.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class NetworkTest extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.networktest);
        WebView webView=(WebView)findViewById(R.id.webView);
        TextView textView=(TextView)findViewById(R.id.textView) ;
        Button network=(Button)findViewById(R.id.network);
        network.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client=new OkHttpClient();    //创建OkHttpClient实例
                    //Request request= new Request.Builder().url("http://192.168.255.195:8080/Control").build();
                    //Response response=client.newCall(request).execute();    //Call对象的execute()方法 发送请求并获取服务器返回的数据
                    //
                    RequestBody requestBody=new FormBody.Builder()    //发起POST请求
                            .add("username","18340096990")
                            .add("password","123123")
                            .build();
                    Request request=new Request.Builder()
                            .url("http://192.168.255.195:8080/Control")
                            .post(requestBody)
                            .build();
                    //webView.loadUrl((URL)request.url());
                    String responsData=request.body().toString();
                    showResponse(responsData);
                    Log.e("NetworkTest",responsData);
                }catch (Exception e){

                }
            }
        };
    }

    private void showResponse(final String response){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //textView.setText(response);

            }
        });
    }
}
