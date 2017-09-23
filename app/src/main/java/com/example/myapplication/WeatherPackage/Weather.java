package com.example.myapplication.WeatherPackage;

import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
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

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Weather extends BackAppCompatActivity implements View.OnClickListener {
    Handler WeatherUiHandler = new Handler();
    TextView textView;
    EditText provence;
    EditText city;
    EditText area;
    private ListView mListView;
    ActionProcessButton weather;
    String weather_Id = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather);
        selfDefinedSetActivityName("天气");
        selfDefinedSetWindowColor("#1c80f0");
        weather = (ActionProcessButton) findViewById(R.id.weather);    //ActionProcessButton
        weather.setOnClickListener(this);
        Button backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        textView = (TextView) findViewById(R.id.textView);
        provence = (EditText) findViewById(R.id.provence);
        city = (EditText) findViewById(R.id.city);
        area = (EditText) findViewById(R.id.area);

        mListView = (ListView) findViewById(R.id.ListView);
        GoogleCardAdapter mAdapter = new GoogleCardAdapter(this, getItems());
        mListView.setAdapter(mAdapter);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads()
                .detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
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
                            if (provence.getText().equals("北京") || provence.getText().equals("天津") || provence.getText().equals("上海")) {
                                weather_Id = getCityId(provence.getText().toString(), "", "", "省");
                            } else {
                                String provenceId = getCityId(provence.getText().toString(), "", "", "省");
                                String cityId = getCityId(city.getText().toString(), "/" + provenceId, "", "市");
                                weather_Id = getCityId(area.getText().toString(), "/" + provenceId, "/" + cityId, "区");
                            }
                            getWeatherReturn(weather_Id);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    weather.setProgress(0);
                                }
                            });
                        } catch (Exception e) {
                            Toast.makeText(Weather.this, "未查询到结果", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                onCLickThread.start();
        }
    }

    private List<GoogleCard> getItems() {
        List<GoogleCard> mCards = new ArrayList<GoogleCard>();
        for (int i = 0; i < 4; i++) {
            GoogleCard mCard = new GoogleCard("这是第" + (i + 1) + "张卡片", R.drawable.line);
            mCards.add(mCard);
        }
        return mCards;
    }

    private int getResource(int Index) {
        int mResult = 0;
        switch (Index % 2) {
            case 0:
                mResult = R.drawable.circularbead;
                break;
            case 1:
                mResult = R.drawable.circularbead;
                break;
        }
        return mResult;
    }

    /* 从"guolin.tech/api/china"获取地区Id */
    /* private String getWeatherId(String 地区名, String 省Id, String 市Id, String 省/市/区) */
    private String getCityId(String resionName, String provenceId, String cityId, String resionKind) {
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
            Toast.makeText(this, "未查询到-" + resionKind, Toast.LENGTH_SHORT).show();
        }
        return "error";
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
                String[] weatherReturn = new String[4];
                weatherReturn[0] = weatherReturnData.getHeWeather().get(0).getBasic().getCity().toString() + "\n"
                        + weatherReturnData.getHeWeather().get(0).getSuggestion().getDrsg().getTxt().toString() + "\n\n";
                for (int i = 0; i < 3; i++) {
                    weatherReturn[i + 1] += weatherReturnData.getHeWeather().get(0).getDaily_forecast().get(i).getDate() + "\n"
                            + weatherReturnData.getHeWeather().get(0).getDaily_forecast().get(i).getCond().getTxt_d() + "\n" + "最高温度："
                            + weatherReturnData.getHeWeather().get(0).getDaily_forecast().get(i).getTmp().getMax() + "度" + "  " + "最低温度："
                            + weatherReturnData.getHeWeather().get(0).getDaily_forecast().get(i).getTmp().getMin() + "度" + "\n\n";
                }
                textView.setText(weatherReturn[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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

