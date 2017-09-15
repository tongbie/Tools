package com.example.myapplication.WeatherPackage;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Weather extends AppCompatActivity implements View.OnClickListener {
    TextView textView;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather);
        Button weather = (Button) findViewById(R.id.weather);
        weather.setOnClickListener(this);
        textView = (TextView) findViewById(R.id.textView);
        editText = (EditText) findViewById(R.id.editText);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads()
                .detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.weather:
                String resion = editText.getText().toString();
                String json = "http://guolin.tech/api/weather?cityid=" + getId("Provence.txt", "name", resion, "weather_id") + "&key=dfad688c28864ccda724ab55de4928cb";
                sendRequestWithOkHttp(json);
        }
    }

    //本地读取json文件
    private String getId(String filename, String findname, String name, String returnfor) {
        String id = null;
        try {
            InputStream is = getAssets().open(filename);    //此处为要加载的json文件名称
            InputStreamReader reader = new InputStreamReader(is, "GBK");
            BufferedReader bufferedReader = new BufferedReader(reader);
            StringBuffer buffer = new StringBuffer("");
            String str;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
                buffer.append("\n");
            }
            String text = buffer.toString();
            try {
                JSONArray jsonArray = new JSONArray(text);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String data = jsonObject.getString(findname);    //""内填写你要读取的数据
                    if (data.equals(name)) {
                        id = jsonObject.getString(returnfor);
                        break;
                    }
                }
            } catch (Exception e) {
                Log.d("handleCitiesResponse", e.toString());
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.d("readFromAssets", e.toString());
        }
        return id;
    }

    private void sendRequestWithOkHttp(final String json) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String massage =null;
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(json)
                            .build();
                    Response response=client.newCall(request).execute();
                    String responseData=response.body().string();
                    JSONArray jsonArray = new JSONArray(responseData);    //将服务器返回数据传入JSONArray对象
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        massage = jsonObject.getString("HeWeather");
                    }
                    textView.setText(massage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}

