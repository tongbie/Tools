package com.example.myapplication.WeatherPackage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Weather extends AppCompatActivity implements View.OnClickListener {
    TextView textView;
    EditText provence;
    EditText city;
    EditText area;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather);
        Button weather = (Button) findViewById(R.id.weather);
        weather.setOnClickListener(this);
        textView = (TextView) findViewById(R.id.textView);
        provence = (EditText) findViewById(R.id.provence);
        city = (EditText) findViewById(R.id.city);
        area = (EditText) findViewById(R.id.area);
        /*StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads()
                .detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());*/
        provence.setText("山东");
        city.setText("济南");
        area.setText("长清");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.weather:
                String provenceId = getCityId(provence.getText().toString(), "", "", "省");
                String cityId = getCityId(city.getText().toString(), "/" + provenceId, "", "市");
                String weather_Id = getCityId(area.getText().toString(), "/" + provenceId, "/" + cityId, "区");
                //textView.setText(weather_Id);
                getWeatherReturn(weather_Id);
        }
    }

    private String getCityId(String resionName, String provenceId, String cityId, String resionKind) {
        //private String getWeatherId(String 地区名, String 省Id, String 市Id, String 省/市/区)
        String weatherId = null;
        try {
            OkHttpClient weatherClient = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("http://guolin.tech/api/china" + provenceId + cityId)
                    .build();
            Response response = weatherClient.newCall(request).execute();
            String weatherGson = response.body().string();
            Gson provence = new Gson();
            if (resionKind.equals("省") || resionKind.equals("市")) {
                List<CityId> weatherData = provence.fromJson(weatherGson, new TypeToken<List<CityId>>() {
                }.getType());
                for (int i = 0; i < weatherData.size(); i++) {
                    if (weatherData.get(i).getName().equals(resionName)) {
                        weatherId = String.valueOf(weatherData.get(i).getId());
                        return weatherId;
                    }
                }
            } else if (resionKind.equals("区")) {
                List<Weather_Id> weatherIdData = provence.fromJson(weatherGson, new TypeToken<List<Weather_Id>>() {
                }.getType());
                for (int i = 0; i < weatherIdData.size(); i++) {
                    if (weatherIdData.get(i).getName().equals(resionName)) {
                        weatherId = String.valueOf(weatherIdData.get(i).getWeather_id());
                        return weatherId;
                    }
                }
            }
        } catch (Exception e) {
            Toast.makeText(this, "发生错误-" + resionKind, Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(this, "未查询到-" + resionKind, Toast.LENGTH_SHORT).show();
        return "error";
    }

    private String getWeatherReturn(String weather_Id) {
        try {
            OkHttpClient weatherReturnClient = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("http://guolin.tech/api/weather?cityid=" + weather_Id + "&key=dfad688c28864ccda724ab55de4928cb")
                    .build();
            Response response = weatherReturnClient.newCall(request).execute();
            String weatherReturnGson = response.body().string();
            Gson weatherReturn = new Gson();
            WeatherReturn weatherReturnData = weatherReturn.fromJson(weatherReturnGson, new TypeToken<List<WeatherReturn>>() {
            }.getType());
//            String string=weatherReturnData.get(1).toString();
//            textView.setText(string);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //本地读取json文件
    private String getId(String filename, String findname, String name, String returnfor) {
        String id = null;
        String catchname;
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

            Gson gson = new Gson();
            List<CityId> weatherIDs = gson.fromJson(text, new TypeToken<List<CityId>>() {
            }.getType());
            for (CityId cityId : weatherIDs) {
                catchname = cityId.getName();
                if (catchname.equals(name)) {
                    id = cityId.getWeather_id();
                    return id;
                }
            }
            /*try {
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
            }*/
        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.d("readFromAssets", e.toString());
        }
        Toast.makeText(this, "未找到该城市", Toast.LENGTH_SHORT).show();
        return "CN101120102";
    }
}

