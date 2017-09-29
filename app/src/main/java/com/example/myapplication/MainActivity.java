package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication.CourseTablePackage.CourseTable;
import com.example.myapplication.UI.Fruit;
import com.example.myapplication.UI.FruitAdapter;
import com.example.myapplication.WeatherPackage.Weather;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private long mExitTime;    //返回键计时
    private List<Fruit> fruitList = new ArrayList<>();
    private Button titleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity);
        ActionBar actionBar = getSupportActionBar();    //隐藏标题栏
        if (actionBar != null) {
            actionBar.hide();
        }
        initFruits();    //MainActivity.initFruits()，用以添加子项
        FruitAdapter adapter = new FruitAdapter(MainActivity.this, R.layout.fruit_item, fruitList);    //适配器?
        ListView listView = (ListView) findViewById(R.id.listView);    //mainactivity.listView列表菜单
        listView.setAdapter(adapter);    //通过适配器为listView添加选项
        titleList = (Button) findViewById(R.id.title_list);
        titleList.setOnClickListener(this);
        Button titleMenu = (Button) findViewById(R.id.title_menu);
        titleMenu.setOnClickListener(this);
        Button notification_banner=(Button)findViewById(R.id.notification_banner);
        notification_banner.setOnClickListener(this);
        Button qrcode_banner=(Button)findViewById(R.id.qrcode_banner);
        qrcode_banner.setOnClickListener(this);
        Button weather_banner=(Button)findViewById(R.id.weather_banner);
        weather_banner.setOnClickListener(this);
        Button map_banner=(Button)findViewById(R.id.map_banner);
        map_banner.setOnClickListener(this);
        Button new_button=(Button)findViewById(R.id.new_button);
        new_button.setOnClickListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fruit fruit = fruitList.get(position);    //通过position选项获取用户点击的是哪一个子项
                switch (fruit.getName().toString()) {
                    case "计算器":
                        Intent counter = new Intent(MainActivity.this, Counter.class);
                        startActivity(counter);
                        break;
                    case "通知":
                        Intent notification = new Intent(MainActivity.this, Notification.class);
                        startActivity(notification);
                        break;
                    case "二维码":
                        Intent qrcode = new Intent(MainActivity.this, QRCode.class);
                        startActivity(qrcode);
                        break;
                    case "天气":
                        Intent weather = new Intent(MainActivity.this, Weather.class);
                        startActivity(weather);
                        break;
                    case "地图":
                        Intent map = new Intent(MainActivity.this, Map.class);
                        startActivity(map);
                        break;
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_list:
                DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
                break;
            case R.id.notification_banner:
                Intent notification_banner=new Intent(MainActivity.this,Notification.class);
                startActivity(notification_banner);
                break;
            case R.id.qrcode_banner:
                Intent qrcode_banner=new Intent(MainActivity.this,QRCode.class);
                startActivity(qrcode_banner);
                break;
            case R.id.weather_banner:
                Intent weather_banner=new Intent(MainActivity.this,Weather.class);
                startActivity(weather_banner);
                break;
            case R.id.map_banner:
                Intent map_banner=new Intent(MainActivity.this,Map.class);
                startActivity(map_banner);
                break;
            case R.id.new_button:
                Intent newActivity=new Intent(MainActivity.this,CourseTable.class);
                startActivity(newActivity);
                break;
        }
    }

    //为listView添加子项
    private void initFruits() {
        Fruit counter = new Fruit("计算器", R.drawable.counter);
        fruitList.add(counter);
        Fruit notification = new Fruit("通知", R.drawable.notification);
        fruitList.add(notification);
        Fruit qrcode = new Fruit("二维码", R.drawable.qrcode);
        fruitList.add(qrcode);
        Fruit werther = new Fruit("天气", R.drawable.weather);
        fruitList.add(werther);
        Fruit map = new Fruit("地图", R.drawable.map);
        fruitList.add(map);
    }

    //监听返回键
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //返回键
    public void exit() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            Toast.makeText(MainActivity.this, "再按一次退出", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }
}
