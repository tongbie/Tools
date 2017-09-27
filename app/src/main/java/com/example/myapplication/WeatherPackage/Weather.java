package com.example.myapplication.WeatherPackage;

import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.dd.processbutton.iml.ActionProcessButton;
import com.example.myapplication.R;
import com.example.myapplication.UI.BackAppCompatActivity;
import com.example.myapplication.UI.GoogleCard;
import com.example.myapplication.UI.GoogleCardAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Weather extends BackAppCompatActivity implements View.OnClickListener {
    Handler WeatherUiHandler = new Handler();
    private EditText provence;
    private EditText city;
    private EditText area;
    private ListView mListView;
    private ActionProcessButton weather;
    private String weather_Id = null;
    private String[] provenceList;
    private String[] cityList;
    private String[] areaList;
    private ListView provenceListView;
    private String[] cityListView;
    private String[] areaListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather);
        selfDefinedSetActivityName("天气");
        selfDefinedSetWindowColor("#1c80f0");
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads()
                .detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
        weather = (ActionProcessButton) findViewById(R.id.weather);    //ActionProcessButton
        weather.setOnClickListener(this);
        Button backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        provence = (EditText) findViewById(R.id.provence);
        city = (EditText) findViewById(R.id.city);
        area = (EditText) findViewById(R.id.area);
        mListView = (ListView) findViewById(R.id.ListView);
        provence.setText("山东");
        city.setText("济南");
        area.setText("长清");

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.weather:
                weather.setProgress(1);
                weather.setText("Loading...");
                Thread onCLickThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String provenceId = getCityId(getWeatherGson("", ""), provence.getText().toString(), "省");
                            String cityId = getCityId(getWeatherGson("/" + provenceId, ""), city.getText().toString(), "市");
                            weather_Id = getCityId(getWeatherGson("/" + provenceId, "/" + cityId), area.getText().toString(), "区");
                            getWeatherReturn(weather_Id);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    weather.setProgress(0);
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                onCLickThread.start();
        }
    }


    //String 省Id, String 市Id
    private String getWeatherGson(String provenceId, String cityId) {
        String weatherGson = "";
        try {
            OkHttpClient weatherClient = new OkHttpClient.Builder()
                    .connectTimeout(5000, TimeUnit.SECONDS)//设置连接超时时间
                    .build();
            Request request = new Request.Builder()
                    .url("http://guolin.tech/api/china" + provenceId + cityId)
                    .build();
            Call call = weatherClient.newCall(request);
            Response response=call.execute();
            weatherGson = response.body().string();
        } catch (Exception e) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    weather.setProgress(0);
                }
            });
        }
        return weatherGson;
    }

    /* 从"guolin.tech/api/china"获取地区Id */
    /* private String getWeatherId(String 地区名, String 省/市/区) */
    private String getCityId(String weatherGson, String resionName, String resionKind) {
        String weatherId = null;
        try {
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
            Toast.makeText(this, "未查询到-" + resionKind, Toast.LENGTH_SHORT).show();
        }
        return weatherId;
    }

    /*获取天气json数据*/
    private String getWeatherReturn(String weather_Id) {
        try {
            OkHttpClient weatherReturnClient = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("http://guolin.tech/api/weather?cityid=" + weather_Id + "&key=dfad688c28864ccda724ab55de4928cb")
                    .build();
            Response response = weatherReturnClient.newCall(request).execute();
            String weatherReturnGson = response.body().string();
            Gson weatherReturn = new Gson();
            WeatherReturn weatherReturnData = weatherReturn.fromJson(weatherReturnGson, WeatherReturn.class);
            ValueRunnable valueRunnable = new ValueRunnable();
            valueRunnable.setWeatherReturn(weatherReturnData);
            Thread thread = new Thread(valueRunnable);
            WeatherUiHandler.post(thread);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error";
    }

    /* 用以实现向Runnable中传值 */
    private class ValueRunnable implements Runnable {
        private WeatherReturn weatherReturnData;

        public void setWeatherReturn(WeatherReturn weatherReturnData) {
            this.weatherReturnData = weatherReturnData;
        }

        @Override
        public void run() {
            try {
                String[] weatherReturn = new String[7];
                weatherReturn[0] = weatherReturnData.getHeWeather().get(0).getBasic().getCity().toString() + "    "
                        + weatherReturnData.getHeWeather().get(0).getDaily_forecast().get(0).getDate() + "\n天气："
                        + weatherReturnData.getHeWeather().get(0).getDaily_forecast().get(0).getCond().getTxt_d() + "\n最高温度："
                        + weatherReturnData.getHeWeather().get(0).getDaily_forecast().get(0).getTmp().getMax() + "度\n最低温度："
                        + weatherReturnData.getHeWeather().get(0).getDaily_forecast().get(0).getTmp().getMin() + "度";
                weatherReturn[1] = weatherReturnData.getHeWeather().get(0).getSuggestion().getDrsg().getTxt();
                weatherReturn[2] = "空气质量：" + weatherReturnData.getHeWeather().get(0).getSuggestion().getAir().getBrf() + "，"
                        + weatherReturnData.getHeWeather().get(0).getSuggestion().getAir().getTxt();
                weatherReturn[3] = "出行建议："
                        + weatherReturnData.getHeWeather().get(0).getSuggestion().getSport().getBrf() + "，"
                        + weatherReturnData.getHeWeather().get(0).getSuggestion().getSport().getTxt();
                weatherReturn[4] = "未来天气：";
                for (int i = 5; i < 7; i++) {
                    if (i == 5) {
                        weatherReturn[i] = "明天    ";
                    } else if (i == 6) {
                        weatherReturn[i] = "后天    ";
                    }
                    weatherReturn[i] += weatherReturnData.getHeWeather().get(0).getDaily_forecast().get(i - 4).getDate() + "\n"
                            + weatherReturnData.getHeWeather().get(0).getDaily_forecast().get(i - 4).getCond().getTxt_d() + "\n" + "最高温度："
                            + weatherReturnData.getHeWeather().get(0).getDaily_forecast().get(i - 4).getTmp().getMax() + "度" + "   " + "最低温度："
                            + weatherReturnData.getHeWeather().get(0).getDaily_forecast().get(i - 4).getTmp().getMin() + "度";
                }
                setItems(weatherReturn, 7);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /*用来显示List*/
    /* private void setItems(String[] 字符串数组, int 数组长度);*/
    private void setItems(String[] weatherData, int itemNumber) {
        List<GoogleCard> mCards = new ArrayList<GoogleCard>();
        for (int i = 0; i < itemNumber; i++) {
            GoogleCard mCard = new GoogleCard(weatherData[i], R.drawable.empty);
            mCards.add(mCard);
        }
        GoogleCardAdapter mAdapter = new GoogleCardAdapter(this, mCards);
        mListView.setAdapter(mAdapter);
    }

    /* 本地读取json文件 */
    /* private String getId(String 文件名, String  要读取的数据, String  用以比较的字符串, String  忘了);*/
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

